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
 * 自定义url信息
 *
 * @author Qiu
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName(value = "reqp_request_url")
public class RequestUrl extends BaseEntity {

    public static final String FIELD_REQUEST_URL_ID = "requestUrlId";
    public static final String FIELD_REQUEST_COMMON_ID = "requestCommonId";
    public static final String FIELD_REQUEST_METHOD = "requestMethod";
    public static final String FIELD_REQUEST_URL = "requestUrl";
    public static final String FIELD_MATCH_STRING = "matchString";


    @TableId(type = IdType.AUTO)
    private Long requestUrlId;

    @NotNull
    @TableField
    private Long requestCommonId;

    @NotBlank
    @TableField
    private String requestMethod;

    @NotBlank
    @TableField
    private String requestUrl;

    @NotBlank
    @TableField
    private String matchString;

    /**
     * @return 表ID，主键
     */
    public Long getRequestUrlId() {
        return requestUrlId;
    }

    public RequestUrl setRequestUrlId(Long requestUrlId) {
        this.requestUrlId = requestUrlId;
        return this;
    }

    /**
     * @return 基础信息ID
     */
    public Long getRequestCommonId() {
        return requestCommonId;
    }

    public RequestUrl setRequestCommonId(Long requestCommonId) {
        this.requestCommonId = requestCommonId;
        return this;
    }

    /**
     * @return 请求方式代码
     */
    public String getRequestMethod() {
        return requestMethod;
    }

    public RequestUrl setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
        return this;
    }

    /**
     * @return 请求的url
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    public RequestUrl setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
        return this;
    }

    /**
     * @return url匹配字符串
     */
    public String getMatchString() {
        return matchString;
    }

    public RequestUrl setMatchString(String matchString) {
        this.matchString = matchString;
        return this;
    }
}
