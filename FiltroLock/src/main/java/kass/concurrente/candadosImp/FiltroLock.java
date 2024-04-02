package kass.concurrente.candadosImp;

import kass.concurrente.candados.Lock;

public class FiltroLock implements Lock {
    private volatile int[] nivel;
    private volatile int[] victima;
    private volatile int capacidad;

    /**
     * Constructor del FiltroLock
     * 
     * @param hilos El número de hilos admitidos
     */
    public FiltroLock(int hilos) {
        this.nivel = new int[hilos];
        this.victima = new int[hilos];
        this.capacidad = hilos - 1;
        for (int i = 0; i < hilos; i++) {
            nivel[i] = 0;
        }
    }

    @Override
    public void lock() {
        int id = Integer.parseInt(Thread.currentThread().getName());
        id = id % this.nivel.length;
        for (int i = 1; i < this.nivel.length; i++) {
            nivel[id] = i;
            victima[i] = id;
            for (int k = 0; k < nivel.length; k++) {
                while ((k != id) && (nivel[k] >= i && victima[i] == id)) {
                }
            }
        }
        while (capacidad <= 0) {
        }
        capacidad--;
    }

    @Override
    public void unlock() {
        int i = Integer.parseInt(Thread.currentThread().getName());
        i = i % this.nivel.length;
        nivel[i] = 0;
        capacidad++;
    }
}
