package webapp.it.diama.git;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GitToolTest {
	GitTool gitTool=new GitTool();
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void deleteRepository() throws IOException {		
		gitTool.deleteRepository(System.getProperty("user.home")+"/gitRemoto");
	}
	
	@Test
	public void cloneRepository() throws InvalidRemoteException, TransportException, GitAPIException {
		gitTool.cloneRepository(System.getProperty("user.home")+"/gitRemoto", "refs/heads/sviluppo");
	}
	

	
	
}
