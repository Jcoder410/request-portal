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
 * soap请求需要维护的信息
 *
 * @author Qiu
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@TableName(value = "reqp_request_soap")
public class RequestSoap extends BaseEntity {

    public static final String FIELD_REQUEST_SOAP_ID = "requestSoapId";
    public static final String FIELD_REQUEST_COMMON_ID = "requestCommonId";
    public static final String FIELD_PARAM_TEMPLATE = "paramTemplate";
    public static final String FIELD_PARAM_NODE_NAME = "paramNodeName";
    public static final String FIELD_SOAP_VERSION = "soapVersion";
    public static final String FIELD_SOAP_ACTION = "soapAction";
    public static final String FIELD_RETURN_DATA_NODE = "returnDataNode";
    public static final String FIELD_RETURN_ERR_NODE = "returnErrNode";
    public static final String FIELD_LIST_NODE_NAME = "listNodeName";


    @TableId(type = IdType.AUTO)
    private Long requestSoapId;

    @NotNull
    @TableField
    private Long requestCommonId;

    @TableField
    private String paramTemplate;

    @TableField
    private String paramNodeName;

    @NotBlank
    @TableField
    private String soapVersion;

    @TableField
    private String soapAction;

    @TableField
    private String returnDataNode;

    @TableField
    private String returnErrNode;

    @TableField
    private String listNodeName;

    /**
     * @return 表ID，主键
     */
    public Long getRequestSoapId() {
        return requestSoapId;
    }

    public RequestSoap setRequestSoapId(Long requestSoapId) {
        this.requestSoapId = requestSoapId;
        return this;
    }

    /**
     * @return 基础信息ID
     */
    public Long getRequestCommonId() {
        return requestCommonId;
    }

    public RequestSoap setRequestCommonId(Long requestCommonId) {
        this.requestCommonId = requestCommonId;
        return this;
    }

    /**
     * @return 参数模板
     */
    public String getParamTemplate() {
        return paramTemplate;
    }

    public RequestSoap setParamTemplate(String paramTemplate) {
        this.paramTemplate = paramTemplate;
        return this;
    }

    /**
     * @return 参数节点名称
     */
    public String getParamNodeName() {
        return paramNodeName;
    }

    public RequestSoap setParamNodeName(String paramNodeName) {
        this.paramNodeName = paramNodeName;
        return this;
    }

    /**
     * @return SOAP协议的版本
     */
    public String getSoapVersion() {
        return soapVersion;
    }

    public RequestSoap setSoapVersion(String soapVersion) {
        this.soapVersion = soapVersion;
        return this;
    }

    /**
     * @return
     */
    public String getSoapAction() {
        return soapAction;
    }

    public RequestSoap setSoapAction(String soapAction) {
        this.soapAction = soapAction;
        return this;
    }

    /**
     * @return 返回报文数据节点名称
     */
    public String getReturnDataNode() {
        return returnDataNode;
    }

    public RequestSoap setReturnDataNode(String returnDataNode) {
        this.returnDataNode = returnDataNode;
        return this;
    }

    /**
     * @return 错误信息存放节点
     */
    public String getReturnErrNode() {
        return returnErrNode;
    }

    public RequestSoap setReturnErrNode(String returnErrNode) {
        this.returnErrNode = returnErrNode;
        return this;
    }

    /**
     * @return 集合数据节点名称
     */
    public String getListNodeName() {
        return listNodeName;
    }

    public RequestSoap setListNodeName(String listNodeName) {
        this.listNodeName = listNodeName;
        return this;
    }
}
