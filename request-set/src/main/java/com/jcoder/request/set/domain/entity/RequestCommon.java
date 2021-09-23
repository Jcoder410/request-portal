package com.jcoder.request.set.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jcoder.request.common.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 注册接口的基本信息
 *
 * @author Qiu
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName(value = "reqp_request_common")
public class RequestCommon extends BaseEntity {

    public static final String FIELD_REQUEST_COMMON_ID = "requestCommonId";
    public static final String FIELD_REQUEST_CODE = "requestCode";
    public static final String FIELD_REQUEST_NAME = "requestName";
    public static final String FIELD_REQUEST_DESC = "requestDesc";
    public static final String FIELD_SOURCE_METHOD_CODE = "sourceMethodCode";
    public static final String FIELD_SOURCE_TYPE_CODE = "sourceTypeCode";
    public static final String FIELD_SOURCE_REQUEST_URL = "sourceRequestUrl";
    public static final String FIELD_INVOKE_TYPE_CODE = "invokeTypeCode";
    public static final String FIELD_INVOKE_URL = "invokeUrl";
    public static final String FIELD_INVOKE_URL_TYPE = "invokeUrlType";
    public static final String FIELD_RETURN_LIST_FLAG = "returnListFlag";
    public static final String FIELD_STATUS_CODE = "statusCode";


    @TableId(type = IdType.AUTO)
    private Long requestCommonId;

    @NotBlank
    @TableField
    private String requestCode;

    @NotBlank
    @TableField
    private String requestName;

    @TableField
    private String requestDesc;

    @NotBlank
    @TableField
    private String sourceMethodCode;

    @NotBlank
    @TableField
    private String sourceTypeCode;

    @NotBlank
    @TableField
    private String sourceRequestUrl;

    @NotBlank
    @TableField
    private String invokeTypeCode;

    @NotBlank
    @TableField
    private String invokeUrl;

    @NotBlank
    @TableField
    private String invokeUrlType;

    @NotNull
    @TableField
    private Integer returnListFlag;

    @NotNull
    @TableField
    private String statusCode;

    /**
     * @return 表ID，主键
     */
    public Long getRequestCommonId() {
        return requestCommonId;
    }

    public void setRequestCommonId(Long requestCommonId) {
        this.requestCommonId = requestCommonId;
    }

    /**
     * @return 请求代码
     */
    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    /**
     * @return 请求名称
     */
    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    /**
     * @return 请求描述
     */
    public String getRequestDesc() {
        return requestDesc;
    }

    public void setRequestDesc(String requestDesc) {
        this.requestDesc = requestDesc;
    }

    /**
     * @return 实际接口的请求方法
     */
    public String getSourceMethodCode() {
        return sourceMethodCode;
    }

    public void setSourceMethodCode(String sourceMethodCode) {
        this.sourceMethodCode = sourceMethodCode;
    }

    /**
     * @return 实际接口类型:SOAP或REST
     */
    public String getSourceTypeCode() {
        return sourceTypeCode;
    }

    public void setSourceTypeCode(String sourceTypeCode) {
        this.sourceTypeCode = sourceTypeCode;
    }

    /**
     * @return 实际接口地址
     */
    public String getSourceRequestUrl() {
        return sourceRequestUrl;
    }

    public void setSourceRequestUrl(String sourceRequestUrl) {
        this.sourceRequestUrl = sourceRequestUrl;
    }

    /**
     * @return 透传接口类型:SOAP或REST
     */
    public String getInvokeTypeCode() {
        return invokeTypeCode;
    }

    public void setInvokeTypeCode(String invokeTypeCode) {
        this.invokeTypeCode = invokeTypeCode;
    }

    /**
     * @return 透传接口地址
     */
    public String getInvokeUrl() {
        return invokeUrl;
    }

    public void setInvokeUrl(String invokeUrl) {
        this.invokeUrl = invokeUrl;
    }

    /**
     * @return 透传地址类型:默认或自定义
     */
    public String getInvokeUrlType() {
        return invokeUrlType;
    }

    public void setInvokeUrlType(String invokeUrlType) {
        this.invokeUrlType = invokeUrlType;
    }

    /**
     * @return 返回集合标识
     */
    public Integer getReturnListFlag() {
        return returnListFlag;
    }

    public void setReturnListFlag(Integer returnListFlag) {
        this.returnListFlag = returnListFlag;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
