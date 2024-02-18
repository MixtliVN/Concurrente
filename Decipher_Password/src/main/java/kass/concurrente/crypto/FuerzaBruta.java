package kass.concurrente.crypto;

import java.util.ArrayList;
import java.util.List;


public class FuerzaBruta {


    // ######|####### = 26^7 = 8,031,810,176
    public static final int N_THREADS = 2;
    public static final int N_CADENAS_POR_HILO = 2000;
    public static final int MIN_LONGITUD = 7;
    public static final int MAX_LONGITUD = 13;

    private static ArrayList<String> contrasennas = new ArrayList<>();

   

    public void generarCombinaciones(String alfabeto, int minLongitud, int maxLongitud, int nCadenasPorHilo) {
        synchronized (contrasennas) {
            for (int longitud = minLongitud; longitud <= maxLongitud; longitud++) {
                while (contrasennas.size() < N_CADENAS_POR_HILO) {
                    generarCombinacionesRecursivo("", alfabeto, longitud, contrasennas);
                }
            }
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

    public String fuerzaBruta(String palabraCifrada) {
        synchronized (contrasennas) {
            while (contrasennas.isEmpty()) {
                try {
                    contrasennas.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            for (String s : contrasennas) {
                try {
                    if (Cifrar.descifraC(palabraCifrada, s)) {
                        return s;
                    }
                } catch (Exception e) {
                    System.out.println("ERROR: Desde clase Cifrar.");
                }
            }

            // Si no se encontró la llave, solicitar nuevas combinaciones
            contrasennas.clear(); // Limpiar las combinaciones anteriores
            contrasennas.notifyAll(); // Notificar al hilo generador que puede generar nuevas combinaciones

            return null;
        }
    }
}
