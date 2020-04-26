package webapp.it.diama.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;



public class XMLTool {
	
	public Document getXMLDocument(String path) throws ParserConfigurationException, SAXException, IOException {
		File fXmlFile = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	public double readPomVersion(Document doc) {
		Node verision = getVersion(doc);
		return Double.valueOf(verision.getTextContent());
	}

	private Node getVersion(Document doc) {
		Node nNode = getProgetNode(doc);
		Element eElement = (Element) nNode;
		Node verision=eElement.getElementsByTagName("version").item(0);
		return verision;
	}

	private Node getProgetNode(Document doc) {
		Node nNode = (Node) doc.getElementsByTagName("project").item(0);
		return nNode;
	}
	
	public void rewriteVersioneAutomatica(Document doc,double version,String path) throws TransformerException {
		Node nNode = getVersion(doc);
		nNode.setTextContent(String.valueOf(version));
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(path));
		transformer.transform(source, result);
	}
	


}
