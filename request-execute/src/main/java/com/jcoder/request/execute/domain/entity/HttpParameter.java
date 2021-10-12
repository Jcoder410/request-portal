package com.jcoder.request.execute.domain.entity;

import com.jcoder.request.common.ParamSetBaseAttr;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Qiu
 */
public class HttpParameter {

    private String url;

    private String methodCode;

    private Object requestBody;

    private Map<String, Object> uriVariables;

    private Map<String, String> headerParams;

    private int connectTimeout;

    private int readTimeout;

    private Class returnType;

    private Map<String, Object> pathVariables;

    private Map<String, Object> requestParams;

    public HttpParameter() {
        this.uriVariables = new HashMap<>();
        this.headerParams = new HashMap<>();
        this.connectTimeout = 30000;
        this.readTimeout = 30000;
        this.returnType = Object.class;
    }

    /**
     * 获取完整的路径参数: 即合并pathVariables和requestParameter
     *
     * @return
     */
    public void buildUriVariables() {

        this.uriVariables.putAll(this.requestParams);
        this.uriVariables.putAll(this.pathVariables);
    }

    /**
     * 构建完整的路径参数
     *
     * @param defaultSetMap
     * @return
     */
    public HttpParameter mergeDefaultPathParam(Map<String, ParamSetBaseAttr> defaultSetMap) {
        merge(defaultSetMap, this.pathVariables);
        return this;
    }

    /**
     * 构建完整的请求参数
     *
     * @param defaultSetMap
     * @return
     */
    public HttpParameter mergeDefaultRequestParam(Map<String, ParamSetBaseAttr> defaultSetMap) {
        merge(defaultSetMap, this.requestParams);
        return this;
    }

    /**
     * 合并
     *
     * @param defaultSetMap
     * @param inboundParams
     */
    private void merge(Map<String, ParamSetBaseAttr> defaultSetMap,
                       Map<String, Object> inboundParams) {
        for (String param : defaultSetMap.keySet()) {
            ParamSetBaseAttr paramSetBaseAttr = defaultSetMap.get(param);
            if (inboundParams.containsKey(param)) {
                if (!paramSetBaseAttr.getOverrideFlag()) {
                    inboundParams.put(param, paramSetBaseAttr.getParamValue());
                }
            } else {
                inboundParams.put(param, paramSetBaseAttr.getParamValue());
            }
        }
    }

    /**
     * 构建完整的请求头参数
     *
     * @param defaultSetMap
     * @return
     */
    public HttpParameter mergeDefaultHeaderParam(Map<String, ParamSetBaseAttr> defaultSetMap) {

        /**
         * 移除掉多余的传入参数
         */
        Map<String,String> cleanMap = new HashMap<>();
        for (String paramName : this.headerParams.keySet()) {
            if (defaultSetMap.containsKey(paramName)) {
                cleanMap.put(paramName,this.headerParams.get(paramName));
            }
        }
        this.headerParams = cleanMap;

        for (String param : defaultSetMap.keySet()) {
            ParamSetBaseAttr paramSetBaseAttr = defaultSetMap.get(param);
            if (this.headerParams.containsKey(param)) {
                if (!paramSetBaseAttr.getOverrideFlag()) {
                    this.headerParams.put(param, paramSetBaseAttr.getParamValue());
                }
            } else {
                this.headerParams.put(param, paramSetBaseAttr.getParamValue());
            }
        }
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(String methodCode) {
        this.methodCode = methodCode;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    public HttpParameter setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public Map<String, Object> getUriVariables() {
        return uriVariables;
    }

    public void setUriVariables(Map<String, Object> uriVariables) {
        this.uriVariables = uriVariables;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Map<String, String> getHeaderParams() {
        return headerParams;
    }

    public HttpParameter setHeaderParams(Map<String, String> headerParams) {
        this.headerParams = headerParams;
        return this;
    }

    public Class getReturnType() {
        return returnType;
    }

    public HttpParameter setReturnType(Class returnType) {
        this.returnType = returnType;
        return this;
    }

    public Map<String, Object> getPathVariables() {
        return pathVariables;
    }

    public HttpParameter setPathVariables(Map<String, Object> pathVariables) {
        this.pathVariables = pathVariables;
        return this;
    }

    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    public HttpParameter setRequestParams(Map<String, Object> requestParams) {
        this.requestParams = requestParams;
        return this;
    }
}
