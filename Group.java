public abstract class Group extends Entity {
    
    // ======
    // FIELDS
    // ======

    private int level;
    private int size;
    private double[] allocations;
    
    // ===========
    // CONSTRUCTOR
    // ===========

    /**
     * Constructor for Group
     * @param level as an int
     * @param size as an int
     * @param populate as a boolean (indicates whether group should be auto populated - used when sim is first started)
     */
    public Group(int level, int size, boolean populate, Group parentGroup) {
        super(parentGroup);
        //System.out.println("Creating Level " + level + " group...");
        this.allocations = new double[Settings.getGroupDepth() - level];
        this.setLevel(level);
        this.setSize(size);
        this.initialise();
        if (populate) {
            this.populate();
        }
    }

    // =======
    // SETTERS
    // =======

    /**
     * Sets group level
     * @param level as an int (Agent is considered level 0, groups begin at 1)
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Sets group size
     * @param size as an int
     */
    public void setSize(int size) {
        this.size = size;
    }

    public void incrementAllocation(int index, double n) {
        if (index < this.allocations.length) {
            this.allocations[index] += n;
        }
    }

    public void resetAllocations() {
        for (int i = 0; i < this.allocations.length; i++) {
            this.allocations[i] = 0.0;
        }
    }

    // =======
    // GETTERS
    // =======

    /**
     * Returns Group level (group containing Agents is level 1, group containing that is level 2, and so on)
     * @return int
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns Group size
     * @return int
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Returns the allocation for a given index
     * @param index as an int
     * @return double
     */
    public double getAllocation(int index) {
        return this.allocations[index];
    }

    public double[] getAllocations() {
        return this.allocations;
    }

    // ================
    // ABSTRACT METHODS
    // ================

    public abstract void populate();            //auto-populates with child Groups or Agents
    public abstract void initialise();          //initialises Group or Agent ArrayLists
    public abstract void gatherAllocations();   //gathers allocations from child Groups or Agents

}
