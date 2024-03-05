package kass.concurrente.modelos;

import static kass.concurrente.constantes.Contante.LOGS;

import java.util.Random;
import java.util.logging.Logger;

import kass.concurrente.constantes.Contante;

/**
 * Clase que fungira como habitacion
 * Se sabe que existe un interruptor que nos dice
 * si el foco esta prendido o no
 * Se desconoce el estado inicial del foco (Usar un random, para que sea
 * pseudoaleatorio el estado inicial)
 * 
 * @author Monadics
 * @version 1.0
 */
public class Habitacion {
    private Boolean prendido;
    private static final Logger logger = Logger.getLogger(Habitacion.class.getName());

    /**
     * Metodo Constructor
     * Aqui se define el como estara el foco inicialmente
     */
    public Habitacion() {
        this.prendido = new Random().nextInt(101) % 2 == 0;
    }

    /**
     * Metodo que permite al prisionero entrar a la habitacion
     * Recordemos que solo uno pasa a la vez, esta es la SECCION CRITICA
     * En este caso se controla desde fuera
     * Es similar al algoritmo que progonan y similar al de su tarea
     * El prisionero espera una cantidad finita de tiempo
     * 
     * @param prisionero El prisionero que viene entrando
     * @return false si ya pasaron todos, true en otro caso
     * @throws InterruptedException Si falla algun hilo
     */
    public Boolean entraHabitacion(Prisionero prisionero) throws InterruptedException {
        // Vocero
        if (Boolean.TRUE.equals(prisionero.esVocero)) {
            if (((Vocero) prisionero).getContador().equals((2 * Contante.PRISIONEROS) - 2)) {
                logger.info("\t \033[31mTODOS HEMOS ENTRADO\033[0m");
                return false;
            }
            if (Boolean.TRUE.equals(this.prendido)) {
                ((Vocero) prisionero).setContador(((Vocero) prisionero).getContador() + 1);
                this.prendido = false;
                prisionero.marcado = true;
                if (Boolean.TRUE.equals(LOGS)) {
                    logger.info((char) 27 + "[34m");
                    logger.info("Contador de vocero : " + ((Vocero) prisionero).getContador());
                }
            }
        } else {
            if (Boolean.FALSE.equals(this.prendido) && (prisionero.contadorInterno < 2)) {
                this.prendido = true;
                prisionero.marcado = true;
                prisionero.contadorInterno = prisionero.contadorInterno + 1;
            }
        }
        if (Boolean.TRUE.equals(LOGS)) {
            String cadena = String.format(" %d \t\t %b \t\t %b \t\t %b %n", prisionero.id,
                    prisionero.esVocero, prisionero.marcado, this.prendido);
            logger.info(cadena);
        }
        return true;
    }

    public Boolean getPrendido() {
        return prendido;
    }

    public void setPrendido(Boolean prendido) {
        this.prendido = prendido;
    }

}
