package com.jcoder.request.execute.domain.entity;

import java.util.Map;

/**
 * @author Qiu
 */
public class RestRequestParam extends BaseRequestParam{

    private Map<String,Object> payload;

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }
}
