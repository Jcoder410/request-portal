package com.jcoder.request.execute.domain.entity;

import com.jcoder.request.execute.infra.util.CustomXmlAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

/**
 * webservice接口参数类
 *
 * @author Qiu
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapRequestParam extends BaseRequestParam {

    @XmlElement(name = "payload")
    @XmlJavaTypeAdapter(CustomXmlAdapter.class)
    public List<String> payload;
}
