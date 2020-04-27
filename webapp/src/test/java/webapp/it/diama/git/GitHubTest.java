package webapp.it.diama.git;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GitHubTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddPushAutomatico() throws Exception {
		GitHub.addPushAutomatico();
		System.out.println("Test Case Bypassato, in produzione jenkins in fase di deploy committa una nuova versione ogni minuto");
	}

}
