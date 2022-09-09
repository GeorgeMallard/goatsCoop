package src.main;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

/**
 * Output class
 * Contains methods for outputting data
 * @author Chris Litting
 * @version 1.0
 */
public class Output {
    
    /**
     * Creates text file in local directory
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
