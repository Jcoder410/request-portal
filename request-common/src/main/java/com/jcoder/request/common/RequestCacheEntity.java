package com.jcoder.request.common;

import java.util.List;
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
     * 目标接口返回集合数据标识, 用于处理soap接口透传成rest接口的情况
     */
    private Boolean targetReturnListFlag;

    /**
     * 路径参数名称列表
     */
    private List<String> pathVariableList;

    /**
     * 路径参数值
     */
    private Map<String, Object> defaultPathVariables;

    /**
     * 默认的请求地址参数值
     */
    private Map<String, Object> defaultRequestParams;

    /**
     * 设置的请求头参数值
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
     * 数据节点名称
     * soap接口透传成rest接口是, 传入的参数如果是一个集合, 则需要指定每一个数据对象对应的xml节点名称
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

    public Boolean getTargetReturnListFlag() {
        return targetReturnListFlag;
    }

    public void setTargetReturnListFlag(Boolean targetReturnListFlag) {
        this.targetReturnListFlag = targetReturnListFlag;
    }

    public List<String> getPathVariableList() {
        return pathVariableList;
    }

    public void setPathVariableList(List<String> pathVariableList) {
        this.pathVariableList = pathVariableList;
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
