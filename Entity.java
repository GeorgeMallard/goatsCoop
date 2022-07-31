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

    private Group parentGroup;

    // ===========
    // CONSTRUCTOR
    // ===========

    /**
     * Creates Entity objects
     * @param parentGroup as a Group (null if Entity is top level Group)
     */
    public Entity(Group parentGroup) {
        this.setParentGroup(parentGroup);
    }

    // =======
    // SETTERS
    // =======

    /**
     * Sets parent group
     * @param parentGroup as a Group
     */
    public void setParentGroup(Group parentGroup) {
        this.parentGroup = parentGroup;
    }

    // =======
    // GETTERS
    // =======

    /**
     * Returns parent group
     * @return Group
     */
    public Group getParentGroup() {
        return this.parentGroup;
    }

}
