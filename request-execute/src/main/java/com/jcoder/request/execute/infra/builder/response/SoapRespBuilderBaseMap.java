package com.jcoder.request.execute.infra.builder.response;

import com.jcoder.request.common.SetCacheEntity;
import com.jcoder.request.execute.domain.entity.SoapResponse;
import com.jcoder.request.execute.infra.util.HttpMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * rest接口透传成soap，根据restTemplate返回的ResponseEntity，构建
 * soap接口的返回内容
 *
 * @author Qiu
 */
@Component
public class SoapRespBuilderBaseMap implements IResponseBuilder<ResponseEntity, SetCacheEntity, SoapResponse> {

    private HttpMessageUtil httpMessageUtil = new HttpMessageUtil();

    @Override
    public SoapResponse builder(ResponseEntity returnContext, SetCacheEntity setCatch) {

        /**
         * 提取返回数据
         */
        List<String> dataList = httpMessageUtil.getDataFromRestResponse(returnContext.getBody(),
                setCatch.getListNodeName());

        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setStatusCode("S");
        soapResponse.payload = dataList;

        if (returnContext.getStatusCode() != HttpStatus.OK) {
            soapResponse.setStatusCode("E");
        }

        return soapResponse;
    }
}
