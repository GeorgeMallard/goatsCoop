import java.util.ArrayList;
import java.util.Arrays;

public class Settings {
    
    // =========
    // CONSTANTS
    // =========

    private static final int maxDepth = 5;          // change this number to alter max group levels
    private static final int maxGroupSize = 100;    // change this number to alter max group size
    private static final int defaultGroupSize = 10; // change this number to alter default group size

    // =========
    // VARIABLES
    // =========

    // ===GROUPS===
    private static int groupDepth = 3;
    private static ArrayList<Integer> groupDefaultSizes = new ArrayList<Integer>(Arrays.asList(10, 10, 10));
    // ===AGENTS===
    private static int agentDefaultMutability = 50;
    private static boolean agentMutableMutability = true;
    private static ArrayList<Integer> agentDefaultWeightings = new ArrayList<Integer>(Arrays.asList(50, 50, 50));
    // ===SIMULATION===
	private static int iterationsPerRound = 10000;
	private static int rounds = 1;

    // =======
    // SETTERS
    // =======

   // ===GROUPS===


    public static void setGroupDepth(int newDepth) {
        if (newDepth > 0 && newDepth < maxDepth && newDepth != groupDepth) {
            if (newDepth < groupDepth) {
                for (int i = 0; i < groupDepth - newDepth; i++) {
                    groupDefaultSizes.remove(newDepth);
                }
            } else {
                for (int i = 0; i < newDepth - groupDepth; i++) {
                    groupDefaultSizes.add(groupDepth, defaultGroupSize);
                }
            }
            groupDepth = newDepth;
        }
    }

    public static void setGroupSize(int groupLevel, int newSize) {
        if (groupLevel > 0 && groupLevel <= groupDefaultSizes.size() && newSize <= maxGroupSize) {
            groupDefaultSizes.set(groupLevel - 1, newSize);
        }  
    }

    // ===AGENTS===

    public static void setAgentDefaultMutability(int mutability) {
        if (mutability >= 0 && mutability <= 100) {
            agentDefaultMutability = mutability;
        }
    }

    public static void setAgentMutableMutability(boolean bool) {
        agentMutableMutability = bool;
    }

    public static void setAgentWeighting(int groupLevel, int value) {
        if (groupLevel > -1 && groupLevel < agentDefaultWeightings.size()) {
            agentDefaultWeightings.set(groupLevel, value);
        }
    }

    // ===SIMULATION===



    // =======
    // GETTERS
    // =======

    // ===GROUPS===


    public static int getDepth() {
        return groupDepth;
    }

    public static int getGroupSize(int groupLevel) {
        return groupDefaultSizes.get(groupLevel - 1);
    }

    // ===AGENTS===

    public static int getAgentDefaultMutability() {
        return agentDefaultMutability;
    }

    public static boolean getAgentMutableMutability() {
        return agentMutableMutability;
    }

    public static int[] getAgentDefaultWeightings() {
        int[] weightings = new int[agentDefaultWeightings.size()];
        for (int i = 0; i < agentDefaultWeightings.size(); i++) {
            weightings[i] = agentDefaultWeightings.get(i);
        }
        return weightings;
    }

    public static int getAgentDefaultWeighting(int groupLevel) {
        if (groupLevel > -1 && groupLevel < agentDefaultWeightings.size()) {
            return agentDefaultWeightings.get(groupLevel);
        } else {
            return -1;
        }
    }

    // ===SIMULATION===

}
