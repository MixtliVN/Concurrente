package cc.minigit;

public class FileVersion {
    private final String content;
    private final long commitId;

    public FileVersion(String content, long commitId) {
        this.content = content;
        this.commitId = commitId;
    }

    public String getContent() {
        return content;
    }

    public long getCommitId() {
        return commitId;
    }
}
