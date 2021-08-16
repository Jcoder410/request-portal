package com.jcoder.request.portal.infra.util;

import org.dom4j.DocumentException;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对目标接口是Rest的接口的适配器，用于将xml对象解析成Map对象，并将map对象还原成xml对象
 * 其中marshal方法必须返回w3c.dom.Element, 不然会报错
 *
 * @author Qiu
 */
public class CustomXmlAdapterForRest extends XmlAdapter<CustomXmlAdapterForRest.AdaptedElement, Object> {

    private XmlConverterUtil xmlConverterUtil = new XmlConverterUtil();

    public static class AdaptedElement {
        @XmlAnyElement
        public List<Element> elements = new ArrayList<>();
    }

    @Override
    public Object unmarshal(AdaptedElement adaptedElement) throws ParserConfigurationException {

        List<org.dom4j.Element> dom4jElements = new ArrayList<>();
        for (Element element : adaptedElement.elements) {
            dom4jElements.add(xmlConverterUtil.elementConvert(element));
        }

        return xmlConverterUtil.convertToMap(dom4jElements);
    }

    @Override
    public AdaptedElement marshal(Object datas) throws DocumentException {

        String tagName = "";
        if (datas instanceof List) {
            tagName = "data";
        }

        List<org.dom4j.Element> dom4jElements = xmlConverterUtil.convertToElement(datas, tagName);
        List<Element> elements = new ArrayList<>();
        for (org.dom4j.Element element : dom4jElements) {
            elements.add(xmlConverterUtil.elementConvert(element));
        }

        AdaptedElement adaptedElement = new AdaptedElement();
        adaptedElement.elements = elements;

        return adaptedElement;
    }

}
