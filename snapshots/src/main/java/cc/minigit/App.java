package cc.minigit;

import java.util.logging.Logger;

/**
 * Ejemplo de funcionamiento del sistema
 * 
 * @author Arroyo Erick
 * @author Alex Terrazas
 * @author Arturo Mixtli
 * @version 1.0
 */
public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        try {
            Repository repo = new Repository();

            // cargar el repositorio con un commit inicial
            repo.add("file1.txt", "¡Hola, mundo!");
            repo.add("file2.txt", "Este es un archivo de prueba.");
            long commitId1 = repo.commit("Commit inicial");

            // creamos la rama dev y la modificamos
            repo.createBranch("dev");
            repo.checkout("dev", commitId1);
            repo.add("file2.txt", "Este es un archivo de prueba actualizado.");
            // Para mas escenarios
            // long commitId2 = repo.commit("Actualizar file2 en dev");

            // Hacer más cambios en 'master'.
            repo.checkout("master", commitId1);
            repo.add("file3.txt", "Este es un nuevo archivo en master.");
            long commitId3 = repo.commit("Agregar file3 en master");

            // hacemos pullrequest de dev a master
            if (repo.pullRequest("dev", "master")) {
                logger.info("El pull request se ha fusionado exitosamente.");
            } else {
                logger.info("El pull request no pudo fusionarse.");
            }

            // empujamos los cambios a la rama dev
            repo.push("dev");

            // Hacer checkout en 'dev' para verificar los cambios.
            repo.checkout("dev", commitId3);
            logger.info("Se ha hecho checkout a la rama dev exitosamente.");

        } catch (Exception e) {
            logger.info("Error durante interaccion del repositorio: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
