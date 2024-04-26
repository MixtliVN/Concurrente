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
    private static String master = "master";
    private static String newFeature = "newFeature";

    public static void main(String[] args) {
        // creamos el repositorio
        Repository repository = new Repository();

        // Agregamos archivos al repositorio
        repository.add("file1.txt", "This is the content of file1.");
        repository.add("file2.txt", "This is the content of file2.");

        // hacemos un commit en el repositorio (master)
        long commitId0 = repository.commit("Initial commit");

        // Creamos una nueva rama
        repository.createBranch(App.newFeature);
        String checkoutResult = repository.checkout(App.newFeature, commitId0);
        App.logger.info(checkoutResult);

        // Agregamos un archivo a la nueva rama
        repository.add("file3.txt", "This is the content of file3 onApp.newFeaturebranch.");
        long commitId1 = repository.commit("File3 added toApp.newFeaturebranch");

        // cambiamos de rama
        repository.checkout(App.newFeature, commitId1);
        repository.checkout(App.master, commitId0);
        repository.checkout(App.newFeature, commitId1);

        // hacemos un push
        repository.push(App.master);
        repository.checkout(App.master, commitId1);
        repository.add("archivoPull.txt", "Este es el contenido del archivo que se va a hacer pull");
        long commitId2 = repository.commit("Pull request commit");
        // hacemos un pullrequest
        repository.pullRequest("master", App.newFeature);
        repository.checkout(App.newFeature, commitId2);
        repository.checkout(App.master, commitId2);

    }
}
