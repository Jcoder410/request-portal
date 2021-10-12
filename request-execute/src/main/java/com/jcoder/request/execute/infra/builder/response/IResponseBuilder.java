package com.jcoder.request.execute.infra.builder.response;

/**
 * 请求返回内容构建:
 * 用于处理返回后的数据, 如将rest接口返回的数据组装成soap接口需要返回的数据
 *
 * @author Qiu
 */
public interface IResponseBuilder<T, S, R> {

    /**
     * 构建返回内容
     *
     * @param returnContext
     * @param setCatch
     * @return
     */
    R builder(T returnContext, S setCatch);

}
