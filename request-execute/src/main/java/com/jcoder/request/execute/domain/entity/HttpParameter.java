package com.jcoder.request.execute.domain.entity;

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

    public HttpParameter() {
        this.uriVariables = new HashMap<>();
        this.headerParams = new HashMap<>();
        this.connectTimeout = 30000;
        this.readTimeout = 30000;
        this.returnType = Object.class;
    }

    /**
     * 合并路径参数
     *
     * @param params
     */
    public void mergeUriVariables(Map<String, Object>... params) {

        if (params != null) {
            for (Map param : params) {
                if (param == null) {
                    continue;
                }
                this.uriVariables.putAll(param);
            }
        }
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

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
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

    public void setHeaderParams(Map<String, String> headerParams) {
        this.headerParams = headerParams;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }
}
