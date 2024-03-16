package kas.concurrente.candadosImp;

import kas.concurrente.candado.Lock;

public class Filtro implements Lock {

    private int[] nivel; 
    private int[] victima; 

    public Filtro(int n) {
        nivel = new int[n];
        victima = new int[n];
        for (int i = 1; i < n; ++i) {
            nivel[i] = 0;
        }

    }

    @Override
    public void lock() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'lock'");
    }

    @Override
    public void unlock() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'unlock'");
    }

}
