package com.jcoder.request.execute.infra.builder.response;

import com.jcoder.request.common.SetCacheEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * rest接口透透传的返回结果, 无额外处理的话, 则直接返回ResponseEntity
 *
 * @author Qiu
 */
@Component
public class RestRespBuilder implements IResponseBuilder<ResponseEntity, SetCacheEntity, ResponseEntity> {

    @Override
    public ResponseEntity builder(ResponseEntity returnContext, SetCacheEntity setCatch) {
        return returnContext;
    }
}
