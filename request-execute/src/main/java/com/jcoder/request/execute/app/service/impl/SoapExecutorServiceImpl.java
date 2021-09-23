package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.common.RequestCacheEntity;
import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.execute.app.service.IExecuteRequestService;
import com.jcoder.request.execute.app.service.IExecutorService;
import com.jcoder.request.execute.app.service.IRequestInfoService;
import com.jcoder.request.execute.domain.entity.HttpParameter;
import com.jcoder.request.execute.domain.entity.SoapRequestParam;
import com.jcoder.request.execute.infra.ExecuteConstants;
import com.jcoder.request.execute.infra.util.HttpMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * soap请求透传执行器: 来源接口可以是rest请求, 也可以是soap请求
 *
 * @author Qiu
 */
@Service
public class SoapExecutorServiceImpl implements IExecutorService<ResponseEntity, SoapRequestParam> {

    @Autowired
    private IExecuteRequestService executeRequestService;

    @Autowired
    private IRequestInfoService requestInfoService;

    private HttpMessageUtil httpMessageUtil = new HttpMessageUtil();

    @Override
    public ResponseEntity execute(SoapRequestParam requestParam) {
        if (StringUtils.isEmpty(requestParam.getInterfaceCode())) {
            throw new CommonException("request.execute.request_code_null", ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);
        }

        //获取接口注册信息
        RequestCacheEntity requestCacheEntity = requestInfoService.getRequestInfo(requestParam.getInterfaceCode());

        HttpParameter httpParameter = new HttpParameter();
        /**
         * 如果目标接口的接口类型是rest,则需要将xml报文转换成rest接口能接收的json格式
         */
        if (ExecuteConstants.RequestType.REST.equals(requestCacheEntity.getTargetRequestType())) {

            httpParameter = buildRestParam(requestParam.payload, requestCacheEntity);

        } else {
            httpParameter = buildSoapParam(requestParam.payload, requestCacheEntity);
            httpParameter.setReturnType(String.class);
        }

        /**
         * 执行请求
         */
        ResponseEntity response = executeRequestService.executeRequest(httpParameter);

        return response;
    }

    /**
     * 构建soap请求报文: 如果存在soap请求报文模板, 则需要基于模板进行报文的构建, 否则直接
     * 使用传入的xml数据发起soap请求
     *
     * @param paramList
     * @param cacheEntity
     * @return
     */
    private HttpParameter buildSoapParam(List<String> paramList, RequestCacheEntity cacheEntity) {

        //从缓存中获取基本访问信息
        HttpParameter httpParameter = new HttpParameter();
        httpParameter.setHeaderParams(cacheEntity.getDefaultRequestHeaders());
        httpParameter.setUrl(cacheEntity.getTargetRequestUrl());
        httpParameter.setMethodCode(cacheEntity.getTargetRequestMethod());

        //获取请求体信息
        if (StringUtils.isEmpty(cacheEntity.getSoapTemplate())) {
            StringBuilder bodyBuilder = new StringBuilder("");
            for (String xmlNode : paramList) {
                bodyBuilder.append(xmlNode);
            }
            httpParameter.setRequestBody(bodyBuilder.toString());
        } else {
            if (StringUtils.isEmpty(cacheEntity.getParamNodeName())) {
                throw new CommonException("request.execute.param_node_null_err");
            }
            try {
                String soapBody = httpMessageUtil.buildSoapRequestBody(paramList,
                        cacheEntity.getSoapTemplate(),
                        cacheEntity.getParamNodeName());
                httpParameter.setRequestBody(soapBody);
            } catch (DocumentException e) {
                throw new CommonException("request.execute.build_soap_body_err", e);
            }
        }
        return httpParameter;
    }

    /**
     * 从xml报文中提取rest请求参数，包括：uri请求参数、请求头、请求体
     *
     * @param paramList
     * @param cacheEntity
     * @return
     */
    private HttpParameter buildRestParam(List<String> paramList, RequestCacheEntity cacheEntity) {

        if (null == paramList || paramList.size() == 0) {
            return new HttpParameter();
        }
        /**
         * 解析xml报文获取请求参数
         */
        Map<String, Object> restParamMap;
        try {
            restParamMap = httpMessageUtil.getDataForRest(paramList);
        } catch (DocumentException e) {
            throw new CommonException("request.execute.build_xml_err", e);
        }

        //获取请求体信息
        HttpParameter httpParameter = new HttpParameter();
        httpParameter.setHeaderParams(cacheEntity.getDefaultRequestHeaders());
        httpParameter.setUrl(cacheEntity.getTargetRequestUrl());
        httpParameter.setMethodCode(cacheEntity.getTargetRequestMethod());

        //合并uri参数
        httpParameter.mergeUriVariables((Map<String, Object>) restParamMap.get(ExecuteConstants.HttpParamType.REQUEST_PARAM),
                (Map<String, Object>) restParamMap.get(ExecuteConstants.HttpParamType.PATH_VARIABLE));

        //收集请求头参数
        if (restParamMap.containsKey(ExecuteConstants.HttpParamType.REQUEST_HEADER)) {
            httpParameter.setHeaderParams((Map<String, String>) restParamMap.get(ExecuteConstants.HttpParamType.REQUEST_HEADER));
        }

        //收集请求体参数
        if (restParamMap.containsKey(ExecuteConstants.HttpParamType.REQUEST_BODY)) {
            httpParameter.setRequestBody(restParamMap.get(ExecuteConstants.HttpParamType.REQUEST_BODY));
        }

        //组装完整的请求路径
        if (restParamMap.containsKey(ExecuteConstants.HttpParamType.REQUEST_PARAM)) {
            String fullUrl = httpMessageUtil.buildUrl(httpParameter.getUrl(), (Map<String, Object>) restParamMap.get(ExecuteConstants.HttpParamType.REQUEST_PARAM));
            httpParameter.setUrl(fullUrl);
        }

        return httpParameter;
    }
}
