package com.jcoder.request.execute.infra.util;

import com.jcoder.request.execute.infra.ExecuteConstants;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;

/**
 * 用于构建请求的参数报文以及返回报文
 *
 * @author Qiu
 */
public class HttpMessageUtil {

    private Dom4JXmlUtil dom4JXmlUtil = new Dom4JXmlUtil();

    /**
     * 基于soap参数模板, 构建soap请求的完整请求参数报文
     *
     * @param soapParamTemplate
     * @param dataTagName
     * @param dataObject
     * @param paramNodeName
     * @return
     * @throws DocumentException
     */
    public String buildSoapRequestParam(String soapParamTemplate,
                                        String paramNodeName,
                                        String dataTagName,
                                        Object dataObject) throws DocumentException {

        List<Element> elementList = dom4JXmlUtil.simpleConvert(dataObject, dataTagName);

        Document soapDoc = DocumentHelper.parseText(soapParamTemplate);
        addElement(soapDoc.getRootElement(), elementList, paramNodeName);

        return soapDoc.asXML();
    }

    /**
     * 往参数节点添加数据节点
     *
     * @param element
     * @param elementList
     * @param paramNodeName
     */
    private void addElement(Element element,
                            List<Element> elementList,
                            String paramNodeName) {

        if (element.getQualifiedName().equals(paramNodeName)) {
            for (Element child : elementList) {
                element.add(child);
            }
            return;
        }

        for (Element child : element.elements()) {
            addElement(child, elementList, paramNodeName);
        }
    }

    /**
     * 提取xml中特定节点的数据内容
     * 只做简单的提取，即获取节点的节点名称和节点值
     *
     * @param xmlStr
     * @param dataNodeName
     * @return
     * @throws DocumentException
     */
    public List<Object> extractDataFromXml(String xmlStr,
                                           String dataNodeName) throws DocumentException {

        Document document = DocumentHelper.parseText(xmlStr);
        List<Element> elementList = getDataElement(Arrays.asList(document.getRootElement()), dataNodeName);
        List<Object> dataList = new ArrayList<>();
        for (Element element : elementList) {
            dataList.add(dom4JXmlUtil.getContent(element.elements()));
        }

        return dataList;
    }

    /**
     * 查找目标节点
     *
     * @return
     */
    private List<Element> getDataElement(List<Element> elements, String dataNodeName) {

        List<Element> elementList = new ArrayList<>();

        for (Element element : elements) {
            if (element.getQualifiedName().equals(dataNodeName)) {
                elementList.add(element);
            } else {
                elementList.addAll(getDataElement(element.elements(), dataNodeName));
            }
        }
        return elementList;
    }

    /**
     * 从xml报文中提取rest请求需要的参数
     *
     * @param dataStrList
     * @return
     */
    public Map<String, Object> getDataForRest(List<String> dataStrList) throws DocumentException {

        Map<String, Object> dataMap = new HashMap<>();

        for (String dataStr : dataStrList) {
            Document document = DocumentHelper.parseText(dataStr);
            Element element = document.getRootElement();
            if (tagNameMatched(element.getName())) {
                Object data = dom4JXmlUtil.getContent(element.elements());
                dataMap.put(element.getName(), data);
            }
        }
        return dataMap;
    }

    /**
     * 判断对应的tag是否为我们所需要的数据
     *
     * @param tagName
     * @return
     */
    private Boolean tagNameMatched(String tagName) {

        switch (tagName) {
            case ExecuteConstants.HttpParamType.REQUEST_PARAM:
                return Boolean.TRUE;
            case ExecuteConstants.HttpParamType.PATH_VARIABLE:
                return Boolean.TRUE;
            case ExecuteConstants.HttpParamType.REQUEST_BODY:
                return Boolean.TRUE;
            case ExecuteConstants.HttpParamType.REQUEST_HEADER:
                return Boolean.TRUE;
            default:
                return Boolean.FALSE;
        }
    }

    /**
     * 将rest请求返回的数据转换成xml字符创
     *
     * @param dataObject
     * @param dataNodeName
     * @return
     */
    public List<String> getDataFromRestResponse(Object dataObject,
                                                String dataNodeName) {

        if(null == dataObject){
            return new ArrayList<>();
        }

        if (dataObject instanceof List && StringUtils.isEmpty(dataNodeName)) {
            /**
             * todo 此处需要抛出异常
             */
        }

        /**
         * 将数据对象转换成element对象, 然后再将element对象转换成string
         */
        List<Element> elementList = dom4JXmlUtil.simpleConvert(dataObject, dataNodeName);
        List<String> dataList = new ArrayList<>();
        for (Element element : elementList) {
            dataList.add(element.asXML());
        }
        return dataList;
    }

    /**
     * 提取soap接口返回的报文, 并转换成xml字符串
     * @param xmlStr xml格式的字符串
     * @param dataNodeName 需要提取的节点名称
     * @param type 提取类型:NODE或CONTENT
     * @return
     */
    public List<String> getDataFromSoapResponse(String xmlStr,
                                                String dataNodeName,
                                                String type) {
        List<String> dataList = new ArrayList<>();
        if (StringUtils.isEmpty(dataNodeName)) {
            dataList.add(xmlStr);
        } else {
            Document document = null;
            try {
                document = DocumentHelper.parseText(xmlStr);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            List<Element> elementList = getDataElement(Arrays.asList(document.getRootElement()), dataNodeName);

            for (Element data : elementList) {
                if(type.equals(ExecuteConstants.ExtractType.XML_NODE)){
                    dataList.add(data.asXML());
                }else{
                    dataList.add(data.asXML());
                }
            }
        }
        return dataList;
    }

}
