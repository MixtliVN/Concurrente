package kass.concurrente;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import kass.concurrente.constants.Constante;


public class Main {

    private static Logger logger = Logger.getGlobal();
    private static final int N_THREADS = 4;
    
    
    /**
     * Metodo principal
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) {
        FileHandler fh;  

        try {  
    
            // This block configure the logger with handler and formatter  
            fh = new FileHandler("./main_log.log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
    
            // the following statement is used to log any messages  
            logger.info("My first log");  
    
        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        Long inicio = System.nanoTime();

        if (N_THREADS == 1) {
            runThreads(Constante.PREFIX_LIST_FOR_1_THREAD);
        } else if (N_THREADS == 2) {
            runThreads(Constante.PREFIX_LIST_FOR_2_THREADS);
        } else if (N_THREADS == 4) {
            runThreads(Constante.PREFIX_LIST_FOR_4_THREADS);
        }
        else if (N_THREADS == 26) {
            runThreads(Constante.PREFIX_LIST_FOR_26_THREADS);
        }

        Long fin = System.nanoTime();
        Long total = fin - inicio;
        logger.info("TIEMPO TOTAL: " + nanoSegundoASegundo(total));
        logger.info("Practica 2");

        //exit(0);


    }

    public static double nanoSegundoASegundo(Long tiempo) {
        return tiempo * 1.0 * Math.pow(10, -9);
    }


    public static void runThreads(List <Character> prefixList) {
        ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(N_THREADS);

        for (int i = 0; i < N_THREADS; i++) {
            Character c = 'z'+1;
            if (i < prefixList.size() -1 ) {
                c = prefixList.get(i+1);
                c =  Character.valueOf((char)((int)c.charValue()));
            }
            executor.execute(new ThreadCifrado(String.valueOf(i), prefixList.get(i), c));

           

        }

        // wait until all threads are finished
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            logger.info("Error en la ejecucion de los hilos");
        }


        logger.info("FIN DE EJECUCION HILOS");

    }


 
}
