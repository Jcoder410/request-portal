package com.jcoder.request.execute.infra.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 针对目标接口是SOAP的接口的适配器，用于将xml对象解析成Map对象
 *
 * @author Qiu
 */
public class CustomXmlAdapter extends XmlAdapter<CustomXmlAdapter.AdaptElements, List<String>> {

    private W3cDomXmlUtil w3cDomXmlUtil = new W3cDomXmlUtil();

    public static class AdaptElements {
        @XmlAnyElement
        public List<Element> elements = new ArrayList<>();
    }

    @Override
    public List<String> unmarshal(AdaptElements adaptedType) {

        List<String> datas = new ArrayList();

        for (Element element : adaptedType.elements) {
            String data = w3cDomXmlUtil.asXml(element.getOwnerDocument());
            datas.add(data);
        }
        return datas;
    }

    @Override
    public AdaptElements marshal(List<String> datas) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();

        List<Element> elements = new ArrayList<>();
        for (String data : datas) {
            Document doc = db.parse(new InputSource(new StringReader(data)));
            elements.add(doc.getDocumentElement());
        }
        AdaptElements adaptedMap = new AdaptElements();
        adaptedMap.elements = elements;

        return adaptedMap;
    }

}
