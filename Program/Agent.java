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
		assert (weightings.length > 1) : "Cannot create Agent with fewer than 2 weightings";
		assert (weightings.length == Settings.getGroupDepth()) : "Incorrect number of weightings";
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

		assert (weightings.length > 1) : "Agent has fewer than 2 weightings";

		this.weightings = new int[weightings.length];
		for (int i = 0; i < weightings.length; i++) {
			assert (weightings[i] >= Settings.getMinAgentWeighting()) : "Weighting below minimum weighting value";
			assert (weightings[i] <= Settings.getMaxAgentWeighting()) : "Weighting above maximum weighting value";
			this.weightings[i] = weightings[i];
		}
	}

	/**
	 * Calculates Agent contributions based on weightings
	 * @param weightings as an int array
	 */
	private void calculateContributions(int[] weightings) {

		assert (weightings.length > 1) : "Agent has fewer than 2 weightings";
		assert (this.getWeightings().length == this.getContributions().length) : "Weightings length and contributions length do not match";

		int total = 0;
		for (int x : weightings) {
			assert (x >= Settings.getMinAgentWeighting()) : "Weighting below minimum weighting value";
			assert (x <= Settings.getMaxAgentWeighting()) : "Weighting above maximum weighting value";
			total += x;
		}
		for (int i = 0; i < weightings.length; i++) {
			this.setContribution(i, (total * 1.0) / weightings[i]);
		}
	}

	public void setWeighting(int level, int value) {
		assert (level >= 0) : "Attempting to access weighting for level below zero";
		assert (level < this.getWeightings().length) : "Attempting to access weighting for level that exceeds the highest available level";
		assert (value >= Settings.getMinAgentWeighting()) : "Attempting to set weighting below minimum weighting value";
		assert (value <= Settings.getMaxAgentWeighting()) : "Attempting to set weighting above maximum weighting value";
		this.weightings[level] = value;
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
		assert (level >= 0) : "Attempting to access weighting for level below zero";
		assert (level < this.getWeightings().length) : "Attempting to access weighting for level that exceeds the highest available level";
		
		return this.weightings[level];
		
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
            this.setMutability(mutate(this.getMutability(), this.getMutability(), 0, Settings.getMaxMutability()));
        }
		for (int i = 0; i < this.getWeightings().length; i++) {
			this.setWeighting(i, mutate(this.getWeighting(i), this.getMutability(), 0, Settings.getMaxMutability()));
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

	/**
     * Checks if this Agent is identical to another, in all aspects except parentGroup
     * @param agent as an Agent
     * @return boolean
     */
    public boolean equals(Agent agent) {
		assert (this.getWeightings().length == agent.getWeightings().length) : "Cannot compare agents with different weighting lengths";
        for (int i = 0; i < this.getWeightings().length; i++) {
            if (this.getWeighting(i) != agent.getWeighting(i)) {
                return false;
            }
        }      
        return (this.getMutability() == agent.getMutability()) ? true : false;
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