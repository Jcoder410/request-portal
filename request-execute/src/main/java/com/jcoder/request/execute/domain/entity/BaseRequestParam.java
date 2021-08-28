package com.jcoder.request.execute.domain.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * webservice接口参数基础类
 *
 * @author Qiu
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseRequestParam {

    @XmlElement(name = "uuid")
    private String interfaceCode;

    public String getInterfaceCode() {
        return interfaceCode;
    }

    public void setInterfaceCode(String interfaceCode) {
        this.interfaceCode = interfaceCode;
    }
}
