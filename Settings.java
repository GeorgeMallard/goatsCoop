import java.util.ArrayList;
import java.util.Arrays;

public class Settings {
    
    // =========
    // CONSTANTS
    // =========

    // ===GROUPS===
    private static final int defaultGroupDepth = 3;         // change this number to alter default group depth (number of group levels)
    private static final int maxDepth = 5;                  // change this number to alter max group depth (number of group levels)
    private static final int defaultGroupSize = 5;         // change this number to alter default group size
    private static final int maxGroupSize = 100;            // change this number to alter max group size
    // ===AGENTS===
    private static final int defaultAgentMutability = 50;   // change this number to alter default agent mutability
    private static final int defaultAgentWeighting = 50;    // change this number to alter default agent weighting
    // ===SIMULATION===
    private static final int defaultIterations = 10000;     // change this number to alter default number of iterations per round
    private static final int maxIterations = 1000000;       // change this number to alter max number of iterations per round
    private static final int defaultRounds = 3;             // change this number to alter default number of rounds
    private static final int maxRounds = 100;               // change this number to alter max number of rounds

    // =========
    // VARIABLES
    // =========

    // ===GROUPS===
    private static int groupDepth = defaultGroupDepth;
    private static ArrayList<Integer> groupInitialSizes = new ArrayList<Integer>(Arrays.asList(
        defaultGroupSize, 
        defaultGroupSize, 
        defaultGroupSize
    ));
    // ===AGENTS===
    private static int agentInitialMutability = defaultAgentMutability;
    private static boolean agentMutableMutability = true;
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

   // ===GROUPS===

    public static void setGroupDepth(int newDepth) {
        if (newDepth > 0 && newDepth <= maxDepth && newDepth != groupDepth) {
            if (newDepth < groupDepth) {
                for (int i = 0; i < groupDepth - newDepth; i++) {
                    groupInitialSizes.remove(newDepth);
                }
            } else {
                for (int i = 0; i < newDepth - groupDepth; i++) {
                    groupInitialSizes.add(groupDepth, defaultGroupSize);
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

    // ===AGENTS===

    public static void setAgentInitialMutability(int mutability) {
        if (mutability >= 0 && mutability <= 100) {
            agentInitialMutability = mutability;
        }
    }

    public static void setAgentMutableMutability(boolean bool) {
        agentMutableMutability = bool;
    }

    public static void setAgentInitialWeighting(int groupLevel, int value) {
        if (groupLevel > -1 && groupLevel < agentInitialWeightings.size()) {
            agentInitialWeightings.set(groupLevel, value);
        }
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

    // ===AGENTS===

    public static int getAgentInitialMutability() {
        return agentInitialMutability;
    }

    public static boolean getAgentMutableMutability() {
        return agentMutableMutability;
    }

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

    public static int getRounds() {
        return rounds;
    }
}
