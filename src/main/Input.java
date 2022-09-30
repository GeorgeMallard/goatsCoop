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
    private static final String GENERIC_PROMPT = ">";

    // ASSERTS AND EXCEPTIONS
    private static final String MIN_GREATER_THAN_MAX_ASSERT = "Cannot read int where min is greater than max. Min: %d. Max: %d";
    private static final String GROUP_DEPTH_EXCEPTION = "Group depth must be between 1 and max group depth. Depth: %d. Max: %d";
    private static final String MUTABILITY_EXCEPTION = "Mutability must be between 0 and max mutability. Mutability: %d. Max: %d";
    private static final String GROUP_SIZE_CAPACITY_EXCEPTION = "Group size cannot be less than group capacity. Size: %d. Capacity: %d";
    private static final String GROUP_SIZE_EXCEPTION = "Group size cannot be less than 2 or greater than max size. Size: %d. Max: %d";
    private static final String GROUP_CAPACITY_EXCEPTION = "Group capacity cannot be less than 1. Capacity: %d";
    private static final String WEIGHTING_EXCEPTION = "Weighting must be between 0 and max weighting. Weighting: %d. Max: %d";
    private static final String ITERATIONS_EXCEPTION = "Iterations must be between 1 and max iterations. Iterations: %d. Max: %d";
    private static final String ROUNDS_EXCEPTION = "Rounds must be between 1 and max rounds. Rounds: %d. Max: %d";
    private static final String RUNS_EXCEPTION = "Runs must be between 1 and max runs. Runs: %d. Max: %d";
    private static final String STEPS_EXCEPTION = "Steps must be between 1 and max steps. Steps: %d. Max: %d";
    private static final String VARIABLE_LEVEL_EXCEPTION = "Variable level must be between 1 and group depth. Level: %d. Depth: %d";
    private static final String GROUP_SIZE_INCREMENTS_EXCEPTION = "Group size cannot exceed max group size after increments. Group size: %d. Max group size: %d";
    private static final String SIZE_INCREMENT_EXCEPTION = "Size increment must be greater than or equal to 0. Increment: %d";
    private static final String GROUP_CAPACITY_INCREMENTS_EXCEPTION = "Group capacity cannot exceed group size after increments. Capacity: %d. Size: %d";
    private static final String CAPACITY_INCREMENT_EXCEPTION = "Capacity increment must be greater than or equal to 0. Increment: %d";
    private static final String NON_INTEGER_ARGUMENT_EXCEPTION = "Unable to read non-integer argument. Argument: %s";

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
        assert (max >= min) : String.format(MIN_GREATER_THAN_MAX_ASSERT, min, max);
        System.out.print(prompt.equals("") ? GENERIC_PROMPT : prompt);
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
        System.out.print(prompt.equals("") ? GENERIC_PROMPT : prompt);
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
        try {
            int[] args = strArrayToIntArray(strArgs);
        
            //GROUP DEPTH
            if (args[counter] < 1 || args[counter] > Settings.getMaxGroupDepth()) {
                throw new IllegalArgumentException(String.format(GROUP_DEPTH_EXCEPTION, args[counter], Settings.getMaxGroupDepth()));
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
                    throw new IllegalArgumentException(String.format(MUTABILITY_EXCEPTION, args[counter], Settings.getMaxMutability()));
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
                    throw new IllegalArgumentException(String.format(GROUP_SIZE_CAPACITY_EXCEPTION, args[counter], args[counter + 1]));
                }
                if (args[counter] < 2 || args[counter] > Settings.getMaxGroupSize()) {
                    throw new IllegalArgumentException(String.format(GROUP_SIZE_EXCEPTION, args[counter], Settings.getMaxGroupSize()));
                }
                if (args[counter + 1] < 1) {
                    throw new IllegalArgumentException(String.format(GROUP_CAPACITY_EXCEPTION, args[counter + 1]));
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
                    throw new IllegalArgumentException(String.format(WEIGHTING_EXCEPTION, args[counter], Settings.getMaxAgentWeighting()));
                }
                Settings.setAgentInitialWeighting(i, args[counter]);
                counter++;
            }

            //SIM ITERATIONS
            if (args[counter] < 1 || args[counter] > Settings.getMaxIterations()) {
                throw new IllegalArgumentException(String.format(ITERATIONS_EXCEPTION, args[counter], Settings.getMaxIterations()));
            }
            Settings.setIterations(args[counter]);
            counter++;

            //SIM ROUNDS
            if (args[counter] < 1 || args[counter] > Settings.getMaxRounds()) {
                throw new IllegalArgumentException(String.format(ROUNDS_EXCEPTION, args[counter], Settings.getMaxRounds()));
            }
            Settings.setRounds(args[counter]);
            counter++;

            //SIM RUNS
            if (args[counter] < 1 || args[counter] > Settings.getMaxRuns()) {
                throw new IllegalArgumentException(String.format(RUNS_EXCEPTION, args[counter], Settings.getMaxRuns()));
            }
            Settings.setRuns(args[counter]);
            counter++;

            //SIM STEPS
            if (args[counter] < 1 || args[counter] > Settings.getMaxSteps()) {
                throw new IllegalArgumentException(String.format(STEPS_EXCEPTION, args[counter],  Settings.getMaxSteps()));
            }
            Settings.setSteps(args[counter]);
            counter++;

            //VARIABLE LEVEL
            if (args[counter] < 1 || args[counter] > Settings.getGroupDepth()) {
                throw new IllegalArgumentException(String.format(VARIABLE_LEVEL_EXCEPTION, args[counter], Settings.getGroupDepth()));
            }
            Settings.setVariableLevel(args[counter]);
            counter++;

            //SIZE INCREMENT
            if (
                Settings.getGroupSize(Settings.getVariableLevel()) + (args[counter] * (Settings.getSteps() - 1)) 
                > Settings.getMaxGroupSize()
            ) {
                throw new IllegalArgumentException(
                    String.format(
                        GROUP_SIZE_INCREMENTS_EXCEPTION, 
                        Settings.getGroupSize(Settings.getVariableLevel()) + (args[counter] * (Settings.getSteps() - 1)), 
                        Settings.getMaxGroupSize()
                    )
                );
            }
            if (args[counter] < 0) {
                throw new IllegalArgumentException(String.format(SIZE_INCREMENT_EXCEPTION, args[counter]));
            }
            Settings.setSizeIncrement(args[counter]);
            counter++;

            //CAPACITY INCREMENT
            if (
                Settings.getGroupCapacity(Settings.getVariableLevel()) + (args[counter] * (Settings.getSteps() - 1)) 
                > Settings.getGroupSize(Settings.getVariableLevel()) + (Settings.getSizeIncrement() * (Settings.getSteps() - 1))
            ) {
                throw new IllegalArgumentException(
                    String.format(
                        GROUP_CAPACITY_INCREMENTS_EXCEPTION, 
                        Settings.getGroupCapacity(Settings.getVariableLevel()) + (args[counter] * (Settings.getSteps() - 1)), 
                        Settings.getGroupSize(Settings.getVariableLevel()) + (Settings.getSizeIncrement() * (Settings.getSteps() - 1))
                    )
                );
            }
            if (args[counter] < 0) {
                throw new IllegalArgumentException(String.format(CAPACITY_INCREMENT_EXCEPTION, args[counter]));
            }
            Settings.setCapacityIncrement(args[counter]);

        } catch (Exception e) {
            throw e;
        } 

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
            throw new IllegalArgumentException(String.format(NON_INTEGER_ARGUMENT_EXCEPTION, str));
        }
        return n;
    }

}
