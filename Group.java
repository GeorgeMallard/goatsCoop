public abstract class Group {
    
    // FIELDS
    // ======
    private int level;
    private int size;
    
    // CONSTRUCTOR
    // ===========
    public Group(int level, int size, boolean populate) {
        this.setLevel(level);
        this.setSize(size);
        if (populate) {
            this.populate();
        }
    }

    // SETTERS
    // =======
    public void setLevel(int level) {
        this.level = level;
    }

    public void setSize(int size) {
        this.size = size;
    }

    // GETTERS
    // =======
    public int getLevel() {
        return this.level;
    }

    public int getSize() {
        return this.size;
    }

    public int getSubGroupSize() {
        return Settings.getGroupSize(this.level - 1);
    }

    // ABSTRACT METHODS
    // ================
    public abstract void populate();
    

}
