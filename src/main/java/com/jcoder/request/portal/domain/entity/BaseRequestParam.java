package com.jcoder.request.portal.domain.entity;

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
    private String interfaceCod;

    public String getInterfaceCod() {
        return interfaceCod;
    }

    public void setInterfaceCod(String interfaceCod) {
        this.interfaceCod = interfaceCod;
    }
}
