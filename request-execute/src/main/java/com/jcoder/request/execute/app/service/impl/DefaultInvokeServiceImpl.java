package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.common.RequestCacheEntity;
import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.common.util.CommonConstants;
import com.jcoder.request.execute.app.service.IDefaultInvokeService;
import com.jcoder.request.execute.app.service.IRequestInfoService;
import com.jcoder.request.execute.app.service.IRequestInvokeService;
import com.jcoder.request.execute.infra.ExecuteConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Qiu
 */
@Service
public class DefaultInvokeServiceImpl implements IDefaultInvokeService {

    @Autowired
    private IRequestInvokeService requestInvokeService;

    @Autowired
    private IRequestInfoService requestInfoService;

    @Override
    public ResponseEntity executeInvoke(Map<String, Object> requestParams,
                                        Object requestBody,
                                        String pathVariableStr,
                                        Map<String, Object> headerParams) {

        String interfaceCode;
        if (requestParams.containsKey(ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE)) {
            interfaceCode = String.valueOf(requestParams.get(ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE));
        } else {
            throw new CommonException("request.execute.request_code_null", ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);
        }

        /**
         * 获取接口缓存信息
         */
        RequestCacheEntity requestCacheEntity = requestInfoService.getRequestInfo(interfaceCode);
        List<String> pathVariableList = requestCacheEntity.getPathVariableList();

        /**
         * 提取pathVariable
         */
        Map<String, Object> pathParams = extractPathParams(pathVariableStr, pathVariableList);

        /**
         * 执行请求
         */
        ResponseEntity responseEntity = requestInvokeService.restInvoke(requestParams,
                requestBody,
                pathParams,
                headerParams);

        return responseEntity;
    }

    /**
     * 提取路径参数
     *
     * @param pathVariableStr
     * @param pathVariableList
     * @return
     */
    private Map<String, Object> extractPathParams(String pathVariableStr, List<String> pathVariableList) {

        String[] pathVariables = pathVariableStr.split(CommonConstants.SpecialSymbol.FORWARD_SLASH);
        if (pathVariables.length != pathVariableList.size()) {
            throw new CommonException("request.execute.extract_path_param_err");
        }

        Map<String, Object> pathParamMap = new HashMap<>();
        for (int i = 0; i < pathVariableList.size(); i++) {
            pathParamMap.put(pathVariableList.get(i), pathVariables[i]);
        }

        return pathParamMap;
    }
}
