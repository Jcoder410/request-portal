package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.execute.app.service.IExecuteRequestService;
import com.jcoder.request.execute.app.service.IWsInvokeService;
import com.jcoder.request.execute.domain.entity.SoapRequestParam;
import com.jcoder.request.execute.domain.entity.SoapResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.Arrays;

@WebService(serviceName = "webserviceInvoke",//对外发布的服务名
        targetNamespace = "http://service.app.portal.request.jcoder.com",//指定你想要的名称空间，通常使用使用包名反转
        endpointInterface = "com.jcoder.request.execute.app.service.IWsInvokeService")
@Service
public class WsInvokeServiceImpl implements IWsInvokeService {

    @Autowired
    private IExecuteRequestService executeRequestService;

    @Override
    public SoapResponse invoke(SoapRequestParam params) {

        String param = params.payload.get(0);

        String result = executeRequestService.executeSoapRequest("http://localhost:9080/webservice/test/person?wsdl",
                param,
                "",
                "soap1.1", null);

        SoapResponse response = new SoapResponse();
        response.payload = Arrays.asList(result);
        response.setMessage("请求成功");
        response.setStatusCode("S");

        return response;
    }
}