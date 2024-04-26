package cc.minigit;

/**
 * Clase que representa una versión de un archivo
 * 
 * @author Arroyo Erick
 * @author Alex Terrazas
 * @author Arturo Mixtli
 * @version 1.0
 */
public class FileVersion {
    private final String content;
    private final long commitId;

    /**
     * Constructor de la clase FileVersion
     * 
     * @param content  contenido del archivo
     * @param commitId identificador del commit
     */
    public FileVersion(String content, long commitId) {
        this.content = content;
        this.commitId = commitId;
    }

    /**
     * Método que regresa el contenido del archivo
     * 
     * @return String contenido del archivo
     */
    public String getContent() {
        return content;
    }

    /**
     * Método que regresa el identificador del commit
     * 
     * @return long identificador del commit
     */
    public long getCommitId() {
        return commitId;
    }
}
