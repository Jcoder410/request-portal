package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.common.SetCacheEntity;
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

        /**
         * todo: 获取接口注册信息
         */
        SetCacheEntity setCacheEntity = new SetCacheEntity();
        HttpParameter httpParameter = new HttpParameter();
        httpParameter.setUrl(setCacheEntity.getSourceRequestUrl());
        httpParameter.setMethodCode(setCacheEntity.getSourceMethodCode());

        httpParameter.setHeaderParams(restParameter.getHeaderParams())
                .setPathVariables(restParameter.getPathParams())
                .setRequestParams(restParameter.getRequestParams())
                .mergeDefaultHeaderParam(setCacheEntity.getHeaderDefaultValues())
                .mergeDefaultPathParam(setCacheEntity.getPathDefaultValues())
                .mergeDefaultRequestParam(setCacheEntity.getRequestDefaultValues())
                .buildUriVariables();

        /**
         * 如果目标接口的接口类型是soap,则需要将map报文转换成xml格式,
         * 并配合设置的参数模板, 拼接出完整的soap请求报文格式
         */
        if (ExecuteConstants.RequestType.SOAP.equals(setCacheEntity.getSourceTypeCode())) {
            String soapBody = buildRequestBody(setCacheEntity, restParameter.getRequestBody());
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
        return buildReturn(restResponse, setCacheEntity);
    }

    /**
     * 针对目标接口是soap的情况, 需解析返回的xml重新构建返回结果
     *
     * @param response
     * @param cacheEntity
     * @return
     */
    private ResponseEntity buildReturn(ResponseEntity response, SetCacheEntity cacheEntity) {
        if (ExecuteConstants.RequestType.SOAP.equals(cacheEntity.getSourceTypeCode())) {

            String xmlData = (String) response.getBody();
            if (StringUtils.isEmpty(xmlData)) {
                return ResponseEntity.ok("");
            }

            //解析获得数据
            List<Object> responseData;
            try {
                responseData = httpMessageUtil.extractDataFromXml(xmlData, cacheEntity.getReturnDataNode());
            } catch (DocumentException e) {
                throw new CommonException("request.execute.build_xml_err", e);
            }

            if (responseData.size() > 0 && !cacheEntity.getReturnListFlag()) {
                return ResponseEntity.ok(responseData.get(0));
            }
            return ResponseEntity.ok(responseData);
        }
        return response;
    }

    /**
     * 构建soap请求的请求体
     *
     * @param cacheEntity
     * @param requestBody
     * @return
     */
    private String buildRequestBody(SetCacheEntity cacheEntity, Object requestBody) {

        if (requestBody == null) {
            return "";
        }

        /**
         * 构建soap请求参数报文
         */
        try {
            String soapParam = httpMessageUtil.buildSoapRequestParam(cacheEntity.getSoapParamTemplate(),
                    cacheEntity.getSoapParamNodeName(),
                    cacheEntity.getListNodeName(),
                    requestBody);
            return soapParam;
        } catch (DocumentException e) {
            throw new CommonException("request.execute.build_xml_err", e);
        }
    }
}
