package cc.minigit;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa un commit
 * 
 * @author Arroyo Erick
 * @author Alex Terrazas
 * @author Arturo Mixtli
 * @version 1.0
 */
public class Commit {
    private final long id;
    private final Map<String, FileVersion> files;
    private final String message;

    /**
     * Constructor de la clase Commit
     * 
     * @param message mensaje del commit
     * @param id      identificador del commit
     * @param files   archivos del commit
     */
    public Commit(String message, long id, Map<String, FileVersion> files) {
        this.id = id;
        this.files = files;
        this.message = message;
    }

    /**
     * Método que regresa el id del commit
     * 
     * @return long id
     */
    public long getId() {
        return id;
    }

    /**
     * Método que regresa los archivos del commit
     * 
     * @return Map<String, FileVersion> archivos
     */
    public Map<String, FileVersion> getFiles() {
        return new HashMap<>(files);
    }

    /**
     * Método que regresa el mensaje del commit
     * 
     * @return String mensaje
     */
    public String getMessage() {
        return message;
    }

}