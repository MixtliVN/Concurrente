package kas.concurrente.candadosImp;

import kas.concurrente.candado.Lock;

public class Peterson implements Lock{
    private final boolean []flag = new boolean[2];
    private volatile int victim;

    @Override
    public void lock() {
        int i = (int) Thread.currentThread().getId(); 
        int j= 1 - i;
        flag[i] = true;    
        victim = i;        
        while(flag[j] && victim == i){}; 
    }

    @Override
    public void unlock() {
        int i = (int) Thread.currentThread().getId();
        flag[i] = false;
    }
    
}
