package src.main;

import java.util.Scanner;

/**
 * Contains methods for reading input from the user
 * @author Chris Litting
 * @version 1.1
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

    // ==============
    // STATIC METHODS
    // ==============

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

    /**
     * Takes args from program run and updates settings accordingly
     * @param strArgs
     */
    public static void parseArgs(String[] strArgs) {
        int counter = 0;
        int[] args = strArrayToIntArray(strArgs);
        
        //GROUP DEPTH
        if (args[counter] < 1 || args[counter] > Settings.getMaxGroupDepth()) {
            throw new IllegalArgumentException("Group depth must be between 1 and max group depth.");
        }
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
            if (args[counter] < 0 || args[counter] > Settings.getMaxMutability()) {
                throw new IllegalArgumentException("Mutability must be between 0 and max mutability.");
            }
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
            if (args[counter] < args[counter + 1]) {
                throw new IllegalArgumentException("Group size cannot be less than group capacity.");
            }
            if (args[counter] < 2 || args[counter] > Settings.getMaxGroupSize()) {
                throw new IllegalArgumentException("Group size cannot be less than 2 or greater than max size.");
            }
            if (args[counter + 1] < 1) {
                throw new IllegalArgumentException("Group capacity cannot be less than 1.");
            }
            //check if capacity is greater than current group size and alter order of operations if needed
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
            if (args[counter] < 0 || args[counter] > Settings.getMaxAgentWeighting()) {
                throw new IllegalArgumentException("Weighting must be between 0 and max weighting.");
            }
            Settings.setAgentInitialWeighting(i, args[counter]);
            counter++;
        }

        //SIM ITERATIONS
        if (args[counter] < 1 || args[counter] > Settings.getMaxIterations()) {
            throw new IllegalArgumentException("Iterations must be between 1 and max iterations.");
        }
        Settings.setIterations(args[counter]);
        counter++;

        //SIM ROUNDS
        if (args[counter] < 1 || args[counter] > Settings.getMaxRounds()) {
            throw new IllegalArgumentException("Rounds must be between 1 and max rounds.");
        }
        Settings.setRounds(args[counter]);
        counter++;

        //SIM RUNS
        if (args[counter] < 1 || args[counter] > Settings.getMaxRuns()) {
            throw new IllegalArgumentException("Runs must be between 1 and max runs.");
        }
        Settings.setRuns(args[counter]);
        counter++;

        //SIM STEPS
        if (args[counter] < 1 || args[counter] > Settings.getMaxSteps()) {
            throw new IllegalArgumentException("Steps must be between 1 and max steps.");
        }
        Settings.setSteps(args[counter]);
        counter++;

        //VARIABLE LEVEL
        if (args[counter] < 1 || args[counter] > Settings.getGroupDepth()) {
            throw new IllegalArgumentException("Variable level must be between 1 and group depth.");
        }
        Settings.setVariableLevel(args[counter]);
        counter++;

        //SIZE INCREMENT
        if (Settings.getGroupSize(Settings.getVariableLevel()) + (args[counter] * Settings.getSteps()) > Settings.getMaxGroupSize()) {
            throw new IllegalArgumentException("Group size cannot exceed max group size after increments.");
        }
        if (args[counter] < 0) {
            throw new IllegalArgumentException("Size increment must be greater than or equal to 0.");
        }
        Settings.setSizeIncrement(args[counter]);
        counter++;

        //CAPACITY INCREMENT
        if (Settings.getGroupCapacity(Settings.getVariableLevel()) + (args[counter] * Settings.getSteps()) > Settings.getGroupSize(Settings.getVariableLevel()) + (args[counter] * Settings.getSteps())) {
            throw new IllegalArgumentException("Group capacity cannot exceed group size after increments.");
        }
        if (args[counter] < 0) {
            throw new IllegalArgumentException("Capacity increment must be greater than or equal to 0.");
        }
        Settings.setCapacityIncrement(args[counter]);

    }

    // ===============
    // UTILITY METHODS
    // ===============
    
    /**
     * Converts a string array into an int array
     * @param strArr as a string array
     * @return int array
     */
    private static int[] strArrayToIntArray(String[] strArr) {
        int[] intArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            intArr[i] = tryParseInt(strArr[i]);
        }
        return intArr;
    }

    /**
     * Attempts to convert a string to an int, returns an exception if not possible
     * @param str as a string
     * @return int
     */
    private static int tryParseInt(String str) {
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
