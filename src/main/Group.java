package src.main;
import java.util.ArrayList;
import java.util.Collections;

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
    private double[] averageMutabilities;   //contains average mutabilities data from Agents and Groups
    private double[] averageCapacities;     //contains average capacities data from Groups

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
        
        assert (mutability >= 0) : "Cannot create Group with mutability below 0. Mutability: " + mutability;
        assert (mutability <= Settings.getMaxMutability()) : "Cannot create Group with mutability above max mutability. Mutability: " + mutability + ". Max mutability: " + Settings.getMaxMutability();
        assert (size > 0) : "Cannot create Group with size below 1. Size: " + size;
        assert (size <= Settings.getMaxGroupSize()) : "Cannot create Group with size above max group size. Size: " + size + ". Max group size: " + Settings.getMaxGroupSize();
        assert (capacity <= size) : "Cannot create Group with capacity above size. Capacity: " + capacity + ". Size: " + size;
        
        this.setSize(size);
        this.children = new ArrayList<Entity>();
        this.averageMutabilities = new double[Settings.getGroupDepth() + 1];
        this.averageCapacities = new double[Settings.getGroupDepth()];
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
        assert (size > 0) : "Cannot set Group size below 0. Size: " + size;
        assert (size <= Settings.getMaxGroupSize()) : "Cannot set Group size above max group size. Size: " + size + ". Max group size: " + Settings.getMaxGroupSize();
        this.size = size;
    }

    /**
     * Adds child to children ArrayList
     * @param child as an Entity
     */
    public void addChild(Entity newChild) {
        assert (this.children.size() < this.size) : "Cannot add child in excess of Group size. Children: " + this.children.size() + ". Group size: " + this.size;
        this.children.add(newChild);
    }

    /**
     * Sets Group capacity (number of children that survive each iteration)
     * @param capacity as an int
     */
    public void setCapacity(int capacity) {
        assert (capacity > 0) : "Cannot set Group capacity below 1. Capacity: " + capacity;
        this.capacity = capacity;     
    }

    public void setAverageMutability(int level, double value) {
        this.averageMutabilities[level] = value;
    }

    public void incrementAverageMutability(int level, double value) {
        this.averageMutabilities[level] += value;
    }

    public void setAverageCapacity(int level, double value) {
        this.averageCapacities[level - 1] = value;
    }

    public void incrementAverageCapacity(int level, double value) {
        this.averageCapacities[level - 1] += value;
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
        assert (this.getLevel() > 1) : "Cannot get sub groups for levels below 2. Group level: " + this.getLevel();
        return Settings.getGroupSize(this.getLevel() - 1);
    }

    /**
     * Returns Group capacity
     * @return int
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Returns average mutability for a given level
     * @param level as an int
     * @return average mutability as a double
     */
    public double getAverageMutability(int level) {
        return this.averageMutabilities[level];
    }

    /**
     * Returns average capacity for a given level
     * @param level as an int
     * @return average capacity as a double
     */
    public double getAverageCapacity(int level) {
        return this.averageCapacities[level - 1];
    }

    // =======
    // METHODS
    // =======

    /**
     * Auto-populates a Group with sub-Groups or Agents (used in simulation setup)
     */
    public void populate() {
        if (this.getLevel() > 1) {
            // New Groups
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
            // New Agents
            for (int i = 0; i < this.getSize(); i++) {
                this.children.add(new Agent(
                    Settings.getInitialMutability(0), 
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
        // Recursive part
        if (this.getLevel() > 1) {
            for (Entity x : children) {
                x.gatherContributions();
            }
        }
        // Function part
        // Contributions
        for (int i = 0; i < children.size(); i++) {
            for (int j = 0; j < Settings.getGroupDepth(); j++) {
                this.incrementContribution(j, children.get(i).getContribution(j));
            }
        }
        for (int j = 0; j < Settings.getGroupDepth(); j++) {
            this.setContribution(j, this.getContribution(j) / this.children.size());
        }

        //Mutabilities
        for (int i = 0; i < children.size(); i++) {
            this.incrementAverageMutability(this.getLevel() - 1, this.children.get(i).getMutability());
            for (int j = 0; j < this.getLevel() - 1; j++) {
                this.incrementAverageMutability(j, this.children.get(j).getAverageMutability(j));
            }
        }
        for (int j = 0; j < this.getLevel(); j++) {
            this.setAverageMutability(j, this.getAverageMutability(j) / this.children.size());
        }
        this.setAverageMutability(this.getLevel(), this.getMutability());

        //Capacities
        for (int i = 1; i < this.getLevel(); i++) {
            for (int j = 0; j < this.children.size(); j++) {
                this.incrementAverageCapacity(i, this.children.get(j).getAverageCapacity(i));
            }
            this.setAverageCapacity(i, this.getAverageCapacity(i) / this.children.size());
        }
        this.setAverageCapacity(this.getLevel(), this.getCapacity());

    }

    /**
	 * Sorts Entities in descending order of self contribution (recursive method)
	 */
	public void sortChildren() {
        // Recursive part
        if (this.getLevel() > 1) {
            for (Entity y : children) {
                y.sortChildren();
            }
        }
        // Function part
        /*
		Entity[] arr = children.toArray(new Entity[children.size()]);
        mergeSort(arr);
        children.clear();
        for (Entity x : arr) {
            children.add(x);
        }
        */
        //System.out.println("level " + this.getLevel()+ " group sorting children");
        Collections.sort(children);
	}

    /**
     * Removes Entities from children until a set number remain (recursive method)
     */
    public void cullChildren() {
        // Function part
        while (children.size() > this.capacity) {
            children.remove(this.capacity);
        }

        // Recursive part
        if (this.getLevel() > 1) {
            for (Entity x : children) {
                x.cullChildren();
            }
        }
    }

    /**
     * Repopulates children from survivors (recursive method)
     */
    public void repopulate() {
        // Recursive part
        for (Entity x : children) {
            x.repopulate();
        }
        // Function part
        int diff = this.getSize() - this.capacity;
        int i = 0;
        while (diff > 0) {
            this.addChild(this.children.get(i % this.capacity).clone(this));
            diff--;
            i++;
        }
    }

    /**
     * Creates clone of existing Entity, used with repopulate method (recursive method)
     */
    public Entity clone(Group parentGroup) {
        // Function part
        Entity clone = (Entity) new Group(
            this.getLevel(), 
            this.getSize(), 
            false, 
            parentGroup,
            this.getMutability(),
            this.getCapacity()
        );
        // Recursive part
        for (Entity x : this.children) {
            clone.addChild(x.clone(this));
        }
        return clone;
    }

    /**
     * Mutates Entities according to their mutability (recursive method)
     */
    public void mutateEntity() {
        // Recursive part
        for (Entity x : children) {
            x.mutateEntity();
        }
        // Function part
        if (Settings.getMutableMutability(this.getLevel())) {
            this.setMutability(mutate(this.getMutability(), this.getMutability(), 0, Settings.getMaxMutability()));
        }
        this.setCapacity(mutate(this.getCapacity(), this.getMutability(), 1, this.getSize()));
    }

    /**
     * Sets all contributions back to zero (recursve method)
     */
    public void resetContributions() {
        // Recursive part
        if (this.getLevel() > 1){
            for (Entity x : children) {
                x.resetContributions();
            }
        }
        // Function part
        for (int i = 0; i < this.getContributions().length; i++) {
            this.setContribution(i, 0.0);
        }
        for (int i = 0; i < this.averageMutabilities.length; i++) {
            this.setAverageMutability(i, 0);
        }
        for (int i = 0; i < this.averageCapacities.length; i++) {
            this.setAverageCapacity(i + 1, 0);
        }
    }

    /**
     * REPORT
     */
    public String report() {
        return this.toString(); 
    }

    /**
     * Checks if this Group is identical to another, in all aspects except parentGroup and children
     * @param group as a Group
     * @return boolean
     */
    public boolean equals(Group group) {    
        if (
            this.getLevel() == group.getLevel()
            && this.getMutability() == group.getMutability()
            && this.getSize() == group.getSize()
            && this.getCapacity() == group.getCapacity()
        ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String str = "";

        str += doubleArrayToString(this.getContributions());
        
        str += doubleArrayToString(this.averageMutabilities);
        
        str += doubleArrayToString(this.averageCapacities);

        return str;
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

    /**
     * 
     * @param d
     * @return
     */
    public String doubleArrayToString(double[] d) {
        String str = "";
        for (double x : d) {
            str += String.format("%.2f ", x);
        }
        return str;
    }

}
