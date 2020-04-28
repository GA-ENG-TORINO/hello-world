package webapp.it.diama.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.servlet.internal.Utils;
import org.xml.sax.SAXException;

import webapp.it.diama.git.GitHub;



@Path("/service")
public class HelloWorldService {

	@GET
	@Path("/{name}")
	public Response sayHello(@PathParam("name") String msg) {
		String output = "Hello, " + msg + "!";
		return Response.status(200).entity(output).build();
	}

	@GET
	@Path("/push")
	public Response push(@Context ContainerRequest request) throws URISyntaxException {
//		try {
////			GitHub.addPushAutomatico();
//		} catch (IOException | GitAPIException | ParserConfigurationException | SAXException | TransformerException
//				| URISyntaxException e) {
//			return Response.status(500).entity(e.getStackTrace()).build();
//		}
		URI originalLocation = new URI(request.getRequestUri().getScheme()+"://"+request.getRequestUri().getAuthority()) ;
		return Response.temporaryRedirect(UriBuilder.fromPath(originalLocation+ "/").build()).build();
	}

	@GET
	@Path("/version")
	public Response version() {

		try {
			String res = "META-INF/maven/com.example.maven-project/webapp/pom.properties";
			InputStream resourceAsStream = this.getClass()
					.getResourceAsStream(res);
			Properties fileAuto = new Properties();
			fileAuto.load(resourceAsStream);			
			return Response.status(200).entity(fileAuto.getProperty("version")).build();
		} catch (Exception e) {
			String res =System.getProperty("catalina.base").replace("\\", "/")+"/webapps/webapp/META-INF/maven/com.example.maven-project/webapp/pom.properties";
			Properties fileAuto = new Properties();
			File file=new File(res);
			FileInputStream fileInputStream;
			try {
				fileInputStream = new FileInputStream(file);
				fileAuto.load(fileInputStream );				
				return Response.status(200).entity(fileAuto.get("version")).build();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return Response.status(200).entity(res).build();
		}
	}
}