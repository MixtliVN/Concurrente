package kass.concurrente.invitados;

import java.util.logging.Logger;

import kass.concurrente.tenedor.Tenedor;

/**
 * Clase abstracta que modela al inversionista.
 * El inversionista tiene 2 tenedores a sus lados.
 * El inversionista posee un ID para que se pueda identificar.
 * El inversionista tiene una variable que indica el numero de veces que ha
 * comido.
 * 
 * @version 1.0
 * @author Kassandra Mirael
 */
public abstract class Inversionista implements Runnable {

    protected Tenedor izq;
    protected Tenedor der;
    protected int id;
    protected int vecesComido;
    private static final Logger logger = Logger.getLogger(Inversionista.class.getName());

    @Override
    public void run() {
        try {
            while (true) {
                piensa();
                entraALaMesa();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Metodo que nos permite entrar a la mesa.
     * El inversionista por fin dejo de pensar y de escribir en su
     * servilleta y se digna en entrar.
     * PRIMERO toma los tenedores.
     * DESPUES come.
     * FINALMENTE los suelta para que los demas los puedan usar.
     * 
     * @throws InterruptedException <Escribe porque se lanzaria esta exception>
     */
    public void entraALaMesa() throws InterruptedException {
        tomaTenedores();
        come();
        sueltaTenedores();
    }

    /**
     * Una vez que termino de pensar sobre finanzas el inversionista
     * se prepara para comer.
     * El inversionista le toma un par de milisegundos comer.
     * ESTA ES LA SECCION CRITICA, SIGNIFICA PELIGRO
     * Incrementa el numero de veces que ha comido.
     * 
     * @throws InterruptedException <Escribe porque se lanzaria esta exception>
     */
    public void come() throws InterruptedException {
        vecesComido++;
        logger.info("Inversionista " + id + " esta comiendo...");
        //Thread.sleep(1);

    }

    /**
     * Metodo que hace que el inversionista piense.
     * El inversionista pensara por una par de milisegundos.
     * Esto pasa antes de que tome sus tenedores.
     * 
     * @throws InterruptedException <Escribe porque se lanzaria esta exception>
     */
    public void piensa() throws InterruptedException {
        logger.info("Inversionista " + id + " esta pensando...");
        Thread.sleep(this.generaTiempoDeEspera());
    }

    /**
     * Metodo que nos permite tomar los tenedores.
     * Los toma uno por uno.
     */
    public abstract void tomaTenedores();

    /**
     * Metodo que hace que el inversionista suelte ambos tenedores una vez
     * que terminara de comer.
     * De esta manera otro los puede usar.
     * Suelta los tenedores uno por uno.
     */
    public abstract void sueltaTenedores();

    /**
     * Metodo que genera un numero pseudoaleatorio entre 1 y 10
     * 
     * @return El tiempo de espera
     */
    private long generaTiempoDeEspera() {
        double i = Math.random() * 10.0;
        return (long) i;
    }

    /**
     * Metodo que nos permite obtener el tenedor izquierdo
     * 
     * @return Tenedor izquierdo
     */
    public Tenedor getTenedorIzq() {
        return izq;
    }

    /**
     * Metodo que nos permite asignar el tenedor izquierdo
     * 
     * @param izq Tenedor izquierdo
     */
    public void setTenedorIzq(Tenedor izq) {
        this.izq = izq;
    }

    /**
     * Metodo que nos permite obtener el tenedor derecho
     * 
     * @return Tenedor derecho
     */
    public Tenedor getTenedorDer() {
        return der;
    }

    /**
     * Metodo que nos permite asignar el tenedor derecho
     * 
     * @param der Tenedor derecho
     */
    public void setTenedorDer(Tenedor der) {
        this.der = der;
    }

    /**
     * Metodo que nos permite obtener el ID del inversionista
     * 
     * @return ID del inversionista
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo que nos permite asignar el ID del inversionista
     * 
     * @param id ID del inversionista
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metodo que nos permite obtener el numero de veces que ha comido
     * 
     * @return Veces que ha comido
     */
    public int getVecesComido() {
        return vecesComido;
    }

    /**
     * Metodo que nos permite asignar el numero de veces que ha comido
     * 
     * @param vecesComido Veces que ha comido
     */
    public void setVecesComido(int vecesComido) {
        this.vecesComido = vecesComido;
    }

}
