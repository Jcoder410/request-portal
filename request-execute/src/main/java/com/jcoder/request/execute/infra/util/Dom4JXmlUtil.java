package com.jcoder.request.execute.infra.util;

import com.jcoder.request.execute.domain.entity.NodeEntity;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.dom4j.io.DOMReader;

import javax.xml.parsers.ParserConfigurationException;
import java.util.*;

/**
 * 使用dom4j提取和还原xml报文
 *
 * @author Qiu
 */
public class Dom4JXmlUtil {

    /**
     * 构建节点的全属性对象
     *
     * @param element
     * @return
     */
    private NodeEntity buildNodeEntity(Element element) {

        //获取节点的attribute和namespace
        NodeEntity nodeEntity = new NodeEntity();
        nodeEntity.setAttributeList(element.attributes());
        nodeEntity.setNameSpaceList(element.declaredNamespaces());

        //获取节点名称的前缀和前缀对应的命名空间
        if (StringUtils.isNotEmpty(element.getNamespacePrefix())) {
            nodeEntity.setNamespacePrefix(element.getNamespacePrefix());
            nodeEntity.setNamespaceUri(element.getNamespaceForPrefix(nodeEntity.getNamespacePrefix()).getURI());
        }

        List<Element> childList = element.elements();

        /**
         * 如果存在子节点，则获取子节点信息，否则获取节点值
         */
        if (childList.size() > 0) {
            Map childContentMap = getFullContent(childList);
            nodeEntity.setChildNodes(childContentMap);
        } else {
            nodeEntity.setValue(element.getText());
        }

        return nodeEntity;
    }

    /**
     * 提取xml到map对象:
     * 1.key为xml标签名称, 含标签前缀
     * 2.NodeEntity用于存放xml的节点值、namespace、attribute
     *
     * @param elementList
     * @return
     */
    public Map<String, List<NodeEntity>> getFullContent(List<Element> elementList) {

        Map<String, List<NodeEntity>> contentMap = new HashMap<>();

        for (Element element : elementList) {

            //获取节点属性对象
            NodeEntity nodeEntity = buildNodeEntity(element);

            //获取xml节点tag名称
            String tagName = RequestToolUtil.getXmlFullTagName(element.getName(), element.getNamespacePrefix());

            /**
             * 如果tag名称已经存在，则进行节点属性合并，用于处理数组对象类型的值
             */
            if (contentMap.containsKey(tagName)) {
                contentMap.get(tagName).add(nodeEntity);
            } else {
                List<NodeEntity> entityList = new ArrayList<>();
                entityList.add(nodeEntity);
                contentMap.put(tagName, entityList);
            }
        }
        return contentMap;
    }

    /**
     * 将Map对象还原成xml对象
     *
     * @param nodeMap          节点数据Map
     * @param defaultNamespace 默认的命名空间
     * @return dom4j的Element对象集合
     */
    public List<Element> completeConvert(Map<String, List<NodeEntity>> nodeMap, String defaultNamespace) {

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
                List<Element> childList = completeConvert(nodeEntity.getChildNodes(), defaultNsUrl);
                for (Element child : childList) {
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
     * 将element转换成键值对对象，只包括节点值和节点tag名称
     * 主要用于提取需要转换成json的数据格式
     *
     * @param elementList
     * @return
     */
    public Object getContent(List<Element> elementList) {

        Map<String, Object> nodeMap = new LinkedHashMap<>(elementList.size());
        List<Object> nodeList = new ArrayList<>();
        Set<String> nodeSet = new HashSet<>();

        for (Element element : elementList) {

            nodeSet.add(element.getName());

            if (element.isTextOnly()) {
                duplicateHandler(nodeMap, element.getName(), element.getText());
            } else {
                Object childNode = getContent(element.elements());

                if (nodeMap.containsKey(element.getName())) {
                    duplicateHandler(nodeMap, element.getName(), childNode);
                } else {
                    nodeMap.put(element.getName(), childNode);
                }

                if (nodeSet.contains(element.getName())) {
                    if (nodeMap.get(element.getName()) instanceof List) {
                        nodeList.addAll((List) nodeMap.get(element.getName()));
                    }
                }
            }
        }
        return nodeList.size() > 0 ? nodeList : nodeMap;
    }

    /**
     * 处理重复数据
     *
     * @param dataMap
     * @param key
     * @param newData
     */
    private void duplicateHandler(Map<String, Object> dataMap,
                                  String key,
                                  Object newData) {
        if (dataMap.containsKey(key)) {
            if (dataMap.get(key) instanceof List) {
                ((List) dataMap.get(key)).add(newData);
            } else {
                List dataList = new ArrayList();
                dataList.add(dataMap.get(key));
                dataList.add(newData);
                dataMap.put(key, dataList);
            }
        } else {
            dataMap.put(key, newData);
        }
    }

    /**
     * 将键值对的数据转换成element
     * 主要用于json对象数据转换成xml
     *
     * @param datas
     * @param tagName
     * @return
     */
    public List<Element> simpleConvert(Object datas, String tagName) {

        List<Element> elementList = new ArrayList<>();

        if (datas instanceof Map) {
            return dealWithMap((Map<String, Object>) datas);
        } else if (datas instanceof List) {
            return dealWithList((List) datas, tagName);
        } else {
            Element element = DocumentHelper.createElement(tagName);
            element.setText(String.valueOf(datas));
            elementList.add(element);
        }
        return elementList;
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

            if (datas.get(tag) instanceof List || datas.get(tag) instanceof Map) {
                List<Element> childs = simpleConvert(datas.get(tag), tag);
                for (Element child : childs) {
                    element.add(child);
                }
            } else {
                element.setText(String.valueOf(datas.get(tag)));
            }
            elementList.add(element);
        }
        return elementList;
    }

    /**
     * 将list对象数据转换成element对象
     *
     * @param datas
     * @param tagName
     * @return
     */
    private List<Element> dealWithList(List datas, String tagName) {

        List<Element> elementList = new ArrayList<>(datas.size());

        datas.forEach(data -> {
            Element element = DocumentHelper.createElement(tagName);
            List<Element> childList = simpleConvert(data, tagName);
            for (Element child : childList) {
                element.add(child);
            }
            elementList.add(element);
        });

        return elementList;
    }

    /**
     * w3c.dom.element转dom4j.Element
     *
     * @param element
     * @return
     * @throws ParserConfigurationException
     */
    public Element elementConvert(org.w3c.dom.Element element) {

        DOMReader reader = new DOMReader();
        Document Dom4jDoc = reader.read(element.getOwnerDocument());

        return Dom4jDoc.getRootElement();
    }

}
