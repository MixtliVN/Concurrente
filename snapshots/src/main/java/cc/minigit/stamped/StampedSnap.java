package cc.minigit.stamped;

/**
 * Clase que representa un StampedSnap
 * 
 * @author Arroyo Erick
 * @author Alex Terrazas
 * @author Arturo Mixtli
 * @version 1.0
 */
public class StampedSnap<T> {
    final long stamp;
    final T value;
    final T[] snap;

    /**
     * Constructor de la clase StampedSnap
     * 
     * @param stamp identificador del stamp
     * @param value valor del stamp
     * @param snap  arreglo de valores
     */
    public StampedSnap(long stamp, T value, T[] snap) {
        this.stamp = stamp;
        this.value = value;
        this.snap = snap;
    }

    /**
     * Método que regresa el stamp
     * 
     * @return long stamp
     */
    public long getStamp() {
        return stamp;
    }

    /**
     * Método que regresa el valor del stamp
     * 
     * @return T value
     */
    public T getValue() {
        return value;
    }

    /**
     * Método que regresa el arreglo de valores
     * 
     * @return T[] arreglo de valores
     */
    public T[] getSnap() {
        return snap;
    }
}