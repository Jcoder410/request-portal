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
}
