package com.jcoder.request.execute.infra.util;

import com.jcoder.request.common.util.CommonConstants;
import com.jcoder.request.execute.infra.ExecuteConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;

public class RequestToolUtil {

    /**
     * 获取请求方式，默认GET
     *
     * @param method
     * @return
     */
    public static RequestMethod getRequestMethod(String method) {

        switch (method.toLowerCase()) {
            case ExecuteConstants.RequestMethod.GET:
                return RequestMethod.GET;
            case ExecuteConstants.RequestMethod.POST:
                return RequestMethod.POST;
            case ExecuteConstants.RequestMethod.PUT:
                return RequestMethod.PUT;
            case ExecuteConstants.RequestMethod.DELETE:
                return RequestMethod.DELETE;
            default:
                return null;
        }
    }

    /**
     * 获取请求方式，默认GET
     *
     * @param method
     * @return
     */
    public static HttpMethod getHttpMethod(String method) {

        switch (method.toLowerCase()) {
            case ExecuteConstants.RequestMethod.GET:
                return HttpMethod.GET;
            case ExecuteConstants.RequestMethod.POST:
                return HttpMethod.POST;
            case ExecuteConstants.RequestMethod.PUT:
                return HttpMethod.PUT;
            case ExecuteConstants.RequestMethod.DELETE:
                return HttpMethod.DELETE;
            default:
                return null;
        }
    }

    /**
     * 用于获取xml标签的全名称
     *
     * @param tagName
     * @param prefix
     * @return
     */
    public static String getXmlFullTagName(String tagName, String prefix) {

        if (StringUtils.isEmpty(prefix)) {
            return tagName;
        } else {
            return prefix + CommonConstants.SpecialSymbol.COLON + tagName;
        }

    }
}
