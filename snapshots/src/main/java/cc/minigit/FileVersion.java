package cc.minigit;

import java.util.Objects;

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
        this.content = Objects.requireNonNull(content, "content no puede ser nulo");
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

    /**
     * Método que regresa el hash del contenido del archivo
     * 
     * @return String hash del contenido
     */
    public String getContentHash() {
        return Integer.toString(content.hashCode());
    }

    /**
     * Método que regresa si el contenido de dos archivos es el mismo
     * 
     * @param other archivo con el que se compara
     * @return boolean true si el contenido es el mismo, false en otro caso
     */
    public boolean isSameContent(FileVersion other) {
        return this.content.equals(other.content);
    }
}
