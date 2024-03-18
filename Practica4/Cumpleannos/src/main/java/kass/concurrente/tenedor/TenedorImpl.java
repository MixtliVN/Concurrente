package kass.concurrente.tenedor;
import kass.concurrente.candados.Lock;
import kass.concurrente.candados.Semaphore;
import kass.concurrente.candadosImpl.Filtro;

/**
 * Clase que implementa el tenedor
 * Tenemos una variable entera que cuenta el numero de veces que fue tomado
 * Tiene una variable que simboliza su id
 * @version 1.1
 * @author Monadics
 */
public class TenedorImpl implements Tenedor {

    private int id;
    private int tt;
    private Semaphore mutex;

    public TenedorImpl(int id){
	this.id = id;
	tt = 0;
	mutex = new Filtro(2, 1);
    }

    @Override
    public void tomar() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'tomar'");
	try {  
	    //acquires a permit from the semaphore  
	    mutex.acquire();
	    tt++;
	}  
	catch (Exception e){  
	    e.printStackTrace(System.out);  
	}
    }

    @Override
    public void soltar() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'soltar'");
        mutex.release();
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
