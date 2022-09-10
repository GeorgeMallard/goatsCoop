package src.main;
import java.util.Random;

/**
 * Abstract superclass for Group and Agent
 * @author Chris Litting
 * @version 1.1
 */
public abstract class Entity implements Comparable<Entity> {
    
    // =========
    // VARIABLES
    // =========

    private static Random random = new Random();    // used in mutate method
    
    // ======
    // FIELDS
    // ======

    private int level;                  // indicates Agent or Group level (Agent is level 0, first Group level is 1, etc)
    private Group parentGroup;          // indicates Group that Entity belongs to, this should be null for top level Group
    private double[] contributions;     // indicates contributions made to this and higher level Entities according to Agent weightings
    private int mutability;             // indicates likelihood for any given property to mutate each iteration

    // ===========
    // CONSTRUCTOR
    // ===========

    /**
     * Creates Entity objects
     * @param parentGroup as a Group (null if Entity is top level Group)
     */
    public Entity(int level, Group parentGroup, int mutability) {
        assert (level >= 0) : "Cannot create Entity with level below 0. Level: " + level;
        assert (level <= Settings.getGroupDepth()) : "Cannot create Entity with level above group depth. Level: " + level + ". Group depth: " + Settings.getGroupDepth();
        assert (mutability >= 0) : "Cannot create Entity with mutability below 0. Mutability: " + mutability;
        assert (mutability <= Settings.getMaxMutability()) : "Cannot create Entity with mutability above max mutability. Mutability: " + mutability + ". Max mutability: " + Settings.getMaxMutability();
        this.setLevel(level);
        this.setParentGroup(parentGroup);
        this.contributions = new double[Settings.getGroupDepth()];
        this.setMutability(mutability);
    }

    // =======
    // SETTERS
    // =======

    /**
     * Sets Entity level
     * @param level as an int (Agent is considered level 0, groups begin at 1)
     */
    public void setLevel(int level) {
        assert (level >= 0) : "Cannot set Entity level below 0. Level: " + level;
        assert (level <= Settings.getGroupDepth()) : "Cannot set Entity level above group depth. Level: " + level + ". Group depth: " + Settings.getGroupDepth();
        this.level = level;
    }

    /**
     * Sets parent Group
     * @param parentGroup as a Group
     */
    public void setParentGroup(Group parentGroup) {
        this.parentGroup = parentGroup;
    }

    /**
     * Sets contribution at given index to value n
     * @param index as an int
     * @param n as a double
     */
    public void setContribution(int index, double n) {
        assert (index < this.contributions.length) : "Cannot set contribution with index above contributions length. Index: " + index + ". Contributions length: " + this.contributions.length;
        assert (index >= 0) : "Cannot set contribution with index below 0. Index: " + index;
        this.contributions[index] = n;
    }

    /**
     * Increments contribution at given index by amount n
     * @param index as an int
     * @param n as a double
     */
    public void incrementContribution(int index, double n) {
        assert (index < this.contributions.length) : "Cannot increment contribution with index above contributions length. Index: " + index + ". Contributions length: " + this.contributions.length;
        assert (index >= 0) : "Cannot increment contribution with index below 0. Index: " + index;
        this.contributions[index] += n;   
    }

    /**
     * Sets mutability
     * @param mutability as an int
     */
    public void setMutability(int mutability) {
        assert (mutability >= 0) : "Cannot set mutability below 0. Mutability: " + mutability;
        assert (mutability <= Settings.getMaxMutability()) : "Cannot set mutability above max mutability. Mutability: " + mutability + ". Max mutability: " + Settings.getMaxMutability();
        this.mutability = mutability;
    }

    // =======
    // GETTERS
    // =======

    /**
     * Returns Entity level (Agent is level 0, Group containing Agents is level 1, Group containing that is level 2, etc.)
     * @return int
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns parent Group
     * @return Group
     */
    public Group getParentGroup() {
        return this.parentGroup;
    }

    /**
     * Returns the contribution for a given index
     * @param index as an int
     * @return double
     */
    public double getContribution(int index) {
        assert (index < this.contributions.length) : "Cannot get contribution with index above contributions length. Index: " + index + ". Contributions length: " + this.contributions.length;
        assert (index >= 0) : "Cannot get contribution with index below 0. Index: " + index;
        return this.contributions[index];
    }

    /**
     * Returns contribution to self
     * @return double
     */
    public double getSelfContribution() {
        if (this.level != Settings.getGroupDepth()) {
            return this.contributions[this.getLevel()];
        } else {
            return 0.0;
        }
    }

    /**
     * Returns all contributions as an array
     * @return double array
     */
    public double[] getContributions() {
        return this.contributions;
    }

    /**
     * Returns mutability
     * @return int
     */
    public int getMutability() {
        return this.mutability;
    }

    // ==============
    // OBJECT METHODS
    // ==============

    @Override
    public int compareTo(Entity otherEntity) {
        if (this.getSelfContribution() > otherEntity.getSelfContribution()) {
            return -1;
        } else if (this.getSelfContribution() < otherEntity.getSelfContribution()) {
            return 1;
        } else {
            return 0;
        }
    }

    // ================
    // ABSTRACT METHODS
    // ================

    public abstract void gatherData(boolean fullGather);
    public abstract void sortChildren();
    public abstract void cullChildren();
    public abstract void repopulate();
    public abstract Entity clone(Group parentGroup);
    public abstract void addChild(Entity newChild);
    public abstract void mutateEntity();
    public abstract void resetData(boolean fullReset);
    public abstract double getAverageMutability(int level);
    public abstract double getAverageCapacity(int level);

    // ==============
    // STATIC METHODS
    // ==============

    /**
	 * Mutates a value up or down based on mutationFactor (mutationFactor = chance of mutation occurring)
	 * @param value as an int
	 * @param mutationFactor as an int between 0 and max mutability
	 * @return int
	 */
	public static int mutateValue(int value, int mutationFactor, int min, int max) {
        assert (max >= min) : "Cannot mutate when max is below min. Max: " + max + ". Min: " + min;
        assert (value >= min) : "Cannot mutate when value is below min. Value: " + value + ". Min: " + min;
        assert (value <= max) : "Cannot mutate when value is above max. Value: " + value + ". Max: " + max;
        assert (mutationFactor >= 0) : "Cannot mutate when mutation factor is below 0. Mutation factor: " + mutationFactor;
        assert (mutationFactor <= Settings.getMaxMutability()) : "Cannot mutate when mutation factor is above max mutability. Mutation factor: " + mutationFactor + ". Max mutability: " + Settings.getMaxMutability();
        
        int x = random.nextInt(Settings.getMaxMutability() + 1);

        if (x == 0 && Settings.getEnhancedMutation()) {
            return random.nextInt(max - min + 1) + min;
        }

		if (x < mutationFactor) {
            value = random.nextInt(2) > 0 ? value + 1 : value - 1;
            if (value < min) {
				value = (min == max) ? min : min + 1;
			} else if (value > max) {
				value = (min == max) ? max : max - 1;
			} 
		}
		return value;
	}      

}
