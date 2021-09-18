package com.jcoder.request.set.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jcoder.request.common.BaseEntity;
import org.springframework.context.annotation.Description;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 请求路径参数
 *
 * @author Qiu
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName(value = "reqp_request_pathvariable")
public class RequestPathvariable extends BaseEntity {

    public static final String FIELD_PATH_VARIABLE_ID = "pathVariableId";
    public static final String FIELD_REQUEST_COMMON_ID = "requestCommonId";
    public static final String FIELD_PARAM_NAME = "paramName";
    public static final String FIELD_DEFAULT_VALUE = "defaultValue";
    public static final String FIELD_OVER_RIDE = "overRide";


    @TableId(type = IdType.AUTO)
    private Long pathVariableId;

    @NotNull
    @TableField
    private Long requestCommonId;

    @NotNull
    @TableField
    private Integer sequenceNum;

    @NotBlank
    @TableField
    private String paramName;

    @TableField
    private String defaultValue;

    @NotNull
    @TableField
    private Integer overRide;

    /**
     * @return 表ID，主键
     */
    public Long getPathVariableId() {
        return pathVariableId;
    }

    public void setPathVariableId(Long pathVariableId) {
        this.pathVariableId = pathVariableId;
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

    public Integer getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(Integer sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    /**
     * @return 路径参数名称
     */
    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * @return 路径参数默认值
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
}
