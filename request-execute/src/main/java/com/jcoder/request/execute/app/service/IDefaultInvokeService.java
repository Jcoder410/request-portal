package com.jcoder.request.execute.app.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * 默认的rest接口透传执行service
 *
 * @author Qiu
 */
public interface IDefaultInvokeService {

    /**
     * 默认的请求投创执行方法:
     * 主要是pathVariable部分, 需要根据设置进行解析映射,
     * 然后调用IRequestInvokeService中的rest接口透传方法进行执行
     *
     * @param requestParams
     * @param requestBody
     * @param pathVariableStr
     * @param headerParams
     * @return
     */
    ResponseEntity executeInvoke(Map<String, Object> requestParams,
                                 Object requestBody,
                                 String pathVariableStr,
                                 Map<String, Object> headerParams);

}
