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
 * request parameter参数
 *
 * @author Qiu
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName(value = "reqp_request_param")
public class RequestParam extends BaseEntity {

    public static final String FIELD_REQUEST_PARAM_ID = "requestParamId";
    public static final String FIELD_REQUEST_COMMON_ID = "requestCommonId";
    public static final String FIELD_PARAM_NAME = "paramName";
    public static final String FIELD_DEFAULT_VALUE = "defaultValue";
    public static final String FIELD_OVER_RIDE = "overRide";
    public static final String FIELD_NOT_NULL_FLAG = "notNullFlag";


    @TableId(type = IdType.AUTO)
    private Long requestParamId;

    @NotNull
    @TableField
    private Long requestCommonId;

    @NotBlank
    @TableField
    private String paramName;

    @TableField
    private String defaultValue;

    @NotNull
    @TableField
    private Integer overRide;

    @NotNull
    @TableField
    private Integer notNullFlag;

    /**
     * @return 表ID，主键
     */
    public Long getRequestParamId() {
        return requestParamId;
    }

    public void setRequestParamId(Long requestParamId) {
        this.requestParamId = requestParamId;
    }

    /**
     * @return 基础信息ID
     */
    public Long getRequestCommonId() {
        return requestCommonId;
    }

    public void setRequestCommonId(Long requestCommonId) {
        this.requestCommonId = requestCommonId;
    }

    /**
     * @return 请求参数名称
     */
    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * @return 参数默认值
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return 允许复写
     */
    public Integer getOverRide() {
        return overRide;
    }

    public void setOverRide(Integer overRide) {
        this.overRide = overRide;
    }

    /**
     * @return 允许为空
     */
    public Integer getNotNullFlag() {
        return notNullFlag;
    }

    public void setNotNullFlag(Integer notNullFlag) {
        this.notNullFlag = notNullFlag;
    }
}
