package cc.minigit;

import java.io.File;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        // Create a new repository instance
        Repository repository = new Repository();

        // Simulate adding files to the repository
        repository.add("file1.txt", "This is the content of file1.");
        repository.add("file2.txt", "This is the content of file2.");

        // Commit a master branch
        long commitId0 = repository.commit("Initial commit");

        // Create a new branch and switch to it
        repository.createBranch("new-feature");
        String checkoutResult = repository.checkout("new-feature", commitId0);
        System.out.println(checkoutResult);

        // Add more changes to the new branch
        repository.add("file3.txt", "This is the content of file3 on new-feature branch.");
        long commitId1 = repository.commit("Add file3");

        // Switch back to master and commit another change
        repository.checkout("new-feature", commitId1);
        repository.checkout("master", commitId0);
        repository.checkout("new-feature", commitId1);
        repository.push("master");
        repository.checkout("master", commitId1);
        repository.add("archivoPull.txt", "Este es el contenido del archivo que se va a hacer pull");
        long commitId2 = repository.commit("Merge new-feature into master");
        repository.pullRequest("master", "new-feature");
        repository.checkout("new-feature", commitId2);
        repository.checkout("master", commitId2);


    }
}
