package kas.concurrente.modelos;

import java.util.Random;

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

    private Lugar lugares[][];
    private int capacidad;
    private int niveles;
    private int lugaresDisponibles;
    private Random r = new Random(System.currentTimeMillis());

    /**
     * Metodo constructor
     * Modifica el constructor o crea otro segun consideres necesario
     * 
     * @param capacidad La capacidad del estacionamiento
     */
    public Estacionamiento(int capacidad, int h) {
        this.niveles = h;
        this.capacidad = capacidad;
        this.lugares = new Lugar[h][capacidad / h];
        this.inicializaLugares();
    }

    public int getLugaresDisponibles() {
        lugaresDisponibles = 0;
        for (int i = 0; i < this.niveles; i++) {
            for (Lugar l : this.lugares[i]) {
                if (l.getDisponible()) {
                    lugaresDisponibles++;
                }
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
        for (int i = 0; i < this.niveles; i++) {
            for (Lugar l : this.lugares[i]) {
                if (l.getDisponible()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Metodo que inicaliza los lugares del arreglo
     * Este es un método optativo
     */
    public void inicializaLugares() {
        int contador = 0;
        for (int i = 0; i < this.niveles; i++) {
            for (int j = 0; j< this.capacidad/this.niveles; j++) {
                this.lugares[i][j] = new Lugar(contador++);
            }
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
       // System.out.format("Entro el carro %d al estacionamiento\n", nombre);
    }

    /**
     * Metodo que asigna el lugar, una vez asignado ESTACIONA su nave
     * 
     * @param lugar El lugar que correspone
     * @throws InterruptedException
     */
    public void asignaLugar(int lugar) throws InterruptedException {
        int porPiso = this.capacidad / this.niveles;
        int i = lugar/porPiso;
        int j = lugar % porPiso;
        this.lugares[i][j].estaciona();
    }

    /**
     * Se obtiene un lugar de forma pseudoAleatoria
     * Aqui necesito que revisen el repaso de estadistica que mande en
     * repaso, quiero que expliquen porque lo pedimos en forma pseudoAleatoria
     * 
     * @return Retorna el indice del lugar
     */
    public int obtenLugar() {
        return this.r.nextInt(capacidad);
    }
}