import java.util.ArrayList;

public class GroupGroup extends Group {
   
    // FIELDS
    // ======
    // private int level; (inherited)
    // private int size;  (inherited)
    public ArrayList<Group> groups; 

    // CONSTRUCTOR
    // ===========
    public GroupGroup(int level, int size) {
        super(level, size);
        this.populate();
    }

    // METHODS
    // =======
    public void populate() {
        int subGroupSize = Settings.getGroupSize(this.getLevel() - 2);
        if (this.getLevel() == 2) {
            for (int i = 0; i < this.getSize(); i++) {
                this.groups.add(new AgentGroup(1, subGroupSize));
            }
        } else {}
        
    }
}
