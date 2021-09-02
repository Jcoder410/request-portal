package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.execute.app.service.IRequestInvokeService;
import com.jcoder.request.execute.app.service.IWsInvokeService;
import com.jcoder.request.execute.domain.entity.SoapRequestParam;
import com.jcoder.request.execute.domain.entity.SoapResponse;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.ArrayList;

@WebService(serviceName = "webserviceInvoke",//对外发布的服务名
        targetNamespace = "http://service.app.portal.request.jcoder.com",//指定你想要的名称空间，通常使用使用包名反转
        endpointInterface = "com.jcoder.request.execute.app.service.IWsInvokeService")
@Service
public class WsInvokeServiceImpl implements IWsInvokeService {

    @Autowired
    private IRequestInvokeService requestInvokeService;

    @Override
    public SoapResponse invoke(SoapRequestParam params) {

        SoapResponse response = null;
        try {
            response = requestInvokeService.soapInvoke(params);
        } catch (Exception e) {
            e.printStackTrace();
            SoapResponse errorReturn = new SoapResponse();
            errorReturn.setStatusCode("E");
            errorReturn.setMessage(e.getMessage());
            errorReturn.payload = new ArrayList<>();
            return response;
        }

        return response;
    }
}