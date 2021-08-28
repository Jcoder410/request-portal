package com.jcoder.request.execute.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcoder.request.execute.infra.util.Dom4JXmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class XmlConvertTest {

    public static void main(String[] args) throws DocumentException, JsonProcessingException {

        String xmlStr = "<requestBody>\n" +
                "  <person sex=\"female\" age=\"18\">\n" +
                "    <firstname>Anna</firstname>\n" +
                "    <lastname>Smith</lastname>\n" +
                "    <relations>父亲</relations>\n" +
                "    <relations>母亲</relations>\n" +
                "  </person>\n" +
                "  <person sex=\"male\">\n" +
                "    <firstname>James</firstname>\n" +
                "    <lastname>Gery</lastname>\n" +
                "    <relations>父亲</relations>\n" +
                "    <relations>母亲</relations>\n" +
                "  </person>\n" +
                "</requestBody>";
        Document doc = DocumentHelper.parseText(xmlStr);

        Dom4JXmlUtil converterUtil = new Dom4JXmlUtil();
        Map datas = converterUtil.getFullContent(Arrays.asList(doc.getRootElement()));
        List<Element> elementList = converterUtil.completeConvert(datas, "");
        System.out.println(elementList.get(0).asXML());

        System.out.println();

       //objectMapper.convertValue(test.result, JsonNode.class).toPrettyString()
        Object jsonDatas = converterUtil.getContent(doc.getRootElement().elements());
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.convertValue(jsonDatas,JsonNode.class).toPrettyString());
        System.out.println("---------------end---------------");
    }

}
