public abstract class Group {
    
    // FIELDS
    // ======
    private int level;
    private int size;
    
    // CONSTRUCTOR
    // ===========
    public Group(int level, int size) {
        this.setLevel(level);
        this.setSize(size);
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

    // ABSTRACT METHODS
    // ================
    public abstract void populate();
    



}
