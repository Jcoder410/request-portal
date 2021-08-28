package com.jcoder.request.execute.infra;

/**
 * @author Qiu
 */
public class ExecuteConstants {

    private ExecuteConstants() {
    }

    /**
     * http请求方式
     */
    public interface RequestMethod {
        String GET = "get";

        String POST = "post";

        String DELETE = "delete";

        String PUT = "put";
    }

    /**
     * 与DynamicRequestController中的方法名保持一致
     */
    public interface RequestHandleMethod {

        String COMMON_HANDLER = "commonMethod";
    }

    /**
     * SOAP协议版本
     */
    public interface SoapVersion {

        String SOAP_1_POINT_1 = "soap1.1";

        String SOAP_1_POINT_2 = "soap1.2";
    }

    public interface RequestType {

        String SOAP = "SOAP";

        String REST = "REST";
    }

    /**
     * 唯一性性字段名称
     */
    public interface UniqueFieldName {
        String REQUEST_UNIQUE = "interfaceCode";
    }
}
