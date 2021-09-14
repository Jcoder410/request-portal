package com.jcoder.request.execute.domain.entity;

import com.jcoder.request.execute.infra.util.CustomXmlAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * webservice接口对应的返回类
 *
 * @author Qiu
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapResponse extends BaseResponseEntity {

    @XmlElement(name = "payload")
    @XmlJavaTypeAdapter(CustomXmlAdapter.class)
    public List<String> payload = new ArrayList<>();

    @XmlTransient
    public String errorCode;
}
