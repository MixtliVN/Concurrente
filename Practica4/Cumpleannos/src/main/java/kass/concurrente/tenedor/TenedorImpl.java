package kass.concurrente.tenedor;

import kass.concurrente.candados.Semaphore;
import kass.concurrente.candadosImpl.Filtro;

/**
 * Clase que implementa el tenedor
 * Tenemos una variable entera que cuenta el numero de veces que fue tomado
 * Tiene una variable que simboliza su id
 * 
 * @version 1.1
 * @author Monadics
 */
public class TenedorImpl implements Tenedor {

    private int id;
    private int tt;
    private Semaphore mutex;

    public TenedorImpl(int id) {
        this.id = id;
        tt = 0;
        mutex = new Filtro(2, 1);
    }

    @Override
    public void tomar() {
        try {
            mutex.acquire();
            tt++;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @Override
    public void soltar() {
        mutex.release();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getVecesTomado() {
        return this.tt;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setVecesTomado(int vecesTomado) {
        this.tt = vecesTomado;
    }

}
