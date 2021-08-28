package com.jcoder.request.execute.config;

import com.jcoder.request.execute.app.service.IWsInvokeService;
import com.jcoder.request.execute.test.app.SoapForTestService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {

    @Autowired
    private IWsInvokeService wsInvokeService;

    @Autowired
    private SoapForTestService soapForTestService;

    @Bean(name = "cxfServlet")
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/webservice/*");
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean(name = "webServiceInvokeEndPoint")
    public Endpoint webServiceInvokeEndPoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), wsInvokeService);
        endpoint.publish("/soap/invoke");
        return endpoint;
    }

    @Bean(name = "webServiceTestEndPoint")
    public Endpoint webServiceTestEndPoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), soapForTestService);
        endpoint.publish("/test/person");
        return endpoint;
    }
}
