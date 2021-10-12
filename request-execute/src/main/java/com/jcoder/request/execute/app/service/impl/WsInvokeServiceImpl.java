package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.execute.app.service.IWsInvokeService;
import com.jcoder.request.execute.domain.entity.SoapRequestParam;
import com.jcoder.request.execute.domain.entity.SoapResponse;
import com.jcoder.request.execute.infra.executor.SoapExecutorServiceImpl;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.ArrayList;

/**
 * soap接口透传处理类
 *
 * @author Qiu
 */
@WebService(serviceName = "webserviceInvoke",//对外发布的服务名
        targetNamespace = "http://service.app.portal.request.jcoder.com",//指定你想要的名称空间，通常使用使用包名反转
        endpointInterface = "com.jcoder.request.execute.app.service.IWsInvokeService")
@Service
public class WsInvokeServiceImpl implements IWsInvokeService {

    @Autowired
    private SoapExecutorServiceImpl soapExecutorService;

    @Override
    public SoapResponse invoke(SoapRequestParam params) {

        try {
            SoapResponse response = soapExecutorService.execute(params);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            SoapResponse errorReturn = new SoapResponse();
            errorReturn.setStatusCode("E");
            errorReturn.setMessage(ExceptionUtils.getStackTrace(e));
            errorReturn.payload = new ArrayList<>();
            return errorReturn;
        }
    }
}