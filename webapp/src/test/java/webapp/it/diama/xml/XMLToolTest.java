package webapp.it.diama.xml;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLToolTest {
	private static final String PATH = System.getProperty("user.home")+"/gitRemoto/pom.xml";
	XMLTool xml = new XMLTool();
	Document doc;
	double version;

	@BeforeEach
	protected void setUp() throws Exception {
	}

	@AfterEach
	protected void tearDown() throws Exception {
	}

	@Test
	public void testPomVersion() throws Exception {
		doc=xml.getXMLDocument(PATH);
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		doc.getChildNodes();
		Node nNode = (Node) doc.getElementsByTagName("project").item(0);
		Element eElement = (Element) nNode;
		eElement.getElementsByTagName("version").item(0).getTextContent();
				
	}

	@Test
	public void testReadPomVersion() throws Exception {
		testPomVersion();
		version=xml.readPomVersion(doc);
		System.out.println(version);
	}

	@Test
	public void testRewriteVersioneAutomatica() throws Exception {
		testReadPomVersion();
		version=(version*10+1)/10;
		xml.rewriteVersioneAutomatica(doc, version, PATH);
		testReadPomVersion();
	}
	
	

}
