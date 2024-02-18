package kass.concurrente.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Clase de Constantes que se utilizaran durante la practica
 * @author Kassandra Mirael
 * @version 1.0
 */
public final class Constante {

    private Constante() {}

    public static final String CONTRASENNA = "Hola"; //Modificar por su contrasenna cuando la encuentren, es la unica con mayuscula
    public static final String LLAVE = "-8682-120-36-16-607532-86-58127118-58-778743";
    //"-87-4-5711-89-255970888-80998286839";
    public static final Integer HILOS = 4;
    public static final String ALFABETO = "abcdefghijklmnopqrstuvwxyz";

    public static final List <Character> PREFIX_LIST_FOR_1_THREAD = new ArrayList<>(Arrays.asList('a'));


    public static final List <Character> PREFIX_LIST_FOR_2_THREADS = new ArrayList<>(Arrays.asList('a', 'n'));

    public static final List <Character> PREFIX_LIST_FOR_4_THREADS = new ArrayList<>(Arrays.asList('a', 'g', 'n', 't'));

    public static final List <Character> PREFIX_LIST_FOR_26_THREADS = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

}
