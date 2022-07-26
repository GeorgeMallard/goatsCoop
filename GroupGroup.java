import java.util.ArrayList;

/**
 * GroupGroup class
 * Creates GroupGroups, defined as (level 2+) groups that contain other Groups, as opposed to containing Agents
 * @author  Chris Litting
 * @version 1.0
 */
public class GroupGroup extends Group {
   
    // ======
    // FIELDS
    // ======
    // private int level; (inherited)
    // private int size;  (inherited)
    public ArrayList<Group> groups; 

    // ===========
    // CONSTRUCTOR
    // ===========

    /**
     * Constructor for GroupGroup class
     * @param level as an int
     * @param size as an int
     * @param populate as a boolean (indicates whether or not to auto-populate, used when first starting a sim)
     */
    public GroupGroup(int level, int size, boolean populate) {
        super(level, size, populate);
    }

    // =======
    // METHODS
    // =======

    /**
     * Auto-populates a group with sub-groups
     */
    public void populate() {
        if (this.getLevel() == 2) {
            for (int i = 0; i < this.getSize(); i++) {
                this.groups.add(new AgentGroup(1, this.getSubGroupSize(), true));
            }
        } else {
            for (int i = 0; i < this.getSize(); i++) {
                this.groups.add(new GroupGroup(this.getLevel() - 1, this.getSubGroupSize(), true));
            }
        }
        
    }
}
