package com.jcoder.request.execute.app.service;

import com.jcoder.request.execute.domain.entity.SoapRequestParam;
import com.jcoder.request.execute.domain.entity.SoapResponse;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * webservice透传接口
 *
 * @author Qiu
 */
@WebService
public interface IWsInvokeService {

    /**
     * soap接口透传成soap接口, 此时的参数可能涉及更复杂的xml报文，比如包含属性、命名空间等,
     * 需要单独的节点属性类来承载解析后的xml报文数据
     *
     * @param params
     * @return
     */
    @WebMethod(operationName = "invoke")
    SoapResponse invoke(@WebParam(name = "params") SoapRequestParam params);
}