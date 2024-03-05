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
 * Esta clase principal, se ejecuta todo la simulacion como en el cuento.
 * 
 * @author Monadics
 * @version 1.0
 */
public class Main implements Runnable {

    protected Lock lock;
    private static Habitacion habitacion = new Habitacion();
    private Prisionero prisionero;
    private static boolean sigue = true;

    public Main(Prisionero prisionero) {
        lock = new ReentrantLock();
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
                seccionCritica(habitacion, prisionero);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            lock.unlock();
        }
    }

    /**
     * Entra a la seccion critica
     * 
     * @param habitacion Habitacion
     * @param prisionero Prisionero
     */
    public static void seccionCritica(Habitacion habitacion, Prisionero prisionero) {
        try {
            sigue = habitacion.entraHabitacion(prisionero);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            sigue = true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Logger LOG = Logger.getLogger("paquete.NombreClase");
        if (LOGS) {
            LOG.info((char) 27 + "[34m");
            LOG.info("ID \t| Vocero \t| Ya entro\t| interruptor");
        }
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

    }
}