package src.main;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

/**
 * Contains methods for outputting data to text files
 * @author Chris Litting
 * @version 1.1
 */
public class Output {
    
    /**
     * Creates a text file in the local directory
     * @param filename as a String
     */
    public static void createFile(String filename) {
        try {
            File outputFile = new File(filename + ".txt");
            if (outputFile.createNewFile()) {
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Writes a line to a named text file
     * @param filename as a String
     * @param text as a String
     */
    public static void writeToFile(String filename, String text) {
        try {
            FileWriter writer = new FileWriter(filename + ".txt", true);
            writer.write(text + "\n");
            writer.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}