package com.jcoder.request.execute.app.service;

import com.jcoder.request.execute.domain.entity.RequestRegistry;

/**
 * 用于请求url的注册和移除
 *
 * @author Qiu
 */
public interface IRequestRegistryService {

    /**
     * 注册url
     *
     * @param requestRegistry
     */
    void requestRegistry(RequestRegistry requestRegistry);

    /**
     * 移除url
     *
     * @param requestRegistry
     */
    void removeRegistry(RequestRegistry requestRegistry);

}
