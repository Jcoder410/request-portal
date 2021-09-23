package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.common.RequestCacheEntity;
import com.jcoder.request.execute.app.service.IRequestInfoService;
import com.jcoder.request.execute.app.service.ISoapInvokeService;
import com.jcoder.request.execute.domain.entity.SoapRequestParam;
import com.jcoder.request.execute.domain.entity.SoapResponse;
import com.jcoder.request.execute.infra.ExecuteConstants;
import com.jcoder.request.execute.infra.util.HttpMessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Qiu
 */
@Service
public class SoapInvokeServiceImpl implements ISoapInvokeService {

    @Autowired
    private SoapExecutorServiceImpl executorService;

    @Autowired
    private IRequestInfoService requestInfoService;

    private HttpMessageUtil httpMessageUtil = new HttpMessageUtil();

    @Override
    public SoapResponse soapInvoke(SoapRequestParam param) {

        ResponseEntity responseEntity = executorService.execute(param);

        //获取缓存信息
        RequestCacheEntity cacheEntity = requestInfoService.getRequestInfo(param.getInterfaceCode());

        //构建返回
        SoapResponse soapResponse = buildResponse(responseEntity, cacheEntity);

        return soapResponse;
    }

    /**
     * 构建返回内容
     *
     * @param response
     * @param cacheEntity
     * @return
     */
    private SoapResponse buildResponse(ResponseEntity response, RequestCacheEntity cacheEntity) {

        if (ExecuteConstants.RequestType.REST.equals(cacheEntity.getTargetRequestType())) {
            return buildFromRest(response, cacheEntity);
        } else {
            return buildFromSoap(response, cacheEntity);
        }
    }

    /**
     * 基于rest请求的返回结果, 构建返回
     *
     * @param response
     * @param cacheEntity
     * @return
     */
    private SoapResponse buildFromRest(ResponseEntity response, RequestCacheEntity cacheEntity) {
        /**
         * 提取返回数据
         */
        List<String> dataList = httpMessageUtil.getDataFromRestResponse(response.getBody(),
                cacheEntity.getResponseTagName());

        SoapResponse soapResponse = new SoapResponse();
        soapResponse.setStatusCode("S");
        soapResponse.payload = dataList;

        if (response.getStatusCode() != HttpStatus.OK) {
            soapResponse.setStatusCode("E");
        }

        return soapResponse;
    }

    /**
     * 根据目标soap接口的返回，构建透传接口的返回内容
     * 需要根据目标接口返回的http状态来设定透传接口的返回状态
     *
     * @param response
     * @param requestCacheEntity
     * @return
     */
    private SoapResponse buildFromSoap(ResponseEntity response,
                                       RequestCacheEntity requestCacheEntity) {

        SoapResponse soapResponse = new SoapResponse();

        if (response.getStatusCode() == HttpStatus.OK) {
            soapResponse.setStatusCode("S");
            /**
             * 如果设置了数据提取节点, 则进行提取; 否则按原样返回, 不做任何出路
             */
            if (StringUtils.isEmpty(requestCacheEntity.getResponseTagName())) {
                soapResponse.payload.add((String) response.getBody());
            } else {
                soapResponse.payload = httpMessageUtil.getDataFromSoapResponse((String) response.getBody(),
                        requestCacheEntity.getResponseTagName(),
                        ExecuteConstants.ExtractType.XML_NODE);
            }
        } else {
            soapResponse.setStatusCode("E");
            if (StringUtils.isEmpty(requestCacheEntity.getResponseErrorTagName())) {
                soapResponse.setMessage((String) response.getBody());
            } else {
                List<String> errorList = httpMessageUtil.getDataFromSoapResponse((String) response.getBody(),
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
}
