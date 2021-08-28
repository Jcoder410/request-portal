package com.jcoder.request.execute.infra.util;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.DOMWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

/**
 * 用于处理w3c.dom相关的事情
 *
 * @author Qiu
 */
public class W3cDomXmlUtil {

    /**
     * 将w3c的文档对象转换成字符串
     *
     * @param document
     * @return
     */
    public String asXml(Document document) {

        DOMImplementationLS domImplLS = (DOMImplementationLS) document
                .getImplementation();
        LSSerializer serializer = domImplLS.createLSSerializer();
        String str = serializer.writeToString(document.getFirstChild());

        return str;
    }

    /**
     * dom4j.Element转w3c.dom.element
     *
     * @param element
     * @return
     * @throws DocumentException
     */
    public Element elementConvert(org.dom4j.Element element) throws DocumentException {
        org.dom4j.Document doc = DocumentHelper.createDocument();
        doc.setRootElement(element);

        DOMWriter writer = new DOMWriter();
        Document doc2 = writer.write(element.getDocument());

        return doc2.getDocumentElement();
    }

}
