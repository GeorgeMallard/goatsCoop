package src.main;

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
    private static int maxMutability = 100;                     // change this number to alter maximum mutability (increase for greater granularity)
    // ===GROUPS===
    private static final int defaultGroupDepth = 3;             // change this number to alter default group depth (number of group levels)
    private static final int maxDepth = 5;                      // change this number to alter max group depth (number of group levels)
    private static int defaultGroupSize = 6;                    // change this number to alter default group size (min 2)
    private static int defaultGroupCapacity = defaultGroupSize / 2;
    private static final int maxGroupSize = 100;                // change this number to alter max group size
    // ===AGENTS===
    private static int defaultAgentWeighting = 50;              // change this number to alter default agent weighting
    private static final int minAgentWeighting = 0;             // change this number to alter minimum agent weighting
    private static final int maxAgentWeighting = 100;           // change this number to alter maximum agent weighting
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
    private static ArrayList<Integer> groupInitialCapacities = new ArrayList<Integer>(Arrays.asList(
        defaultGroupCapacity,
        defaultGroupCapacity,
        defaultGroupCapacity
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
        assert (level >= 0) : "Cannot set initial mutability for level below 0";
        assert (level <= groupDepth) : "Cannot set initial mutability for level above max level";
        assert (mutability >= 0) : "Cannot set initial mutability below 0";
        assert (mutability <= maxMutability) : "Cannot set initial mutability greater than max mutability";
        initialMutabilities.set(level, mutability);
    }

    public static void setMutableMutability(int level, boolean bool) {
        assert (level >= 0) : "Cannot set mutable mutability for level below 0";
        assert (level <= groupDepth) : "Cannot set mutable mutability for level above max level";
        mutableMutabilities.set(level, bool);
    }

    public static void toggleMutableMutability(int level) {
        assert (level >= 0) : "Cannot toggle mutable mutability for level below 0";
        assert (level <= groupDepth) : "Cannot toggle mutable mutability for level above max level";
        mutableMutabilities.set(level, !mutableMutabilities.get(level));
    }

    // ===GROUPS===

    public static void setGroupDepth(int newDepth) {
        assert (newDepth > 0) : "Cannot set group depth less than 1";
        assert (newDepth <= maxDepth) : "Cannot set group depth greater than max depth";
        if (newDepth != groupDepth) {
            if (newDepth < groupDepth) {
                for (int i = 0; i < groupDepth - newDepth; i++) {
                    groupInitialSizes.remove(newDepth);
                    groupInitialCapacities.remove(newDepth);
                    agentInitialWeightings.remove(newDepth);
                    initialMutabilities.remove(newDepth + 1);
                    mutableMutabilities.remove(newDepth + 1);

                }
            } else {
                for (int i = 0; i < newDepth - groupDepth; i++) {
                    groupInitialSizes.add(groupDepth, defaultGroupSize);
                    groupInitialCapacities.add(groupDepth, defaultGroupSize / 2);
                    agentInitialWeightings.add(groupDepth, defaultAgentWeighting);
                    initialMutabilities.add(groupDepth + 1, defaultMutability);
                    mutableMutabilities.add(groupDepth + 1, defaultMutableMutability);
                }
            }
            groupDepth = newDepth;
        }
    }

    public static void setGroupSize(int groupLevel, int newSize) {
        assert (groupLevel > 0) : "Cannot set group size for group level below 0";
        assert (groupLevel <= groupDepth) : "Cannot set group size for group level above max";
        assert (newSize >= getGroupCapacity(groupLevel)) : "Cannot set group size lower than group capacity";
        assert (newSize <= maxGroupSize) : "Cannot set group size above max group size";
        groupInitialSizes.set(groupLevel - 1, newSize); 
    }

    public static void setAllGroupSizes(int newSize) {
        assert (newSize >= getMinGroupSize()) : "Cannot set group size below group capacity";
        assert (newSize <= maxGroupSize) : "Group size cannot exceed max group size";
        for (int i = 0; i < groupInitialSizes.size(); i++) {
            groupInitialSizes.set(i, newSize);
        }
        defaultGroupSize = newSize;
    }

    public static void setGroupCapacity(int groupLevel, int newCapacity) {
        assert (newCapacity > 0) : "Capacity cannot be less than 1";
        assert (newCapacity <= getGroupSize(groupLevel)) : "Capacity cannot exceed group size";
        assert (groupLevel > 0) : "Group level cannot be less than 1";
        assert (groupLevel <= groupDepth) : "Group level cannot exceed group depth";
        groupInitialCapacities.set(groupLevel - 1, newCapacity);
        defaultGroupCapacity = newCapacity; 
    }

    public static void setAllGroupCapacities(int newCapacity) {
        assert (newCapacity <= getMaxCapacity()) : "Capacity cannot exceed size of any group";
        assert (newCapacity > 0) : "Capacity must be greater than 0";
        for (int i = 0; i <groupInitialCapacities.size(); i++) {
            groupInitialCapacities.set(i, newCapacity);
        }
        defaultGroupCapacity = newCapacity;
    }

    public static void setAllGroupMutabilities(int newMutability) {
        assert (newMutability >= 0) : "Mutability cannot be less than 0";
        assert (newMutability <= maxMutability) : "Mutability cannot exceed max mutability";
        if (newMutability >= 0 && newMutability <= 100) {
            for (int i = 1; i < initialMutabilities.size(); i++) {
                initialMutabilities.set(i, newMutability);
            }  
            defaultMutability = newMutability; 
        }
    }

    public static void toggleGroupMutableMutabilities() {
        defaultMutableMutability = !defaultMutableMutability;
        for (int i = 1; i < initialMutabilities.size(); i++) {
            mutableMutabilities.set(i, defaultMutableMutability);
        }
    }

    // ===AGENTS===

    public static void setAgentInitialWeighting(int groupLevel, int newWeighting) {
        assert (groupLevel >= 0) : "Group level cannot be less than 0";
        assert (groupLevel <= groupDepth) : "Group level cannot exceed group depth";
        assert (newWeighting >= minAgentWeighting) : "Weighting cannot be less than minimum agent weighting";
        assert (newWeighting <= maxAgentWeighting) : "Weighting cannot exceed maximum agent weighting";
        agentInitialWeightings.set(groupLevel, newWeighting);
    }

    public static void setAllAgentWeightings(int newWeighting) {
        assert (newWeighting >= minAgentWeighting) : "Weighting cannot be less than minimum agent weighting";
        assert (newWeighting <= maxAgentWeighting) : "Weighting cannot exceed maximum agent weighting";
        for (int i = 0; i < agentInitialWeightings.size(); i++) {
            agentInitialWeightings.set(i, newWeighting);
        }
        defaultAgentWeighting = newWeighting;
    }

    // ===SIMULATION===

    public static void setIterations(int n) {
        assert (n > 0) : "Iterations must be greater than 0";
        assert (n <= maxIterations) : "Iterations cannot exceed max iterations";
        iterationsPerRound = n;
    }

    public static void setRounds(int n) {
        assert (n > 0) : "Rounds must be greater than 0";
        assert (n <= maxRounds) : "Rounds cannot exceed max rounds";
        rounds = n;
    }

    // =======
    // GETTERS
    // =======

    // ===ENTITIES===

    public static int getInitialMutability(int level) {
        assert (level > 0) : "Level cannot be less than 0";
        assert (level <= groupDepth) : "Level cannot exceed group depth";
        return initialMutabilities.get(level);
    }

    public static boolean getMutableMutability(int level) {
        assert (level > 0) : "Level cannot be less than 0";
        assert (level <= groupDepth) : "Level cannot exceed group depth";
        return mutableMutabilities.get(level);
    }

    public static String getMutableMutabilityString(int level) {
        return mutableMutabilities.get(level) ? "ON" : "OFF";
    }

    public static int getMaxMutability() {
        return maxMutability;
    }

    // ===GROUPS===

    public static int getGroupDepth() {
        return groupDepth;
    }

    public static int getMaxGroupDepth() {
        return maxDepth;
    }

    public static int getGroupSize(int groupLevel) {
        assert (groupLevel > 0) : "Group level cannot be less than 1";
        assert (groupLevel <= groupDepth) : "Group level cannot exceed group depth";
        return groupInitialSizes.get(groupLevel - 1);
    }

    public static int getMaxGroupSize() {
        return maxGroupSize;
    }

    public static int getGroupCapacity(int groupLevel) {
        assert (groupLevel > 0) : "Group level cannot be less than 1";
        assert (groupLevel <= groupDepth) : "Group level cannot be greater than group depth";
        return groupInitialCapacities.get(groupLevel - 1);
    }

    public static int getMaxCapacity() {
        int min = maxGroupSize;
        for (Integer x : groupInitialSizes) {
            if (x < min) {
                min = x;
            }
        }
        return min;
    }

    public static int getMinGroupSize() {
        int max = 0;
        for (Integer x : groupInitialCapacities) {
            if (x > max) {
                max = x;
            }
        }
        return max;
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
        assert (groupLevel > -1) : "Group level cannot be less than 0";
        assert (groupLevel <= groupDepth) : "Group level cannot exceed group depth";
        return agentInitialWeightings.get(groupLevel);
    }

    public static int getMinAgentWeighting() {
        return minAgentWeighting;
    }

    public static int getMaxAgentWeighting() {
        return maxAgentWeighting;
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
