package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.common.RequestCacheEntity;
import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.execute.app.service.IExecuteRequestService;
import com.jcoder.request.execute.app.service.IExecutorService;
import com.jcoder.request.execute.app.service.IRequestInfoService;
import com.jcoder.request.execute.domain.entity.HttpParameter;
import com.jcoder.request.execute.domain.entity.RestParameter;
import com.jcoder.request.execute.infra.ExecuteConstants;
import com.jcoder.request.execute.infra.util.HttpMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * rest请求透传执行器: 来源接口可以是rest请求, 也可以是soap请求
 *
 * @author Qiu
 */
@Service
public class RestExecutorServiceImpl implements IExecutorService<ResponseEntity, RestParameter> {

    @Autowired
    private IExecuteRequestService executeRequestService;

    @Autowired
    private IRequestInfoService requestInfoService;

    private HttpMessageUtil httpMessageUtil = new HttpMessageUtil();

    @Override
    public ResponseEntity<?> execute(RestParameter restParameter) {

        String requestCode;
        if (restParameter.getRequestParams().containsKey(ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE)) {
            requestCode = (String) restParameter.getRequestParams().get(ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);
        } else {
            throw new CommonException("request.execute.request_code_null", ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);
        }

        //获取接口注册信息
        RequestCacheEntity requestCacheEntity = requestInfoService.getRequestInfo(requestCode);

        HttpParameter httpParameter = new HttpParameter();
        httpParameter.setUrl(requestCacheEntity.getTargetRequestUrl());
        httpParameter.setMethodCode(requestCacheEntity.getTargetRequestMethod());
        httpParameter.mergeUriVariables(restParameter.getPathParams(), restParameter.getRequestParams());

        /**
         * 如果目标接口的接口类型是soap,则需要将map报文转换成xml格式,
         * 并配合设置的参数模板, 拼接出完整的soap请求报文格式
         */
        if (ExecuteConstants.RequestType.SOAP.equals(requestCacheEntity.getTargetRequestType())) {

            String soapBody = buildRequestBody(requestCacheEntity, restParameter.getRequestBody());
            httpParameter.setRequestBody(soapBody);
            httpParameter.setReturnType(String.class);
        } else {
            httpParameter.setRequestBody(restParameter.getRequestBody());
        }

        /**
         * 执行rest请求
         */
        ResponseEntity restResponse = executeRequestService.executeRequest(httpParameter);

        /**
         * 返回报文处理
         */
        return buildReturn(restResponse, requestCacheEntity);
    }

    /**
     * 针对目标接口是soap的情况, 需解析返回的xml重新构建返回结果
     *
     * @param response
     * @param cacheEntity
     * @return
     */
    private ResponseEntity buildReturn(ResponseEntity response, RequestCacheEntity cacheEntity) {
        if (ExecuteConstants.RequestType.SOAP.equals(cacheEntity.getTargetRequestType())) {

            String xmlData = (String) response.getBody();
            if (StringUtils.isEmpty(xmlData)) {
                return ResponseEntity.ok("");
            }

            //解析获得数据
            List<Object> responseData;
            try {
                responseData = httpMessageUtil.extractDataFromXml(xmlData, cacheEntity.getResponseTagName());
            } catch (DocumentException e) {
                throw new CommonException("request.execute.build_xml_err", e);
            }

            if (responseData.size() > 0 && !cacheEntity.getTargetReturnListFlag()) {
                return ResponseEntity.ok(responseData.get(0));
            }
            return ResponseEntity.ok(responseData);
        }
        return response;
    }

    /**
     * 构建soap请求的请求体
     *
     * @param requestCacheEntity
     * @param requestBody
     * @return
     */
    private String buildRequestBody(RequestCacheEntity requestCacheEntity, Object requestBody) {

        if (requestBody == null) {
            return "";
        }

        /**
         * 构建soap请求参数报文
         */
        try {
            String soapParam = httpMessageUtil.buildSoapRequestParam(requestCacheEntity.getSoapTemplate(),
                    requestCacheEntity.getParamNodeName(),
                    requestCacheEntity.getDataTagName(),
                    requestBody);
            return soapParam;
        } catch (DocumentException e) {
            throw new CommonException("request.execute.build_xml_err", e);
        }
    }
}
