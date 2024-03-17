package kass.concurrente.tenedor;
import kass.concurrente.candados.Lock;
import kass.concurrente.candadosImpl.PetersonLock;

/**
 * Clase que implementa el tenedor
 * Tenemos una variable entera que cuenta el numero de veces que fue tomado
 * Tiene una variable que simboliza su id
 * @version 1.1
 * @author Monadics
 */
public class TenedorImpl implements Tenedor {

    private volatile int id;
    private volatile int tt;
    private Lock lock;

    public TenedorImpl(int id){
	this.id = id;
	tt = 0;
	lock = new PetersonLock();
    }

    @Override
    public void tomar() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'tomar'");
	lock.lock();
	tt++;
    }

    @Override
    public void soltar() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'soltar'");
	lock.unlock();
    }

    @Override
    public int getId() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'getId'");
	return this.id;
    }

    @Override
    public int getVecesTomado() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'vecesTomado'");
	return this.tt;
    }

    @Override
    public void setId(int id) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'setId'");
	this.id = id;
    }

    @Override
    public void setVecesTomado(int vecesTomado) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'setVecesTomado'");
	this.tt = vecesTomado;
    }
    
}
