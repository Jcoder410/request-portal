package com.jcoder.request.execute.app.service;

import com.jcoder.request.execute.domain.entity.HttpParameter;
import org.springframework.http.ResponseEntity;

/**
 * 对实际接口发起请求
 *
 * @author Qiu
 */
public interface IExecuteRequestService {

    /**
     * 执行http请求
     *
     * @param httpParameter
     * @return
     */
    ResponseEntity<Object> executeRequest(HttpParameter httpParameter);

}
