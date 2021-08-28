package com.jcoder.request.execute.app.service;


import com.jcoder.request.common.RequestCacheEntity;

/**
 * 获取请求注册信息
 *
 * @author Qiu
 */
public interface IRequestInfoService {

    /**
     * 获取请求的注册信息
     *
     * @param requestCode
     * @return
     */
    RequestCacheEntity getRequestInfo(String requestCode);

}
