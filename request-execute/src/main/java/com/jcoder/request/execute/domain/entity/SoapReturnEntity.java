package com.jcoder.request.execute.domain.entity;

/**
 * 返回结果包装类
 *
 * @author Qiu
 */
public class SoapReturnEntity {

    private String responseStr;

    private Integer httpStatus;

    public String getResponseStr() {
        return responseStr;
    }

    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }
}
