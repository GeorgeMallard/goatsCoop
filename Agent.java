import java.util.Random;

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

	private static Random random = new Random(); 				//used in mutation
	//public static String title = "MUT\tAGT\tCOM\tSTE\tWLD"; 	//used for output
	
	// ======
	// FIELDS
	// ======

	//private int level 					(inherited from Entity)
	//private Group parentGroup 			(inherited from Entity)
	//private double[] contributions 		(inherited from Entity)
	private int mutability;					//indicates % chance that mutation will occur for any given property upon reproduction
	private boolean mutableMutability;		//indicates whether mutability is itself a mutable property
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
	public Agent(int mutability, boolean mutableMutability, int[] weightings, Group parentGroup) {
		super(0, parentGroup);
		System.out.println("Creating Agent...");
		this.setMutability(mutability);
		this.setMutableMutability(mutableMutability);
		this.setWeightings(weightings);
		calculateContributions(weightings);
	}
		
	// =======
	// SETTERS
	// =======

	/**
	 * Sets Agent mutability
	 * @param mutability as an int
	 */
	private void setMutability(int mutability) {
		if (mutability < 0){
			this.mutability = 0;
		} else if (mutability > 100){
			this.mutability = 100;
		} else {
			this.mutability = mutability;
		}
	}

	/**
	 * Sets whether mutability is itself mutable
	 * @param bool as a boolean
	 */
	private void setMutableMutability(boolean bool) {
		this.mutableMutability = bool;
	}

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
		//System.out.println("Agent contributions: " + convert(this.getContributions()));
	}

	// =======
	// GETTERS
	// =======

	/**
	 * Get Agent mutability
	 * @return mutability as an integer
	 */
	public int getMutability() {
		return this.mutability;
	}

	/**
	 * Get whether mutability is itself mutable
	 * @return boolean
	 */
	public boolean getMutableMutability() {
		return this.mutableMutability;
	}

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
		//System.out.println("cloning agent...");
		return (Entity) new Agent(
			this.getMutability(),
			this.getMutableMutability(),
			this.getWeightings(),
			parentGroup
		);
	}

	/**
	 * Creates a new Agent from an existing one
	 * @param agent as an Agent
	 * @return Agent
	 */
	public static Agent reproduce(Agent agent) {
		
		int mutability = agent.mutableMutability ? mutate(agent.mutability, agent.mutability) : agent.mutability;
		boolean mutableMutability = agent.mutableMutability;
		int[] weightings = new int[agent.weightings.length];
		for (int i = 0; i < agent.weightings.length; i++) {
			weightings[i] = mutate(agent.weightings[i], mutability);
		}
		return new Agent(
			mutability,
			mutableMutability,
			weightings,
			agent.getParentGroup()
		);
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

	// =========
	// UTILITIES
	// =========

	/**
	 * Mutates a value up or down based on mutationFactor
	 * @param value as an int
	 * @param mutationFactor as an int
	 * @return int
	 */
	public static int mutate(int value, int mutationFactor) {
		if (random.nextInt(101) < mutationFactor) {
			if (value == 0) {
				value++;
			} else if (value == 100) {
				value--;
			} else {
				value = random.nextInt(2) > 0 ? value + 1 : value - 1;
			}
		}
		return value;
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