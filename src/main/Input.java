package src.main;

import java.util.Scanner;

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
        assert (max >= min) : "Cannot read int where min is greater than max. Min: " + min + ". Max: " + max;
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

    public static void parseArgs(String[] strArgs) {
        int counter = 0;
        int[] args = strArrayToIntArray(strArgs);
        
        //GROUP DEPTH
        Settings.setGroupDepth(args[counter]);
        counter++;

        //ENHANCED MUTATION
        if (args[counter] == 1) {
            Settings.setEnhancedMutation(true);
        } else {
            Settings.setEnhancedMutation(false);
        }
        counter++;

        //MUTABILITIES
        for (int i = 0; i <= Settings.getGroupDepth(); i++) {
            Settings.setInitialMutability(i, args[counter]);
            counter++;
        }

        //MUTABLE MUTABILITIES
        for (int i = 0; i <= Settings.getGroupDepth(); i++) {
            if (args[counter] == 1) {
                Settings.setMutableMutability(i, true);
            } else {
                Settings.setMutableMutability(i, false);
            }
            counter++;
        }

        //GROUP SIZES AND CAPACITIES (size first then capacity)
        for (int i = 0; i < Settings.getGroupDepth(); i++) {
            if (args[counter] < Settings.getGroupSize(i + 1)) {
                Settings.setGroupCapacity(i + 1, args[counter + 1]);
                Settings.setGroupSize(i + 1, args[counter]);
            } else {
                Settings.setGroupSize(i + 1, args[counter]);
                Settings.setGroupCapacity(i + 1, args[counter + 1]);
            }
            counter += 2;
        }     

        //AGENT WEIGHTINGS
        for (int i = 0; i < Settings.getGroupDepth(); i++) {
            Settings.setAgentInitialWeighting(i, args[counter]);
            counter++;
        }

        //SIM ITERATIONS
        Settings.setIterations(args[counter]);
        counter++;

        //SIM ROUNDS
        Settings.setRounds(args[counter]);
    }

    public static int[] strArrayToIntArray(String[] strArr) {
        int[] intArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            intArr[i] = tryParseInt(strArr[i]);
        }
        return intArr;
    }

    public static int tryParseInt(String str) {
        int n = -1;
        try {
            n = Integer.parseInt(str);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        return n;
    }


}
