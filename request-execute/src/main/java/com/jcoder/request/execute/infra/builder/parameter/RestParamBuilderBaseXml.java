package com.jcoder.request.execute.infra.builder.parameter;

import com.jcoder.request.common.SetCacheEntity;
import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.execute.domain.entity.HttpParameter;
import com.jcoder.request.execute.infra.ExecuteConstants;
import com.jcoder.request.execute.infra.util.HttpMessageUtil;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 来源接口是rest，透传成了soap接口的情况，基于soap接口接收到的xml报文
 * 构建rest接口的http请求参数
 *
 * @author Qiu
 */
@Component
public class RestParamBuilderBaseXml implements IHttpParamBuilder<List<String>, SetCacheEntity> {

    private HttpMessageUtil httpMessageUtil = new HttpMessageUtil();

    @Override
    public HttpParameter builder(List<String> paramList, SetCacheEntity cacheEntity) {

        if (null == paramList || paramList.size() == 0) {
            return new HttpParameter();
        }
        /**
         * 解析xml报文获取请求参数
         */
        Map<String, Object> restParamMap;
        try {
            restParamMap = httpMessageUtil.getDataForRest(paramList);
        } catch (DocumentException e) {
            throw new CommonException("request.execute.build_xml_err", e);
        }

        //获取请求体信息
        HttpParameter httpParameter = new HttpParameter();
        /**
         * 构建请求头信息
         */
        httpParameter.setUrl(cacheEntity.getSourceRequestUrl());
        httpParameter.setMethodCode(cacheEntity.getSourceMethodCode());

        //路径参数
        if (restParamMap.containsKey(ExecuteConstants.HttpParamType.PATH_VARIABLE)) {
            httpParameter.setPathVariables((Map<String, Object>) restParamMap.get(ExecuteConstants.HttpParamType.PATH_VARIABLE));
        }

        //request parameter
        if (restParamMap.containsKey(ExecuteConstants.HttpParamType.REQUEST_PARAM)) {
            httpParameter.setRequestParams((Map<String, Object>) restParamMap.get(ExecuteConstants.HttpParamType.REQUEST_PARAM));
            //组装完整的请求路径
            String fullUrl = httpMessageUtil.buildUrl(httpParameter.getUrl(), httpParameter.getRequestParams());
            httpParameter.setUrl(fullUrl);
        }

        //收集请求头参数
        if (restParamMap.containsKey(ExecuteConstants.HttpParamType.REQUEST_HEADER)) {
            httpParameter.setHeaderParams((Map<String, String>) restParamMap.get(ExecuteConstants.HttpParamType.REQUEST_HEADER));
        }

        //收集请求体参数
        if (restParamMap.containsKey(ExecuteConstants.HttpParamType.REQUEST_BODY)) {
            httpParameter.setRequestBody(restParamMap.get(ExecuteConstants.HttpParamType.REQUEST_BODY));
        }

        /**
         * 合并默认的配置
         */
        httpParameter.mergeDefaultHeaderParam(cacheEntity.getHeaderDefaultValues())
                .mergeDefaultPathParam(cacheEntity.getPathDefaultValues())
                .mergeDefaultRequestParam(cacheEntity.getRequestDefaultValues())
                .buildUriVariables();

        return httpParameter;
    }
}
