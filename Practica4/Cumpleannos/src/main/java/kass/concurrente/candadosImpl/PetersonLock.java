package kass.concurrente.candadosImpl;

import kass.concurrente.candados.Lock;

/**
 * Clase que implementa el candado usando el Legendario
 * algoritmo de PeterGod.
 * No hay mucho que decir, ya saben que hacer
 * @version 1.0
 * @author Kassandra Mirael
 */
public class PetersonLock implements Lock {
    private volatile boolean []flag = new boolean[2];
    private volatile int victim;
    private volatile boolean barr;

    @Override
    public void lock() {
	int i = Integer.parseInt(Thread.currentThread().getName());
	i = i % 2;
        int j = 1 - i;
        flag[i] = true;    
        victim = i;        
        while(barr && flag[j] && victim == i){}; 
    }

    @Override
    public void unlock() {
	int i = Integer.parseInt(Thread.currentThread().getName());
	i = i % 2;
        flag[i] = false;
	barr = true;
    }
    
}
