package com.jcoder.request.portal.domain.entity;

import com.jcoder.request.portal.infra.util.CustomXmlAdapterForRest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * rest接口透传成soap接口的返回类
 *
 * @author Qiu
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapResponseForRest extends BaseResponseEntity {

    @XmlElement(name = "payload")
    @XmlJavaTypeAdapter(CustomXmlAdapterForRest.class)
    private Object datas;

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }
}
