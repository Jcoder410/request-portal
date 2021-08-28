package com.jcoder.request.execute.domain.entity;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Namespace;

import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.Map;

/**
 * xml节点属性类：包含节点值、标签前缀、前缀对应的命名空间、属性列表、命名空间
 *
 * @author Qiu
 */
@XmlType
public class NodeEntity {

    /**
     * xml节点值
     */
    private String value;

    /**
     * tag对应的前缀：如<atom:drawOrder>1</atom:drawOrder>中的atom
     */
    private String namespacePrefix;

    /**
     * tag前缀对应的namespace
     */
    private String namespaceUri;

    /**
     * 节点对应的属性列表
     */
    private List<Attribute> attributeList;

    /**
     * 节点对应的命名空间定义
     */
    private List<Namespace> nameSpaceList;

    /**
     * 子节点
     */
    private Map<String, List<NodeEntity>> childNodes;

    /**
     * 判断是否存在tag前缀
     *
     * @return
     */
    public Boolean hasNsprefix() {
        return !StringUtils.isEmpty(this.namespacePrefix);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNamespacePrefix() {
        return namespacePrefix;
    }

    public void setNamespacePrefix(String namespacePrefix) {
        this.namespacePrefix = namespacePrefix;
    }

    public String getNamespaceUri() {
        return namespaceUri;
    }

    public void setNamespaceUri(String namespaceUri) {
        this.namespaceUri = namespaceUri;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }

    public List<Namespace> getNameSpaceList() {
        return nameSpaceList;
    }

    public void setNameSpaceList(List<Namespace> nameSpaceList) {
        this.nameSpaceList = nameSpaceList;
    }

    public Map<String, List<NodeEntity>> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(Map<String, List<NodeEntity>> childNodes) {
        this.childNodes = childNodes;
    }
}
