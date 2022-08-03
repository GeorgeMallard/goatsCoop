import java.util.ArrayList;
import java.util.Arrays;

/**
 * Settings class
 * Provides default settings and keeps track of changes to settings
 * @author Chris Litting
 * @version 1.0
 */
public class Settings {
    
    // ==============
    // DEFAULT VALUES
    // ==============

    // ===ENTITIES===
    private static int defaultMutability = 50;                  // change this number to alter default mutability for all Entities
    private static boolean defaultMutableMutability = false;    // change this value to alter whether mutabilities are mutable by default
    // ===GROUPS===
    private static final int defaultGroupDepth = 3;             // change this number to alter default group depth (number of group levels)
    private static final int maxDepth = 5;                      // change this number to alter max group depth (number of group levels)
    private static int defaultGroupSize = 6;                    // change this number to alter default group size
    private static final int maxGroupSize = 100;                // change this number to alter max group size
    // ===AGENTS===
    private static int defaultAgentWeighting = 50;              // change this number to alter default agent weighting
    // ===SIMULATION===
    private static final int defaultIterations = 5;             // change this number to alter default number of iterations per round
    private static final int maxIterations = 1000000;           // change this number to alter max number of iterations per round
    private static final int defaultRounds = 1;                 // change this number to alter default number of rounds
    private static final int maxRounds = 100;                   // change this number to alter max number of rounds

    // =========
    // VARIABLES
    // =========

    // ===ENTITIES===
    private static ArrayList<Integer> initialMutabilities = new ArrayList<Integer>(Arrays.asList(
        defaultMutability,
        defaultMutability,
        defaultMutability,
        defaultMutability
    ));
    private static ArrayList<Boolean> mutableMutabilities = new ArrayList<Boolean>(Arrays.asList(
        defaultMutableMutability,
        defaultMutableMutability,
        defaultMutableMutability,
        defaultMutableMutability
    ));
    // ===GROUPS===
    private static int groupDepth = defaultGroupDepth;
    private static ArrayList<Integer> groupInitialSizes = new ArrayList<Integer>(Arrays.asList(
        defaultGroupSize, 
        defaultGroupSize, 
        defaultGroupSize
    ));
    // ===AGENTS===
    private static ArrayList<Integer> agentInitialWeightings = new ArrayList<Integer>(Arrays.asList(
        defaultAgentWeighting, 
        defaultAgentWeighting, 
        defaultAgentWeighting
    ));
    // ===SIMULATION===
	private static int iterationsPerRound = defaultIterations;
	private static int rounds = defaultRounds;

    // =======
    // SETTERS
    // =======

    // ===ENTITIES===

    public static void setInitialMutability(int level, int mutability) {
        if (mutability >= 0 && mutability <= 100) {
            initialMutabilities.set(level, mutability);
        }
    }

    public static void setMutableMutability(int level, boolean bool) {
        mutableMutabilities.set(level, bool);
    }

    // ===GROUPS===

    public static void setGroupDepth(int newDepth) {
        if (newDepth > 0 && newDepth <= maxDepth && newDepth != groupDepth) {
            if (newDepth < groupDepth) {
                for (int i = 0; i < groupDepth - newDepth; i++) {
                    groupInitialSizes.remove(newDepth);
                    agentInitialWeightings.remove(newDepth);
                    initialMutabilities.remove(newDepth + 1);
                    mutableMutabilities.remove(newDepth + 1);

                }
            } else {
                for (int i = 0; i < newDepth - groupDepth; i++) {
                    groupInitialSizes.add(groupDepth, defaultGroupSize);
                    agentInitialWeightings.add(groupDepth, defaultAgentWeighting);
                    initialMutabilities.add(groupDepth + 1, defaultMutability);
                    mutableMutabilities.add(groupDepth + 1, defaultMutableMutability);
                }
            }
            groupDepth = newDepth;
        }
    }

    public static void setGroupSize(int groupLevel, int newSize) {
        if (groupLevel > 0 && groupLevel <= groupInitialSizes.size() && newSize <= maxGroupSize) {
            groupInitialSizes.set(groupLevel - 1, newSize);
        }  
    }

    public static void setAllGroupSizes(int newSize) {
        for (int i = 0; i < groupInitialSizes.size(); i++) {
            groupInitialSizes.set(i, newSize);
        }
        defaultGroupSize = newSize;
    }

    // ===AGENTS===

    public static void setAgentInitialWeighting(int groupLevel, int value) {
        if (groupLevel > -1 && groupLevel < agentInitialWeightings.size()) {
            agentInitialWeightings.set(groupLevel, value);
        }
    }

    public static void setAllAgentWeightings(int newWeighting) {
        for (int i = 0; i < agentInitialWeightings.size(); i++) {
            agentInitialWeightings.set(i, newWeighting);
        }
        defaultAgentWeighting = newWeighting;
    }

    // ===SIMULATION===

    public static void setIterations(int n) {
        if (n > 0 && n <= maxIterations) {
            iterationsPerRound = n;
        }
    }

    public static void setRounds(int n) {
        if (n > 0 && n <= maxRounds) {
            rounds = n;
        }
    }

    // =======
    // GETTERS
    // =======

    // ===ENTITIES===

    public static int getInitialMutability(int level) {
        return initialMutabilities.get(level);
    }

    public static boolean getMutableMutability(int level) {
        return mutableMutabilities.get(level);
    }

    // ===GROUPS===

    public static int getGroupDepth() {
        return groupDepth;
    }

    public static int getMaxGroupDepth() {
        return maxDepth;
    }

    public static int getGroupSize(int groupLevel) {
        return groupInitialSizes.get(groupLevel - 1);
    }

    public static int getMaxGroupSize() {
        return maxGroupSize;
    }

    // ===AGENTS===

    public static int[] getAgentInitialWeightings() {
        int[] weightings = new int[agentInitialWeightings.size()];
        for (int i = 0; i < agentInitialWeightings.size(); i++) {
            weightings[i] = agentInitialWeightings.get(i);
        }
        return weightings;
    }

    public static int getAgentInitialWeighting(int groupLevel) {
        if (groupLevel > -1 && groupLevel < agentInitialWeightings.size()) {
            return agentInitialWeightings.get(groupLevel);
        } else {
            return -1;
        }
    }

    // ===SIMULATION===

    public static int getIterations() {
        return iterationsPerRound;
    }

    public static int getMaxIterations() {
        return maxIterations;
    }

    public static int getRounds() {
        return rounds;
    }

    public static int getMaxRounds() {
        return maxRounds;
    }
}
