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

    // private int level; (inherited)
    // private int size;  (inherited)
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
    public AgentGroup(int level, int size, boolean populate) {
        super(level, size, populate);
    }

    // =======
    // METHODS
    // =======

    public void initialise() {
        agents = new ArrayList<Agent>();
    }

    /**
     * Populates AgentGroup with default Agents
     */
    public void populate() {
        for (int i = 0; i < this.getSize(); i++) {
            agents.add(new Agent(
                Settings.getAgentInitialMutability(), 
                Settings.getAgentMutableMutability(),
                Settings.getAgentInitialWeightings()
            ));
        }
    }
    
}
