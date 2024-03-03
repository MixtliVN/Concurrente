package kass.concurrente.modelos;

import java.util.Random;

import kass.concurrente.constantes.Contante;

/**
 * Clase que fungira como habitacion
 * Se sabe que existe un interruptor que nos dice
 * si el foco esta prendido o no
 * Se desconoce el estado inicial del foco (Usar un random, para que sea
 * pseudoaleatorio el estado inicial)
 * 
 * @author <Equipo>
 * @version 1.0
 */
public class Habitacion {
    private Boolean prendido;

    /**
     * Metodo Constructor
     * Aqui se define el como estara el foco inicialmente
     */
    public Habitacion() {
        this.prendido = true;// new Random().nextInt(101) % 2 == 0;
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
        if (prisionero.esVocero) {
            if (this.prendido) {// Esta prendido
                ((Vocero) prisionero).setContador(((Vocero) prisionero).getContador() + 1);
                this.prendido = false;
		prisionero.marcado = true;
                System.out.println("Contador de vocero : " + ((Vocero) prisionero).getContador());
                if (((Vocero) prisionero).getContador().equals((2 * Contante.PRISIONEROS) - 2)) {
                    System.out.println("\t TODOS HEMOS PASADO");
                    return false;
                }
            }
        } else {
            if (Boolean.FALSE.equals(this.prendido) && (prisionero.contadorInterno < 2)) {
                this.prendido = true;
                prisionero.marcado = true;
		prisionero.contadorInterno = prisionero.contadorInterno + 1;
            }
        }
	System.out.format("  %d \t\t %b \t\t %b \t\t %b \n", prisionero.id, prisionero.esVocero, prisionero.marcado, this.prendido);
        return true;
    }

    public Boolean getPrendido() {
        return prendido;
    }

    public void setPrendido(Boolean prendido) {
        this.prendido = prendido;
    }

}
