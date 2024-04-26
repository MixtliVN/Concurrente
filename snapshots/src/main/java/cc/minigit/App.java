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

        // Commit changes
        long commitId1 = repository.commit("Initial commit");

        // Create a new branch and switch to it
        repository.createBranch("new-feature");
        String checkoutResult = repository.checkout("new-feature", commitId1);

        // Add more changes to the new branch
        repository.add("file3.txt", "This is the content of file3 on new-feature branch.");
        long commitId2 = repository.commit("Add file3");
        

    }
}
