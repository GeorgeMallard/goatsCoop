package src.main;
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
		assert (weightings.length > 1) : "Cannot create Agent with fewer than 2 weightings. Current weightings: " + weightings.length;
		assert (weightings.length == Settings.getGroupDepth()) : "Cannot create Agent with incorrect number of weightings. Current weightings: " + weightings.length + ". Group depth: " + Settings.getGroupDepth();
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

		assert (weightings.length > 1) : "Cannot set Agent with fewer than 2 weightings. Current weightings: " + weightings.length;
		assert (weightings.length == Settings.getGroupDepth()) : "Cannot set Agent with incorrect number of weightings. Current weightings: " + weightings.length + ". Group depth: " + Settings.getGroupDepth();

		this.weightings = new int[weightings.length];
		for (int i = 0; i < weightings.length; i++) {
			assert (weightings[i] >= Settings.getMinAgentWeighting()) : "Cannot set weighting below minimum weighting value. Weighting: " + weightings[i] + ". Min weighting: " + Settings.getMinAgentWeighting();
			assert (weightings[i] <= Settings.getMaxAgentWeighting()) : "Cannot set weighting above maximum weighting value. Weighting: " + weightings[i] + ". Max weighting: " + Settings.getMaxAgentWeighting();
			this.weightings[i] = weightings[i];
		}
	}

	/**
	 * Calculates Agent contributions based on weightings
	 * @param weightings as an int array
	 */
	private void calculateContributions(int[] weightings) {

		assert (weightings.length > 1) : "Cannot calculate contributions for fewer than 2 weightings. Current weightings: " + weightings.length;
		assert (this.getWeightings().length == this.getContributions().length) : "Cannot calculate contributions when weightings length and contributions length do not match. Weightings length: " + weightings.length + ". Contributions length: " + this.getContributions().length;

		int total = 0;
		for (int x : weightings) {
			assert (x >= Settings.getMinAgentWeighting()) : "Weighting below minimum weighting value. Weighting: " + x + ". Min weighting: " + Settings.getMinAgentWeighting();
			assert (x <= Settings.getMaxAgentWeighting()) : "Weighting above maximum weighting value. Weighting: " + x + ". Max weighting: " + Settings.getMaxAgentWeighting();
			total += x;
		}
		if (total == 0) {
			total = 1;
		}
		for (int i = 0; i < weightings.length; i++) {
			this.setContribution(i, weightings[i] / (total * 1.0));
		}
	}

	public void setWeighting(int level, int value) {
		assert (level >= 0) : "Cannot set weighting for level below 0. Level: " + level;
		assert (level < this.getWeightings().length) : "Cannot set weighting for level above highest available level. Level: " + level + ". Highest available level: " + this.getWeightings().length;
		assert (value >= Settings.getMinAgentWeighting()) : "Cannot set weighting below minimum weighting value. Weighting: " + value + ". Min weighting: " + Settings.getMinAgentWeighting();
		assert (value <= Settings.getMaxAgentWeighting()) : "Cannot set weighting above maximum weighting value. Weighting: " + value + ". Max weighting: " + Settings.getMaxAgentWeighting();
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
		assert (level >= 0) : "Cannot get weighting for level below 0. Level: " + level;
		assert (level < this.getWeightings().length) : "Cannot get weighting for level above highest available level. Level: " + level + ". Highest available level: " + this.getWeightings().length;
		
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
		this.calculateContributions(this.getWeightings());
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

	public double getAverageMutability(int level) {return 0.0;}
	public double getAverageCapacity(int level) {return 0.0;}

	/**
     * Checks if this Agent is identical to another, in all aspects except parentGroup
     * @param agent as an Agent
     * @return boolean
     */
    public boolean equals(Agent agent) {
		assert (this.getWeightings().length == agent.getWeightings().length) : "Cannot compare Agents with different weighting lengths. This Agent weightings length: " + this.getWeightings().length + ". Comparison Agent weightings length: " + agent.getWeightings().length;
        for (int i = 0; i < this.getWeightings().length; i++) {
            if (this.getWeighting(i) != agent.getWeighting(i)) {
                return false;
            }
        }      
        return (this.getMutability() == agent.getMutability()) ? true : false;
    }

	
}
