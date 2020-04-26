package webapp.it.diama.git;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

public class GitTool {

	public void cloneRepository(String path, String branch)throws InvalidRemoteException, TransportException, GitAPIException {
		System.out.print(path);
		Git.cloneRepository().setURI("https://github.com/GA-ENG-TORINO/hello-world")
			.setDirectory(new File(path))
			.setBranchesToClone(Arrays.asList(branch))
			.setBranch(branch)
			.call();
	}
	
	public void deleteRepository(String path) throws IOException {
		FileUtils.deleteDirectory(new File(path));
	}
}
