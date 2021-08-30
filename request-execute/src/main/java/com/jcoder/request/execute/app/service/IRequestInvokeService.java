package com.jcoder.request.execute.app.service;

import com.jcoder.request.execute.domain.entity.SoapRequestParam;
import com.jcoder.request.execute.domain.entity.SoapResponse;
import org.dom4j.DocumentException;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author Qiu
 */
public interface IRequestInvokeService {

    /**
     * 用于soap接口的透传
     *
     * @param requestParam
     * @return
     */
    SoapResponse soapInvoke(SoapRequestParam requestParam) throws DocumentException;

    /**
     * 用于rest接口的透传
     *
     * @param requestParams
     * @param requestBody
     * @param pathParams
     * @param requestHeader
     * @return
     */
    ResponseEntity<Object> restInvoke(Map<String, Object> requestParams,
                                      Object requestBody,
                                      Map<String, Object> pathParams,
                                      Map<String, Object> requestHeader) throws DocumentException;

}
