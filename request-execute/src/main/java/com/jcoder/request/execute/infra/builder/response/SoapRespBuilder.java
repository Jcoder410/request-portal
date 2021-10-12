package com.jcoder.request.execute.infra.builder.response;

import com.jcoder.request.common.SetCacheEntity;
import com.jcoder.request.execute.domain.entity.SoapResponse;
import com.jcoder.request.execute.infra.ExecuteConstants;
import com.jcoder.request.execute.infra.util.HttpMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * soap接口透传成soap，根据restTemplate返回的ResponseEntity，构建
 * soap接口的返回内容
 *
 * @author Qiu
 */
@Component
public class SoapRespBuilder implements IResponseBuilder<ResponseEntity, SetCacheEntity, SoapResponse> {

    private HttpMessageUtil httpMessageUtil = new HttpMessageUtil();

    @Override
    public SoapResponse builder(ResponseEntity returnContext, SetCacheEntity setCatch) {

        SoapResponse soapResponse = new SoapResponse();

        if (returnContext.getStatusCode() == HttpStatus.OK) {
            soapResponse.setStatusCode("S");
            /**
             * 如果设置了数据提取节点, 则进行提取; 否则按原样返回, 不做任何出路
             */
            if (StringUtils.isEmpty(setCatch.getReturnDataNode())) {
                soapResponse.payload.add((String) returnContext.getBody());
            } else {
                soapResponse.payload = httpMessageUtil.getDataFromSoapResponse((String) returnContext.getBody(),
                        setCatch.getReturnDataNode(),
                        ExecuteConstants.ExtractType.XML_NODE);
            }
        } else {
            soapResponse.setStatusCode("E");
            if (StringUtils.isEmpty(setCatch.getReturnErrNode())) {
                soapResponse.setMessage((String) returnContext.getBody());
            } else {
                List<String> errorList = httpMessageUtil.getDataFromSoapResponse((String) returnContext.getBody(),
                        setCatch.getReturnErrNode(),
                        ExecuteConstants.ExtractType.XML_CONTENT);

                StringBuilder errorMsg = new StringBuilder("");
                for (String error : errorList) {
                    errorMsg.append(error);
                }
                soapResponse.setMessage(errorMsg.toString());
            }
        }
        return soapResponse;
    }
}
