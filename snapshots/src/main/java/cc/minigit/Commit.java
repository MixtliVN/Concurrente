package cc.minigit;

import java.util.HashMap;
import java.util.Map;

public class Commit {
    private final long id;
    private final Map<String, FileVersion> files;
    private final String message;

    public Commit(String message,long id, Map<String, FileVersion> files) {
        this.id = id;
        this.files = files;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public Map<String, FileVersion> getFiles() {
        return new HashMap<>(files);
    }
    public String getMessage() {
        return message;
    }
    
}
