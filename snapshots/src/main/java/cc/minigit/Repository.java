package cc.minigit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cc.minigit.snapshotsImp.WFS;

public class Repository {
    private WFS<Commit> commitHistory;
    private Map<String, List<Commit>> branches; // Maps branch names to commit histories
    private Map<String, FileVersion> stagingArea; // Staging area to store added files before commit
    private long nextCommitId; // Auto-incrementing commit id
    private String currentBranchName; // The current active branch

    public Repository() {
        commitHistory = new WFS<>(100); // Supongamos un máximo de 100 commits por rama
        branches = new HashMap<>();
        stagingArea = new HashMap<>();
        nextCommitId = 0;
        // Default branch
        branches.put("master", new ArrayList<>());
        currentBranchName = "master";
    }

    public void add(String fileName, String content) {
        stagingArea.put(fileName, new FileVersion(content, nextCommitId));
    }

    public long commit(String message) {
        if (stagingArea.isEmpty()) {
            throw new IllegalStateException("No changes to commit.");
        }
        List<Commit> currentBranch = branches.get(currentBranchName);
        // Gather the latest commit's files and apply staged changes
        Map<String, FileVersion> newFilesState = currentBranch.isEmpty() ? 
            new HashMap<>() : new HashMap<>(currentBranch.get(currentBranch.size() - 1).getFiles());
        newFilesState.putAll(stagingArea);
        
        Commit newCommit = new Commit(nextCommitId++, newFilesState);
        currentBranch.add(newCommit);

        stagingArea.clear(); // Clear staging area after commit
        return newCommit.getId();
    }

    public void push(String branchName) {
        if (!branches.containsKey(branchName)) {
            throw new IllegalArgumentException("Branch does not exist: " + branchName);
        }
        List<Commit> currentBranch = branches.get(currentBranchName);
        List<Commit> targetBranch = branches.get(branchName);
        List<Commit> commitsToPush = currentBranch.stream()
            .filter(commit -> commit.getId() > (targetBranch.isEmpty() ? -1 : targetBranch.get(targetBranch.size() - 1).getId()))
            .collect(Collectors.toList());
        
        targetBranch.addAll(commitsToPush);
    }

    public String checkout(String branchName, long commitId) {
        if (!branches.containsKey(branchName)) {
            throw new IllegalArgumentException("Branch does not exist: " + branchName);
        }
        
        List<Commit> branchCommits = branches.get(branchName);
        Commit checkoutCommit = branchCommits.stream()
            .filter(commit -> commit.getId() == commitId)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Commit does not exist: " + commitId));
        
        // Here, you could clone the directory state of the commit into the working directory using Util methods
        // Assuming Util.writeToFile(String path, String content) and Util.createFolder(String path) are available

        for (Map.Entry<String, FileVersion> fileEntry : checkoutCommit.getFiles().entrySet()) {
            String filePath = fileEntry.getKey();
            String fileContent = fileEntry.getValue().getContent();
            // Util.writeToFile(filePath, fileContent);
        }

        currentBranchName = branchName; // Update current branch
        return "Checked out to commit " + commitId + " on branch " + branchName;
    }

    public void createBranch(String branchName) {
        if (branches.containsKey(branchName)) {
            throw new IllegalArgumentException("Branch already exists: " + branchName);
        }
        List<Commit> currentBranch = branches.get(currentBranchName);
        List<Commit> newBranchCommits = new ArrayList<>(currentBranch);
        branches.put(branchName, newBranchCommits);
    }

    public boolean pullRequest(String sourceBranch, String targetBranch) {
        if (!branches.containsKey(sourceBranch) || !branches.containsKey(targetBranch)) {
            throw new IllegalArgumentException("One or both branches do not exist.");
        }

        List<Commit> sourceCommits = branches.get(sourceBranch);
        List<Commit> targetCommits = branches.get(targetBranch);
        Commit lastTargetCommit = targetCommits.isEmpty() ? null : targetCommits.get(targetCommits.size() - 1);
        
        if (lastTargetCommit != null && sourceCommits.size() > 0 && sourceCommits.get(sourceCommits.size() - 1).getId() > lastTargetCommit.getId()) {
            // Emulate a merge by adding all unique commits from the source branch to the target branch
            List<Commit> uniqueSourceCommits = sourceCommits.stream()
                .filter(commit -> commit.getId() > lastTargetCommit.getId())
                .collect(Collectors.toList());
            targetCommits.addAll(uniqueSourceCommits);
            return true;
        }
        
        return false;
    }
}

