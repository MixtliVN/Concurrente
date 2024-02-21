package cc.monadics.Hilos;

public class Producto extends Thread {

    private static Integer[][] matriz = new Integer[Constantes.N_FILAS_A][Constantes.N_COL_B];
    private Integer filaA;
    private Integer colB;
    private Integer valorC;

    /**
     * Constructor
     * 
     * @param filaA
     * @param colB
     */
    public Producto(Integer filaA, Integer colB) {
        this.filaA = filaA;
        this.colB = colB;
        this.valorC = 0;
    }

    /**
     * Determina una entrada del producto de matrices
     */
    public void calcularValorC() {
        for (int i = 0; i < Constantes.N_COL_A; i++) {
            valorC += Matrices.getMatrizA()[filaA][i] * Matrices.getMatrizB()[i][colB];
        }

    }

    /**
     * Carga el valor a la matriz
     */
    public synchronized void cargarValor() {
        matriz[filaA][colB] = valorC;
    }

    /**
     * Retorna la matriz resultante
     * 
     * @return Integer[][]
     */
    public static Integer[][] getMatrizC() {
        return matriz;
    }

    @Override
    public void run() {
        calcularValorC();
        cargarValor();
    }

}
