package cc.minigit;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private Repository repo;

    @Before
    public void setUp() {
        repo = new Repository();
        // Agrega un archivo inicial y realiza un commit en la rama master
        repo.add("reame.txt", "readme");
        repo.commit("Initial Commit");
    }

    /**
     * Verifica que se pueda agregar un archivo al repositorio
     */
    @Test
    public void testAddFile() {
        String fileName = "holamundo.txt";
        String fileContent = "Hello world";
        repo.add(fileName, fileContent);

        Map<String, FileVersion> stagingArea = repo.getStagingArea();
        assertTrue("El area Staging debe contener el archivo anterior", stagingArea.containsKey(fileName));
        assertEquals("El contendio del archivo debe ser analogo al anterior", fileContent,
                stagingArea.get(fileName).getContent());
    }

    /**
     * Verifica que se pueda hacer un commit
     */
    @Test
    public void testCommit() {
        String fileName = "test.txt";
        String content = "Esto apenas jala";
        repo.add(fileName, content);
        long commitId = repo.commit("Initial commit");

        assertTrue("El ID del commit debe ser mayor o igual a 0", commitId >= 0);
        assertTrue("El area Staging debe estar vacia después del commit", repo.getStagingArea().isEmpty());
        Commit lastCommit = repo.getCurrentBranch().get(repo.getCurrentBranch().size() - 1);
        assertEquals("El mensaje de commit debe hacer match", "Initial commit", lastCommit.getMessage());
        assertTrue("Los archivos en el ultimo commit deben incluir el archivo agregado",
                lastCommit.getFiles().containsKey(fileName));
        assertEquals("El contenido del archivo del commit debe coincidir", content,
                lastCommit.getFiles().get(fileName).getContent());
    }

    @Test
    public void testCheckout() {
        repo.createBranch("dev");
        repo.add("base.txt", "Saludos desde la rama dev");
        repo.commit("Añadiendo base en dev");

        // Commit en dev
        List<Commit> commitsInDev = repo.getBranch("dev");
        long commitIdDev = commitsInDev.get(commitsInDev.size() - 1).getId();  

        // Commit en master
        List<Commit> commitsInMaster = repo.getBranch("master");
        long commitIdMaster = commitsInMaster.get(0).getId();  // Debería ser el commit inicial

        // Checkout al commit inicial en master
        repo.checkout("master", commitIdMaster);
        assertEquals("Debería estar en la rama master", "master", repo.getCurrentBranchName());

        // Checkout al último commit en dev
        repo.checkout("dev", commitIdDev);
        assertEquals("Deberia cambiar a la rama dev", "dev", repo.getCurrentBranchName());

        Commit checkedOutCommit = repo.getBranch("dev").stream()
            .filter(commit -> commit.getId() == commitIdDev)
            .findFirst()
            .orElse(null);
        assertNotNull("Debera encontrar el commit en la rama dev", checkedOutCommit);
        Map<String, FileVersion> baseFile = checkedOutCommit.getFiles();
        for (String bf : baseFile.keySet()) {
           System.out.println(bf);
        }
        // Revisar
        //assertEquals("El commit debería tener el archivo correcto", "Saludos desde la rama dev", checkedOutCommit.getFiles().get("base.txt").getContent());
        assertEquals("El commit debería tener el archivo correcto", "readme", checkedOutCommit.getFiles().get("reame.txt").getContent());
    }

}
