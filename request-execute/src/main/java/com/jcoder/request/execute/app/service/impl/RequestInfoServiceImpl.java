package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.common.RequestCacheEntity;
import com.jcoder.request.execute.app.service.IRequestInfoService;
import org.springframework.stereotype.Service;

/**
 * @author Qiu
 */
@Service
public class RequestInfoServiceImpl implements IRequestInfoService {
    @Override
    public RequestCacheEntity getRequestInfo(String requestCode) {

        RequestCacheEntity requestCacheEntity = new RequestCacheEntity();

        return requestCacheEntity;
    }
}
