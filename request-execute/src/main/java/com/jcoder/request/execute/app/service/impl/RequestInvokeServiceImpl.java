package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.common.RequestCacheEntity;
import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.execute.app.service.IExecuteRequestService;
import com.jcoder.request.execute.app.service.IRequestInfoService;
import com.jcoder.request.execute.app.service.IRequestInvokeService;
import com.jcoder.request.execute.domain.entity.SoapRequestParam;
import com.jcoder.request.execute.domain.entity.SoapResponse;
import com.jcoder.request.execute.infra.ExecuteConstants;
import com.jcoder.request.execute.infra.util.HttpMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Qiu
 */
@Service
public class RequestInvokeServiceImpl implements IRequestInvokeService {

    @Autowired
    private IExecuteRequestService executeRequestService;

    @Autowired
    private IRequestInfoService requestInfoService;

    private HttpMessageUtil httpMessageUtil = new HttpMessageUtil();

    @Override
    public SoapResponse soapInvoke(SoapRequestParam requestParam) {

        if (StringUtils.isEmpty(requestParam.getInterfaceCode())) {
            throw new CommonException("request.execute.request_code_null", ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);
        }

        //获取接口注册信息
        RequestCacheEntity requestCacheEntity = requestInfoService.getRequestInfo(requestParam.getInterfaceCode());

        /**
         * 如果目标接口的接口类型是rest,则需要将xml报文转换成rest接口能接收的json格式
         */
        if (ExecuteConstants.RequestType.REST.equals(requestCacheEntity.getTargetRequestType())) {

        }
        return null;
    }

    @Override
    public ResponseEntity<Object> restInvoke(Map<String, Object> requestParams,
                                             Object requestBody,
                                             Map<String, Object> pathParams,
                                             Map<String, Object> requestHeader) throws DocumentException {

        String requestCode = (String) requestParams.get(ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);

        if (StringUtils.isEmpty(requestCode)) {
            throw new CommonException("request.execute.request_code_null", ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);
        }

        //获取接口注册信息
        RequestCacheEntity requestCacheEntity = requestInfoService.getRequestInfo(requestCode);

        /**
         * 如果目标接口的接口类型是soap,则需要将map报文转换成xml格式,
         * 并配合设置的参数模板, 拼接出完整的soap请求报文格式
         */
        if (ExecuteConstants.RequestType.SOAP.equals(requestCacheEntity.getTargetRequestType())) {
            String soapParam = httpMessageUtil.buildSoapRequestParam(requestCacheEntity.getSoapTemplate(),
                    requestCacheEntity.getParamNodeName(),
                    requestCacheEntity.getDataTagName(),
                    requestBody);
            /**
             * 执行soap请求
             */
            String soapReponse = executeRequestService.executeSoapRequest(requestCacheEntity.getTargetRequestUrl(),
                    soapParam,
                    requestCacheEntity.getSoapAction(),
                    requestCacheEntity.getSoapVersion(),
                    requestCacheEntity.getDefaultRequestHeaders());

            /**
             * 解析获取数据报文
             */
        }

        return null;
    }

    /**
     * 构建SOAP请求参数
     *
     * @param soapTemplate soap参数模板
     * @param requestBody
     * @return
     */
    private String buildSoapRequestParam(String soapTemplate,
                                         Object requestBody) {

        return null;
    }
}
