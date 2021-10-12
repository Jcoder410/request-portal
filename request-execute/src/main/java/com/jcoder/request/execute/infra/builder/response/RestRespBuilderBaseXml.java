package com.jcoder.request.execute.infra.builder.response;

import com.jcoder.request.common.SetCacheEntity;
import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.execute.infra.util.HttpMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * soap接口透透传成rest接口的返回结果构建，需要解析返回的xml字符串报文
 * 并转换成Map或者List<Map>进行返回
 *
 * @author Qiu
 */
@Component
public class RestRespBuilderBaseXml implements IResponseBuilder<ResponseEntity, SetCacheEntity, ResponseEntity> {

    private HttpMessageUtil httpMessageUtil = new HttpMessageUtil();

    @Override
    public ResponseEntity builder(ResponseEntity returnContext, SetCacheEntity setCatch) {

        //提取xml字符串
        String xmlData = (String) returnContext.getBody();
        if (StringUtils.isEmpty(xmlData)) {
            return ResponseEntity.ok("");
        }

        //解析获得数据
        List<Object> responseData;
        try {
            responseData = httpMessageUtil.extractDataFromXml(xmlData, setCatch.getReturnDataNode());
        } catch (DocumentException e) {
            throw new CommonException("request.execute.build_xml_err", e);
        }

        /**
         * 如果设置的内容，返回结果不是集合类型，则获取解析结果中的第一个数据，否则直接返回集合内容
         */
        if (responseData.size() > 0 && !setCatch.getReturnListFlag()) {
            return ResponseEntity.ok(responseData.get(0));
        }
        return ResponseEntity.ok(responseData);
    }
}
