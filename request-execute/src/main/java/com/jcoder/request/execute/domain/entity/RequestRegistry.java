package com.jcoder.request.execute.domain.entity;

import com.jcoder.request.common.util.CommonConstants;

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
     * 获取完整的请求地址, 将路径参数部分添加到url
     *
     * @return
     */
    public String getRequestUrl() {

        if(pathVariables == null){
            return baseRequestUrl;
        }

        StringBuilder paramsStr = new StringBuilder();
        for (String param : pathVariables) {
            if (paramsStr.length() == 0) {
                paramsStr.append(CommonConstants.SpecialSymbol.OPENING_BRACE)
                        .append(param)
                        .append(CommonConstants.SpecialSymbol.CLOSING_BRACE);
            } else {
                paramsStr.append(CommonConstants.SpecialSymbol.FORWARD_SLASH)
                        .append(CommonConstants.SpecialSymbol.OPENING_BRACE)
                        .append(param)
                        .append(CommonConstants.SpecialSymbol.CLOSING_BRACE);
            }
        }
        return paramsStr.length() == 0 ? baseRequestUrl : baseRequestUrl + CommonConstants.SpecialSymbol.FORWARD_SLASH + paramsStr.toString();

    }

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
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
}
