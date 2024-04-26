package cc.minigit.snapshotsImp;

import java.lang.management.ThreadInfo;
import java.util.concurrent.atomic.AtomicStampedReference;

import cc.minigit.snapshots.Snapshot;
import cc.minigit.stamped.StampedSnap;
import cc.minigit.stamped.StampedValue;

public class WFS<T> implements Snapshot<T> {
    private AtomicStampedReference<StampedValue<T>>[] a_table;

    public WFS(int capacity) {
        // Initialize the atomic reference array with the given capacity
        a_table = (AtomicStampedReference<StampedValue<T>>[]) new AtomicStampedReference[capacity];
        for (int i = 0; i < a_table.length; i++) {
            a_table[i] = new AtomicStampedReference<>(new StampedValue<>(0, null), 0);
        }
    }

    private StampedSnap<T> collect() {
        StampedValue<T>[] values = (StampedValue<T>[]) new StampedValue[a_table.length];
        for (int i = 0; i < a_table.length; i++) {
            values[i] = a_table[i].get(new int[1]); // Extract StampedValue from AtomicStampedReference
        }
        T[] snap = (T[]) new Object[a_table.length];
        for (int i = 0; i < snap.length; i++) {
            snap[i] = values[i].getValue();
        }
        return new StampedSnap<>(0, null, snap); // The stamp and value aren't used for the snapshot itself
    }

    @Override
    public void update(T value) {
        int[] stampHolder = new int[1];
        int myIndex = (int)(Thread.currentThread().getId());

        StampedValue<T> oldValue = a_table[myIndex].get(stampHolder);
        StampedValue<T> newValue = new StampedValue<>(stampHolder[0] + 1, value);
        a_table[myIndex].compareAndSet(oldValue, newValue, stampHolder[0], stampHolder[0] + 1);
    }

    @Override
    public T[] scan() {
        StampedSnap<T> oldSnap;
        StampedSnap<T> newSnap;
        boolean[] moved = new boolean[a_table.length];
        oldSnap = collect();
        while (true) {
            newSnap = collect();
            for (int j = 0; j < a_table.length; j++) {
                if (oldSnap.getSnap()[j] != newSnap.getSnap()[j]) {
                    if (moved[j]) {
                        return oldSnap.getSnap();
                    } else {
                        moved[j] = true;
                        oldSnap = newSnap;
                    }
                }
            }
            return newSnap.getSnap(); 
        }
    }
}