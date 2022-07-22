import java.util.ArrayList;

public class AgentGroup extends Group {

    // FIELDS
    // ======
    // private int level; (inherited)
    // private int size;  (inherited)
    public ArrayList<Agent> agents; 

    // CONSTRUCTOR
    // ===========
    public AgentGroup(int level, int size) {
        super(level, size);
        this.populate();
    }

    // METHODS
    // =======
    public void populate() {
        
    }
    
}
