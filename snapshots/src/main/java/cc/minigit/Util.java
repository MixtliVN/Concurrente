import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class

class Util {

    private String file;

    public void read(String filename) {
	try {
	    file = "";
	    File myObj = new File(filename);
	    Scanner myReader = new Scanner(myObj);
	    while (myReader.hasNextLine()) {
		file = file + "\n" + myReader.nextLine();
	    }
	    myReader.close();
	    System.out.println(file);
	} catch (FileNotFoundException e) {
	    System.out.println("An error occurred.");
	    e.printStackTrace();
	}
    }

    public void write(String filename, String data) {
	try {
	    File myObj = new File(filename);
	    FileWriter myWriter = new FileWriter(filename);
	    myWriter.write(data);
	    myWriter.close();
	} catch (IOException e) {
	    System.out.println("An error occurred.");
	    e.printStackTrace();
	}
    }

    public void mkdir(String dirname){
	File directory = new File(dirname);
	directory.mkdir();
    }
    /*
    public static void main(String[] args) {
	Util u = new Util();
	String f = "A.txt";
	String d = "HOLA";
	String dir = "A";
	u.write(f, d);
	u.read(f);
	u.mkdir(dir);
    }
    */
}
