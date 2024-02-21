package cc.monadics.Secuencial;

public class MatricesSecuencial {

    /**
     * Multiplicacion secuencial de matrices
     * 
     * @param matrizA
     * @param matrizB
     * @return
     */
    public static Integer[][] multiplicarMatrices(Integer[][] matrizA, Integer[][] matrizB) {
        int filasA = matrizA.length;
        int columnasA = matrizA[0].length;
        int columnasB = matrizB[0].length;

        Integer[][] resultado = new Integer[filasA][columnasB];
        for (int i = 0; i < filasA; i++) {
            for (int j = 0; j < columnasB; j++) {
                resultado[i][j] = 0;
            }
        }

        for (int i = 0; i < filasA; i++) {
            for (int j = 0; j < columnasB; j++) {
                for (int k = 0; k < columnasA; k++) {
                    resultado[i][j] += matrizA[i][k] * matrizB[k][j];
                }
            }
        }

        return resultado;
    }

}
