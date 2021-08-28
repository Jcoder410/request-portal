package com.jcoder.request.common;

import java.util.Map;

/**
 * 请求设置缓存实体类
 *
 * @author Qiu
 */
public class RequestCacheEntity {

    /**
     * 请求唯一识别吗
     */
    private String requestCode;

    /**
     * 实际接口的访问地址
     */
    private String targetRequestUrl;

    /**
     * 实际接口的类型,对应rest和soap
     */
    private String targetRequestType;

    /**
     * 实际接口的请求方式, 对应get、post、delete等
     */
    private String targetRequestMethod;

    /**
     * 路径参数
     */
    private Map<String, Object> defaultPathVariables;

    /**
     * 默认的请求地址参数
     */
    private Map<String, Object> defaultRequestParams;

    /**
     * 设置的请求头参数
     */
    private Map<String, String> defaultRequestHeaders;

    /**
     * soap报文模板, 用于组装完整的soap请求报文
     */
    private String soapTemplate;

    /**
     * soap模板中用于存放数据的节点名称
     */
    private String paramNodeName;

    /**
     * 数据节点名称, 集合参数下的节点名称
     */
    private String dataTagName;

    /**
     * soap协议版本
     */
    private String soapVersion;

    private String soapAction;

    /**
     * 返回报文中的数据节点名称
     */
    private String responseTagName;

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getTargetRequestUrl() {
        return targetRequestUrl;
    }

    public void setTargetRequestUrl(String targetRequestUrl) {
        this.targetRequestUrl = targetRequestUrl;
    }

    public String getTargetRequestType() {
        return targetRequestType;
    }

    public void setTargetRequestType(String targetRequestType) {
        this.targetRequestType = targetRequestType;
    }

    public String getTargetRequestMethod() {
        return targetRequestMethod;
    }

    public void setTargetRequestMethod(String targetRequestMethod) {
        this.targetRequestMethod = targetRequestMethod;
    }

    public Map<String, Object> getDefaultPathVariables() {
        return defaultPathVariables;
    }

    public void setDefaultPathVariables(Map<String, Object> defaultPathVariables) {
        this.defaultPathVariables = defaultPathVariables;
    }

    public Map<String, Object> getDefaultRequestParams() {
        return defaultRequestParams;
    }

    public void setDefaultRequestParams(Map<String, Object> defaultRequestParams) {
        this.defaultRequestParams = defaultRequestParams;
    }

    public Map<String, String> getDefaultRequestHeaders() {
        return defaultRequestHeaders;
    }

    public void setDefaultRequestHeaders(Map<String, String> defaultRequestHeaders) {
        this.defaultRequestHeaders = defaultRequestHeaders;
    }

    public String getSoapTemplate() {
        return soapTemplate;
    }

    public void setSoapTemplate(String soapTemplate) {
        this.soapTemplate = soapTemplate;
    }

    public String getParamNodeName() {
        return paramNodeName;
    }

    public void setParamNodeName(String paramNodeName) {
        this.paramNodeName = paramNodeName;
    }

    public String getDataTagName() {
        return dataTagName;
    }

    public void setDataTagName(String dataTagName) {
        this.dataTagName = dataTagName;
    }

    public String getSoapVersion() {
        return soapVersion;
    }

    public void setSoapVersion(String soapVersion) {
        this.soapVersion = soapVersion;
    }

    public String getSoapAction() {
        return soapAction;
    }

    public void setSoapAction(String soapAction) {
        this.soapAction = soapAction;
    }

    public String getResponseTagName() {
        return responseTagName;
    }

    public void setResponseTagName(String responseTagName) {
        this.responseTagName = responseTagName;
    }
}
