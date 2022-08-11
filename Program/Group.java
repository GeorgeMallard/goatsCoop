package Program;
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
    //private int mutability                (inherited from Entity)
    private int size;                       //number of child Agents or Groups this Group can contain
    private int capacity;                   //number of child Agents or Groups that will survive each iteration
    private ArrayList<Entity> children;     //contains child Agents or Groups, depending on level

    // ===========
    // CONSTRUCTOR
    // ===========

    /**
     * Constructor for Group
     * @param level as an int
     * @param size as an int
     * @param populate as a boolean (indicates whether group should be auto populated - used when sim is first started)
     */
    public Group(int level, int size, boolean populate, Group parentGroup, int mutability, int capacity) {
        super(level, parentGroup, mutability);
        this.setSize(size);
        this.children = new ArrayList<Entity>();
        if (populate) {
            this.populate();
        }
        if (capacity > size) {
            this.setCapacity(size);
        } else {
            this.setCapacity(capacity);
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

    /**
     * Adds child to children ArrayList
     * @param child as an Entity
     */
    public void addChild(Entity newChild) {
        this.children.add(newChild);
    }

    /**
     * Sets Group capacity (number of children that survive each iteration)
     * @param capacity as an int
     */
    public void setCapacity(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        } else {
            this.capacity = 1;
        }    
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

    /**
     * Returns Group capacity
     * @return int
     */
    public int getCapacity() {
        return this.capacity;
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
                    this,
                    Settings.getInitialMutability(this.getLevel() - 1),
                    Settings.getGroupCapacity(this.getLevel() - 1)
                ));
            }
        } else {
            for (int i = 0; i < this.getSize(); i++) {
                this.children.add(new Agent(
                    Settings.getInitialMutability(0), 
                    Settings.getMutableMutability(0),
                    Settings.getAgentInitialWeightings(),
                    this
                ));
            }
        }   
    }

    /**
     * Counts tokens assigned by Agents to this and higher level Groups (recursive method)
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
    }

    /**
	 * Sorts Entities in descending order of self contribution (recursive method)
	 */
	public void sortChildren() {
        if (this.getLevel() > 1) {
            for (Entity y : children) {
                y.sortChildren();
            }
        }
		Entity[] arr = children.toArray(new Entity[children.size()]);
        mergeSort(arr);
        children.clear();
        for (Entity x : arr) {
            children.add(x);
        }
	}

    /**
     * Removes Entities from children until a set number remain (recursive method)
     */
    public void cullChildren() {
        while (children.size() > this.capacity) {
            children.remove(this.capacity);
        }
        if (this.getLevel() > 1) {
            for (Entity x : children) {
                x.cullChildren();
            }
        }
    }

    /**
     * Creates clone of existing Entity (recursive method)
     */
    public Entity clone(Group parentGroup) {
        Entity clone = (Entity) new Group(
            this.getLevel(), 
            this.getSize(), 
            false, 
            parentGroup,
            this.getMutability(),
            this.getCapacity()
        );
        for (Entity x : this.children) {
            clone.addChild(x.clone(this));
        }
        return clone;
    }

    /**
     * Repopulates children from survivors (recursive method)
     */
    public void repopulate() {
        for (Entity x : children) {
            x.repopulate();
        }
        int diff = this.getSize() - this.capacity;
        int i = 0;
        while (diff > 0) {
            this.addChild(this.children.get(i % this.capacity).clone(this));
            diff--;
            i++;
        }
    }

    /**
     * Mutates Entities according to their mutability (recursive function)
     */
    public void mutateEntity() {
        for (Entity x : children) {
            x.mutateEntity();
        }
        int newCapacity = mutate(this.getCapacity(), this.getMutability(), 0, 100);
        if (newCapacity <= this.getSize()) {
            this.setCapacity(newCapacity);
        }
    }

    /**
     * Sets all contributions back to zero
     */
    public void resetContributions() {
        if (this.getLevel() > 1){
            for (Entity x : children) {
                x.resetContributions();
            }
        }
        for (int i = 0; i < this.getContributions().length; i++) {
            this.setContribution(i, 0.0);
        }
    }

    /**
     * Causes Group and all subGroups to produce a report
     */
    public void report() {
        for (Entity x : children) {
            x.report();
        }
        System.out.println("Level " + this.getLevel() + " Group reporting! Size: " + this.getSize() + " Capacity: " + this.getCapacity());
    }

    /**
     * Checks if this Group is identical to another, in all aspects except parentGroup and children
     * @param group as a Group
     * @return boolean
     */
    public boolean equals(Group group) {
        boolean contributionsCheck = true;
        for (int i = 0; i < this.getContributions().length; i++) {
            if (this.getContribution(i) != group.getContribution(i)) {
                contributionsCheck = false;
                break;
            }
        }      
        if (
            this.getLevel() == group.getLevel()
            && this.getMutability() == group.getMutability()
            && this.getSize() == group.getSize()
            && this.getCapacity() == group.getCapacity()
            && contributionsCheck
        ) {
            return true;
        } else {
            return false;
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
