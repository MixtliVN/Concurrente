package cc.monadics;

import java.util.logging.Logger;

import cc.monadics.Hilos.Constantes;
import cc.monadics.Hilos.Matrices;
import cc.monadics.Hilos.Producto;

/**
 * @author Erick, Mixtli y Alex
 * @version 1.0
 *
 */
public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    /**
     * Ejecuta la generacion de matrices y calcula el producto
     */
    public static void run() {
        if (!Constantes.N_COL_A.equals(Constantes.N_FILAS_B)) {
            logger.info("\t ERROR: El producto de matrices no está definido para estas dimensiones");
        } else {
            cargarMatrices();
            calcularProducto();
            mostrarMatricesABC();
        }
    }

    /**
     * Genera los hilos necesarios para determinar el producto y lo calcula
     */
    public static void calcularProducto() {
        Thread[] hilosProducto = new Producto[Constantes.N_FILAS_A * Constantes.N_COL_B];
        int contFila = 0;
        int contCol = 0;
        while (contFila < Constantes.N_FILAS_A) {
            for (int i = 0; i < Constantes.N_COL_B; i++) {
                hilosProducto[contCol] = new Producto(contFila, i);
                contCol++;
            }
            contFila++;
        }
        for (Thread t : hilosProducto) {
            t.start();
        }
        for (Thread t : hilosProducto) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Genera los hilos adecuados a las dimensiones de ambas matrices y las llena
     */
    public static void cargarMatrices() {
        Thread[] thread = new Thread[Constantes.N_COL_A + Constantes.N_COL_B];
        for (int i = 0; i < thread.length; i++) {
            if (i < Constantes.N_COL_A) {
                thread[i] = new Matrices(i, i, true);
                thread[i].start();
            } else {
                thread[i] = new Matrices(i, i - Constantes.N_COL_A, false);
                thread[i].start();
            }
        }

        for (Thread t : thread)
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
    }

    /**
     * Muestra el estado de las matrices
     */
    public static void mostrarMatricesABC() {
        logger.info("\tMatriz A");
        Matrices.printMatriz(Matrices.getMatrizA());
        logger.info("\tMatriz B");
        Matrices.printMatriz(Matrices.getMatrizB());
        logger.info("\tMatriz C");
        Matrices.printMatriz(Producto.getMatrizC());

    }

    public static void main(String[] args) {
        run();
    }
}
