package com.jcoder.request.execute.infra.executor;

import com.jcoder.request.common.SetCacheEntity;
import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.execute.app.service.IHttpExecuteService;
import com.jcoder.request.execute.app.service.ISetCacheService;
import com.jcoder.request.execute.domain.entity.HttpParameter;
import com.jcoder.request.execute.domain.entity.RestParameter;
import com.jcoder.request.execute.infra.ExecuteConstants;
import com.jcoder.request.execute.infra.builder.parameter.IHttpParamBuilder;
import com.jcoder.request.execute.infra.builder.parameter.ParamBuilderFactory;
import com.jcoder.request.execute.infra.builder.response.IResponseBuilder;
import com.jcoder.request.execute.infra.builder.response.ResponseBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * rest请求透传执行器: 来源接口可以是rest请求, 也可以是soap请求
 *
 * @author Qiu
 */
@Service
public class RestExecutorServiceImpl implements IExecutorService<ResponseEntity, RestParameter> {

    @Autowired
    private IHttpExecuteService httpExecuteService;

    @Autowired
    private ISetCacheService setCacheService;

    @Autowired
    private ParamBuilderFactory paramBuilderFactory;

    @Autowired
    private ResponseBuilderFactory responseBuilderFactory;

    @Override
    public ResponseEntity<?> execute(RestParameter restParameter) {

        String requestCode;
        if (restParameter.getRequestParams().containsKey(ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE)) {
            requestCode = (String) restParameter.getRequestParams().get(ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);
        } else {
            throw new CommonException("request.execute.request_code_null", ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);
        }

        /**
         * 获取接口缓存信息
         */
        SetCacheEntity setCacheEntity = setCacheService.getCacheInfo(requestCode);

        /**
         * 获取请求参数构造实现类
         */
        IHttpParamBuilder paramBuilder = paramBuilderFactory.getBuilder(ExecuteConstants.RequestType.REST, setCacheEntity.getSourceTypeCode());

        /**
         * 构建请求参数
         */
        HttpParameter httpParameter = paramBuilder.builder(restParameter, setCacheEntity);

        /**
         * 执行http请求
         */
        ResponseEntity restResponse = httpExecuteService.executeRequest(httpParameter);

        /**
         * 获取构建返回内容的bean
         */
        IResponseBuilder responseBuilder = responseBuilderFactory.getBuilder(ExecuteConstants.RequestType.REST, setCacheEntity.getSourceTypeCode());

        /**
         * 返回报文处理
         */
        return (ResponseEntity<?>) responseBuilder.builder(restResponse, setCacheEntity);
    }
}
