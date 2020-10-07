import com.google.gson.Gson;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class DataParserManager {
    Document doc;
    String stringData;
    public DataParserManager(String stringData) {
        this.stringData = stringData;
    }
    public List<String>getReceivers() {
        List<String> receivers = new ArrayList<String>();
        for(int i=0;i<doc.getChildNodes().item(0).getChildNodes().getLength();i++)
            receivers.add(i,doc.getChildNodes().item(0).getChildNodes().item(i).getTextContent());
        return receivers;
    }

    public String getMessage(){
        return doc.getChildNodes().item(0).getAttributes().getNamedItem("text").getTextContent();
    }

    public boolean isXML() {
        try
        {
            DocumentBuilderFactory documentBuildFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuildFactory.newDocumentBuilder();
            doc = documentBuilder.parse(new ByteArrayInputStream(stringData.getBytes()));
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
