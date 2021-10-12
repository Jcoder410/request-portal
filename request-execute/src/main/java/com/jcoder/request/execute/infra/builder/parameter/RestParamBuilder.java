package com.jcoder.request.execute.infra.builder.parameter;

import com.jcoder.request.common.SetCacheEntity;
import com.jcoder.request.execute.domain.entity.HttpParameter;
import com.jcoder.request.execute.domain.entity.RestParameter;
import org.springframework.stereotype.Component;


/**
 * 来源和透传出去的接口类型都为rest的情况
 * rest接口请求参数构建
 *
 * @author Qiu
 */
@Component
public class RestParamBuilder implements IHttpParamBuilder<RestParameter, SetCacheEntity> {
    @Override
    public HttpParameter builder(RestParameter param, SetCacheEntity setCatch) {

        HttpParameter httpParameter = new HttpParameter();
        httpParameter.setUrl(setCatch.getSourceRequestUrl());
        httpParameter.setMethodCode(setCatch.getSourceMethodCode());

        httpParameter.setHeaderParams(param.getHeaderParams())
                .setPathVariables(param.getPathParams())
                .setRequestParams(param.getRequestParams())
                .setRequestBody(param.getRequestBody())
                .mergeDefaultHeaderParam(setCatch.getHeaderDefaultValues())
                .mergeDefaultPathParam(setCatch.getPathDefaultValues())
                .mergeDefaultRequestParam(setCatch.getRequestDefaultValues())
                .buildUriVariables();

        return httpParameter;
    }
}
