package cc.minigit.snapshots;

public interface Snapshot<T> {

    public void update(T t);

    public T[] scan();
    
}
