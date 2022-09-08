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
    private static boolean enhancedMutation = false;            // change this value to turn advanced mutation on or off
    // ===GROUPS===
    private static final int defaultGroupDepth = 3;             // change this number to alter default group depth (number of group levels)
    private static final int maxDepth = 5;                      // change this number to alter max group depth (number of group levels)
    private static int defaultGroupSize = 6;                    // change this number to alter default group size (min 2)
    private static int defaultGroupCapacity = defaultGroupSize / 2;
    private static final int maxGroupSize = 100;                // change this number to alter max group size
    private static final int defaultVariableLevel = 1;          // change this number to alter default variable level
    private static final int defaultSizeIncrement = 0;          // change this number to alter default size increment of variable level
    private static final int defaultCapacityIncrement = 0;      // change this number to alter default capacity increment of variable level
    // ===AGENTS===
    private static int defaultAgentWeighting = 50;              // change this number to alter default agent weighting
    private static final int minAgentWeighting = 0;             // change this number to alter minimum agent weighting
    private static final int maxAgentWeighting = 100;           // change this number to alter maximum agent weighting
    // ===SIMULATION===
    private static final int defaultIterations = 5;             // change this number to alter default number of iterations per round
    private static final int maxIterations = 1000000;           // change this number to alter max number of iterations per round
    private static final int defaultRounds = 1;                 // change this number to alter default number of rounds
    private static final int maxRounds = 100;                   // change this number to alter max number of rounds
    private static final int defaultRuns = 1;                   // change this number to alter default number of runs
    private static final int maxRuns = 1000;                    // change this number to alter max number of runs
    private static final int defaultSteps = 1;                  // change this number to alter default number of steps
    private static final int maxSteps = 1000;                   // change this number to alter max number of steps

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
    private static int runs = defaultRuns;
    private static int steps = defaultSteps;
    private static int variableLevel = defaultVariableLevel;
    private static int sizeIncrement = defaultSizeIncrement;
    private static int capacityIncrement = defaultCapacityIncrement;

    // =======
    // SETTERS
    // =======

    // ===ENTITY SETTERS===

    public static void setInitialMutability(int level, int mutability) {
        assert (level >= 0) : "Cannot set initial mutability for level below 0. Level: " + level;
        assert (level <= groupDepth) : "Cannot set initial mutability for level above max level. Level: " + level + ". Max level: " + groupDepth;
        assert (mutability >= 0) : "Cannot set initial mutability below 0. Mutability: " + mutability;
        assert (mutability <= maxMutability) : "Cannot set initial mutability above max mutability. Mutability: " + mutability + ". Max mutability: " + maxMutability;
        initialMutabilities.set(level, mutability);
    }

    public static void setMutableMutability(int level, boolean bool) {
        assert (level >= 0) : "Cannot set mutable mutability for level below 0. Level: " + level;
        assert (level <= groupDepth) : "Cannot set mutable mutability for level above max level. Level: " + level + ". Max level: " + groupDepth;
        mutableMutabilities.set(level, bool);
    }

    public static void toggleMutableMutability(int level) {
        assert (level >= 0) : "Cannot toggle mutable mutability for level below 0. Level: " + level;
        assert (level <= groupDepth) : "Cannot toggle mutable mutability for level above max level. Level: " + level + ". Max level: " + groupDepth;
        mutableMutabilities.set(level, !mutableMutabilities.get(level));
    }

    public static void setEnhancedMutation(boolean bool) {
        enhancedMutation = bool;
    }

    public static void toggleEnhancedMutation() {
        enhancedMutation = !enhancedMutation;
    }

    // ===GROUP SETTERS===

    public static void setGroupDepth(int newDepth) {
        assert (newDepth > 0) : "Cannot set group depth below 1. Depth: " + newDepth;
        assert (newDepth <= maxDepth) : "Cannot set group depth above max depth. Depth: " + maxDepth + ". Max depth: " + maxDepth;
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
        assert (groupLevel > 0) : "Cannot set group size for group level below 0. Level: " + groupLevel;
        assert (groupLevel <= groupDepth) : "Cannot set group size for group level above max. Level: " + groupLevel + ". Max level: " + groupDepth;
        assert (newSize >= getGroupCapacity(groupLevel)) : "Cannot set group size below group capacity. Level: " + groupLevel + ". Size: " + newSize + ". Capacity: " + getGroupCapacity(groupLevel);
        assert (newSize <= maxGroupSize) : "Cannot set group size above max group size. Level: " + groupLevel + ". Size: " + newSize + ". Max size: " + maxGroupSize;
        groupInitialSizes.set(groupLevel - 1, newSize); 
    }

    public static void setAllGroupSizes(int newSize) {
        assert (newSize >= getMinGroupSize()) : "Cannot set group sizes below any group capacity. Size: " + newSize + ". Capacity: " + getMinGroupSize();
        assert (newSize <= maxGroupSize) : "Cannot set group size above max group size. Size: " + newSize + ". Max: " + maxGroupSize;
        for (int i = 0; i < groupInitialSizes.size(); i++) {
            groupInitialSizes.set(i, newSize);
        }
        defaultGroupSize = newSize;
    }

    public static void setGroupCapacity(int groupLevel, int newCapacity) {
        assert (newCapacity > 0) : "Cannot set capacity below 1. Capacity: " + newCapacity;
        assert (newCapacity <= getGroupSize(groupLevel)) : "Cannot set capacity above group size. Capacity: " + newCapacity + ". Size: " + getGroupSize(groupLevel);
        assert (groupLevel > 0) : "Cannot set capacity for group level below 1. Level: " + groupLevel;
        assert (groupLevel <= groupDepth) : "Cannot set capacity for group level above group depth. Level: " + groupLevel + ". Depth: " + groupDepth;
        groupInitialCapacities.set(groupLevel - 1, newCapacity);
        defaultGroupCapacity = newCapacity; 
    }

    public static void setAllGroupCapacities(int newCapacity) {
        assert (newCapacity <= getMaxCapacity()) : "Cannot set capacities above the size of any group. Capacity: " + newCapacity + ". Max: " + getMaxCapacity();
        assert (newCapacity > 0) : "Cannot set capacity below 1. Capacity: " + newCapacity;
        for (int i = 0; i <groupInitialCapacities.size(); i++) {
            groupInitialCapacities.set(i, newCapacity);
        }
        defaultGroupCapacity = newCapacity;
    }

    public static void setAllGroupMutabilities(int newMutability) {
        assert (newMutability >= 0) : "Cannot set mutabilities below 0. Mutability: " + newMutability;
        assert (newMutability <= maxMutability) : "Cannot set mutabilities above max mutability. Mutability: " + newMutability + ". Max: " + maxMutability;
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

    // ===AGENT SETTERS===

    public static void setAgentInitialWeighting(int groupLevel, int newWeighting) {
        assert (groupLevel >= 0) : "Cannot set weighting for group level below 0. Level: " + groupLevel;
        assert (groupLevel <= groupDepth) : "Cannot set weighting for group level above group depth. Level: " + groupLevel + ". Depth: " + groupDepth;
        assert (newWeighting >= minAgentWeighting) : "Cannot set weighting below minimum agent weighting. Weighting: " + newWeighting + ". Min: " + minAgentWeighting;
        assert (newWeighting <= maxAgentWeighting) : "Cannot set weighting above maximum agent weighting. Weighting: " + newWeighting + ". Max: " + maxAgentWeighting;
        agentInitialWeightings.set(groupLevel, newWeighting);
    }

    public static void setAllAgentWeightings(int newWeighting) {
        assert (newWeighting >= minAgentWeighting) : "Cannot set weightings below minimum agent weighting. Weighting: " + newWeighting + ". Min: " + minAgentWeighting;
        assert (newWeighting <= maxAgentWeighting) : "Cannot set weightings above maximum agent weighting. Weighting: " + newWeighting + ". Max: " + maxAgentWeighting;
        for (int i = 0; i < agentInitialWeightings.size(); i++) {
            agentInitialWeightings.set(i, newWeighting);
        }
        defaultAgentWeighting = newWeighting;
    }

    // ===SIMULATION SETTERS===

    public static void setIterations(int n) {
        assert (n > 0) : "Cannot set iterations below 1. Iterations: " + n;
        assert (n <= maxIterations) : "Cannot set iterations above max iterations. Iterations: " + n + ". Max: " + maxIterations;
        iterationsPerRound = n;
    }

    public static void setRounds(int n) {
        assert (n > 0) : "Cannot set rounds below 1. Rounds: " + n;
        assert (n <= maxRounds) : "Cannot set rounds above max rounds. Rounds: " + n + "Max: " + maxRounds;
        rounds = n;
    }

    public static void setRuns(int n) {
        assert (n > 0) : "Cannot set runs below 1. Runs: " + n;
        assert (n <= maxRuns) : "Cannot set runs above max runs. Runs: " + n + "Max: " + maxRuns;
        runs = n;
    }

    public static void setSteps(int n) {
        assert (n > 0) : "Cannot set steps below 1. Steps: " + n;
        assert (n <= maxSteps) : "Cannot set steps above max steps. Steps: " + n + "Max: " + maxSteps;
        steps = n;
    }

    public static void setVariableLevel(int n) {
        assert (n > 0) : "Cannot set variable level below 1. Level: " + n;
        assert (n <= groupDepth) : "Cannot set variable level above group depth. Level: " + n + "Depth: " + groupDepth;
        variableLevel = n;
    }

    public static void setSizeIncrement(int n) {
        assert (n >= 0) : "Cannot set size increment below 0. Increment: " + n;
        sizeIncrement = n;
    }

    public static void setCapacityIncrement(int n) {
        assert (n >= 0) : "Cannot set capacity increment below 0. Increment: " + n;
        capacityIncrement = n;
    }

    // =======
    // GETTERS
    // =======

    // ===ENTITY GETTERS===

    public static int getInitialMutability(int level) {
        assert (level >= 0) : "Cannot get mutability for Level below 0. Level: " + level;
        assert (level <= groupDepth) : "Cannot get mutability for Level above group depth. Level: " + level + ". Depth: " + groupDepth;
        return initialMutabilities.get(level);
    }

    public static boolean getMutableMutability(int level) {
        assert (level >= 0) : "Cannot get mutable mutability for Level below 0. Level: " + level;
        assert (level <= groupDepth) : "Cannot get mutable mutability for Level above group depth. Level: " + level + ". Depth: " + groupDepth;
        return mutableMutabilities.get(level);
    }

    public static String getMutableMutabilityString(int level) {
        return mutableMutabilities.get(level) ? "ON" : "OFF";
    }

    public static int getMaxMutability() {
        return maxMutability;
    }

    public static boolean getEnhancedMutation() {
        return enhancedMutation;
    }

    public static String getEnhancedMutationString() {
        return enhancedMutation ? "ON" : "OFF";
    }

    // ===GROUP GETTERS===

    public static int getGroupDepth() {
        return groupDepth;
    }

    public static int getMaxGroupDepth() {
        return maxDepth;
    }

    public static int getGroupSize(int groupLevel) {
        assert (groupLevel > 0) : "Cannot get group size for level below 1. Level: " + groupLevel;
        assert (groupLevel <= groupDepth) : "Cannot get group size for level above group depth. Level: " + groupLevel + ". Depth: " + groupDepth;
        return groupInitialSizes.get(groupLevel - 1);
    }

    public static int getMaxGroupSize() {
        return maxGroupSize;
    }

    public static int getGroupCapacity(int groupLevel) {
        assert (groupLevel > 0) : "Cannot get capacity for level below 1. Level: " + groupLevel;
        assert (groupLevel <= groupDepth) : "Cannot get capacity for level above group depth. Level: " + groupLevel + ". Depth: " + groupDepth;
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

    // ===AGENT GETTERS===

    public static int[] getAgentInitialWeightings() {
        int[] weightings = new int[agentInitialWeightings.size()];
        for (int i = 0; i < agentInitialWeightings.size(); i++) {
            weightings[i] = agentInitialWeightings.get(i);
        }
        return weightings;
    }

    public static int getAgentInitialWeighting(int groupLevel) {
        assert (groupLevel > -1) : "Cannot get weighting for level below 0. Level: " + groupLevel;
        assert (groupLevel <= groupDepth) : "Cannot get weighting for level above group depth. Level: " + groupLevel + ". Depth: " + groupDepth;
        return agentInitialWeightings.get(groupLevel);
    }

    public static int getMinAgentWeighting() {
        return minAgentWeighting;
    }

    public static int getMaxAgentWeighting() {
        return maxAgentWeighting;
    }

    // ===SIMULATION GETTERS===

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

    public static int getRuns() {
        return runs;
    }

    public static int getMaxRuns() {
        return maxRuns;
    }

    public static int getSteps() {
        return steps;
    }

    public static int getMaxSteps() {
        return maxSteps;
    }

    public static int getVariableLevel() {
        return variableLevel;
    }

    public static int getSizeIncrement() {
        return sizeIncrement;
    }

    public static int getCapacityIncrmeent() {
        return capacityIncrement;
    }

    // ===============
    // UTILITY METHODS
    // ===============

    public static void incrementVariableSize() {
        setGroupSize(variableLevel, getGroupSize(variableLevel) + sizeIncrement);
    }

    public static void incrementVariableCapacity() {
        setGroupCapacity(variableLevel, getGroupCapacity(variableLevel) + capacityIncrement);
    }
    
}
