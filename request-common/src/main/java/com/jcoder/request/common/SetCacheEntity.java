package com.jcoder.request.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求设置缓存类
 *
 * @author Qiu
 */
public class SetCacheEntity {

    /**
     * 请求设置代码
     */
    private String requestCode;

    /**
     * 请求设置名称
     */
    private String requestName;

    /**
     * 实际请求的请求方式
     */
    private String sourceMethodCode;

    /**
     * 实际请求的请求地址
     */
    private String sourceRequestUrl;

    /**
     * 实际请求的类型: SOAP、REST
     */
    private String sourceTypeCode;

    /**
     * 返回集合标识
     */
    private Boolean returnListFlag;

    /**
     * 状态代码
     */
    private String statusCode;

    /**
     * 路径参数列表
     */
    private Map<Integer, String> pathVariables;

    /**
     * 路径参数默认值
     */
    private Map<String, ParamSetBaseAttr> pathDefaultValues;

    /**
     * 请求头参数
     */
    private Map<String, ParamSetBaseAttr> headerDefaultValues;

    /**
     * request参数默认值
     */
    private Map<String, ParamSetBaseAttr> requestDefaultValues;

    /**
     * soap参数模板
     */
    private String soapParamTemplate;

    /**
     * soap参数模板中的参数节点
     */
    private String soapParamNodeName;

    /**
     * soap返回报文中的数据节点
     */
    private String returnDataNode;

    /**
     * soap返回报文中的错误节点
     */
    private String returnErrNode;

    /**
     * 集合节点名称
     */
    private String listNodeName;

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getSourceMethodCode() {
        return sourceMethodCode;
    }

    public void setSourceMethodCode(String sourceMethodCode) {
        this.sourceMethodCode = sourceMethodCode;
    }

    public String getSourceRequestUrl() {
        return sourceRequestUrl;
    }

    public void setSourceRequestUrl(String sourceRequestUrl) {
        this.sourceRequestUrl = sourceRequestUrl;
    }

    public String getSourceTypeCode() {
        return sourceTypeCode;
    }

    public void setSourceTypeCode(String sourceTypeCode) {
        this.sourceTypeCode = sourceTypeCode;
    }

    public Boolean getReturnListFlag() {
        return returnListFlag;
    }

    public void setReturnListFlag(Boolean returnListFlag) {
        this.returnListFlag = returnListFlag;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Map<Integer, String> getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(Map<Integer, String> pathVariables) {
        this.pathVariables = pathVariables;
    }

    public Map<String, ParamSetBaseAttr> getPathDefaultValues() {
        return pathDefaultValues;
    }

    public void setPathDefaultValues(Map<String, ParamSetBaseAttr> pathDefaultValues) {
        this.pathDefaultValues = pathDefaultValues;
    }

    public Map<String, ParamSetBaseAttr> getHeaderDefaultValues() {
        return headerDefaultValues;
    }

    public void setHeaderDefaultValues(Map<String, ParamSetBaseAttr> headerDefaultValues) {
        this.headerDefaultValues = headerDefaultValues;
    }

    public Map<String, ParamSetBaseAttr> getRequestDefaultValues() {
        return requestDefaultValues;
    }

    public void setRequestDefaultValues(Map<String, ParamSetBaseAttr> requestDefaultValues) {
        this.requestDefaultValues = requestDefaultValues;
    }

    public String getSoapParamTemplate() {
        return soapParamTemplate;
    }

    public void setSoapParamTemplate(String soapParamTemplate) {
        this.soapParamTemplate = soapParamTemplate;
    }

    public String getSoapParamNodeName() {
        return soapParamNodeName;
    }

    public void setSoapParamNodeName(String soapParamNodeName) {
        this.soapParamNodeName = soapParamNodeName;
    }

    public String getReturnDataNode() {
        return returnDataNode;
    }

    public void setReturnDataNode(String returnDataNode) {
        this.returnDataNode = returnDataNode;
    }

    public String getReturnErrNode() {
        return returnErrNode;
    }

    public void setReturnErrNode(String returnErrNode) {
        this.returnErrNode = returnErrNode;
    }

    public String getListNodeName() {
        return listNodeName;
    }

    public void setListNodeName(String listNodeName) {
        this.listNodeName = listNodeName;
    }
}
