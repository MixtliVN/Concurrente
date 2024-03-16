package kas.concurrente;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import kas.concurrente.constante.Contante;

import kas.concurrente.modelos.Estacionamiento;

/**
 * Clase principal, la usaran para SUS pruebas
 * Pueden modigicar los valores estaticos para ver como funciona
 * NO USEN VALORES EXTREMEDAMENTE ALTOS, puede alentar mucho su compu
 * AQUI EJECUTAN LA SIMULACION
 * @author Kassandra Mirael
 * @version 1.0
 */
public class Main implements Runnable{
    private Estacionamiento estacionamiento;
    private Semaphore semaforo;
    private List<Thread> carros = new ArrayList<>();

    /**
     * Metodo constructor
     * Se inicializa el Semaforo Modificado con _______
     * Se inicaliza el Estacionamiento con _______
     */
    public Main(){
        this.semaforo = new Semaphore(Contante.NUM_CARROS-2);
        this.estacionamiento = new Estacionamiento(Contante.NUM_CARROS, Contante.NIVELES);
    }

    /**
     * Una documentacion del main xD, esta bien 
     * Paso 0: Lee estas instrucciones
     * Paso 1: Crea el Objeto de tipo main
     * Paso 2: Crea Una estructura de datos que contenga a nuestros hilos
     * Paso 3: Genera con un ciclo, el cual inialice un numero igual de NUM_CARROS
     * Paso 4: No olvides agregarlos a la estructura e inicializarlos
     * Paso 5: Finalmente has un Join a tus hilos
     * @param args Los Argumentos
     * @throws InterruptedException Por si explota su compu al ponerle medio millon de hilos xD
     */
    public static void main(String[] args) throws InterruptedException{
        Main main = new Main();
        main.crearHilos();
        main.inicializarHilos();
        main.joinHilos();
    }

    /**
     * Aqui esta su primer seccion crítica
     * Paso 1: Keep calm and ...
     * Paso 2: Beware with the concurrent code
     * Paso 3: Try to remember some basics of Java and POO
     * Paso 4: Obten el ID de tu hilo
     * Paso 5: TU CARRO (HILO) ENTRARA AL ESTACIONAMIENTO (Los Hilos simulan ser carros, 
     * no es necesario que generes clase Carro (puedes hacerlo si quieres))
     */
    @Override
    public void run(){
        try {
            semaforo.acquire(); // Adquiere el semáforo para entrar al estacionamiento
            int lugar = estacionamiento.obtenLugar(); // Obtén un lugar pseudo-aleatorio
            estacionamiento.entraCarro((int) Thread.currentThread().getId()); // Simula la entrada del carro
            estacionamiento.asignaLugar(lugar); // Asigna el lugar al carro
            semaforo.release(); // Libera el semáforo después de salir del estacionamiento
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * Crea los hilos correspondientes a los carros y los agrega a la lista
     */
    public void crearHilos() {
        for (int i = 0; i < Contante.NUM_CARROS; i++) {
            Thread hilo = new Thread(this);
            carros.add(hilo);
        }
    }

    /**
     * Inicializa los hilos y los hace empezar a correr
     */
    public void inicializarHilos() {
        for (Thread hilo : carros) {
            hilo.start();
        }
    }

    /**
     * Espera a que todos los hilos terminen su ejecución
     * @throws InterruptedException Si ocurre algún error al hacer join
     */
    public void joinHilos() throws InterruptedException {
        for (Thread hilo : carros) {
            hilo.join();
        }
    }
}
