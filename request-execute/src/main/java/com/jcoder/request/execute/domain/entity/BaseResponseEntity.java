package com.jcoder.request.execute.domain.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * webservice接口返回基础类
 *
 * @author Qiu
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseResponseEntity {

    @XmlElement(name = "message")
    private String message;

    @XmlElement(name = "status")
    private String statusCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
