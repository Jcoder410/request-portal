package com.jcoder.request.execute.infra.builder.parameter;

import com.jcoder.request.common.SetCacheEntity;
import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.execute.domain.entity.HttpParameter;
import com.jcoder.request.execute.domain.entity.RestParameter;
import com.jcoder.request.execute.infra.util.HttpMessageUtil;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

/**
 * 来源接口是soap，透传成了rest接口的情况，基于rest接口接收到的报文
 * 构建soap接口的http请求参数
 *
 * @author Qiu
 */
@Component
public class SoapParamBuilderBaseMap implements IHttpParamBuilder<RestParameter, SetCacheEntity> {

    private HttpMessageUtil httpMessageUtil = new HttpMessageUtil();

    @Override
    public HttpParameter builder(RestParameter param, SetCacheEntity setCatch) {

        HttpParameter httpParameter = new HttpParameter();
        httpParameter.setUrl(setCatch.getSourceRequestUrl());
        httpParameter.setMethodCode(setCatch.getSourceMethodCode());

        httpParameter.setHeaderParams(param.getHeaderParams())
                .setPathVariables(param.getPathParams())
                .setRequestParams(param.getRequestParams())
                .setReturnType(String.class)
                .mergeDefaultHeaderParam(setCatch.getHeaderDefaultValues())
                .mergeDefaultPathParam(setCatch.getPathDefaultValues())
                .mergeDefaultRequestParam(setCatch.getRequestDefaultValues())
                .buildUriVariables();

        String soapBody = buildRequestBody(setCatch, param.getRequestBody());
        httpParameter.setRequestBody(soapBody);

        return httpParameter;
    }

    /**
     * 构建soap请求的请求体
     *
     * @param cacheEntity
     * @param requestBody
     * @return
     */
    private String buildRequestBody(SetCacheEntity cacheEntity, Object requestBody) {

        if (requestBody == null) {
            return "";
        }

        /**
         * 构建soap请求参数报文
         */
        try {
            String soapParam = httpMessageUtil.buildSoapRequestParam(cacheEntity.getSoapParamTemplate(),
                    cacheEntity.getSoapParamNodeName(),
                    cacheEntity.getListNodeName(),
                    requestBody);
            return soapParam;
        } catch (DocumentException e) {
            throw new CommonException("request.execute.build_xml_err", e);
        }
    }
}
