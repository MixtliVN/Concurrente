package kass.concurrente.candadosImpl;

import kass.concurrente.candados.Semaphore;

/**
 * Clase que modela el Algoritmo del Filtro Modificado
 * Este algoritmo es similar al del filtro, lo diferente es que
 * permite una cantidad m de hilos SIMULTANEOS en la seccion critica
 * Todo es casi igual, solo realiza la modificacion pertinente para esto
 * 
 * @version 1.0
 * @author Kassandra Mirael
 */
public class Filtro implements Semaphore {

    private volatile int[] nivel;
    private volatile int[] victima;
    private volatile int max;
    private volatile int permits;

    /**
     * Constructor del Filtro
     * 
     * @param hilos                El numero de Hilos Permitidos
     * @param maxHilosConcurrentes EL numero de hilos concurrentes simultaneos
     */
    public Filtro(int hilos, int maxHilosConcurrentes) {
        this.nivel = new int[hilos];
        this.victima = new int[hilos];
        this.max = maxHilosConcurrentes;
        this.permits = maxHilosConcurrentes;
        for (int i = 0; i < hilos; i++) {
            nivel[i] = 0;
        }
    }

    @Override
    public int getPermitsOnCriticalSection() {
        return this.permits;
    }

    @Override
    public void acquire() {
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
        while (permits <= 0) {
        }
        ;
        this.permits--;
    }

    @Override
    public void release() {
        int i = Integer.parseInt(Thread.currentThread().getName());
        i = i % this.nivel.length;
        nivel[i] = 0;
        this.permits += 1;
    }
}
