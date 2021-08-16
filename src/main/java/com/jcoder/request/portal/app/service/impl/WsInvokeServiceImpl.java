package com.jcoder.request.portal.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcoder.request.portal.app.service.WsInvokeService;
import com.jcoder.request.portal.domain.entity.SoapRequestParam;
import com.jcoder.request.portal.domain.entity.SoapRequestParamForRest;
import com.jcoder.request.portal.domain.entity.SoapResponse;
import com.jcoder.request.portal.domain.entity.SoapResponseForRest;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

@WebService(serviceName = "webserviceDemo",//对外发布的服务名
        targetNamespace = "http://service.app.portal.request.jcoder.com",//指定你想要的名称空间，通常使用使用包名反转
        endpointInterface = "com.jcoder.request.portal.app.service.WsInvokeService")
@Service
public class WsInvokeServiceImpl implements WsInvokeService {

    @Override
    public SoapResponse invoke(SoapRequestParam params) {

        SoapResponse response = new SoapResponse();
        response.setDatas(params.getDatas());
        response.setMessage("请求成功");
        response.setStatusCode("S");

        return response;
    }

    @Override
    public SoapResponseForRest invokeForRest(SoapRequestParamForRest params) throws JsonProcessingException {

        SoapResponseForRest response = new SoapResponseForRest();
        response.setDatas(params.getDatas().get("requestBody"));
        response.setMessage("普天同庆，喜大普奔");
        response.setStatusCode("S");

        return response;
    }
}