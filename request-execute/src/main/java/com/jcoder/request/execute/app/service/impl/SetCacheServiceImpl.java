package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.common.RequestCacheEntity;
import com.jcoder.request.common.SetCacheEntity;
import com.jcoder.request.execute.app.service.ISetCacheService;
import com.jcoder.request.execute.test.testapi.BuildRequestEntity;
import org.springframework.stereotype.Service;

/**
 * @author Qiu
 */
@Service
public class SetCacheServiceImpl implements ISetCacheService {
    @Override
    public SetCacheEntity getCacheInfo(String requestCode) {

        SetCacheEntity requestCacheEntity = BuildRequestEntity.buildInvokeEntity().get(requestCode);

        return requestCacheEntity;
    }
}
