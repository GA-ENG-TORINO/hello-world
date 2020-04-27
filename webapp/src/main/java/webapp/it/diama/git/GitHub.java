package webapp.it.diama.git;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import webapp.it.diama.xml.XMLTool;

public class GitHub {

	private static final List<String> PATH = Arrays.asList(
			System.getProperty("user.home") + "/gitRemoto",
			System.getProperty("user.home") + "/gitRemoto/server",
			System.getProperty("user.home") + "/gitRemoto/webapp");
	private static final String BRANCH = "refs/heads/master";
	public enum TIPO{ROOT,CHILD}

	public static void addPushAutomatico() throws IOException, InvalidRemoteException, TransportException,
			GitAPIException, ParserConfigurationException, SAXException, TransformerException, URISyntaxException {
		GitTool gitTool = new GitTool();
		XMLTool xmlTool = new XMLTool();
		gitTool.deleteRepository(PATH.get(0));		
		Git git=gitTool.cloneRepository(PATH.get(0), BRANCH);
		Document doc = xmlTool.getXMLDocument(PATH.get(0)+"/pom.xml");
		double version = xmlTool.readPomVersion(doc,TIPO.ROOT);
		version = (version * 10000 + 1000) / 10000;		
		for (String path : PATH) {
			doc = xmlTool.getXMLDocument(path+"/pom.xml");		
			xmlTool.rewriteVersioneAutomatica(doc, version, path+"/pom.xml",path.endsWith("/gitRemoto")?TIPO.ROOT:TIPO.CHILD);
			System.out.println("effettuato modifica path "+path+"/pom.xml");
		}
		///gitTool.merge(git,PATH.get(0),BRANCH);
		gitTool.push(git,PATH.get(0), BRANCH, version);
	}

}
