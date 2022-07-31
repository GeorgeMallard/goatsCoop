import java.util.ArrayList;

/**
 * AgentGroup class
 * Creates AgentGroups, defined as (level 1) groups that contain Agents, as opposed to containing other Groups
 * @author  Chris Litting
 * @version 1.0
 */
public class AgentGroup extends Group {

    // ======
    // FIELDS
    // ======

    // private int level;           (inherited from Group)
    // private int size;            (inherited from Group)
    // private double allocations;  (inherited from Group)
    public ArrayList<Agent> agents; 

    // ===========
    // CONSTRUCTOR
    // ===========

    /**
     * Constructor for AgentGroup
     * @param level as an int
     * @param size as an int
     * @param populate as a boolean (indicates whether AgentGroup should auto-populate, used when sim is first created)
     */
    public AgentGroup(int level, int size, boolean populate, Group parentGroup) {
        super(level, size, populate, parentGroup);
    }

    // =======
    // METHODS
    // =======

    /**
     * Initialises agents ArrayList
     */
    public void initialise() {
        agents = new ArrayList<Agent>();
    }

    /**
     * Populates AgentGroup with default Agents (used in simulation setup)
     */
    public void populate() {
        for (int i = 0; i < this.getSize(); i++) {
            agents.add(new Agent(
                Settings.getAgentInitialMutability(), 
                Settings.getAgentMutableMutability(),
                Settings.getAgentInitialWeightings(),
                this
            ));
        }
    }

    /**
     * Counts tokens assigned by Agents to this and higher level Groups
     */
    public void gatherAllocations() {

        for (int i = 0; i < agents.size(); i++) {
            for (int j = 0; j < Settings.getGroupDepth() - this.getLevel(); j++) {
                this.incrementAllocation(j, agents.get(i).getContribution(j + 1));
            }
        }
        System.out.println("Agent Group reporting: " + convert(this.getAllocations()));
    }

    // ===============
    // UTILITY METHODS
    // ===============

    //USED FOR TESTING PURPOSES - DELETE LATER
    public String convert(double[] d) {
        String str = "";
        for (double x : d) {
            str += Double.toString(x) + ", ";
        }
        return str;
    }
    
}
