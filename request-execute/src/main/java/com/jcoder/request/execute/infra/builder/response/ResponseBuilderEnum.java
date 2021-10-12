package com.jcoder.request.execute.infra.builder.response;

import com.jcoder.request.execute.infra.ExecuteConstants;

/**
 * 参数构建类与透传接口之间的对应关系
 *
 * @author Qiu
 */

public enum ResponseBuilderEnum {

    REST_BUILDER(ExecuteConstants.RequestType.REST, ExecuteConstants.RequestType.REST, "restRespBuilder"),
    SOAP_BUILDER(ExecuteConstants.RequestType.SOAP, ExecuteConstants.RequestType.SOAP, "soapRespBuilder"),
    REST_BUILDER_BASE_SOAP(ExecuteConstants.RequestType.SOAP, ExecuteConstants.RequestType.REST, "soapRespBuilderBaseMap"),
    SOAP_BUILDER_BASE_REST(ExecuteConstants.RequestType.REST, ExecuteConstants.RequestType.SOAP, "restRespBuilderBaseXml");

    private String invokeType;

    private String sourceType;

    private String beanName;

    private ResponseBuilderEnum(String invokeType, String sourceType, String beanName) {
        this.invokeType = invokeType;
        this.sourceType = sourceType;
        this.beanName = beanName;
    }

    /**
     * 获取bean名称
     *
     * @param invokeType
     * @param sourceType
     * @return
     */
    public static String getBuilderName(String invokeType, String sourceType) {

        String beanName = "";

        for (ResponseBuilderEnum builder : ResponseBuilderEnum.values()) {

            if (invokeType.equals(builder.getInvokeType()) && sourceType.equals(builder.getSourceType())) {
                beanName = builder.getBeanName();
                break;
            }
        }
        return beanName;
    }

    public String getInvokeType() {
        return invokeType;
    }

    public void setInvokeType(String invokeType) {
        this.invokeType = invokeType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
