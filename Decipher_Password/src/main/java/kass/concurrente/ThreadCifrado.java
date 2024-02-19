package kass.concurrente;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import kass.concurrente.constants.Constante;
import kass.concurrente.crypto.Cifrar;

/**
 * Clase Principal
 */
public class ThreadCifrado implements Runnable {
    public static final int MIN_LONGITUD = 6;
    public static final int MAX_LONGITUD = 6;
    private final List<String> prefixList;
    private String nombre;
    private int contador = 0;
    private boolean isFirstWord = true;
    private String lastWord = "";
    
    private static final Logger logger = Logger.getLogger(ThreadCifrado.class.getName());

    public ThreadCifrado(String nombre, List<String> prefixList) {
       
        this.nombre = nombre;
        this.prefixList = prefixList;

        logger.log(Level.INFO, "HILO: {0} CREADO", nombre);
        // print prefix list in one line
        System.out.println(prefixList);
    }

    static { 
        FileHandler fh;  

        try {  
    
            // This block configure the logger with handler and formatter  
            fh = new FileHandler("./log.log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
    
        } catch (SecurityException | IOException e) {  
            e.printStackTrace();  
        }   
    }
    
    public void run() {

        logger.log(Level.INFO, "HILO: {0} INICIADO", nombre);

        
        for (int longitud = MIN_LONGITUD; longitud <= MAX_LONGITUD; longitud++) {
            for (int i = 0; i < prefixList.size(); i++) {
                String letra = prefixList.get(i);
                generarCombinacionesRecursivo(""+letra, Constante.ALFABETO, longitud - 2);
            }
            
        }

        logger.log(Level.INFO, "Hilo {0} terminado, La última palabra fue: {1}, el total de combinaciones fue: {2}", new Object[]{nombre, lastWord, contador});
    }


    private void generarCombinacionesRecursivo(String prefijo, String alfabeto, int longitud){

        if (longitud == 0) {

                if (isFirstWord) {
                    logger.log(Level.INFO, "Hilo {0}: La primera palabra es: {1}", new Object[]{nombre, prefijo});
                    isFirstWord = false;
                }


                contador++;

                String cadena = (prefijo.charAt(0) + "") + prefijo.substring(1);

                lastWord = cadena;
                boolean esCorrecto = false;
                try {
                    esCorrecto = Cifrar.descifraC(Constante.LLAVE, cadena);
                    if (esCorrecto) {
                        logger.log(Level.INFO, "Hilo {0}, Encontró la palabra, es:[                    {1}                               ] ", new Object[]{nombre, cadena});
                    }
                    else {
                        /* 
                        if (contador % 1000000 == 0){
                            logger.log(Level.INFO, "Hilo {0}: Llevamos {1} combinaciones, la palabra actual es {2}", new Object[]{nombre, contador, cadena});
                        }
                        */
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            return;
        }

        for (int i = 0; i < alfabeto.length(); i++) {
            char letra = alfabeto.charAt(i);
            generarCombinacionesRecursivo(prefijo + letra, alfabeto, longitud - 1);
        }


    }
 
}
