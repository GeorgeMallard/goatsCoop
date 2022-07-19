import java.util.Scanner;

public class Input {
    
    private static final String INTEGER_FORMAT = "Enter an integer:";
    private static final String INTEGER_BETWEEN = "Enter an integer between %d and %d:";

    /**
     * Read an integer input by the user between two values (inclusive)
     * @param prompt as a String
     * @param min as an int
     * @param max as an int
     * @return user input as an int
     */
    public static int readIntBetween(String prompt, int min, int max) {
        System.out.print(prompt);
        Scanner in = new Scanner(System.in);
        boolean inputError = false;
        int value = min - 1;
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
        in.close();
        return value;
    }







}
