package cc.minigit.snapshotsImp;

import java.util.concurrent.atomic.AtomicStampedReference;
import cc.minigit.snapshots.Snapshot;
import cc.minigit.stamped.StampedSnap;
import cc.minigit.stamped.StampedValue;

/**
 * Implementación de Wait-Free Snapshot
 * 
 * @author Arroyo Erick
 * @author Alex Terrazas
 * @author Arturo Mixtli
 * @version 1.0
 */
public class WFS<T> implements Snapshot<T> {
    private AtomicStampedReference<StampedValue<T>>[] aTable;

    public WFS(int capacity) {
        aTable = (AtomicStampedReference<StampedValue<T>>[]) new AtomicStampedReference[capacity];
        for (int i = 0; i < aTable.length; i++) {
            aTable[i] = new AtomicStampedReference<>(new StampedValue<>(0, null), 0);
        }
    }

    /**
     * Método que recolecta los valores de los AtomicStampedReference
     * 
     * @return StampedSnap<T> snapshot de los valores
     */
    public StampedSnap<T> collect() {
        StampedValue<T>[] values = (StampedValue<T>[]) new StampedValue[aTable.length];
        for (int i = 0; i < aTable.length; i++) {
            values[i] = aTable[i].get(new int[1]); // Extraemos el StampedValue del AtomicStampedReference
        }
        T[] snap = (T[]) new Object[aTable.length];
        for (int i = 0; i < snap.length; i++) {
            snap[i] = values[i].getValue();
        }
        return new StampedSnap<>(0, null, snap); // El staomp y value no se usan para el snapshot en sí
    }

    /**
     * Método que actualiza el valor de un AtomicStampedReference
     * 
     * @param value valor a actualizar
     */
    @Override
    public void update(T value) {
        int[] stampHolder = new int[1];
        int myIndex = (int) (Thread.currentThread().getId());

        StampedValue<T> oldValue = aTable[myIndex].get(stampHolder);
        StampedValue<T> newValue = new StampedValue<>(stampHolder[0] + 1, value);
        aTable[myIndex].compareAndSet(oldValue, newValue, stampHolder[0], stampHolder[0] + 1);
    }

    /**
     * Método que escanea los valores de los AtomicStampedReference
     * 
     * @return T[] arreglo de valores
     */
    @Override
    public T[] scan() {
        StampedSnap<T> oldSnap;
        StampedSnap<T> newSnap;
        boolean[] moved = new boolean[aTable.length];
        oldSnap = collect();
        while (true) {
            newSnap = collect();
            for (int j = 0; j < aTable.length; j++) {
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