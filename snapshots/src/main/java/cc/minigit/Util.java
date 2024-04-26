package cc.minigit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;
import java.io.FileWriter;

/**
 * Clase que representa una utilidad para manejar archivos
 * 
 * @author Arroyo Erick
 * @author Alex Terrazas
 * @author Arturo Mixtli
 * @version 1.0
 */
class Util {

	public static final String SEPARADOR = System.getProperty("file.separator");
	private static final Logger logger = Logger.getLogger(Util.class.getName());

	/**
	 * Método que regresa el contenido del archivo
	 * 
	 * @param filename nombre del archivo
	 */
	public void read(String filename) {
		try {
			String file = "";
			File myObj = new File(filename);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				file = file + "\n" + myReader.nextLine();
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			Util.logger.warning("An error occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Método que escribe en un archivo
	 * 
	 * @param filename nombre del archivo
	 * @param data     datos a escribir
	 */
	public static void write(String filename, String data) {
		try (FileWriter myWriter = new FileWriter(filename)) {
			myWriter.write(data);
		} catch (Exception e) {
			Util.logger.warning("An error occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Método que crea un directorio
	 * 
	 * @param dirname nombre del directorio
	 */
	public static void mkdir(String dirname) {
		File directory = new File(dirname);
		directory.mkdir();
	}

}