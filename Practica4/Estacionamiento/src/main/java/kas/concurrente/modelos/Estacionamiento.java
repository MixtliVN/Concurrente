package kas.concurrente.modelos;

import java.util.Random;
import java.util.logging.Logger;

import kas.concurrente.constante.Contante;

/**
 * En esta clase se simula el estacionamiento en si
 * Posee un conjunto de arreglos de tipo Lugar (o arreglo bidimensional?)
 * Posee un entero de lugaresDisponibles (Se podra hacer por pisos?) (Habra otra
 * manera de hacerlo mejor?)
 * 
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Estacionamiento {

    private Lugar lugares[];
    private int capacidad;
    private int lugaresDisponibles;
    private Random r = new Random(System.currentTimeMillis());
    private static final Logger logger = Logger.getLogger(Estacionamiento.class.getName());

    /**
     * Metodo constructor
     * Modifica el constructor o crea otro segun consideres necesario
     * 
     * @param capacidad La capacidad del estacionamiento
     */
    public Estacionamiento(int capacidad) {
        this.capacidad = capacidad;
        this.lugares = new Lugar[capacidad];
        this.lugaresDisponibles = 0;
        this.inicializaLugares();
    }

    public int getLugaresDisponibles() {
        lugaresDisponibles = 0;
        for (Lugar l : this.lugares) {
            if (l.getDisponible()) {
                lugaresDisponibles++;
            }
        }
        return lugaresDisponibles;
    }

    public void setLugaresDisponibles(int lugaresDisponibles) {
        this.lugaresDisponibles = lugaresDisponibles;
    }

    /**
     * Metodo que nos indica si esta lleno el estacionamiento
     * 
     * @return true si esta lleno, false en otro caso
     */
    public boolean estaLleno() {
        for (Lugar l : this.lugares) {
            if (l.getDisponible()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo que inicaliza los lugares del arreglo
     * Este es un método optativo
     */
    public void inicializaLugares() {
        for (int i = 0; i < this.capacidad; i++) {
            this.lugares[i] = new Lugar(i);
        }
    }

    /**
     * Metodo en el que se simula la entrada de un carro
     * Imprime un texto que dice que el carro a entrado de color AZUL
     * 
     * @param nombre El nombre del carro
     * @throws InterruptedException Si llega a fallar
     */
    public void entraCarro(int nombre) throws InterruptedException {
        int lugar = obtenLugar();
        if (Contante.LOGS) {
            logger.info((char) 27 + "[34m");
            logger.info(String.format("El proceso %d entro al lugar %d \n", Thread.currentThread().getId(), lugar));
        }
        asignaLugar(lugar);
    }

    /**
     * Metodo que asigna el lugar, una vez asignado ESTACIONA su nave
     * 
     * @param lugar El lugar que correspone
     * @throws InterruptedException
     */
    public void asignaLugar(int lugar) throws InterruptedException {
        int i = lugar;
        this.lugares[i].estaciona();
    }

    /**
     * Se obtiene un lugar de forma pseudoAleatoria
     * Aqui necesito que revisen el repaso de estadistica que mande en
     * repaso, quiero que expliquen porque lo pedimos en forma pseudoAleatoria
     * 
     * @return Retorna el indice del lugar
     */
    public int obtenLugar() {
        int lugar = this.r.nextInt(capacidad);
        return (this.lugares[lugar].getDisponible()) ? lugar : obtenLugar();
    }

    /**
     * Retorna el atributo lugares
     * 
     * @return Lugar[]
     */
    public Lugar[] getLugares() {
        return lugares;
    }
}
