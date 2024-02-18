package kass.concurrente.crypto;

import java.util.ArrayList;
import java.util.List;

import kass.concurrente.constants.Constante;

public class Combinaciones {

    private static final int RONDAS = 10000;
    private static final Object lock = new Object();
    private static List<String> cadenas = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        Combinaciones c = new Combinaciones();

        Thread hiloGenerador = new Thread(() -> {
            if(Combinaciones.cadenas.size()<Combinaciones.RONDAS){
            synchronized (lock) {
                // Realiza alguna tarea
                c.generarCombinaciones(Constante.ALFABETO, 4);

                // Despierta al otro hilo
                lock.notify();
            }
        }
        });

        Thread hiloEsperador = new Thread(() -> {
            synchronized (lock) {
                try {
                    // Espera hasta que sea notificado por el otro hilo
                    lock.wait();

                    // Realiza alguna tarea después de ser notificado
                    c.fuerzaBruta(Constante.LLAVE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Inicia los hilos
        hiloGenerador.start();
        hiloEsperador.start();

        // Espera a que ambos hilos terminen
        hiloGenerador.join();
        hiloEsperador.join();
    }

    public void generarCombinaciones(String alfabeto, int longitud) {
        List<String> resultado = new ArrayList<>();
        generarCombinacionesRecursivo("", alfabeto, longitud, resultado);

        synchronized (lock) {
            cadenas.addAll(resultado);
        }
    }

    private void generarCombinacionesRecursivo(String prefijo, String alfabeto, int longitud, List<String> resultado) {
        if (longitud == 0) {
            
            resultado.add((prefijo.charAt(0) + "").toUpperCase() + prefijo.substring(1));
            return;
        }

        for (int i = 0; i < alfabeto.length(); i++) {
            char letra = alfabeto.charAt(i);
            generarCombinacionesRecursivo(prefijo + letra, alfabeto, longitud - 1, resultado);
        }
    }

    public void fuerzaBruta(String palabraCifrada) {
        synchronized (lock) {
            for (String s : cadenas) {
                System.out.println("Se revisa la cadena: "+ s);
                try {
                    if (Cifrar.descifraC(palabraCifrada, s)) {
                        cadenas.clear();
                        System.out.println("Se encontró la palabra");
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        cadenas.clear();
    }
}