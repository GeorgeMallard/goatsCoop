package Program;
import java.util.Random;

/**
 * Entity class
 * Abstract superclass for Group and Agent
 * @author Chris Litting
 * @version 1.0
 */
public abstract class Entity {
    
    // =========
    // VARIABLES
    // =========

    private static Random random = new Random();
    
    // ======
    // FIELDS
    // ======

    private int level;
    private Group parentGroup;
    private double[] contributions;
    private int mutability;

    // ===========
    // CONSTRUCTOR
    // ===========

    /**
     * Creates Entity objects
     * @param parentGroup as a Group (null if Entity is top level Group)
     */
    public Entity(int level, Group parentGroup, int mutability) {
        this.setLevel(level);
        this.setParentGroup(parentGroup);
        this.contributions = new double[Settings.getGroupDepth() - level];
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
        this.contributions[index] = n;
    }

    /**
     * Increments contribution at given index by amount n
     * @param index as an int
     * @param n as a double
     */
    public void incrementContribution(int index, double n) {
        if (index < this.contributions.length) {
            this.contributions[index] += n;
        }
    }

    /**
     * Sets mutability
     * @param mutability as an int
     */
    public void setMutability(int mutability) {
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
        return this.contributions[index];
    }

    /**
     * Returns contribution to self
     * @return double
     */
    public double getSelfContribution() {
        if (this.level != Settings.getGroupDepth()) {
            return this.contributions[0];
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
        if (max < min) {
            throw new IllegalArgumentException("Max is less than min");
        }
        if (value < min) {
            throw new IllegalArgumentException("Value is less than min");
        }
        if (value > max) {
            throw new IllegalArgumentException("Value is greater than max");
        }
        if (mutationFactor < 0) {
            throw new IllegalArgumentException("Mutation factor is less than 0");
        }
        if (mutationFactor > 100) {
            throw new IllegalArgumentException("Mutation factor is greater than 100");
        }
        int x = random.nextInt(101);
		if (x < mutationFactor) {
            value = random.nextInt(2) > 0 ? value + 1 : value - 1;
            if (value < min) {
				value = min;
			} else if (value > max) {
				value = max;
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
