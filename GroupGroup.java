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
    public GroupGroup(int level, int size, boolean populate, Group parentGroup) {
        super(level, size, populate, parentGroup);
    }

    // =======
    // SETTERS
    // =======

    //empty

    // =======
    // GETTERS
    // =======

    /**
     * Returns size of child group
     * @return int
     */
    public int getSubGroupSize() {
        return Settings.getGroupSize(this.getLevel() - 1);
    }

    // =======
    // METHODS
    // =======

    /**
     * Initialises groups ArrayList
     */
    public void initialise() {
        groups = new ArrayList<Group>();
    }


    /**
     * Auto-populates a group with sub-groups (used in simulation setup)
     */
    public void populate() {
        if (this.getLevel() == 2) {
            for (int i = 0; i < this.getSize(); i++) {
                this.groups.add(new AgentGroup(1, this.getSubGroupSize(), true, this));
            }
        } else {
            for (int i = 0; i < this.getSize(); i++) {
                this.groups.add(new GroupGroup(this.getLevel() - 1, this.getSubGroupSize(), true, this));
            }
        }
        
    }

    /**
     * Counts tokens assigned by Agents to this and higher level Groups
     */
    public void gatherAllocations() {
        for (Group x : groups) {
            x.gatherAllocations();
        }
        for (int i = 0; i < groups.size(); i++) {
            for (int j = 0; j < Settings.getGroupDepth() - this.getLevel(); j++) {
                this.incrementAllocation(j, groups.get(i).getAllocation(j + 1));
            }
        }
        System.out.println("Level " + this.getLevel() + " Group reporting: " + convert(this.getAllocations()));
    }

    // ===============
    // UTILITY METHODS
    // ===============

    //FOR TESTING PURPOSES - DELETE LATER
    public String convert(double[] d) {
        String str = "";
        for (double x : d) {
            str += Double.toString(x) + ", ";
        }
        return str;
    }

}
