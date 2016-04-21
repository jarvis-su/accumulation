package jarvis.samples.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import logging.util.CustomLogManager;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created by Jarvis on 4/21/16.
 */
public class TestXpath {
    private final static Logger logger = CustomLogManager.getLogger(TestXpath.class.getName());


    public static void main(String[] args) {
        read();
    }

    public static void read() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            InputStream in = TestXpath.class.getClassLoader().getResourceAsStream(Temp.textXmlFileName);
            Document doc = builder.parse(in);
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            // 选取所有class元素的name属性
            // XPath语法介绍： http://w3school.com.cn/xpath/
            XPathExpression expr = xpath.compile("//class/@name");
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); i++) {
                System.out.println("name = " + nodes.item(i).getNodeValue());
            }
        } catch (XPathExpressionException | ParserConfigurationException | SAXException | IOException e) {
            logger.log(Level.WARNING, " catch exception ", e);
//            e.printStackTrace();
        }
    }


}
