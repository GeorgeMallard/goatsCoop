import java.util.Random;

/**
 * Agent class
 * Creates Agents
 * @author  Chris Litting
 * @version 3.0
 */
public class Agent {
	
	// =========
	// VARIABLES
	// =========

	private static Random random = new Random(); //used in mutation
	//public static String title = "MUT\tAGT\tCOM\tSTE\tWLD"; //used for output
	
	// ======
	// FIELDS
	// ======

	private int mutability;
	private boolean mutableMutability;
	private int[] weightings;
	private double[] contributions;

	// ===Deprecated===
	public Agent next = null;
	
		
	// ============
	// CONSTRUCTORS
	// ============

	/**
	 * Constructor for Agent class
	 * @param mutability as an int
	 * @param weightings as an int array
	 */
	public Agent(int mutability, boolean mutableMutability, int[] weightings) {
		System.out.println("Creating Agent...");
		this.setMutability(mutability);
		this.setMutableMutability(mutableMutability);
		this.setWeightings(weightings);
		calculateContributions(weightings);
	}

	/**
	 * Returns a clone of an existing Agent
	 * @param agent1 as an Agent
	 * @return Agent
	 */
	public static Agent clone(Agent agent) {
		//System.out.println("cloning agent...");
		return new Agent(
			agent.getMutability(),
			agent.getMutableMutability(),
			agent.getWeightings()
		);
	}
			
	// =======
	// SETTERS
	// =======

	private void setMutability(int mutability) {
		if (mutability < 0){
			this.mutability = 0;
		} else if (mutability > 100){
			this.mutability = 100;
		} else {
			this.mutability = mutability;
		}
	}

	private void setMutableMutability(boolean bool) {
		this.mutableMutability = bool;
	}

	private void setWeightings(int[] weightings) {
		this.weightings = new int[weightings.length];
		for (int i = 0; i < weightings.length; i++) {
			this.weightings[i] = weightings[i];
		}
	}

	private void calculateContributions(int[] weightings) {
		this.contributions = new double[weightings.length];
		int total = 0;
		for (int x : weightings) {
			total += x;
		}
		for (int i = 0; i < weightings.length; i++) {
			this.contributions[i] = (total * 1.0) / weightings[i];
		}
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

	/**
	 * Get contributions
	 * @return contributions as a double array
	 */
	public double[] getContributions() {
		return this.contributions;
	}

	/**
	 * Gets the contribution for a particular group level
	 * @param level as an int
	 * @return contribution as a double
	 */
	public double getContribution(int level) {
		return this.contributions[level];
	}

	// =========
	// UTILITIES
	// =========

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
			weightings
		);
	}

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

	// ============
	// MISC METHODS
	// ============


}