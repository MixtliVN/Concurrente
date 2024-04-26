package cc.minigit.stamped;

/**
 * Clase que representa un StampedValue
 * 
 * @author Arroyo Erick
 * @author Alex Terrazas
 * @author Arturo Mixtli
 * @version 1.0
 */
public class StampedValue<T> {
    private final long stamp;
    private final T value;

    /**
     * Constructor de la clase StampedValue
     * 
     * @param stamp identificador del stamp
     * @param value valor del stamp
     */
    public StampedValue(long stamp, T value) {
        this.stamp = stamp;
        this.value = value;
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

}