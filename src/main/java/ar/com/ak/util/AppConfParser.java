package ar.com.ak.util;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class AppConfParser {

    public static final String APP_CONF_FILE_NAME = "app-conf.xml";

    private Document document;

    private AppConfParser(Document document) {
        this.document = document;
    }

    public static AppConfParser parse() {
        Document document;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(FacesUtil.getResourceRealPath(APP_CONF_FILE_NAME));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new AppConfParser(document);
    }

    public String getVersion() {
        return getElementValueByTagName("version");
    }

    private String getElementValueByTagName(String tagName) {
        String result = null;

        NodeList list = document.getElementsByTagName(tagName);

        if (list.getLength() > 0) {
            NodeList nodes = list.item(0).getChildNodes();

            if (nodes.getLength() > 0) {
                result = nodes.item(0).getNodeValue();
            }
        }

        return result;
    }
}
