package Program;
/**
 * Agent class
 * Creates Agents
 * @author  Chris Litting
 * @version 3.0
 */
public class Agent extends Entity {
	
	// =========
	// VARIABLES
	// =========
	
	// ======
	// FIELDS
	// ======

	//private int level 					(inherited from Entity)
	//private Group parentGroup 			(inherited from Entity)
	//private double[] contributions 		(inherited from Entity)
	//private int mutability;				(inherited from Entity)
	private int[] weightings;				//indicates how the Agent prioritises different group levels
	
	// ===Deprecated===
	public Agent next = null;				//used in conjunction with AgentList class, previously used when migrating Agents (deprecated feature)
			
	// ===========
	// CONSTRUCTOR
	// ===========

	/**
	 * Constructor for Agent class
	 * @param mutability as an int
	 * @param weightings as an int array
	 */
	public Agent(int mutability, int[] weightings, Group parentGroup) {
		super(0, parentGroup, mutability);
		this.setWeightings(weightings);
		calculateContributions(weightings);
	}
		
	// =======
	// SETTERS
	// =======

	/**
	 * Sets Agent weightings
	 * @param weightings as an int array
	 */
	private void setWeightings(int[] weightings) {
		this.weightings = new int[weightings.length];
		for (int i = 0; i < weightings.length; i++) {
			this.weightings[i] = weightings[i];
		}
	}

	/**
	 * Calculates Agent contributions based on weightings
	 * @param weightings as an int array
	 */
	private void calculateContributions(int[] weightings) {
		int total = 0;
		for (int x : weightings) {
			total += x;
		}
		for (int i = 0; i < weightings.length; i++) {
			this.setContribution(i, (total * 1.0) / weightings[i]);
		}
	}

	// =======
	// GETTERS
	// =======

	/**
	 * Get weightings
	 * @return weightings as an int array
	 */
	public int[] getWeightings() {
		return this.weightings;
	}

	/**
	 * Get the weighting for a particular group level
	 * @return weighting as an int
	 */
	public int getWeighting(int level) {
		if (level >= 0 && level < weightings.length) {
			return this.weightings[level];
		} else {
			return -1;
		}
	}

	// =======
	// METHODS
	// =======

	/**
	 * Returns a clone of an existing Agent
	 * @param agent1 as an Agent
	 * @return Agent
	 */
	public Entity clone(Group parentGroup) {
		return (Entity) new Agent(
			this.getMutability(),
			this.getWeightings(),
			parentGroup
		);
	}

	/**
	 * Mutates Agent properties
	 */
	public void mutateEntity() {
		if (Settings.getMutableMutability(this.getLevel())) {
            this.setMutability(mutate(this.getMutability(), this.getMutability(), 0, 100));
        }
		for (int i = 0; i < this.weightings.length; i++) {
			this.weightings[i] = mutate(this.weightings[i], this.getMutability(), 0, 100);
		}
	}

	public void report() {
		System.out.println("Agent reporting! Current weightings: " + intArrToString(this.weightings));
	}

	public void gatherContributions() {
		//intentionally left blank
	}

	public void sortChildren() {
		//intentionally left blank
	}

	public void cullChildren() {
		//intentionally left blank
	}

	public void repopulate() {
		//intentionally left blank
	}

	public void addChild(Entity newChild) {
		//intentionally left blank
	}

	public void resetContributions() {
		//intentionally left blank
	}

	// ======
	// OUTPUT
	// ======

	//@Override
	/**
	 * toString method for Agent
	 * @return String
	 */
	/*
	public String toString() {
		return String.format(
			"%d\t%d\t%d\t%d\t%d", 
			this.mutability,
			this.agentWeighting, 
			this.communityWeighting, 
			this.stateWeighting, 
			this.worldWeighting);
	}
	*/

	public static String intArrToString(int[] arr) {
		String str = "";
		for (int x : arr) {
			str += x + " ";
		}
		return str;
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