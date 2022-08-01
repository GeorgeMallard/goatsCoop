/**
 * Entity class
 * Abstract superclass for Group and Agent
 * @author Chris Litting
 * @version 1.0
 */
public abstract class Entity {
    
    // ======
    // FIELDS
    // ======

    private int level;
    private Group parentGroup;
    private double[] contributions;

    // ===========
    // CONSTRUCTOR
    // ===========

    /**
     * Creates Entity objects
     * @param parentGroup as a Group (null if Entity is top level Group)
     */
    public Entity(int level, Group parentGroup) {
        this.setLevel(level);
        this.setParentGroup(parentGroup);
        this.contributions = new double[Settings.getGroupDepth() - level];
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
     * Sets all contributions back to zero
     */
    public void resetContributions() {
        for (int i = 0; i < this.contributions.length; i++) {
            this.contributions[i] = 0.0;
        }
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

    // =======
    // METHODS
    // =======

    
    // ================
    // ABSTRACT METHODS
    // ================

    public abstract void gatherContributions();
    public abstract void sortChildren();
}
