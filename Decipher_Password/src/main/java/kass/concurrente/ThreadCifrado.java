package kass.concurrente;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import kass.concurrente.constants.Constante;
import kass.concurrente.crypto.Cifrar;

/**
 * Clase Principal
 */
public class ThreadCifrado implements Runnable {
    public static final int MIN_LONGITUD = 5;
    public static final int MAX_LONGITUD = 5;
    private final int firstCharacterValue;
    private final int lastCharacterValue;
    private String nombre;
    private int contador = 0;
    
    private static final Logger logger = Logger.getLogger(ThreadCifrado.class.getName());

    public ThreadCifrado(String nombre, Character firstCharacter, Character lastCharacter) {
       
        this.nombre = nombre;
        this.firstCharacterValue = (int) firstCharacter -97;
        this.lastCharacterValue = (int) lastCharacter -97;
    }

    static { 
        FileHandler fh;  

        try {  
    
            // This block configure the logger with handler and formatter  
            fh = new FileHandler("./log.log");  
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
    }
    public void run() {

        logger.info("HILO: " + nombre + " INICIADO" + " con cuenta: " + contador + " con valores: " + Constante.ALFABETO.charAt(firstCharacterValue) + " " + Constante.ALFABETO.charAt(firstCharacterValue));

        
        for (int longitud = MIN_LONGITUD; longitud <= MAX_LONGITUD; longitud++) {
            for (int i = firstCharacterValue; i < lastCharacterValue; i++) {
                char letra = Constante.ALFABETO.charAt(i);
                generarCombinacionesRecursivo(""+letra, Constante.ALFABETO, longitud - 1);
            }
            
        }

        logger.info("HILO: " + nombre + " TERMINADO con cuenta: " + contador);
    }


    private void generarCombinacionesRecursivo(String prefijo, String alfabeto, int longitud){
        contador++;

        if (longitud == 0) {
                String cadena = (prefijo.charAt(0) + "") + prefijo.substring(1);
                boolean esCorrecto = false;
                try {
                    esCorrecto = Cifrar.descifraC(Constante.LLAVE, cadena);
                    if (esCorrecto) {
                        logger.info(cadena);
                        logger.info("CADENA ENCONTRADA: " + cadena);
                        //Thread.currentThread().interrupt();
                        
                    }
                    else {

                        //logger.info(nombre + " CADENA NO ENCONTRADA: " + cadena);

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
