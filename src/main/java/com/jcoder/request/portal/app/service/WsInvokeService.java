package com.jcoder.request.portal.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jcoder.request.portal.domain.entity.SoapRequestParam;
import com.jcoder.request.portal.domain.entity.SoapRequestParamForRest;
import com.jcoder.request.portal.domain.entity.SoapResponse;
import com.jcoder.request.portal.domain.entity.SoapResponseForRest;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * webservice透传接口
 *
 * @author Qiu
 */
@WebService
public interface WsInvokeService {

    /**
     * soap接口透传成soap接口, 此时的参数可能涉及更复杂的xml报文，比如包含属性、命名空间等,
     * 需要单独的节点属性类来承载解析后的xml报文数据
     *
     * @param params
     * @return
     */
    @WebMethod(operationName = "soap-invoke")
    SoapResponse invoke(@WebParam(name = "params") SoapRequestParam params);

    /**
     * rest接口透传成soap接口, 此时xml报文很简单, 不会包含属性、前缀等信息,
     * 只需要用Map或者List<Map>存储解析后的xml报文数据
     *
     * @param params
     * @return
     * @throws JsonProcessingException
     */
    @WebMethod(operationName = "rest-invoke")
    SoapResponseForRest invokeForRest(@WebParam(name = "params") SoapRequestParamForRest params) throws JsonProcessingException;
}