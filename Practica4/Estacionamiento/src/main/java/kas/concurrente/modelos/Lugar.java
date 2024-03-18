package kas.concurrente.modelos;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Clase que modela un Lugar
 * El lugar consta de un id
 * un booleano que nos dice si esta dispoible
 * y un objeto del tipo Semaphore (El semaforo)
 * 
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Lugar {
    private Integer id;
    private Integer vecesEstacionado;
    private Semaphore semaforo;
    private Random r;
    private boolean disponible;

    /**
     * Metodo constructor
     * El lugar por defecto esta disponible
     * Pueden llegar un numero n de carros en el peor de los casos
     * veces estacionado sera el numero de veces que se han estacianado en el lugar
     * Si llegan 2 carros y ambos se estacionan, entonces, su valor sera de 2
     * 
     * @param id El id del Lugar
     */
    public Lugar(int id) {
        this.id = id;
        this.vecesEstacionado = 0;
        this.semaforo = new Semaphore(1);
        this.r = new Random();
        this.disponible = true;
    }

    /**
     * En este metodo se simula que se estaciona
     * PELIGRO: ESTAS ENTRANDO A LA 2da SECCION CRITICA
     * Cambia el valor de disponible a false
     * Y se simula que vas pastel de cumple :D
     * Al final, imprime un texto color ROJO diciendo que va salir (Esperen
     * instrucciones para esto)
     * 
     * @throws InterruptedException Si algo falla
     */
    public void estaciona() throws InterruptedException {
        this.semaforo.acquire();
        this.vecesEstacionado++;
        System.out.format("El proceso %d entro al lugar %d \n", Thread.currentThread().getId(), this.id);
        this.vePorPastel();
        System.out.format("El proceso %d dejo el lugar %d \n", Thread.currentThread().getId(), this.id);
        this.semaforo.release();
    }

    /**
     * En este metodo se genera la sumulación de espera
     * Se genera un tiempo entre 1 y 5 segundos
     * Es pseudo aleatorio
     * 
     * @throws InterruptedException En caso de que falle
     */
    public void vePorPastel() throws InterruptedException {
        int retardo = this.r.nextInt(5) + 1;
        Thread.sleep(retardo * 1000);
    }

    public boolean getDisponible() {
        return this.disponible;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVecesEstacionado() {
        return vecesEstacionado;
    }

    public void setVecesEstacionado(Integer vecesEstacionado) {
        this.vecesEstacionado = vecesEstacionado;
    }

    public Semaphore getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(Semaphore semaforo) {
        this.semaforo = semaforo;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

}
