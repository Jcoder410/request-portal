package com.jcoder.request.execute.domain.entity;

import com.jcoder.request.common.util.CommonConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * url注册基础类
 *
 * @author Qiu
 */
public class RequestRegistry {

    /**
     * 路径参数名称列表
     */
    private List<String> pathVariables;

    /**
     * 自定义的请求url
     */
    private String baseRequestUrl;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求类型: SOAP、REST
     */
    private String requestType;

    /**
     * 返回的数据格式, 对应header里面contentType中的格式
     */
    private String[] produces;

    /**
     * 支持的入参格式, 对应header里面contentType中的格式
     */
    private String[] consumes;

    public List<String> getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(List<String> pathVariables) {
        this.pathVariables = pathVariables;
    }

    public String getBaseRequestUrl() {
        return baseRequestUrl;
    }

    public void setBaseRequestUrl(String baseRequestUrl) {

        this.baseRequestUrl = baseRequestUrl;

        /**
         * 解析获得pathVariable
         */
        String[] urlSnippets = baseRequestUrl.split(CommonConstants.SpecialSymbol.FORWARD_SLASH);
        for (String pathSnippet : urlSnippets) {
            if (pathSnippet.startsWith(CommonConstants.SpecialSymbol.OPENING_BRACE)
                    && pathSnippet.endsWith(CommonConstants.SpecialSymbol.CLOSING_BRACE)) {

                if (this.pathVariables == null) {
                    this.pathVariables = new ArrayList<>();
                }
                this.pathVariables.add(pathSnippet.substring(1, pathSnippet.length() - 1));
            }
        }

    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String[] getProduces() {
        return produces;
    }

    public void setProduces(String[] produces) {
        this.produces = produces;
    }

    public String[] getConsumes() {
        return consumes;
    }

    public void setConsumes(String[] consumes) {
        this.consumes = consumes;
    }
}
