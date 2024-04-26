package cc.minigit.stamped;

public class StampedValue<T> {
    private final long stamp;
    private final T value;

    public StampedValue(long stamp, T value) {
        this.stamp = stamp;
        this.value = value;
    }

    public long getStamp() {
        return stamp;
    }

    public T getValue() {
        return value;
    }
}
