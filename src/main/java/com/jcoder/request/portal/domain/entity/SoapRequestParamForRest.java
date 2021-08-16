package com.jcoder.request.portal.domain.entity;

import com.jcoder.request.portal.infra.util.CustomXmlAdapterForRest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Map;

/**
 * rest接口透传成soap接口时的参数类
 *
 * @author Qiu
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapRequestParamForRest extends BaseRequestParam {

    @XmlElement(name = "payload")
    @XmlJavaTypeAdapter(CustomXmlAdapterForRest.class)
    private Map<String, Object> datas;

    public Map<String, Object> getDatas() {
        return datas;
    }

    public void setDatas(Map<String, Object> datas) {
        this.datas = datas;
    }
}
