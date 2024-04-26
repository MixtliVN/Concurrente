package cc.minigit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import cc.minigit.snapshotsImp.WFS;

/**
 * clase que representa un repositorio con operaciones: add, commit, push,
 * checkout, createBranch, pullRequest
 * 
 * @author Arroyo Erick
 * @author Alex Terrazas
 * @author Arturo Mixtli
 * @version 1.0
 */
public class Repository {
    private WFS<Commit> commitHistory;
    private Map<String, List<Commit>> branches; // Ramas del repositorio
    private Map<String, FileVersion> stagingArea; // area actual de trabajo
    private long nextCommitId;
    private String currentBranchName;

    /**
     * Constructor de la clase
     */
    public Repository() {
        commitHistory = new WFS<>(100); // Supongamos un máximo de 100 commits por rama
        branches = new HashMap<>();
        stagingArea = new HashMap<>();
        nextCommitId = 0;
        // rama por deafult
        branches.put("master", new ArrayList<>());
        currentBranchName = "master";
    }

    /**
     * Agrega un archivo al area de trabajo
     * 
     * @param fileName nombre del archivo
     * @param content  contenido del archivo
     */
    public void add(String fileName, String content) {
        stagingArea.put(fileName, new FileVersion(content, nextCommitId));
    }

    /**
     * Agrega un conjunto de archivos al area de trabajo/ empaqueta los archivos
     * del area actual de trabajo
     * 
     * @param message mensaje del commit
     * @return id del commit
     */
    public long commit(String message) {
        if (stagingArea.isEmpty()) {
            throw new IllegalStateException("No changes to commit.");
        }
        List<Commit> currentBranch = branches.get(currentBranchName);
        Map<String, FileVersion> newFilesState = currentBranch.isEmpty() ? new HashMap<>()
                : new HashMap<>(currentBranch.get(currentBranch.size() - 1).getFiles());
        newFilesState.putAll(stagingArea);

        Commit newCommit = new Commit(message, nextCommitId++, newFilesState);
        currentBranch.add(newCommit);
        commitHistory.update(newCommit);
        stagingArea.clear();
        return newCommit.getId();
    }

    /**
     * Método que empuja los cambios de la rama actual a otra rama
     * 
     * @param branchName nombre de la rama
     */
    public void push(String branchName) {
        if (!branches.containsKey(branchName)) {
            throw new IllegalArgumentException("Branch does not exist: " + branchName);
        }
        List<Commit> currentBranch = branches.get(currentBranchName);
        List<Commit> targetBranch = branches.get(branchName);
        List<Commit> commitsToPush = currentBranch.stream()
                .filter(commit -> commit
                        .getId() > (targetBranch.isEmpty() ? -1 : targetBranch.get(targetBranch.size() - 1).getId()))
                .collect(Collectors.toList());

        targetBranch.addAll(commitsToPush);
    }

    /**
     * Método que cambia de rama y de commit / copia el estado de un commit en el
     * directorio de trabajo actual (emulando un checkout)
     * 
     * @param branchName nombre de la rama
     * @param commitId   id del commit
     * @return mensaje de confirmación
     */
    public String checkout(String branchName, long commitId) {
        if (!branches.containsKey(branchName)) {
            throw new IllegalArgumentException("Branch does not exist: " + branchName);
        }

        List<Commit> branchCommits = branches.get(branchName);

        Commit checkoutCommit = branchCommits.stream()
                .filter(commit -> commit.getId() == commitId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Commit does not exist: " + commitId));

        // La nueva rama se representa como un directorio
        Util.mkdir(branchName);
        for (Map.Entry<String, FileVersion> fileEntry : checkoutCommit.getFiles().entrySet()) {
            String filePath = fileEntry.getKey();
            String fileContent = fileEntry.getValue().getContent();
            Util.write(branchName + Util.SEPARADOR + filePath, fileContent);
        }

        currentBranchName = branchName;
        return "Checked out to commit " + commitId + " on branch " + branchName;
    }

    /**
     * Método que crea una nueva rama
     * 
     * @param branchName nombre de la rama
     */
    public void createBranch(String branchName) {
        if (branches.containsKey(branchName)) {
            throw new IllegalArgumentException("Branch already exists: " + branchName);
        }
        List<Commit> currentBranch = branches.get(currentBranchName);
        List<Commit> newBranchCommits = new ArrayList<>(currentBranch);
        branches.put(branchName, newBranchCommits);
    }

    /**
     * Emula un pull request desde una rama de origen a una rama de destinp
     * 
     * @param sourceBranch rama de origen
     * @param targetBranch rama de destino
     * @return true si se realizó el pull request, false en caso contrario
     */
    public boolean pullRequest(String sourceBranch, String targetBranch) {
        if (!branches.containsKey(sourceBranch) || !branches.containsKey(targetBranch)) {
            throw new IllegalArgumentException("One or both branches do not exist.");
        }

        List<Commit> sourceCommits = branches.get(sourceBranch);
        List<Commit> targetCommits = branches.get(targetBranch);
        Commit lastTargetCommit = targetCommits.isEmpty() ? null : targetCommits.get(targetCommits.size() - 1);

        if (lastTargetCommit != null && !sourceCommits.isEmpty()
                && sourceCommits.get(sourceCommits.size() - 1).getId() > lastTargetCommit.getId()) {
            // Emula un merge, busca los archivos con commits más recientes
            List<Commit> uniqueSourceCommits = sourceCommits.stream()
                    .filter(commit -> commit.getId() > lastTargetCommit.getId())
                    .collect(Collectors.toList());
            targetCommits.addAll(uniqueSourceCommits);
            return true;
        }
        return false;
    }

    /**
     * Método que regresa la rama actual
     * 
     * @return lista de commits de la rama actual
     */
    public List<Commit> getCurrentBranch() {
        return branches.get(currentBranchName);
    }
}
