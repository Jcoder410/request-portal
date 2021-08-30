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

import java.util.ArrayList;
import java.util.List;
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
    public SoapResponse soapInvoke(SoapRequestParam requestParam) throws DocumentException {

        if (StringUtils.isEmpty(requestParam.getInterfaceCode())) {
            throw new CommonException("request.execute.request_code_null", ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);
        }

        //获取接口注册信息
        RequestCacheEntity requestCacheEntity = requestInfoService.getRequestInfo(requestParam.getInterfaceCode());

        List<String> dataList = new ArrayList<>();
        /**
         * 如果目标接口的接口类型是rest,则需要将xml报文转换成rest接口能接收的json格式
         */
        if (ExecuteConstants.RequestType.REST.equals(requestCacheEntity.getTargetRequestType())) {
            /**
             * 获取rest请求的参数
             */
            Map<String, Object> restParamMap = httpMessageUtil.getDataForRest(requestParam.payload);

            /**
             * 执行rest请求
             */
            ResponseEntity restResponse = executeRequestService.executeRestRequest(requestCacheEntity.getTargetRequestUrl(),
                    requestCacheEntity.getTargetRequestMethod(),
                    (Map<String, Object>) restParamMap.get(ExecuteConstants.HttpParamType.REQUEST_PARAM),
                    restParamMap.get(ExecuteConstants.HttpParamType.REQUEST_BODY),
                    (Map<String, Object>) restParamMap.get(ExecuteConstants.HttpParamType.PATH_VARIABLE),
                    (Map<String, Object>) restParamMap.get(ExecuteConstants.HttpParamType.REQUEST_HEADER));

            /**
             * 提取返回数据
             */
            dataList = httpMessageUtil.getDataFromRestResponse(restResponse.getBody(),
                    requestCacheEntity.getResponseTagName());


        } else {
            /**
             * 执行soap请求
             */
            String response = executeRequestService.executeSoapRequest(requestCacheEntity.getTargetRequestUrl(),
                    "",
                    requestCacheEntity.getSoapAction(),
                    requestCacheEntity.getSoapVersion(),
                    requestCacheEntity.getDefaultRequestHeaders());

            /**
             * 提取soap报文, 并组装返回结果
             */
            dataList = httpMessageUtil.getDataFromSoapResponse(response, requestCacheEntity.getResponseTagName());
        }

        SoapResponse soapResponse = new SoapResponse();
        soapResponse.payload = dataList;
        soapResponse.setStatusCode("S");

        return soapResponse;
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
            /**
             * 构建soap请求参数报文
             */
            String soapParam = httpMessageUtil.buildSoapRequestParam(requestCacheEntity.getSoapTemplate(),
                    requestCacheEntity.getParamNodeName(),
                    requestCacheEntity.getDataTagName(),
                    requestBody);
            /**
             * 执行soap请求
             */
            String soapResponse = executeRequestService.executeSoapRequest(requestCacheEntity.getTargetRequestUrl(),
                    soapParam,
                    requestCacheEntity.getSoapAction(),
                    requestCacheEntity.getSoapVersion(),
                    requestCacheEntity.getDefaultRequestHeaders());

            /**
             * 解析获取数据报文:
             * 提取节点数据, 转换成map进行返回
             */
            Object responseData = httpMessageUtil.extractDataFromXml(soapResponse, requestCacheEntity.getResponseTagName());

            return ResponseEntity.ok(responseData);
        }

        /**
         * 执行rest请求
         */
        ResponseEntity restResponse = executeRequestService.executeRestRequest(requestCacheEntity.getTargetRequestUrl(),
                requestCacheEntity.getTargetRequestMethod(),
                requestParams,
                requestBody,
                pathParams,
                requestHeader);

        return restResponse;
    }
}
