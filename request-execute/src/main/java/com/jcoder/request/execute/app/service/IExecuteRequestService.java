package com.jcoder.request.execute.app.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * 对实际接口发起请求
 *
 * @author Qiu
 */
public interface IExecuteRequestService {

    /**
     * 用于执行rest请求
     *
     * @param url
     * @param requestMethod
     * @param requestParams
     * @param requestBody
     * @param pathParams
     * @param headerParams
     * @return
     */
    ResponseEntity<Object> executeRestRequest(String url,
                                              String requestMethod,
                                              Map<String, Object> requestParams,
                                              Object requestBody,
                                              Map<String, Object> pathParams,
                                              Map<String, Object> headerParams);

    /**
     * 用于执行soap请求
     *
     * @param url
     * @param params
     * @param soapAction
     * @param soapVersion
     * @param httpHeader
     * @return
     */
    String executeSoapRequest(String url,
                              String params,
                              String soapAction,
                              String soapVersion,
                              Map<String, String> httpHeader);

}
