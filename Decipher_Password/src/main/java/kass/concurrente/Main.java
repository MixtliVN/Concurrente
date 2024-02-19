package kass.concurrente;

import java.util.logging.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import kass.concurrente.crypto.ThreadCifrado;

public class Main {

    private static Logger logger = Logger.getGlobal();
    private static final int N_THREADS = 8;
    private static List<String> prefixList = new ArrayList<>();

    /**
     * Metodo principal
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        Long inicio = System.nanoTime();
        createListOfPrefix();
        runThreads(N_THREADS);
        Long fin = System.nanoTime();
        Long total = fin - inicio;
        logger.log(Level.INFO, "Tiempo de ejecucion: {0} segundos", nanoSegundoASegundo(total));
        logger.info("Practica 2");
    }

    /**
     * Crea una lista de prefijos de longitud dos
     */
    private static void createListOfPrefix() {
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                prefixList.add("" + (char) (i + 97) + (char) (j + 97));
            }
        }
    }

    /**
     * Determina el tiempo
     * 
     * @param tiempo long
     * @return double
     */
    public static double nanoSegundoASegundo(Long tiempo) {
        return tiempo * 1.0 * Math.pow(10, -9);
    }

    /**
     * Metodo que ejecuta el pool de threads
     * 
     * @param nThreads
     */
    public static void runThreads(int nThreads) {
        ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(nThreads);

        for (int i = 0; i < nThreads; i++) {
            int fromIndex = i * (prefixList.size() / nThreads);
            int toIndex = (i + 1) * (prefixList.size() / nThreads);
            List<String> subList = prefixList.subList(fromIndex, toIndex);
            executor.execute(new ThreadCifrado(String.valueOf(i + 1), subList));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            logger.info("Error en la ejecucion de los hilos");
            Thread.currentThread().interrupt();
        }

        logger.info("FIN DE EJECUCION HILOS");
    }
}