import java.util.ArrayList;

public class AgentGroup extends Group {

    // FIELDS
    // ======
    // private int level; (inherited)
    // private int size;  (inherited)
    public ArrayList<Agent> agents; 

    // CONSTRUCTOR
    // ===========
    public AgentGroup(int level, int size, boolean populate) {
        super(level, size, populate);
    }

    // METHODS
    // =======
    public void populate() {
        for (int i = 0; i < this.getSize(); i++) {
            agents.add(new Agent());
        }
    }
    
}
