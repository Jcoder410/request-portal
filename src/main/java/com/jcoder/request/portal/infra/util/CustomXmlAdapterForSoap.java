package com.jcoder.request.portal.infra.util;

import com.jcoder.request.portal.domain.entity.NodeEntity;
import org.dom4j.Element;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 针对目标接口是SOAP的接口的适配器，用于将xml对象解析成Map对象
 *
 * @author Qiu
 */
public class CustomXmlAdapterForSoap extends XmlAdapter<Object, Map<String, List<NodeEntity>>> {

    private XmlConverterUtil xmlConverterUtil = new XmlConverterUtil();

    @Override
    public Map<String, List<NodeEntity>> unmarshal(Object w3cElement) throws Exception {

        Element element = xmlConverterUtil.elementConvert((org.w3c.dom.Element) w3cElement);

        NodeEntity rootNode = xmlConverterUtil.getElementContent(element);
        List<NodeEntity> nodeEntities = new ArrayList<>();
        nodeEntities.add(rootNode);
        Map<String, List<NodeEntity>> xmlMap = new HashMap<>();
        xmlMap.put(element.getName(), nodeEntities);

        return xmlMap;
    }

    @Override
    public org.w3c.dom.Element marshal(Map<String, List<NodeEntity>> nodeMap) throws Exception {

        List<Element> elements = xmlConverterUtil.mapToElement(nodeMap, "");
        return xmlConverterUtil.elementConvert(elements.get(0));
    }

}
