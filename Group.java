public abstract class Group {
    
    // ======
    // FIELDS
    // ======

    private int level;
    private int size;
    
    // ===========
    // CONSTRUCTOR
    // ===========

    /**
     * Constructor for Group
     * @param level as an int
     * @param size as an int
     * @param populate as a boolean (indicates whether group should be auto populated - used when sim is first started)
     */
    public Group(int level, int size, boolean populate) {
        System.out.println("Creating Level " + level + " group...");
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
     * R
     * @return
     */
    public int getSubGroupSize() {
        return Settings.getGroupSize(this.level - 1);
    }

    // ================
    // ABSTRACT METHODS
    // ================

    /**
     * Abstract method for auto-populating Groups
     */
    public abstract void populate();


    public abstract void initialise();
    

}
