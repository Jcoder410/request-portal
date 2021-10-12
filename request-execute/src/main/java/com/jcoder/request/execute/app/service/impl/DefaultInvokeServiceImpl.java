package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.common.SetCacheEntity;
import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.common.util.CommonConstants;
import com.jcoder.request.execute.app.service.IDefaultInvokeService;
import com.jcoder.request.execute.app.service.ISetCacheService;
import com.jcoder.request.execute.domain.entity.RestParameter;
import com.jcoder.request.execute.infra.ExecuteConstants;
import com.jcoder.request.execute.infra.executor.RestExecutorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Qiu
 */
@Service
public class DefaultInvokeServiceImpl implements IDefaultInvokeService {

    @Autowired
    private RestExecutorServiceImpl restExecutorService;

    @Autowired
    private ISetCacheService setCacheService;

    @Override
    public ResponseEntity executeInvoke(Map<String, Object> requestParams,
                                        Object requestBody,
                                        String pathVariableStr,
                                        Map<String, String> headerParams) {

        String interfaceCode;
        if (requestParams.containsKey(ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE)) {
            interfaceCode = String.valueOf(requestParams.get(ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE));
        } else {
            throw new CommonException("request.execute.request_code_null", ExecuteConstants.UniqueFieldName.REQUEST_UNIQUE);
        }

        /**
         * 获取接口缓存信息
         */
        SetCacheEntity setCacheEntity = setCacheService.getCacheInfo(interfaceCode);
        Map<String, Object> pathParams = extractPathParams(pathVariableStr, setCacheEntity.getPathVariables());

        RestParameter restParameter = new RestParameter();
        restParameter.setHeaderParams(headerParams);
        restParameter.setPathParams(pathParams);
        restParameter.setRequestBody(requestBody);
        restParameter.setRequestParams(requestParams);

        /**
         * 执行请求
         */
        ResponseEntity response = restExecutorService.execute(restParameter);

        return response;
    }

    /**
     * 提取路径参数
     *
     * @param pathVariableStr
     * @param pathVariableList
     * @return
     */
    private Map<String, Object> extractPathParams(String pathVariableStr, Map<Integer, String> pathVariableList) {

        /**
         * 获取pathVariable部分, 除开默认路径，其他的一律认为是路径参数部分
         */
        Map<String, Object> pathParamMap = new HashMap<>();

        String defaultUrl = "/invoke/default/";
        if (pathVariableStr.length() > defaultUrl.length()) {

            pathVariableStr = pathVariableStr.replace(defaultUrl, "");
            String[] pathVariables = pathVariableStr.split(CommonConstants.SpecialSymbol.FORWARD_SLASH);

            if (pathVariables.length != pathVariableList.size()) {
                throw new CommonException("request.execute.extract_path_param_err");
            }

            for (int i = 0; i < pathVariableList.size(); i++) {
                pathParamMap.put(pathVariableList.get(i), pathVariables[i]);
            }
        }

        if (pathParamMap.size() != pathVariableList.size()) {
            throw new CommonException("request.execute.extract_path_param_err");
        }

        return pathParamMap;
    }
}
