package cc.minigit.stamped;

public class StampedSnap<T> {
    final long stamp;
    final T value;
    final T[] snap;

    public StampedSnap(long stamp, T value, T[] snap) {
        this.stamp = stamp;
        this.value = value;
        this.snap = snap;
    }

    public long getStamp() {
        return stamp;
    }

    public T getValue() {
        return value;
    }

    public T[] getSnap() {
        return snap;
    }
}