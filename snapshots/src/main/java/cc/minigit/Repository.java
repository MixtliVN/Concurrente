package cc.minigit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import cc.minigit.snapshotsImp.WFS;
import cc.minigit.stamped.StampedSnap;

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
        // Snapshot actual del commit más reciente
        StampedSnap<Commit> snapshot = commitHistory.collect();
        // Obtiene la ultima version los archivos o crea uno nuevo si es nuevo
        Map<String, FileVersion> lastFilesState = snapshot.getValue() != null ? snapshot.getValue().getFiles()
                : new HashMap<>();

        Map<String, FileVersion> newFilesState = new HashMap<>(lastFilesState);

        // Agrega o actualiza el archivo
        newFilesState.put(fileName, new FileVersion(content, nextCommitId));

        // Actualiza el staging area con el nuevo estado de archivos
        stagingArea.clear();
        stagingArea.putAll(newFilesState);
    }

    /**
     * Realiza un commit de los cambios en el staging area al repositorio,
     * capturando un nuevo snapshot.
     * 
     * @param message mensaje del commit
     * @return id del commit
     */
    public synchronized long commit(String message) {
        if (stagingArea.isEmpty()) {
            throw new IllegalStateException("No changes to commit.");
        }

        // Captura el estado actual desde el ultimo snapshot
        StampedSnap<Commit> snapshot = commitHistory.collect();
        Commit lastCommit = snapshot.getValue(); // Puede ser null si es el primer commit
        Map<String, FileVersion> lastFilesState = lastCommit != null ? new HashMap<>(lastCommit.getFiles())
                : new HashMap<>();

        // Combina el estado del ultimo commit con los cambios del staging
        lastFilesState.putAll(stagingArea);

        Commit newCommit = new Commit(message, nextCommitId++, lastFilesState);
        branches.get(currentBranchName).add(newCommit);

        // Actualiza el snapshot
        commitHistory.update(newCommit);
        stagingArea.clear();
        return newCommit.getId();
    }

    /**
     * Empuja los cambios de la rama actual a otra rama, asegurando que los commits
     * sean transferidos
     * de manera consistente y atómica.
     * 
     * @param branchName nombre de la rama a la que se empujarán los cambios
     */
    public synchronized void push(String branchName) {
        if (!branches.containsKey(branchName)) {
            throw new IllegalArgumentException("Branch does not exist: " + branchName);
        }
        if (currentBranchName.equals(branchName)) {
            throw new IllegalArgumentException("Cannot push to the same branch: " + branchName);
        }

        List<Commit> currentBranch = branches.get(currentBranchName);
        List<Commit> targetBranch = branches.get(branchName);

        // Obtenemos el id del último commit en la rama destino
        long lastTargetCommitId = targetBranch.isEmpty() ? -1 : targetBranch.get(targetBranch.size() - 1).getId();

        // Filtrar los commits que aún no están en la rama destino.
        List<Commit> commitsToPush = currentBranch.stream()
                .filter(commit -> commit.getId() > lastTargetCommitId)
                .collect(Collectors.toList());

        if (commitsToPush.isEmpty()) {
            throw new IllegalStateException("No new commits to push.");
        }

        targetBranch.addAll(commitsToPush);
    }

    /**
     * Cambia de rama y de commit, copiando el estado de un commit al directorio de
     * trabajo actual.
     * Asegura la coherencia mediante el uso de bloqueo para evitar condiciones de
     * carrera.
     * 
     * @param branchName nombre de la rama a la cual cambiar
     * @param commitId   id del commit al que cambiar
     * @return mensaje de confirmación
     */
    public synchronized String checkout(String branchName, long commitId) {
        if (!branches.containsKey(branchName)) {
            throw new IllegalArgumentException("Branch does not exist: " + branchName);
        }
        List<Commit> branchCommits = branches.get(branchName);

        Commit checkoutCommit = branchCommits.stream()
                .filter(commit -> commit.getId() == commitId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Commit does not exist: " + commitId));
        // Actualiza el directorio de trabajo con los archivos del commit
        setCurrentWorkingDirectory(branchName, checkoutCommit);
        currentBranchName = branchName;
        return "Checked out to commit " + commitId + " on branch " + branchName;
    }

    /**
     * Método que establece el directorio de trabajo actual con los archivos de un
     * commit
     * 
     * @param branchName     nombre de la rama
     * @param checkoutCommit commit al que cambiar
     */
    private void setCurrentWorkingDirectory(String branchName, Commit checkoutCommit) {
        Util.mkdir(branchName);

        // Actualiza el contenido del directorio con los archivos del commit
        for (Map.Entry<String, FileVersion> fileEntry : checkoutCommit.getFiles().entrySet()) {
            String filePath = branchName + Util.SEPARADOR + fileEntry.getKey();
            String fileContent = fileEntry.getValue().getContent();
            Util.write(filePath, fileContent);
        }
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
        // Obtenemos los commits de las ramas
        Commit baseCommit = findBaseCommit(sourceBranch, targetBranch);
        Commit targetCommit = branches.get(targetBranch).get(branches.get(targetBranch).size() - 1);
        Commit sourceCommit = branches.get(sourceBranch).get(branches.get(sourceBranch).size() - 1);
        // Veriiica si algun parametro es nulo
        if (baseCommit == null || targetCommit == null || sourceCommit == null) {
            throw new IllegalArgumentException("One or more commits are null.");
        }

        return detectConflicts(baseCommit, targetCommit, sourceCommit).isEmpty();
    }

    /**
     * Método que regresa la rama actual
     * 
     * @return lista de commits de la rama actual
     */
    public List<Commit> getCurrentBranch() {
        return branches.get(currentBranchName);
    }

    /**
     * Detecta conflictos entre dos commits.
     *
     * @param baseCommit   El commit base común más reciente.
     * @param targetCommit El commit en la rama destino.
     * @param sourceCommit El commit en la rama fuente.
     * @return Un mapa de archivos conflictivos con detalles de los conflictos.
     */
    private Map<String, String> detectConflicts(Commit baseCommit, Commit targetCommit, Commit sourceCommit) {
        Map<String, FileVersion> baseFiles = baseCommit.getFiles();
        Map<String, FileVersion> targetFiles = targetCommit.getFiles();
        Map<String, FileVersion> sourceFiles = sourceCommit.getFiles();

        Map<String, String> conflicts = new HashMap<>();

        // Verifica cada archivo en la rama fuente contra la rama destino.
        sourceFiles.forEach((fileName, sourceVersion) -> {
            FileVersion targetVersion = targetFiles.get(fileName);
            FileVersion baseVersion = baseFiles.get(fileName);

            if ((targetVersion != null && !sourceVersion.getContent().equals(targetVersion.getContent()))
                    && (baseVersion == null || !baseVersion.getContent().equals(sourceVersion.getContent()))) {
                // Encontramos un conflicto si el archivo fue modificado en ambas ramas
                conflicts.put(fileName, "Conflict detected: File modified in both branches.");
            }
        });

        return conflicts;
    }

    /**
     * Resuelve un conflicto manualmente para un archivo específico.
     *
     * @param fileName        El nombre del archivo con conflictos.
     * @param resolvedContent El contenido resuelto proporcionado por el usuario.
     */
    public void resolveConflict(String fileName, String resolvedContent) {
        if (currentBranchName == null) {
            throw new IllegalStateException("No active branch selected.");
        }
        // Revisar
        // Actualiza el archivo en el staging area con el contenido resuelto
        stagingArea.put(fileName, new FileVersion(resolvedContent, nextCommitId));
    }

    /**
     * Encuentra el commit base común más reciente entre dos ramas.
     *
     * @param branchName1 El nombre de la primera rama.
     * @param branchName2 El nombre de la segunda rama.
     * @return El commit base común más reciente, o null si no existe tal commit.
     */
    private Commit findBaseCommit(String branchName1, String branchName2) {
        if (!branches.containsKey(branchName1) || !branches.containsKey(branchName2)) {
            throw new IllegalArgumentException("One or both branches do not exist.");
        }

        List<Commit> commitsBranch1 = branches.get(branchName1);
        List<Commit> commitsBranch2 = branches.get(branchName2);

        Set<Long> idsBranch2 = commitsBranch2.stream()
                .map(Commit::getId)
                .collect(Collectors.toSet());

        // Revisa los commits de la primera rama en orden inverso buscando el primer
        // commit que est tambien en la segunda rama
        for (int i = commitsBranch1.size() - 1; i >= 0; i--) {
            Commit commit = commitsBranch1.get(i);
            if (idsBranch2.contains(commit.getId())) {
                return commit;
            }
        }

        return null;
    }

    /**
     * Método que regresa el area de trabajo
     * 
     * @return area de actual trabajo
     */
    public Map<String, FileVersion> getStagingArea() {
        return stagingArea;
    }

    /**
     * Método que regresa el nombre de la rama actual
     * 
     * @return nombre de la rama actual
     */
    public String getCurrentBranchName() {
        return currentBranchName;
    }

    /**
     * Método que regresa la rama de un repositorio
     * 
     * @param branchName nombre de la rama
     * @return lista de commits de la rama
     */
    public List<Commit> getBranch(String branchName) {
        return branches.get(branchName);
    }
}
