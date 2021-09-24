package com.jcoder.request.common;

/**
 * 请求参数设置的基本属性
 *
 * @author Qiu
 */
public class ParamSetBaseAttr {

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 参数默认值
     */
    private String paramValue;

    /**
     * 允许复写标识
     */
    private Boolean overrideFlag;

    /**
     * 不能为空标识
     */
    private Boolean notNullFlag;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Boolean getOverrideFlag() {
        return overrideFlag;
    }

    public void setOverrideFlag(Boolean overrideFlag) {
        this.overrideFlag = overrideFlag;
    }

    public Boolean getNotNullFlag() {
        return notNullFlag;
    }

    public void setNotNullFlag(Boolean notNullFlag) {
        this.notNullFlag = notNullFlag;
    }
}
