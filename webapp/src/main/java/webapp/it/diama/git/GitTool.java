package webapp.it.diama.git;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class GitTool {

	public Git cloneRepository(String path, String branch)
			throws InvalidRemoteException, TransportException, GitAPIException {
		Git git = Git.cloneRepository().setURI("https://github.com/GA-ENG-TORINO/hello-world")
				.setDirectory(new File(path)).setBranchesToClone(Arrays.asList(branch)).setBranch(branch).call();
		return git;
	}

	public void deleteRepository(String path) throws IOException {
		FileUtils.deleteDirectory(new File(path));
	}

	public void push(Git git, double version)
			throws NoHeadException, NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException,
			WrongRepositoryStateException, AbortedByHookException, GitAPIException, IOException {
		File file = new File(System.getProperty("user.home") + "/GitHub.properties");
		FileInputStream fileInputStream = new FileInputStream(file);
		Properties fileAuto = new Properties();
		fileAuto.load(fileInputStream);
		git.commit().setMessage("auto commit versione " + version).call();
		git.push().setCredentialsProvider(
				new UsernamePasswordCredentialsProvider(fileAuto.getProperty("user"), fileAuto.getProperty("paasword")))
				.call();
	}

	public void merge(Git git, String branch) throws RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException {
		CheckoutCommand coCmd = git.checkout(); 
		coCmd.setName(branch);
		coCmd.setCreateBranch(false); // probably not needed, just to make sure
		coCmd.call(); // switch to "master" branch

		MergeResult mgCmd = git.merge().call(); // actually do the merge		
	}

}
