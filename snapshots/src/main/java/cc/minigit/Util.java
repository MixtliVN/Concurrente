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
	public String read(String filename) {
		StringBuilder fileContent = new StringBuilder();
		try (Scanner myReader = new Scanner(new File(filename))) {
			while (myReader.hasNextLine()) {
				if (fileContent.length() > 0) {
					fileContent.append("\n");
				}
				fileContent.append(myReader.nextLine());
			}
		} catch (FileNotFoundException e) {
			logger.warning("Error en Lectura de archivo [Archivo no encontrado]");
			return null; // Or handle more gracefully
		}
		return fileContent.toString();
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
			Util.logger.warning("Error en Lectura de archivo");
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
		if (!directory.mkdir()) {
			logger.warning("Error al crear el directorio: ");
		}
	}

}