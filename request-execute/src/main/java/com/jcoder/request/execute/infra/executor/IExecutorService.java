package com.jcoder.request.execute.infra.executor;

/**
 * 透传执行抽象接口
 *
 * @author Qiu
 */
public interface IExecutorService<U, T> {

    /**
     * 执行透传接口
     *
     * @param param
     * @return
     */
    U execute(T param);

}
