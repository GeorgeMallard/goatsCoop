import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Scanner;

import org.junit.Test;

/**
 * Input class
 * Contains methods for taking different kinds of input from the user
 * @author Chris Litting
 * @version 1.0
 */
public class Input {
    
    // =========
    // CONSTANTS
    // =========

    private static final String INTEGER_FORMAT = "Enter an integer: ";
    private static final String INTEGER_BETWEEN = "Enter an integer between %d and %d: ";
    private static final String YES = "y";
    private static final String NO = "n";
    private static final String YES_OR_NO = "Please enter $s or $s: ";

    // =======
    // METHODS
    // =======

    /**
     * Read an integer input by the user between two values (inclusive)
     * @param prompt as a String
     * @param min as an int
     * @param max as an int
     * @return int
     */
    public static int readIntBetween(String prompt, int min, int max) {
        if (max < min) {
            throw new IllegalArgumentException("Max value is less than min value");
        }
        System.out.print(prompt.equals("") ? ">" : prompt);
        Scanner in = new Scanner(System.in);
        boolean inputError = false;
        Integer value;
        do {
            while (!in.hasNextInt()) {
                System.out.print(INTEGER_FORMAT);
                in.nextLine();
            }
            value = in.nextInt();
            in.nextLine();
            if (value < min || value > max) {
                inputError = true;
                System.out.print(
                    String.format(INTEGER_BETWEEN, min, max)
                );
            } else {
                inputError = false;
            }
        } while (inputError);
        return value;
    }

    @Test
    public void minGreaterThanMaxShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> readIntBetween("", 3, 2));
    }

   /**
    * Read a boolean input by the user in the form y/n
    * @param prompt as a String
    * @return boolean
    */
    public static boolean readBoolean(String prompt) {
        System.out.print(prompt.equals("") ? ">" : prompt);
        Scanner in = new Scanner(System.in);
        boolean bool = false;
        boolean inputError = false;
        String input = "";
        do {
            input = in.nextLine();
            if (input.toLowerCase().equals(YES)) {
                bool = true;
                inputError = false;
            } else if (input.toLowerCase().equals(NO)) {
                bool = false;
                inputError = false;
            } else {
                System.out.print(
                    String.format(YES_OR_NO, YES, NO)
                );
                inputError = true;
            }
        } while (inputError);
        return bool;
    }


}
