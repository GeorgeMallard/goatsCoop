import java.util.ArrayList;

/**
 * Group class
 * Groups contain either Agents or other Groups
 * @author  Chris Litting
 * @version 1.0
 */
public class Group extends Entity {
    
    // ======
    // FIELDS
    // ======

    //private int level 					(inherited from Entity)
	//private Group parentGroup 			(inherited from Entity)
	//private double[] contributions 		(inherited from Entity)
    private int size;                       //number of Agents or Groups this Group can contain
    private ArrayList<Entity> children;     //contains either Groups or Agents
    
    // ===========
    // CONSTRUCTOR
    // ===========

    /**
     * Constructor for Group
     * @param level as an int
     * @param size as an int
     * @param populate as a boolean (indicates whether group should be auto populated - used when sim is first started)
     */
    public Group(int level, int size, boolean populate, Group parentGroup) {
        super(level, parentGroup);
        System.out.println("Creating Level " + level + " group...");
        this.setSize(size);
        this.children = new ArrayList<Entity>();
        if (populate) {
            this.populate();
        }
    }

    // =======
    // SETTERS
    // =======

    /**
     * Sets group size
     * @param size as an int
     */
    public void setSize(int size) {
        this.size = size;
    }

    // =======
    // GETTERS
    // =======

    /**
     * Returns Group size
     * @return int
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Returns size of child Group
     * @return int
     */
    public int getSubGroupSize() {
        if (this.getLevel() > 1) {
            return Settings.getGroupSize(this.getLevel() - 1);
        } else {
            return 0;
        }
    }

    // =======
    // METHODS
    // =======

    /**
     * Auto-populates a Group with sub-Groups or Agents (used in simulation setup)
     */
    public void populate() {
        if (this.getLevel() > 1) {
            for (int i = 0; i < this.getSize(); i++) {
                this.children.add(new Group(
                    this.getLevel() - 1, 
                    this.getSubGroupSize(), 
                    true, 
                    this
                ));
            }
        } else {
            for (int i = 0; i < this.getSize(); i++) {
                this.children.add(new Agent(
                    Settings.getAgentInitialMutability(), 
                    Settings.getAgentMutableMutability(),
                    Settings.getAgentInitialWeightings(),
                    this
                ));
            }
        }   
    }

    /**
     * Counts tokens assigned by Agents to this and higher level Groups
     */
    public void gatherContributions() {

        if (this.getLevel() > 1) {
            for (Entity x : children) {
                x.gatherContributions();
            }
        }

        for (int i = 0; i < children.size(); i++) {
            for (int j = 0; j < Settings.getGroupDepth() - this.getLevel(); j++) {
                this.incrementContribution(j, children.get(i).getContribution(j + 1));
            }
        }
        System.out.println("Level " + this.getLevel() + " Group reporting: " + convert(this.getContributions()));
    }

    /**
     * Sorts children for all Groups
     */
    public void sortAll() {
        if (this.getLevel() > 1) {
            for (Entity y : children) {
                y.sortChildren();
            }
        }
        this.sortChildren();
    }

    /**
	 * Sorts Entities in descending order of self contribution
	 */
	public void sortChildren() {
		Entity[] arr = children.toArray(new Entity[children.size()]);
        mergeSort(arr);
        children.clear();
        for (Entity x : arr) {
            children.add(x);
        }
	}


    // ===============
    // UTILITY METHODS
    // ===============

    /**
	 * Merge Sort algorithm
	 * @param  A as an array of Entities
	 * @return array of Entities (sorted by self contribution)
	 */
	public Entity[] mergeSort(Entity[] A){
		int n = A.length;
		if (n <= 1){
			return A;
		} else {
			int m = n/2;
			Entity[] B = copyArray(A, 0, m-1);
			Entity[] C = copyArray(A, m, n-1);
			mergeSort(B);
			mergeSort(C);
			merge(B, C, A);
			return A;
		}
	}
	
	/**
	 * Merge two subarrays into a third, longer array 
	 * (used in mergeSort)
	 * @param  sub1 as an array of Agents
	 * @param  sub2 as an array of Agents
	 * @param  A as an array of Agents
	 * @return array of Agents (sorted by self contribution)
	 */
	public static Entity[] merge(Entity[] sub1, Entity[] sub2, Entity[] A){
		int ptr1 = 0;
		int ptr2 = 0;
		int ptrA = 0;
		int nA = A.length;
		int n1 = sub1.length;
		int n2 = sub2.length;
		
		while (ptrA < nA){
			if (ptr1 < n1 && ptr2 >= n2){
				A[ptrA] = sub1[ptr1];
				ptr1++;
			} else if (ptr2 < n2 && ptr1 >= n1){
				A[ptrA] = sub2[ptr2];
				ptr2++;
			} else if (sub1[ptr1].getSelfContribution() > sub2[ptr2].getSelfContribution()){
				A[ptrA] = sub1[ptr1];
				ptr1++;
			} else {
				A[ptrA] = sub2[ptr2];
				ptr2++;
			}
			ptrA++;
		}
		return A;		
	}

	/**
	 * Copy part of an array 
	 * (used in mergeSort)
	 * @param  A as an array of Agents
	 * @param  start index as an int
	 * @param  end index as an int
	 * @return array of Agents
	 */
	public static Entity[] copyArray(Entity[] A, int start, int end){
		int n = end - start + 1;
		Entity[] B = new Entity[n];
		for (int i = 0; i < n; i++){
			B[i] = A[i + start];
		}
		return B;
	}




    //USED FOR TESTING PURPOSES - DELETE LATER
    public String convert(double[] d) {
        String str = "";
        for (double x : d) {
            str += Double.toString(x) + ", ";
        }
        return str;
    }


}
