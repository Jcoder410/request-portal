package com.jcoder.request.execute.test;

import com.jcoder.request.execute.domain.entity.NodeEntity;
import com.jcoder.request.execute.infra.util.Dom4JXmlUtil;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.DOMWriter;
import org.dom4j.io.SAXReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class W3cDomTest {

    public static String asXml(Document document) {
        DOMImplementationLS domImplLS = (DOMImplementationLS) document
                .getImplementation();
        LSSerializer serializer = domImplLS.createLSSerializer();
        String str = serializer.writeToString(document.getFirstChild());

        return str;
    }

    public Document getDoc() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("/Users/qiuchen/Documents/json/xml_test.txt");

        return doc;
    }

    public void printNode(Node node) {

        NodeList childList = node.getChildNodes();

        if (childList.getLength() == 0) {
            Element element = (Element) node;
            System.out.println(node.getNodeName() + ":" + node.getNodeValue());
        } else {
            for (int i = 0; i < childList.getLength(); i++) {
                printNode(childList.item(i));
            }
        }

    }

    public static void main(String[] args) throws DocumentException {

        SAXReader reader = new SAXReader();
        org.dom4j.Document document = reader.read(new File("/Users/qiuchen/Documents/json/xml_test.txt"));
        Dom4JXmlUtil converterUtil = new Dom4JXmlUtil();
    }

}
