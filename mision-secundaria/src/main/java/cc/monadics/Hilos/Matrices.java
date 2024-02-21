package cc.monadics.Hilos;

import java.util.Random;
import java.util.logging.Logger;

public class Matrices extends Thread {
    private static final Logger logger = Logger.getLogger(Matrices.class.getName());
    private static Integer[][] matrizA = new Integer[Constantes.N_FILAS_A][Constantes.N_COL_A];
    private static Integer[][] matrizB = new Integer[Constantes.N_FILAS_B][Constantes.N_COL_B];
    private Integer colA;
    private Integer colB;
    private Random rA;
    private Random rB;
    private boolean trabajaConA;

    /**
     * Constructor
     * 
     * @param colA Integer
     * @param colB Integer
     * @param a    boolean
     */
    public Matrices(Integer colA, Integer colB, boolean a) {
        this.colA = colA;
        this.colB = colB;
        this.trabajaConA = a;
        initRandom();
    }

    /**
     * Retorna la matriz A
     * 
     * @return Integer[][]
     */
    public static Integer[][] getMatrizA() {
        return matrizA;
    }

    /**
     * Retorna la matriz B
     * 
     * @return Integer[][]
     */
    public static Integer[][] getMatrizB() {
        return matrizB;
    }

    /**
     * Inicializa los atributos random
     */
    private void initRandom() {
        long aob = (long) (trabajaConA ? 0.5 : 1.5);
        if (this.trabajaConA) {
            long semillaA = System.currentTimeMillis() + this.colA.hashCode() + aob;
            this.rA = new Random(semillaA);
        } else {
            long semillaB = System.currentTimeMillis() + this.colB.hashCode() + aob;
            this.rB = new Random(semillaB);
        }
    }

    @Override
    public void run() {
        if (this.trabajaConA) {
            for (int i = 0; i < Constantes.N_FILAS_A; i++) {
                cargaA(i, colA, this.rA.nextInt(Constantes.MAX_RANDOM_VALUE));
            }
        } else {
            for (int i = 0; i < Constantes.N_FILAS_B; i++) {
                cargaB(i, colB, this.rB.nextInt(Constantes.MAX_RANDOM_VALUE));
            }
        }
    }

    /**
     * Asigna el valor a una entrada de la matriz A
     * 
     * @param fila  Integer
     * @param col   Integer
     * @param valor Integer
     */
    public static void cargaA(int fila, int col, int valor) {
        matrizA[fila][col] = valor;
    }

    /**
     * Asigna el valor a una entrada de la matriz B
     * 
     * @param fila  Integer
     * @param col   Integer
     * @param valor Integer
     */
    public static void cargaB(int fila, int col, int valor) {
        matrizB[fila][col] = valor;
    }

    /**
     * Imprime el estado de la matriz
     * 
     * @param matriz Integer[][]
     */
    public static void printMatriz(Integer[][] matriz) {
        for (int i = 0; i < matriz.length; i++) {
            String fila = "";
            for (int j = 0; j < matriz[0].length; j++) {
                fila += String.format("\t %d", matriz[i][j]);
            }
            logger.info(fila);

        }
    }
}
