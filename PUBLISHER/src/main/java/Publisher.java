
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright [2020] [TBerloffe]
 * -=[ -_- -_- -_- -_- ]=-
 */
public class Publisher {

    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException {
        while (true) {
        String message;
        String receiver;

        List<String> receivers = new ArrayList<String>();
        Socket socket;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        socket = new Socket(Constants.HOSTNAME, Constants.PORT);
        System.out.println("Input the receivers: (enter ok after typing the receivers)");
        while (!(receiver = bufferedReader.readLine()).equals("ok")) {
            receivers.add(receiver);
        }

            System.out.println("Input the message: ");
            message = bufferedReader.readLine();
            try {

                message = formXMLMessage(message, receivers);
                System.out.println("Serialized data in XML: ");
                System.out.println(message);
                IReadWrite readWrite = new TransportService(socket);
                readWrite.writeAsync(message);
                System.out.println("data has been sent");
            } catch (ParserConfigurationException e) {
                System.out.println("connection failed");
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    static String formXMLMessage(String message,List<String>rec) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation impl = builder.getDOMImplementation();
        Document doc = impl.createDocument(null, null, null);
        Element rootNode = doc.createElement("message");
        rootNode.setAttribute("text",message);
        doc.appendChild(rootNode);
        for(int i=0;i<rec.size();i++)
        {
            Element childNode=doc.createElement("receiver");
            childNode.setTextContent(rec.get(i));
            rootNode.appendChild(childNode);
        }
        DOMSource domSource = new DOMSource(doc);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
        //transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter sw = new StringWriter();
        StreamResult sr = new StreamResult(sw);
        transformer.transform(domSource, sr);
        String data=sw.toString();
        return data;
    }
}
