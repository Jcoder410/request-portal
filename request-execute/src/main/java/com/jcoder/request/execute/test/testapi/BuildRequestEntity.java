package com.jcoder.request.execute.test.testapi;

import com.jcoder.request.common.RequestCacheEntity;
import com.jcoder.request.execute.infra.ExecuteConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * 构建接口透传的实体对象数据
 *
 * @author Qiu
 */
public class BuildRequestEntity {

    public static Map<String, RequestCacheEntity> buildInvokeEntity() {
        Map<String, RequestCacheEntity> invokeEntityMap = new HashMap<>();
        invokeEntityMap.put("RestInvoke", getRestEntity());
        invokeEntityMap.put("RestInvokeBaseOnSoap", getRestEntityBaseOnSoap());
        invokeEntityMap.put("SoapInvoke", getSoapEntity());
        invokeEntityMap.put("SoapInvokeBaseOnRest", getSoapEntityBaseOnRest());

        return invokeEntityMap;
    }

    /**
     * 获取rest接口透传的请求对象
     *
     * @return
     */
    public static RequestCacheEntity getRestEntity() {

        Map<String, String> defaultRequestHeader = new HashMap<>();
        defaultRequestHeader.put("Content-Type", "application/json");

        RequestCacheEntity requestCacheEntity = new RequestCacheEntity();
        requestCacheEntity.setRequestCode("RestInvoke");
        requestCacheEntity.setTargetRequestMethod("POST");
        requestCacheEntity.setTargetRequestType("REST");
        requestCacheEntity.setTargetRequestUrl("http://localhost:9080/test/person/{age}");
        requestCacheEntity.setDefaultPathVariables(null);
        requestCacheEntity.setDefaultRequestHeaders(defaultRequestHeader);
        requestCacheEntity.setDefaultRequestParams(null);

        requestCacheEntity.setDataTagName(null);
        requestCacheEntity.setParamNodeName(null);
        requestCacheEntity.setResponseTagName(null);
        requestCacheEntity.setSoapAction(null);
        requestCacheEntity.setSoapVersion(null);
        requestCacheEntity.setSoapTemplate(null);

        return requestCacheEntity;
    }

    /**
     * 获取soap接口透传成rest的请求对象
     *
     * @return
     */
    public static RequestCacheEntity getRestEntityBaseOnSoap() {

        RequestCacheEntity requestCacheEntity = new RequestCacheEntity();

        requestCacheEntity.setRequestCode("RestInvokeBaseOnSoap");
        requestCacheEntity.setTargetRequestMethod("");
        requestCacheEntity.setTargetRequestType("SOAP");
        requestCacheEntity.setTargetRequestUrl("http://localhost:9080/webservice/test/person?wsdl");
        requestCacheEntity.setDefaultPathVariables(null);
        requestCacheEntity.setDefaultRequestHeaders(null);
        requestCacheEntity.setDefaultRequestParams(null);

        String template = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:app=\"http://app.test.execute.request.jcoder.com/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <app:singlePerson>\n" +
                "      </app:singlePerson>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        requestCacheEntity.setDataTagName("");
        requestCacheEntity.setParamNodeName("app:singlePerson");
        requestCacheEntity.setResponseTagName("return");
        requestCacheEntity.setSoapAction(null);
        requestCacheEntity.setSoapVersion(ExecuteConstants.SoapVersion.SOAP_1_POINT_1);
        requestCacheEntity.setSoapTemplate(template);

        return requestCacheEntity;
    }

    /**
     * 构建soap接口透传的请求对象
     *
     * @return
     */
    public static RequestCacheEntity getSoapEntity() {
        RequestCacheEntity requestCacheEntity = new RequestCacheEntity();

        requestCacheEntity.setRequestCode("SoapInvoke");
        requestCacheEntity.setTargetRequestMethod("");
        requestCacheEntity.setTargetRequestType("SOAP");
        requestCacheEntity.setTargetRequestUrl("http://localhost:9080/webservice/test/person?wsdl");
        requestCacheEntity.setDefaultPathVariables(null);
        requestCacheEntity.setDefaultRequestHeaders(null);
        requestCacheEntity.setDefaultRequestParams(null);

        String template = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:app=\"http://app.test.execute.request.jcoder.com/\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <app:singlePerson>\n" +
                "      </app:singlePerson>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        requestCacheEntity.setDataTagName(null);
        requestCacheEntity.setParamNodeName("app:singlePerson");
        requestCacheEntity.setResponseTagName("return");
        requestCacheEntity.setSoapAction(null);
        requestCacheEntity.setSoapVersion(ExecuteConstants.SoapVersion.SOAP_1_POINT_1);
        requestCacheEntity.setSoapTemplate(template);

        return requestCacheEntity;
    }

    /**
     * 构建soap接口透传的请求对象
     *
     * @return
     */
    public static RequestCacheEntity getSoapEntityBaseOnRest() {
        Map<String, String> defaultRequestHeader = new HashMap<>();
        defaultRequestHeader.put("Content-Type", "application/json");

        RequestCacheEntity requestCacheEntity = new RequestCacheEntity();
        requestCacheEntity.setRequestCode("SoapInvokeBaseOnRest");
        requestCacheEntity.setTargetRequestMethod("POST");
        requestCacheEntity.setTargetRequestType("REST");
        requestCacheEntity.setTargetRequestUrl("http://localhost:9080/test/person/{age}");
        requestCacheEntity.setDefaultPathVariables(null);
        requestCacheEntity.setDefaultRequestHeaders(defaultRequestHeader);
        requestCacheEntity.setDefaultRequestParams(null);

        requestCacheEntity.setDataTagName(null);
        requestCacheEntity.setParamNodeName(null);
        requestCacheEntity.setResponseTagName("person");
        requestCacheEntity.setSoapAction(null);
        requestCacheEntity.setSoapVersion(ExecuteConstants.SoapVersion.SOAP_1_POINT_1);
        requestCacheEntity.setSoapTemplate(null);

        return requestCacheEntity;
    }

}
