package src.main;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Provides default settings and allows changes to settings
 * @author Chris Litting
 * @version 1.2
 */
public class Settings {
    
    // =========
    // CONSTANTS
    // =========

    //private static final String

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
	private static int iterations = defaultIterations;
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

    /**
     * Sets mutability for a given Entity level
     * @param level as an int
     * @param mutability as an int
     */
    public static void setInitialMutability(int level, int mutability) {
        assert (level >= 0) : "Cannot set initial mutability for level below 0. Level: " + level;
        assert (level <= groupDepth) : "Cannot set initial mutability for level above max level. Level: " + level + ". Max level: " + groupDepth;
        assert (mutability >= 0) : "Cannot set initial mutability below 0. Mutability: " + mutability;
        assert (mutability <= maxMutability) : "Cannot set initial mutability above max mutability. Mutability: " + mutability + ". Max mutability: " + maxMutability;
        initialMutabilities.set(level, mutability);
    }

    /**
     * Sets whether mutability is mutable for a given Entity level
     * @param level as an int
     * @param bool as a boolean
     */
    public static void setMutableMutability(int level, boolean bool) {
        assert (level >= 0) : "Cannot set mutable mutability for level below 0. Level: " + level;
        assert (level <= groupDepth) : "Cannot set mutable mutability for level above max level. Level: " + level + ". Max level: " + groupDepth;
        mutableMutabilities.set(level, bool);
    }

    /**
     * Toggles mutable mutability on/off for a given Entity level
     * @param level as an int
     */
    public static void toggleMutableMutability(int level) {
        assert (level >= 0) : "Cannot toggle mutable mutability for level below 0. Level: " + level;
        assert (level <= groupDepth) : "Cannot toggle mutable mutability for level above max level. Level: " + level + ". Max level: " + groupDepth;
        mutableMutabilities.set(level, !mutableMutabilities.get(level));
    }

    /**
     * Sets enhacend mutation on/off for all Entities (enhanced mutation is an experimental extra feature)
     * @param bool as a boolean
     */
    public static void setEnhancedMutation(boolean bool) {
        enhancedMutation = bool;
    }

    /**
     * Toggles enhanced mutation on/off for all Entities (enhanced mutation is an experimental extra feature)
     */
    public static void toggleEnhancedMutation() {
        enhancedMutation = !enhancedMutation;
    }

    // ===GROUP SETTERS===

    /**
     * Sets group depth (number of group levels)
     * @param newDepth as an int
     */
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
        if (newDepth < getVariableLevel()) {
            setVariableLevel(newDepth);
        }
    }

    /**
     * Sets the size for groups of a given level
     * @param groupLevel as an int
     * @param newSize as an int
     */
    public static void setGroupSize(int groupLevel, int newSize) {
        assert (groupLevel > 0) : "Cannot set group size for group level below 0. Level: " + groupLevel;
        assert (groupLevel <= groupDepth) : "Cannot set group size for group level above max. Level: " + groupLevel + ". Max level: " + groupDepth;
        assert (newSize >= getGroupCapacity(groupLevel)) : "Cannot set group size below group capacity. Level: " + groupLevel + ". Size: " + newSize + ". Capacity: " + getGroupCapacity(groupLevel);
        assert (newSize <= maxGroupSize) : "Cannot set group size above max group size. Level: " + groupLevel + ". Size: " + newSize + ". Max size: " + maxGroupSize;
        groupInitialSizes.set(groupLevel - 1, newSize); 
    }

    /**
     * Sets size for groups of all levels
     * @param newSize as an int
     */
    public static void setAllGroupSizes(int newSize) {
        assert (newSize >= getHighestGroupCapacity()) : "Cannot set group sizes below any group capacity. Size: " + newSize + ". Capacity: " + getHighestGroupCapacity();
        assert (newSize <= maxGroupSize) : "Cannot set group size above max group size. Size: " + newSize + ". Max: " + maxGroupSize;
        for (int i = 0; i < groupInitialSizes.size(); i++) {
            groupInitialSizes.set(i, newSize);
        }
        defaultGroupSize = newSize;
        if (defaultGroupCapacity > newSize) {
            defaultGroupCapacity = newSize;
        }
    }

    /**
     * Sets capacity for groups of a given level
     * @param groupLevel as an int
     * @param newCapacity as an int
     */
    public static void setGroupCapacity(int groupLevel, int newCapacity) {
        assert (newCapacity > 0) : "Cannot set capacity below 1. Capacity: " + newCapacity;
        assert (newCapacity <= getGroupSize(groupLevel)) : "Cannot set capacity above group size. Capacity: " + newCapacity + ". Size: " + getGroupSize(groupLevel);
        assert (groupLevel > 0) : "Cannot set capacity for group level below 1. Level: " + groupLevel;
        assert (groupLevel <= groupDepth) : "Cannot set capacity for group level above group depth. Level: " + groupLevel + ". Depth: " + groupDepth;
        groupInitialCapacities.set(groupLevel - 1, newCapacity);
        defaultGroupCapacity = newCapacity; 
    }

    /**
     * Sets capacity for groups of all levels
     * @param newCapacity as an int
     */
    public static void setAllGroupCapacities(int newCapacity) {
        assert (newCapacity <= getLowestGroupSize()) : "Cannot set capacities above the size of any group. Capacity: " + newCapacity + ". Max: " + getLowestGroupSize();
        assert (newCapacity > 0) : "Cannot set capacity below 1. Capacity: " + newCapacity;
        for (int i = 0; i <groupInitialCapacities.size(); i++) {
            groupInitialCapacities.set(i, newCapacity);
        }
        defaultGroupCapacity = newCapacity;
        if (defaultGroupSize < newCapacity) {
            defaultGroupSize = newCapacity;
        }
    }

    /**
     * Sets mutability for all groups (but not agents)
     * @param newMutability as an int
     */
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

    /**
     * Toggles default mutable mutability on/off, and sets all groups to that setting (but not agents)
     */
    public static void toggleGroupMutableMutabilities() {
        defaultMutableMutability = !defaultMutableMutability;
        for (int i = 1; i < initialMutabilities.size(); i++) {
            mutableMutabilities.set(i, defaultMutableMutability);
        }
    }

    /**
     * Increments group size for variable level
     */
    public static void incrementVariableSize() {
        setGroupSize(variableLevel, getGroupSize(variableLevel) + sizeIncrement);
    }

    /**
     * Increments group capacity for variable level
     */
    public static void incrementVariableCapacity() {
        setGroupCapacity(variableLevel, getGroupCapacity(variableLevel) + capacityIncrement);
    }

    // ===AGENT SETTERS===

    /**
     * Sets agent weighting for a given group level
     * @param groupLevel as an int
     * @param newWeighting as an int
     */
    public static void setAgentInitialWeighting(int groupLevel, int newWeighting) {
        assert (groupLevel >= 0) : "Cannot set weighting for group level below 0. Level: " + groupLevel;
        assert (groupLevel <= groupDepth) : "Cannot set weighting for group level above group depth. Level: " + groupLevel + ". Depth: " + groupDepth;
        assert (newWeighting >= minAgentWeighting) : "Cannot set weighting below minimum agent weighting. Weighting: " + newWeighting + ". Min: " + minAgentWeighting;
        assert (newWeighting <= maxAgentWeighting) : "Cannot set weighting above maximum agent weighting. Weighting: " + newWeighting + ". Max: " + maxAgentWeighting;
        agentInitialWeightings.set(groupLevel, newWeighting);
    }

    /**
     * Sets agent weightings for all group levels
     * @param newWeighting as an int
     */
    public static void setAllAgentWeightings(int newWeighting) {
        assert (newWeighting >= minAgentWeighting) : "Cannot set weightings below minimum agent weighting. Weighting: " + newWeighting + ". Min: " + minAgentWeighting;
        assert (newWeighting <= maxAgentWeighting) : "Cannot set weightings above maximum agent weighting. Weighting: " + newWeighting + ". Max: " + maxAgentWeighting;
        for (int i = 0; i < agentInitialWeightings.size(); i++) {
            agentInitialWeightings.set(i, newWeighting);
        }
        defaultAgentWeighting = newWeighting;
    }

    // ===SIMULATION SETTERS===

    /**
     * Sets number of iterations per round
     * @param n as an int
     */
    public static void setIterations(int n) {
        assert (n > 0) : "Cannot set iterations below 1. Iterations: " + n;
        assert (n <= maxIterations) : "Cannot set iterations above max iterations. Iterations: " + n + ". Max: " + maxIterations;
        iterations = n;
    }

    /**
     * Sets number of rounds per run
     * @param n as an int
     */
    public static void setRounds(int n) {
        assert (n > 0) : "Cannot set rounds below 1. Rounds: " + n;
        assert (n <= maxRounds) : "Cannot set rounds above max rounds. Rounds: " + n + "Max: " + maxRounds;
        rounds = n;
    }

    /**
     * Sets number of runs per step
     * @param n as an int
     */
    public static void setRuns(int n) {
        assert (n > 0) : "Cannot set runs below 1. Runs: " + n;
        assert (n <= maxRuns) : "Cannot set runs above max runs. Runs: " + n + "Max: " + maxRuns;
        runs = n;
    }

    /**
     * Sets number of steps per simulation
     * @param n as an int
     */
    public static void setSteps(int n) {
        assert (n > 0) : "Cannot set steps below 1. Steps: " + n;
        assert (n <= maxSteps) : "Cannot set steps above max steps. Steps: " + n + "Max: " + maxSteps;
        steps = n;
    }

    /**
     * Sets the group level to be altered after each step
     * @param n as an int
     */
    public static void setVariableLevel(int n) {
        assert (n > 0) : "Cannot set variable level below 1. Level: " + n;
        assert (n <= groupDepth) : "Cannot set variable level above group depth. Level: " + n + "Depth: " + groupDepth;
        variableLevel = n;
    }

    /**
     * Sets the amount to increment the size of the variable group level after each step
     * @param n
     */
    public static void setSizeIncrement(int n) {
        assert (n >= 0) : "Cannot set size increment below 0. Increment: " + n;
        sizeIncrement = n;
    }

    /**
     * Sets the amount to increment the capacity of the variable group level after each step
     * @param n
     */
    public static void setCapacityIncrement(int n) {
        assert (n >= 0) : "Cannot set capacity increment below 0. Increment: " + n;
        capacityIncrement = n;
    }

    // =======
    // GETTERS
    // =======

    // ===ENTITY GETTERS===

    /**
     * Returns the mutability for a given Entity level
     * @param level as an int
     * @return int
     */
    public static int getInitialMutability(int level) {
        assert (level >= 0) : "Cannot get mutability for Level below 0. Level: " + level;
        assert (level <= groupDepth) : "Cannot get mutability for Level above group depth. Level: " + level + ". Depth: " + groupDepth;
        return initialMutabilities.get(level);
    }

    /**
     * Returns whether mutability is mutable for a given level
     * @param level as an int
     * @return boolean
     */
    public static boolean getMutableMutability(int level) {
        assert (level >= 0) : "Cannot get mutable mutability for Level below 0. Level: " + level;
        assert (level <= groupDepth) : "Cannot get mutable mutability for Level above group depth. Level: " + level + ". Depth: " + groupDepth;
        return mutableMutabilities.get(level);
    }

    /**
     * Returns whether mutability is mutable for a given level as a String **used in conjunction with GUI**
     * @param level as an int
     * @param on as a String
     * @param off as a String
     * @return String
     */
    public static String getMutableMutabilityString(int level, String on, String off) {
        return mutableMutabilities.get(level) ? on : off;
    }

    /**
     * Returns value for max mutability
     * @return int
     */
    public static int getMaxMutability() {
        return maxMutability;
    }

    /**
     * Returns whether enhanced mutation is active
     * @return boolean
     */
    public static boolean getEnhancedMutation() {
        return enhancedMutation;
    }

    /**
     * Returns whether enhanced mutation is active as a String **used in conjunction with GUI**
     * @param on as a String
     * @param off as a String
     * @return String
     */
    public static String getEnhancedMutationString(String on, String off) {
        return enhancedMutation ? on : off;
    }

    /**
     * Return whether default mutable mutability is on as a String **used in conjunction with GUI**
     * @param on as a String
     * @param off as a String
     * @return String
     */
    public static String getDefaultMutableMutabilityString(String on, String off) {
        return defaultMutableMutability ? on : off;
    }

    // ===GROUP GETTERS===

    /**
     * Returns group depth (number of group levels)
     * @return int
     */
    public static int getGroupDepth() {
        return groupDepth;
    }

    /**
     * Returns the maximum group depth
     * @return int
     */
    public static int getMaxGroupDepth() {
        return maxDepth;
    }

    /**
     * Returns the group size for a given level
     * @param groupLevel as an int
     * @return int
     */
    public static int getGroupSize(int groupLevel) {
        assert (groupLevel > 0) : "Cannot get group size for level below 1. Level: " + groupLevel;
        assert (groupLevel <= groupDepth) : "Cannot get group size for level above group depth. Level: " + groupLevel + ". Depth: " + groupDepth;
        return groupInitialSizes.get(groupLevel - 1);
    }

    /**
     * Returns the maximum group size
     * @return int
     */
    public static int getMaxGroupSize() {
        return maxGroupSize;
    }

    /**
     * Returns the group capacity for a given level
     * @param groupLevel as an int
     * @return int
     */
    public static int getGroupCapacity(int groupLevel) {
        assert (groupLevel > 0) : "Cannot get capacity for level below 1. Level: " + groupLevel;
        assert (groupLevel <= groupDepth) : "Cannot get capacity for level above group depth. Level: " + groupLevel + ". Depth: " + groupDepth;
        return groupInitialCapacities.get(groupLevel - 1);
    }

    /**
     * Returns the lowest group size of all groups **used for limiting max capacity in GUI**
     * @return int
     */
    public static int getLowestGroupSize() {
        int min = maxGroupSize;
        for (Integer x : groupInitialSizes) {
            if (x < min) {
                min = x;
            }
        }
        return min;
    }

    /**
     * Returns the highest group capacity of all levels **used for limiting min group size in GUI**
     * @return int
     */
    public static int getHighestGroupCapacity() {
        int max = 0;
        for (Integer x : groupInitialCapacities) {
            if (x > max) {
                max = x;
            }
        }
        return max;
    }

    // ===AGENT GETTERS===

    /**
     * Returns all agent initial weightings
     * @return int array
     */
    public static int[] getAgentInitialWeightings() {
        int[] weightings = new int[agentInitialWeightings.size()];
        for (int i = 0; i < agentInitialWeightings.size(); i++) {
            weightings[i] = agentInitialWeightings.get(i);
        }
        return weightings;
    }

    /**
     * Returns agent initial weighting for a given level
     * @param groupLevel as an int
     * @return int
     */
    public static int getAgentInitialWeighting(int groupLevel) {
        assert (groupLevel > -1) : "Cannot get weighting for level below 0. Level: " + groupLevel;
        assert (groupLevel <= groupDepth) : "Cannot get weighting for level above group depth. Level: " + groupLevel + ". Depth: " + groupDepth;
        return agentInitialWeightings.get(groupLevel);
    }

    /**
     * Returns the minimum allowed agent weighting
     * @return int
     */
    public static int getMinAgentWeighting() {
        return minAgentWeighting;
    }

    /**
     * Returns the maximum allowed agent weighting
     * @return int
     */
    public static int getMaxAgentWeighting() {
        return maxAgentWeighting;
    }

    // ===SIMULATION GETTERS===

    /**
     * Returns number of iterations
     * @return int
     */
    public static int getIterations() {
        return iterations;
    }

    /**
     * Returns maximum allowed number of iterations
     * @return int
     */
    public static int getMaxIterations() {
        return maxIterations;
    }

    /**
     * Returns number of rounds
     * @return int
     */
    public static int getRounds() {
        return rounds;
    }

    /**
     * Returns maximum allowed number of rounds
     * @return int
     */
    public static int getMaxRounds() {
        return maxRounds;
    }

    /**
     * Returns number of runs
     * @return int
     */
    public static int getRuns() {
        return runs;
    }

    /**
     * Returns maximum allowed number of runs
     * @return int
     */
    public static int getMaxRuns() {
        return maxRuns;
    }

    /**
     * Returns steps
     * @return int
     */
    public static int getSteps() {
        return steps;
    }

    /**
     * Returns maximum allowed number of steps
     * @return int
     */
    public static int getMaxSteps() {
        return maxSteps;
    }

    /**
     * Returns the maximum number of steps allowed under current settings
     * @return int
     */
    public static int getCurrentMaxSteps() {
        int max = getMaxSteps();
        int n = (getMaxGroupSize() - getGroupSize(getVariableLevel())) / getSizeIncrement();
        if (n < max) {
            max = n;
        } 
        if (getCapacityIncrement() > getSizeIncrement()) {
            n = 0;
            int s = getGroupSize(getVariableLevel());
            int c = getGroupCapacity(getVariableLevel());
            while (c <= s) {
                s += getSizeIncrement();
                c += getCapacityIncrement();
                n++;
            }
            if (n < max) {
                max = n;
            }
        }
        return max;
    }

    /**
     * Returns group level to be altered after each step
     * @return int
     */
    public static int getVariableLevel() {
        return variableLevel;
    }

    /**
     * Returns amount variable level size is incremented after each step
     * @return
     */
    public static int getSizeIncrement() {
        return sizeIncrement;
    }

    /**
     * Returns amount variable level capacity is incremented after each step
     * @return
     */
    public static int getCapacityIncrement() {
        return capacityIncrement;
    }

    // =======
    // METHODS
    // =======

    /**
     * Generates a set of space-delimited arguments based on current settings
     * @return String
     */
    public static String generateArgs() {
        String str = "";

        str += groupDepth + " ";
        str += (enhancedMutation ? 1 : 0) + " ";
        for (int i = 0; i <= groupDepth; i++) {
            str += initialMutabilities.get(i) + " ";
        }
        for (int i = 0; i <= groupDepth; i++) {
            str += (mutableMutabilities.get(i) ? 1 : 0) + " ";
        }
        for (int i = 0; i < groupDepth; i++) {
            str += groupInitialSizes.get(i) + " ";
            str += groupInitialCapacities.get(i) + " ";
        }
        for (int i = 0; i < groupDepth; i++) {
            str += agentInitialWeightings.get(i) + " ";
        }
        str += iterations + " ";
        str += rounds + " ";
        str += runs + " ";
        str += steps + " ";
        str += variableLevel + " ";
        str += sizeIncrement + " ";
        str += capacityIncrement + " ";
        return str;
    }
    
}
