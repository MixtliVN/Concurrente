package kass.concurrente;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import kass.concurrente.constantes.Contante;
import kass.concurrente.modelos.Habitacion;
import kass.concurrente.modelos.Prisionero;
import kass.concurrente.modelos.Vocero;

import static kass.concurrente.constantes.Contante.LOGS;

/**
 * Clase principal, se ejecuta todo la simulacion
 * Como en el cuento.
 * 
 * @author <Equipo>
 * @version 1.0
 */
public class Main implements Runnable {

    Lock lock;
    static Habitacion habitacion = new Habitacion();
    private Prisionero prisionero;
    static boolean sigue = true;

    public Main(Prisionero prisionero) {
        lock = new ReentrantLock();
        // Agregar lo que haga falta para que funcione
        this.prisionero = prisionero;
    }

    /*
     * INSTRUCCIONES:
     * 1.- Ya genere el lock, es un reentrantLock, investiguen que hace
     * 2.- Tenenemos que tener un lugar el donde se albergaran los prisioneros
     * 3.- Tenemos que tener un lugar donde se albergan los Hilos
     * 4.- Se nececita un objeto de tipo Habitacion para que sea visitada
     * 5.- Aqui controlaremos el acceso a la habitacion, aunque por defecto tenia
     * exclusion mutua
     * aqui hay que especificar el como se controlara
     * 6.- Hay que estar ITERANDO constantemente para que todos los prisiones puedan
     * ir ingresando
     */
    @Override
    public void run() {
        while (sigue) {
            lock.lock();
            try {
                sigue = habitacion.entraHabitacion(prisionero);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Logger LOG = Logger.getLogger("paquete.NombreClase"); // EJEMPLO LOGGER

        LOG.info("ID \t| Vocero \t| Ya entro\t| interruptor");
        List<Thread> hilos = new ArrayList<>();

        for (int i = 0; i < Contante.PRISIONEROS; i++) {
            Thread t;
            if (i == 0) {
                t = new Thread(new Main(new Vocero(i, true, false)));
                hilos.add(t);
            } else {
                t = new Thread(new Main(new Prisionero(i, false, false)));
                hilos.add(t);
            }
            hilos.get(i).start();
        }
        for (Thread t : hilos) {
            t.join();
        }

        if (LOGS)
            LOG.info("HOLA SOY UN MENSAJE");
    }
}