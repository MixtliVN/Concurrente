package kass.concurrente.candadosImpl;

import kass.concurrente.candados.Semaphore;

/**
 * Clase que modela el Algoritmo del Filtro Modificado
 * Este algoritmo es similar al del filtro, lo diferente es que
 * permite una cantidad m de hilos SIMULTANEOS en la seccion critica
 * Todo es casi igual, solo realiza la modificacion pertinente para esto
 * @version 1.0
 * @author Kassandra Mirael
 */
public class Filtro implements Semaphore {

    private int[] nivel; 
    private int[] victima;
    private int max;

    /**
     * Constructor del Filtro
     * @param hilos El numero de Hilos Permitidos
     * @param maxHilosConcurrentes EL numero de hilos concurrentes simultaneos
     */
    public Filtro(int hilos, int maxHilosConcurrentes) {
        /**
         * AQUI VA TU CODIGO
         */
	this.nivel = new int[hilos];
	this.victima = new int[hilos];
	this.max = maxHilosConcurrentes;
	for (int i = 0; i < hilos; i++){
	    nivel[i] = 0;
	}
    }

    @Override
    public int getPermitsOnCriticalSection() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPermitsOnCriticalSection'");
    }

    @Override
    public void acquire() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'acquire'");
	//int id = (int) Thread.currentThread().getId();
	int id = Integer.parseInt(Thread.currentThread().getName());
	for(int i = 1; i < this.nivel.length; i++){
	    nivel[id] = i;
	    victima[i] = id;
	    boolean conflicts_exist = true;
	    while (conflicts_exist) {
		conflicts_exist = false;
		for (int k = 1; k < this.nivel.length; k++) {
		    if (k != id && nivel[k] >= i && victima[i] == id) {
			conflicts_exist = true;
			break;
		    }
		}
	    }
	}
    }

    @Override
    public void release() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'release'");
        //int i = (int) Thread.currentThread().getId();
	int i = Integer.parseInt(Thread.currentThread().getName());
        nivel[i] = 0;
    }
    
}
