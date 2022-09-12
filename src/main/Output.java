package src.main;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * Contains methods for outputting data to text files
 * @author Chris Litting
 * @version 1.1
 */
public class Output {

    // =========
    // CONSTANTS
    // =========

    private static final String FILE_CREATED = "File created: ";
    private static final String FILE_ALREADY_EXISTS = "File already exists";
    private static final String ERROR_OCCURRED = "An error occurred";
    private static final String DATE_FORMAT_NOW = "yyyyMMddHHmmss";

    // =======
    // METHODS
    // =======
    
    /**
     * Creates a text file in the local directory, filename format: "datetime_type.txt"
     * @param type as a String
     * @return full filename including extension as a String
     */
    public static String createDateTimeFile(String type) {
        String now = now(DATE_FORMAT_NOW);
        String filename = now + "_" + type + ".txt";
        try {
            File outputFile = new File(filename);
            if (outputFile.createNewFile()) {
                System.out.println(FILE_CREATED + outputFile.getName());
            } else {
                System.out.println(FILE_ALREADY_EXISTS);
            }
        } catch (IOException e) {
            System.out.println(ERROR_OCCURRED);
            e.printStackTrace();
        }
        return filename;
    }

    /**
     * Writes a line to a named text file
     * @param filename as a String
     * @param text as a String
     */
    public static void writeToFile(String filename, String text) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.write(text + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println(ERROR_OCCURRED);
            e.printStackTrace();
        }
    }

    // ===============
    // UTILITY METHODS
    // ===============

    /**
	 * Returns current date and time
	 * @return String
	 */
	public static String now(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}

}
