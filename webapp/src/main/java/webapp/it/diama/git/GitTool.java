package webapp.it.diama.git;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
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
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class GitTool {

	public Git cloneRepository(String path, String branch)
			throws InvalidRemoteException, TransportException, GitAPIException {
		Git git = Git.cloneRepository().setURI("https://github.com/GA-ENG-TORINO/hello-world")
				.setDirectory(new File(path)).setBranchesToClone(Arrays.asList(branch)).setBranch(branch).call();
		return git;
	}

	public void deleteRepository(String path){		
		try {
			FileDeleteStrategy.FORCE.delete(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void push(Git git, String PATH, String branch, double version)
			throws NoHeadException, NoMessageException, UnmergedPathsException, ConcurrentRefUpdateException,
			WrongRepositoryStateException, AbortedByHookException, GitAPIException, IOException, URISyntaxException {
		File file = new File("/GitHub.properties");
		FileInputStream fileInputStream = new FileInputStream(file);
		Properties fileAuto = new Properties();
		fileAuto.load(fileInputStream);

		Git localGit = Git.init().setDirectory(new File(PATH)).call();
		localGit.add().addFilepattern("pom.xml").call();
		localGit.add().addFilepattern("server/pom.xml").call();
		localGit.add().addFilepattern("webapp/pom.xml").call();
		localGit.commit().setMessage("autocommit versione: "+version)
          .call();
		
		RemoteAddCommand remoteAddCommand = git.remoteAdd();
	      remoteAddCommand.setName("master");
	      remoteAddCommand.setUri(new URIish("git@github.com:GA-ENG-TORINO/hello-world.git"));
	      remoteAddCommand.call();
	      
	      PushCommand pushCommand = git.push();
	      pushCommand.add("master");
	      pushCommand.setRemote("origin");
	      pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(fileAuto.getProperty("user"), fileAuto.getProperty("password")));
	      pushCommand.call();	
	      
	      localGit.getRepository().close();
	      git.getRepository().close();

	}

	public void merge(Git git, String branch) throws RefAlreadyExistsException, RefNotFoundException,
			InvalidRefNameException, CheckoutConflictException, GitAPIException, IOException {
		CheckoutCommand coCmd = git.checkout();
		coCmd.setName(branch);
		coCmd.setCreateBranch(false); // probably not needed, just to make sure
		coCmd.call(); // switch to "master" branch

		MergeResult mgCmd = git.merge().include(git.getRepository().findRef("refs/remotes/origin/sviluppo"))
				.setCommit(true).setFastForward(MergeCommand.FastForwardMode.NO_FF).
				// setSquash(false).
				setMessage("Merged changes").call();

	}

}
