package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.common.exception.CommonException;
import com.jcoder.request.common.util.CommonConstants;
import com.jcoder.request.execute.app.service.IExecuteRequestService;
import com.jcoder.request.execute.infra.ExecuteConstants;
import com.jcoder.request.execute.infra.util.RequestToolUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author Qiu
 */
@Service
public class ExecuteRequestServiceImpl implements IExecuteRequestService {

    @Override
    public ResponseEntity<Object> executeRestRequest(String url,
                                                     String requestMethod,
                                                     Map<String, Object> requestParams,
                                                     Object requestBody,
                                                     Map<String, Object> pathParams,
                                                     Map<String, Object> headerParams) {

        HttpHeaders headers = buildHttpHeader(headerParams);
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        //请求超时时间设置
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(30000);
        httpRequestFactory.setReadTimeout(30000);

        //合并请求地址中的所有参数
        pathParams.putAll(requestParams);

        //构建完整的请求地址
        String requestUrl = buildUrl(url, requestParams);

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        HttpMethod httpMethod = RequestToolUtil.getHttpMethod(String.valueOf(requestMethod));
        ResponseEntity<Object> response = restTemplate.exchange(requestUrl, httpMethod, entity, Object.class, pathParams);

        System.out.println(response.getBody().toString());

        return response;
    }

    @Override
    public String executeSoapRequest(String url,
                                     String params,
                                     String soapAction,
                                     String soapVersion,
                                     Map<String, String> httpHeader) {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("SOAPAction", soapAction);

        if (soapVersion.equalsIgnoreCase(ExecuteConstants.SoapVersion.SOAP_1_POINT_1)) {
            httpPost.setHeader("Content-Type", "text/xml;charset=utf-8");
        } else if (soapVersion.equalsIgnoreCase(ExecuteConstants.SoapVersion.SOAP_1_POINT_2)) {
            httpPost.setHeader("Content-Type", "application/soap+xml;charset=utf-8");
        } else {
            throw new CommonException("request.execute.soap_version_error");
        }

        ByteArrayEntity data = new ByteArrayEntity(params.getBytes());
        httpPost.setEntity(data);

        String result = "";
        try {
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
            org.apache.http.HttpEntity httpEntity = response.getEntity();

            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity, "UTF-8");
            }
        } catch (Exception e) {
            throw new CommonException("request.execute.soap_request_error", e);
        } finally {
            IOUtils.closeQuietly(closeableHttpClient);
        }
        return result;
    }

    /**
     * 构建请求头
     *
     * @param headerParams
     * @return
     */
    private HttpHeaders buildHttpHeader(Map<String, Object> headerParams) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("charset", "UTF-8");

        for (String name : headerParams.keySet()) {
            if (name.startsWith("test")) {
                headers.add(name, String.valueOf(headerParams.get(name)));
            }
        }
        return headers;
    }

    /**
     * 用于处理请求地址后存在参数的情况, 比如http://localhost/xxx?aaa=1234
     *
     * @param baseUrl
     * @param requestParams
     * @return
     */
    private String buildUrl(String baseUrl, Map<String, Object> requestParams) {

        StringBuilder paramsStr = new StringBuilder(baseUrl);
        Boolean hasParam = Boolean.FALSE;
        paramsStr.append(CommonConstants.SpecialSymbol.QUESTION_MARK);

        for (String paramName : requestParams.keySet()) {

            if (hasParam) {
                paramsStr.append(CommonConstants.SpecialSymbol.AT);
            }
            paramsStr.append(paramName)
                    .append(CommonConstants.SpecialSymbol.EQUAL)
                    .append(CommonConstants.SpecialSymbol.OPENING_BRACE)
                    .append(paramName)
                    .append(CommonConstants.SpecialSymbol.CLOSING_BRACE);
            hasParam = Boolean.TRUE;
        }
        return paramsStr.toString();
    }
}
