package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.common.RequestCacheEntity;
import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.execute.app.service.IExecuteRequestService;
import com.jcoder.request.execute.app.service.IRequestInfoService;
import com.jcoder.request.execute.app.service.IRequestInvokeService;
import com.jcoder.request.execute.domain.entity.SoapRequestParam;
import com.jcoder.request.execute.domain.entity.SoapResponse;
import com.jcoder.request.execute.domain.entity.SoapReturnEntity;
import com.jcoder.request.execute.infra.ExecuteConstants;
import com.jcoder.request.execute.infra.util.HttpMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

            /**
             * 执行rest请求
             */
            return executeRestBaseOnSoap(requestCacheEntity, requestParam.payload);
        } else {
            /**
             * 执行soap请求
             */
            SoapReturnEntity response = executeRequestService.executeSoapRequest(requestCacheEntity.getTargetRequestUrl(),
                    "",
                    requestCacheEntity.getSoapAction(),
                    requestCacheEntity.getSoapVersion(),
                    requestCacheEntity.getDefaultRequestHeaders());

            /**
             * 提取soap报文, 并组装返回结果
             */
            return buildSoapResponse(response, requestCacheEntity);
        }
    }

    /**
     * 根据目标soap接口的返回，构建透传接口的返回内容
     * 需要根据目标接口返回的http状态来设定透传接口的返回状态
     *
     * @param response
     * @param requestCacheEntity
     * @return
     */
    private SoapResponse buildSoapResponse(SoapReturnEntity response,
                                           RequestCacheEntity requestCacheEntity) {

        SoapResponse soapResponse = new SoapResponse();

        if (response.getHttpStatus() == HttpStatus.OK.value()) {
            soapResponse.setStatusCode("S");
            /**
             * 如果设置了数据提取节点, 则进行提取; 否则按原样返回, 不做任何出路
             */
            if (StringUtils.isEmpty(requestCacheEntity.getResponseTagName())) {
                soapResponse.payload.add(response.getResponseStr());
            } else {
                soapResponse.payload = httpMessageUtil.getDataFromSoapResponse(response.getResponseStr(),
                        requestCacheEntity.getResponseTagName(),
                        ExecuteConstants.ExtractType.XML_NODE);
            }
        } else {
            soapResponse.setStatusCode("E");
            if (StringUtils.isEmpty(requestCacheEntity.getResponseErrorTagName())) {
                soapResponse.setMessage(response.getResponseStr());
            } else {
                List<String> errorList = httpMessageUtil.getDataFromSoapResponse(response.getResponseStr(),
                        requestCacheEntity.getResponseErrorTagName(),
                        ExecuteConstants.ExtractType.XML_CONTENT);

                StringBuilder errorMsg = new StringBuilder("");
                for (String error : errorList) {
                    errorMsg.append(error);
                }
                soapResponse.setMessage(errorMsg.toString());
            }
        }
        return soapResponse;

    }

    /**
     * 执行发起时为soap的webservice接口, 实际为rest的接口
     * 需要解析soap请求报文以获得对应的rest请求参数, 并将rest请求返回的数据组装成xml格式的字符串
     *
     * @param requestCacheEntity
     * @param paramList
     * @return
     */
    private SoapResponse executeRestBaseOnSoap(RequestCacheEntity requestCacheEntity, List<String> paramList) {

        /**
         * 获取rest请求的参数
         */
        Map<String, Object> restParamMap = null;
        try {
            restParamMap = httpMessageUtil.getDataForRest(paramList);
        } catch (DocumentException e) {
            throw new CommonException("request.execute.build_xml_err", e);
        }

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
        List<String> dataList = httpMessageUtil.getDataFromRestResponse(restResponse.getBody(),
                requestCacheEntity.getResponseTagName());

        SoapResponse response = new SoapResponse();
        response.setStatusCode("S");
        response.payload = dataList;

        if (restResponse.getStatusCode() != HttpStatus.OK) {
            response.setStatusCode("E");
        }

        return response;
    }

    @Override
    public ResponseEntity<Object> restInvoke(Map<String, Object> requestParams,
                                             Object requestBody,
                                             Map<String, Object> pathParams,
                                             Map<String, Object> requestHeader) {

        String requestCode;
        if (requestParams.containsKey(ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE)) {
            requestCode = (String) requestParams.get(ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);
        } else {
            throw new CommonException("request.execute.request_code_null", ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);
        }

        //获取接口注册信息
        RequestCacheEntity requestCacheEntity = requestInfoService.getRequestInfo(requestCode);

        /**
         * 如果目标接口的接口类型是soap,则需要将map报文转换成xml格式,
         * 并配合设置的参数模板, 拼接出完整的soap请求报文格式
         */
        if (ExecuteConstants.RequestType.SOAP.equals(requestCacheEntity.getTargetRequestType())) {

            ResponseEntity response = executeSoapBaseOnRest(requestCacheEntity, requestBody);

            return response;
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

    /**
     * 执行发起时为rest接口, 实际为soap的webservice的接口
     * 需要根据模板组装soap请求报文, 并根据设置解析出对应的数据, 按能转换成json的数据格式进行返回
     *
     * @param requestCacheEntity
     * @param requestBody
     * @return
     */
    private ResponseEntity executeSoapBaseOnRest(RequestCacheEntity requestCacheEntity, Object requestBody) {
        /**
         * 构建soap请求参数报文
         */
        String soapParam = null;
        try {
            soapParam = httpMessageUtil.buildSoapRequestParam(requestCacheEntity.getSoapTemplate(),
                    requestCacheEntity.getParamNodeName(),
                    requestCacheEntity.getDataTagName(),
                    requestBody);
        } catch (DocumentException e) {
            throw new CommonException("request.execute.build_xml_err", e);
        }
        /**
         * 执行soap请求
         */
        SoapReturnEntity soapResponse = executeRequestService.executeSoapRequest(requestCacheEntity.getTargetRequestUrl(),
                soapParam,
                requestCacheEntity.getSoapAction(),
                requestCacheEntity.getSoapVersion(),
                requestCacheEntity.getDefaultRequestHeaders());

        /**
         * 解析获取数据报文:
         * 提取节点数据, 转换成map进行返回
         */
        List<Object> responseData = null;
        try {
            responseData = httpMessageUtil.extractDataFromXml(soapResponse.getResponseStr(), requestCacheEntity.getResponseTagName());
        } catch (DocumentException e) {
            throw new CommonException("request.execute.build_xml_err", e);
        }

        if (responseData.size() > 0 && !requestCacheEntity.getTargetReturnListFlag()) {
            return ResponseEntity.ok(responseData.get(0));
        }
        return ResponseEntity.ok(responseData);
    }
}
