package cc.minigit;

import java.util.HashMap;
import java.util.Map;

public class Commit {
    private final long id;
    private final Map<String, FileVersion> files;

    public Commit(long id, Map<String, FileVersion> files) {
        this.id = id;
        this.files = files;
    }

    public long getId() {
        return id;
    }

    public Map<String, FileVersion> getFiles() {
        return new HashMap<>(files);
    }
    
}
