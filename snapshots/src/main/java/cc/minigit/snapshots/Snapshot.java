package cc.minigit.snapshots;

/**
 * Interface Snapshot
 * @author Arroyo Erick
 * @author Alex Terrazas
 * @author Arturo Mixtli
 * @version 1.0
 */
public interface Snapshot<T> {

    public void update(T t);

    public T[] scan();
    
}
