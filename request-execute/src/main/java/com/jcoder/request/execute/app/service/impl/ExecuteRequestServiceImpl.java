package com.jcoder.request.execute.app.service.impl;

import com.jcoder.request.execute.app.service.IExecuteRequestService;
import com.jcoder.request.execute.domain.entity.HttpParameter;
import com.jcoder.request.execute.infra.util.RequestToolUtil;
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
    public ResponseEntity<Object> executeRequest(HttpParameter httpParameter) {

        HttpHeaders headers = buildHttpHeader(httpParameter.getHeaderParams());
        HttpEntity<Object> entity = new HttpEntity<>(httpParameter.getRequestBody(), headers);

        //请求超时时间设置
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(httpParameter.getConnectTimeout());
        httpRequestFactory.setReadTimeout(httpParameter.getReadTimeout());

        //发起请求
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        HttpMethod httpMethod = RequestToolUtil.getHttpMethod(httpParameter.getMethodCode());
        ResponseEntity<Object> response = restTemplate.exchange(httpParameter.getUrl(),
                httpMethod,
                entity,
                httpParameter.getReturnType(),
                httpParameter.getUriVariables());

        return response;
    }

    /**
     * 构建请求头
     *
     * @param headerParams
     * @return
     */
    private HttpHeaders buildHttpHeader(Map<String, String> headerParams) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("charset", "UTF-8");

        if (headerParams != null) {
            for (String name : headerParams.keySet()) {
                headers.add(name, String.valueOf(headerParams.get(name)));
            }
        }

        return headers;
    }
}
