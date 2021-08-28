package com.jcoder.request.execute.infra.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
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
    public Object extractDataFromXml(String xmlStr,
                                     String dataNodeName) throws DocumentException {

        Document document = DocumentHelper.parseText(xmlStr);
        //Element dataElement = getDataElement(document.getRootElement(), dataNodeName);

        List<Element> elementList = getDataElement(Arrays.asList(document.getRootElement()),dataNodeName);

        /**
         * todo 此处如果没找到节点，则需要报错
         */
        Object datas = dom4JXmlUtil.getContent(elementList);

        return datas;
    }

    /**
     * 查找目标节点
     *
     * @return
     */
    private List<Element> getDataElement(List<Element> elements, String dataNodeName) {

        List<Element> elementList = new ArrayList<>();

        for (Element element : elements) {
            if(element.getQualifiedName().equals(dataNodeName)){
                elementList.add(element);
            }else{
                elementList.addAll(getDataElement(element.elements(),dataNodeName));
            }
        }
        return elementList;
    }

    /**
     * 查找目标节点
     *
     * @param element
     * @param dataNodeName
     * @return
     */
    private Element getDataElement(Element element, String dataNodeName) {

        if (element.getQualifiedName().equals(dataNodeName)) {
            return element;
        }

        Element target = null;

        for (Element child : element.elements()) {
            target = getDataElement(child, dataNodeName);

            if (target != null) {
                break;
            }
        }
        return target;
    }

    public static void main(String[] args) throws DocumentException {
        String xmlStr = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "   <soap:Body>\n" +
                "      <ns2:multiPersonResponse xmlns:ns2=\"http://app.test.execute.request.jcoder.com/\">\n" +
                "         <return>\n" +
                "            <age>18</age>\n" +
                "            <country>中国</country>\n" +
                "            <name>盖世猪猪</name>\n" +
                "            <personId>1001</personId>\n" +
                "            <relations>父亲</relations>\n" +
                "            <relations>母亲</relations>\n" +
                "            <sex>男</sex>\n" +
                "         </return>\n" +
                "      </ns2:multiPersonResponse>\n" +
                "   </soap:Body>\n" +
                "</soap:Envelope>";

        HttpMessageUtil httpMessageUtil = new HttpMessageUtil();
        Object datas = httpMessageUtil.extractDataFromXml(xmlStr,"return");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.convertValue(datas,JsonNode.class);
        System.out.println(jsonNode.isArray());
        System.out.println(jsonNode.toPrettyString());

        System.out.println("---------------------结束---------------------");

    }

}
