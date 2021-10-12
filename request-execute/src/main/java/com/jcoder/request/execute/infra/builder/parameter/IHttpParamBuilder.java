package com.jcoder.request.execute.infra.builder.parameter;

import com.jcoder.request.execute.domain.entity.HttpParameter;

/**
 * @author Qiu
 */
public interface IHttpParamBuilder<T, S> {

    /**
     * 请求参数构建
     *
     * @param param
     * @param setCatch
     * @return
     */
    HttpParameter builder(T param, S setCatch);

}
