package com.jcoder.request.portal.infra.util;

import com.jcoder.request.portal.domain.entity.NodeEntity;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.dom4j.io.DOMReader;
import org.dom4j.io.DOMWriter;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.parsers.ParserConfigurationException;
import java.util.*;

/**
 * 使用dom4j提取和还原xml报文
 *
 * @author Qiu
 */
public class XmlConverterUtil {

    /**
     * 提取xml到map对象:
     * 1.key为xml标签名称, 含标签前缀
     * 2.NodeEntity用于存放xml的节点值、namespace、attribute
     *
     * @param element
     * @return
     */
    public NodeEntity getElementContent(Element element) {

        //获取节点的attribute和namespace
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setAttributeList(element.attributes());
        nodeEntity.setNameSpaceList(element.declaredNamespaces());

        //获取节点名称的前缀和前缀对应的命名空间
        if (StringUtils.isNotEmpty(element.getNamespacePrefix())) {
            nodeEntity.setNamespacePrefix(element.getNamespacePrefix());
            nodeEntity.setNamespaceUri(element.getNamespaceForPrefix(nodeEntity.getNamespacePrefix()).getURI());
        }

        //获取子节点
        List<Element> childList = element.elements();

        if (childList.size() == 0) {
            nodeEntity.setValue(element.getText());
            return nodeEntity;
        } else {
            Map<String, List<NodeEntity>> childMap = new HashMap<>(childList.size());

            for (Element child : childList) {
                NodeEntity childNode = getElementContent(child);

                //tag的全程应该包含命名空间的前缀，比如<atom:drawOrder>1</atom:drawOrder>
                String tagName = StringUtils.isEmpty(child.getNamespacePrefix()) ? child.getName() : child.getNamespacePrefix() + ":" + child.getName();

                if (!childMap.containsKey(tagName)) {
                    List<NodeEntity> nodeList = new ArrayList<>();
                    nodeList.add(childNode);
                    childMap.put(tagName, nodeList);
                } else {
                    childMap.get(tagName).add(childNode);
                }
            }
            nodeEntity.setChildNodes(childMap);
        }
        return nodeEntity;
    }

    /**
     * 将Map对象还原成xml对象
     *
     * @param nodeMap          节点数据Map
     * @param defaultNamespace 默认的命名空间
     * @return dom4j的Element对象集合
     */
    public List<Element> mapToElement(Map<String, List<NodeEntity>> nodeMap, String defaultNamespace) {

        List<Element> elements = new ArrayList<>();

        if (nodeMap == null || nodeMap.isEmpty()) {
            return elements;
        }

        for (String tagName : nodeMap.keySet()) {
            List<NodeEntity> nodeList = nodeMap.get(tagName);
            nodeList.forEach(nodeEntity -> {

                //判断获取默认命名空间的url
                String defaultNsUrl = getDefaultNs(defaultNamespace, nodeEntity.getNameSpaceList());

                /**
                 * 创建节点:
                 * 如果标签存在前缀, 则默认命名空间的uri使用前缀对应的uri;
                 * 如果标签本身的命名空间存在默认命名空间，则使用标签本身的
                 * 如果标签本身的命名空间不存在默认的, 则必须使用父标签某人的命名空间
                 */
                Element element;
                if (nodeEntity.hasNsprefix()) {
                    element = DocumentHelper.createElement(QName.get(tagName, nodeEntity.getNamespaceUri()));
                } else {
                    element = DocumentHelper.createElement(QName.get(tagName, defaultNsUrl));
                }

                //设置节点值
                if (StringUtils.isNotEmpty(nodeEntity.getValue())) {
                    element.setText(nodeEntity.getValue());
                }

                //设置节点属性
                element.setAttributes(nodeEntity.getAttributeList());

                //添加命名空间
                for (Namespace namespace : nodeEntity.getNameSpaceList()) {
                    if (StringUtils.isEmpty(namespace.getPrefix())) {
                        continue;
                    }
                    element.add(namespace);
                }

                //获取子节点
                List<Element> childs = mapToElement(nodeEntity.getChildNodes(), defaultNsUrl);
                for (Element child : childs) {
                    element.add(child);
                }
                elements.add(element);
            });
        }
        return elements;
    }

    /**
     * 获取默认的namespace, 如果自身的命名空间列表不存在前缀为空的namespace,
     * 则必须使用父标签对应的默认namespace
     *
     * @param defaultNamespace
     * @param nameSpaceList
     * @return
     */
    private String getDefaultNs(String defaultNamespace,
                                List<Namespace> nameSpaceList) {
        String defaultNs = "";
        for (Namespace namespace : nameSpaceList) {
            if (StringUtils.isEmpty(namespace.getPrefix())) {
                defaultNs = namespace.getURI();
                break;
            }
        }
        return StringUtils.isEmpty(defaultNs) ? defaultNamespace : defaultNs;
    }

    /**
     * 将w3c的文档对象转换成字符串
     *
     * @param document
     * @return
     */
    public String asXml(org.w3c.dom.Document document) {
        DOMImplementationLS domImplLS = (DOMImplementationLS) document
                .getImplementation();
        LSSerializer serializer = domImplLS.createLSSerializer();
        String str = serializer.writeToString(document.getFirstChild());

        return str;
    }

    public Object convertToMap(List<Element> elementList) {

        Map<String, Object> nodeMap = new LinkedHashMap<>(elementList.size());
        Set<String> nodeNames = new HashSet<>();
        List valueList = new ArrayList();

        for (Element element : elementList) {

            if (element.isTextOnly()) {
                if (nodeNames.contains(element.getName())) {
                    valueList.add(element.getText());
                    if (nodeMap.containsKey(element.getName())) {
                        valueList.add(nodeMap.get(element.getName()));
                        nodeMap.remove(element.getName());
                    }
                } else {
                    nodeMap.put(element.getName(), element.getText());
                    nodeNames.add(element.getName());
                }
            } else {
                Object childNode = convertToMap(element.elements());
                if (nodeNames.contains(element.getName())) {
                    valueList.add(childNode);
                    if (nodeMap.containsKey(element.getName())) {
                        valueList.add(nodeMap.get(element.getName()));
                        nodeMap.remove(element.getName());
                    }
                } else {
                    nodeMap.put(element.getName(), childNode);
                    nodeNames.add(element.getName());
                }
            }
        }
        return valueList.size() == 0 ? nodeMap : valueList;
    }

    /**
     * 将map数据转换成element对象
     *
     * @param datas
     * @return
     */
    private List<Element> dealWithMap(Map<String, Object> datas) {

        List<Element> elementList = new ArrayList<>(datas.size());
        for (String tag : datas.keySet()) {
            Element element = DocumentHelper.createElement(tag);
            if (datas.get(tag) instanceof String) {
                element.setText((String) datas.get(tag));
            } else {
                List<Element> childs = convertToElement(datas.get(tag), tag);

                for (Element child : childs) {
                    element.add(child);
                }
            }
            elementList.add(element);
        }
        return elementList;
    }

    private List<Element> dealWithList(List datas, String tagName) {

        List<Element> elementList = new ArrayList<>(datas.size());
        datas.forEach(data -> {
            Element element = DocumentHelper.createElement(tagName);
            List<Element> childs = convertToElement(data, tagName);
            for (Element child : childs) {
                element.add(child);
            }
            elementList.add(element);
        });

        return elementList;
    }

    /**
     * 讲数据对象转换成element
     *
     * @param datas
     * @param tagName
     * @return
     */
    public List<Element> convertToElement(Object datas, String tagName) {

        List<Element> elementList = new ArrayList<>();

        if (datas instanceof Map) {
            return dealWithMap((Map<String, Object>) datas);
        } else if (datas instanceof String) {
            Element element = DocumentHelper.createElement(tagName);
            element.setText((String) datas);
            elementList.add(element);
        } else {
            return dealWithList((List) datas, tagName);
        }
        return elementList;
    }

    /**
     * dom4j.Element转w3c.dom.element
     *
     * @param element
     * @return
     * @throws DocumentException
     */
    public org.w3c.dom.Element elementConvert(Element element) throws DocumentException {
        Document doc = DocumentHelper.createDocument();
        doc.setRootElement(element);

        DOMWriter writer = new DOMWriter();
        org.w3c.dom.Document doc2 = writer.write(doc);

        return doc2.getDocumentElement();
    }

    /**
     * w3c.dom.element转dom4j.Element
     *
     * @param element
     * @return
     * @throws ParserConfigurationException
     */
    public Element elementConvert(org.w3c.dom.Element element) throws ParserConfigurationException {

        DOMReader reader = new DOMReader();
        Document Dom4jDoc = reader.read(element.getOwnerDocument());

        return Dom4jDoc.getRootElement();
    }

}
