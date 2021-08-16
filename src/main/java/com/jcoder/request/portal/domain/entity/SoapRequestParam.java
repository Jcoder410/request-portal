package com.jcoder.request.portal.domain.entity;

import com.jcoder.request.portal.infra.util.CustomXmlAdapterForSoap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;
import java.util.Map;

/**
 * webservice接口参数类
 *
 * @author Qiu
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapRequestParam extends BaseRequestParam {

    @XmlElement(name = "payload")
    @XmlJavaTypeAdapter(CustomXmlAdapterForSoap.class)
    private Map<String, List<NodeEntity>> datas;

    public Map<String, List<NodeEntity>> getDatas() {
        return datas;
    }

    public void setDatas(Map<String, List<NodeEntity>> datas) {
        this.datas = datas;
    }
}
