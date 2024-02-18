package kass.concurrente;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import kass.concurrente.constants.Constante;

import kass.concurrente.crypto.FuerzaBruta;

/**
 * Clase Principal
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Long inicio = System.nanoTime();
        // System.out.println(Cifrar.descifraC(Constante.LLAVE, Constante.CONTRASENNA));
        // System.out.println("Encontrada: "+ Cifrar.fuerzaBruta(Constante.LLAVE,4));
        FuerzaBruta fb = new FuerzaBruta();

        ExecutorService executor = Executors.newFixedThreadPool(FuerzaBruta.N_THREADS);

        for (int i = 0; i < FuerzaBruta.N_THREADS; i++) {
            executor.execute(() -> fb.generarCombinaciones(Constante.ALFABETO, FuerzaBruta.MIN_LONGITUD,
                    FuerzaBruta.MAX_LONGITUD, FuerzaBruta.N_CADENAS_POR_HILO));
        }

        executor.shutdown();
 
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String llaveEncontrada = fb.fuerzaBruta(Constante.LLAVE);
        if (llaveEncontrada != null) {
            System.out.println("Llave encontrada: " + llaveEncontrada);
        } else {
            System.out.println("No se encontró la llave.");
        }
        Long fin = System.nanoTime();
        Long total = fin - inicio;
        System.out.println("TIEMPO TOTAL: " + nanoSegundoASegundo(total));
        System.out.println("Practica 2");
    }

    public static double nanoSegundoASegundo(Long tiempo) {
        return tiempo * 1.0 * Math.pow(10, -9);
    }
}
