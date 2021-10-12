package com.jcoder.request.execute.app.service;


import com.jcoder.request.common.RequestCacheEntity;
import com.jcoder.request.common.SetCacheEntity;

/**
 * 获取请求透传缓存信息
 *
 * @author Qiu
 */
public interface ISetCacheService {

    /**
     * 获取请求的注册信息
     *
     * @param requestCode
     * @return
     */
    SetCacheEntity getCacheInfo(String requestCode);

}
