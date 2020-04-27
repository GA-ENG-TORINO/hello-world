package webapp.it.diama.service;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.eclipse.jgit.api.errors.GitAPIException;
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
    public Response push(@PathParam("name") String msg) {
			try {
				GitHub.addPushAutomatico();
			} catch (IOException | GitAPIException | ParserConfigurationException | SAXException | TransformerException
					| URISyntaxException e) {
				return Response.status(500).build();
			}
			return Response.status(200).build();    
    }

}