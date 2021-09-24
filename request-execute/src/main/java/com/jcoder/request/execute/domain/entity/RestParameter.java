package com.jcoder.request.execute.domain.entity;

import java.util.Map;

/**
 * rest请求参数包装类
 *
 * @author Qiu
 */
public class RestParameter extends BaseRequestParam {

    private Map<String, Object> requestParams;

    private Object requestBody;

    private Map<String, Object> pathParams;

    private Map<String, String> headerParams;

    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, Object> requestParams) {
        this.requestParams = requestParams;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
    }

    public Map<String, Object> getPathParams() {
        return pathParams;
    }

    public void setPathParams(Map<String, Object> pathParams) {
        this.pathParams = pathParams;
    }

    public Map<String, String> getHeaderParams() {
        return headerParams;
    }

    public void setHeaderParams(Map<String, String> headerParams) {
        this.headerParams = headerParams;
    }
}
