package com.jcoder.request.execute.infra.executor;

import com.jcoder.request.common.SetCacheEntity;
import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.execute.app.service.IHttpExecuteService;
import com.jcoder.request.execute.app.service.ISetCacheService;
import com.jcoder.request.execute.domain.entity.HttpParameter;
import com.jcoder.request.execute.domain.entity.SoapRequestParam;
import com.jcoder.request.execute.domain.entity.SoapResponse;
import com.jcoder.request.execute.infra.ExecuteConstants;
import com.jcoder.request.execute.infra.builder.parameter.IHttpParamBuilder;
import com.jcoder.request.execute.infra.builder.parameter.ParamBuilderFactory;
import com.jcoder.request.execute.infra.builder.response.IResponseBuilder;
import com.jcoder.request.execute.infra.builder.response.ResponseBuilderFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * soap请求透传执行器: 来源接口可以是rest请求, 也可以是soap请求
 *
 * @author Qiu
 */
@Service
public class SoapExecutorServiceImpl implements IExecutorService<SoapResponse, SoapRequestParam> {

    @Autowired
    private IHttpExecuteService httpExecuteService;

    @Autowired
    private ISetCacheService setCacheService;

    @Autowired
    private ParamBuilderFactory paramBuilderFactory;

    @Autowired
    private ResponseBuilderFactory responseBuilderFactory;

    @Override
    public SoapResponse execute(SoapRequestParam requestParam) {

        if (StringUtils.isEmpty(requestParam.getInterfaceCode())) {
            throw new CommonException("request.execute.request_code_null", ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);
        }

        /**
         * todo: 获取接口注册信息
         */
        SetCacheEntity setCacheEntity = setCacheService.getCacheInfo(requestParam.getInterfaceCode());

        /**
         * 获取请求参数构造实现类
         */
        IHttpParamBuilder paramBuilder = paramBuilderFactory.getBuilder(ExecuteConstants.RequestType.SOAP, setCacheEntity.getSourceTypeCode());

        /**
         * 构建请求参数
         */
        HttpParameter httpParameter = paramBuilder.builder(requestParam.payload, setCacheEntity);

        /**
         * 执行请求
         */
        ResponseEntity response = httpExecuteService.executeRequest(httpParameter);

        /**
         * 获取构建返回内容的bean
         */
        IResponseBuilder responseBuilder = responseBuilderFactory.getBuilder(ExecuteConstants.RequestType.SOAP, setCacheEntity.getSourceTypeCode());

        return (SoapResponse) responseBuilder.builder(response, setCacheEntity);
    }
}
