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
 * 请求头信息参数
 *
 * @author Qiu
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName(value = "reqp_request_header")
public class RequestHeader extends BaseEntity {

    public static final String FIELD_REQUEST_HEADER_ID = "requestHeaderId";
    public static final String FIELD_REQUEST_COMMON_ID = "requestCommonId";
    public static final String FIELD_HEADER_NAME = "headerName";
    public static final String FIELD_HEADER_VALUE = "headerValue";
    public static final String FIELD_OVER_RIDE_FLAG = "overRideFlag";


    @TableId(type = IdType.AUTO)
    private Long requestHeaderId;

    @NotNull
    @TableField
    private Long requestCommonId;

    @NotBlank
    @TableField
    private String headerName;

    @NotBlank
    @TableField
    private String headerValue;

    @NotNull
    @TableField
    private Integer overRideFlag;

    /**
     * @return 表ID，主键
     */
    public Long getRequestHeaderId() {
        return requestHeaderId;
    }

    public void setRequestHeaderId(Long requestHeaderId) {
        this.requestHeaderId = requestHeaderId;
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
     * @return 参数名称
     */
    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    /**
     * @return 参数值
     */
    public String getHeaderValue() {
        return headerValue;
    }

    public void setHeaderValue(String headerValue) {
        this.headerValue = headerValue;
    }

    /**
     * @return 允许复写
     */
    public Integer getOverRideFlag() {
        return overRideFlag;
    }

    public void setOverRideFlag(Integer overRideFlag) {
        this.overRideFlag = overRideFlag;
    }
}
