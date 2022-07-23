import java.util.ArrayList;

public class GroupGroup extends Group {
   
    // FIELDS
    // ======
    // private int level; (inherited)
    // private int size;  (inherited)
    public ArrayList<Group> groups; 

    // CONSTRUCTOR
    // ===========
    public GroupGroup(int level, int size, boolean populate) {
        super(level, size, populate);
    }

    // METHODS
    // =======
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
