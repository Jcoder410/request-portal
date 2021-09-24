package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.execute.api.controller.DynamicRestInvokeController;
import com.jcoder.request.execute.api.controller.DynamicSoapInvokeController;
import com.jcoder.request.execute.app.service.IRequestRegistryService;
import com.jcoder.request.execute.domain.entity.RequestRegistry;
import com.jcoder.request.execute.infra.ExecuteConstants;
import com.jcoder.request.execute.infra.util.RequestToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 请求地址注册
 *
 * @author Qiu
 */
@Service
public class RequestRegistryServiceImpl implements IRequestRegistryService {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Override
    public void requestRegistry(RequestRegistry requestRegistry) {

        //获取请求方式对象，如果不存在则报错
        RequestMethod method = RequestToolUtil.getRequestMethod(requestRegistry.getRequestMethod());

        if (method == null) {
            throw new CommonException("request.registry.request_method_err");
        }

        //构建requestMappingInfo对象
        RequestMappingInfo requestMappingInfo = buildRequestMappingInfo(requestRegistry, method);
        if (urlRegisted(requestMappingInfo)) {
            throw new CommonException("request.registry.request_registered", requestRegistry.getBaseRequestUrl());
        }

        /**
         * 从bean容器获取RequestMappingHandlerMapping对象，
         * 将url与处理方法进行绑定
         */
        RequestMappingHandlerMapping requestMappingHandlerMapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);

        if (requestRegistry.getRequestType().equals(ExecuteConstants.RequestType.REST)) {
            Method targetMethod = ReflectionUtils.findMethod(DynamicRestInvokeController.class, ExecuteConstants.RequestHandleMethod.COMMON_HANDLER, null);
            requestMappingHandlerMapping.registerMapping(requestMappingInfo, "dynamicRestInvokeController", targetMethod);
        } else if (requestRegistry.getRequestType().equals(ExecuteConstants.RequestType.SOAP)) {
            Method targetMethod = ReflectionUtils.findMethod(DynamicSoapInvokeController.class, ExecuteConstants.RequestHandleMethod.COMMON_HANDLER, null);
            requestMappingHandlerMapping.registerMapping(requestMappingInfo, "dynamicSoapInvokeController", targetMethod);
        } else {
            throw new CommonException("request.registry.request_type_err", requestRegistry.getRequestType());
        }
    }

    @Override
    public void removeRegistry(RequestRegistry requestRegistry) {

        //获取请求方式对象，如果不存在则报错
        RequestMethod method = RequestToolUtil.getRequestMethod(requestRegistry.getRequestMethod());

        if (method == null) {
            throw new CommonException("request.registry.request_method_err");
        }

        RequestMappingHandlerMapping requestMappingHandlerMapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);

        //构建requestMappingInfo对象
        RequestMappingInfo requestMappingInfo = buildRequestMappingInfo(requestRegistry, method);

        requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
    }

    /**
     * 构建RequestMappingInfo
     *
     * @param requestRegistry
     * @param requestMethod
     * @return
     */
    private RequestMappingInfo buildRequestMappingInfo(RequestRegistry requestRegistry, RequestMethod requestMethod) {

        //构建requestMappingInfo对象
        String[] headers = new String[]{};
        RequestMappingInfo.Builder builder = RequestMappingInfo
                .paths(requestRegistry.getBaseRequestUrl())
                .methods(requestMethod)
                .params(null)
                .headers(headers)
                .consumes(requestRegistry.getConsumes())
                .produces(requestRegistry.getProduces())
                .mappingName(null);
        RequestMappingInfo requestMappingInfo = builder.build();

        return requestMappingInfo;
    }

    /**
     * 验证url是否已经存在
     *
     * @return
     */
    private Boolean urlRegisted(RequestMappingInfo requestMappingInfo) {

        RequestMappingHandlerMapping requestMappingHandlerMapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        return map.containsKey(requestMappingInfo);
    }

    /**
     * 验证url是否已经存在
     *
     * @param requestUrl
     * @param requestMethod
     * @return
     */
    @Deprecated
    private Boolean urlRegisted(String requestUrl, RequestMethod requestMethod) {

        RequestMappingHandlerMapping requestMappingHandlerMapping = webApplicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

        for (RequestMappingInfo info : map.keySet()) {
            for (String pattern : info.getPatternsCondition().getPatterns()) {
                if (pattern.equalsIgnoreCase(requestUrl)) {
                    if (info.getMethodsCondition().getMethods().contains(requestMethod)) {
                        return Boolean.TRUE;
                    }
                }
            }
        }
        return Boolean.FALSE;
    }
}
