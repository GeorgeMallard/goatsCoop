package src.main;
import java.util.Random;

/**
 * Entity class
 * Abstract superclass for Group and Agent
 * @author Chris Litting
 * @version 1.0
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
        assert (level >= 0) : "Cannot create entity with level below 0 (level is " + level + ")";
        assert (level <= Settings.getGroupDepth()) : "Cannot create entity with level above group depth";
        assert (mutability >= 0) : "Cannot create entity with mutability below 0";
        assert (mutability <= Settings.getMaxMutability()) : "Cannot create entity with mutability greater than max mutability";

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
        assert (level >= 0) : "Cannot set entity level below 0";
        assert (level <= Settings.getGroupDepth()) : "Cannot set entity level above group depth";
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
        assert (index < this.contributions.length) : "Contributions index is too high";
        assert (index >= 0) : "Contributions index is below 0";
        this.contributions[index] = n;
    }

    /**
     * Increments contribution at given index by amount n
     * @param index as an int
     * @param n as a double
     */
    public void incrementContribution(int index, double n) {
        assert (index < this.contributions.length) : "Contributions index is too high";
        assert (index >= 0) : "Contributions index is below 0";
        this.contributions[index] += n;   
    }

    /**
     * Sets mutability
     * @param mutability as an int
     */
    public void setMutability(int mutability) {
        assert (mutability >= 0) : "Mutability cannot be below 0";
        assert (mutability <= Settings.getMaxMutability()) : "Mutability cannot be above max mutability";
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
        assert (index < this.contributions.length) : "Contributions index is too high";
        assert (index >= 0) : "Contributions index is below 0";
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

    // =======
    // METHODS
    // =======

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

    // ==============
    // STATIC METHODS
    // ==============

    /**
	 * Mutates a value up or down based on mutationFactor (mutationFactor = % chance of mutation occurring)
	 * @param value as an int
	 * @param mutationFactor as an int between 0 and 100
	 * @return int
	 */
	public static int mutate(int value, int mutationFactor, int min, int max) {
        assert (max >= min) : "max is less than min";
        assert (value >= min) : "value is less than min";
        assert (value <= max) : "value is greater than max";
        assert (mutationFactor >= 0) : "mutation factor is less than 0";
        assert (mutationFactor <= 100) : "mutation factor is greater than 100";
        
        int x = random.nextInt(101);
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

    // ================
    // ABSTRACT METHODS
    // ================

    public abstract void gatherContributions();
    public abstract void sortChildren();
    public abstract void cullChildren();
    public abstract void repopulate();
    public abstract Entity clone(Group parentGroup);
    public abstract void addChild(Entity newChild);
    public abstract void mutateEntity();
    public abstract void resetContributions();
    public abstract void report();

}
