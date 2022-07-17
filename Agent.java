import java.util.Random;

/**
 * Citizen class
 * Creates Citizens
 * @author  Chris Litting
 * @version 2.1
 */
public class Agent {
	
	// =========
	// VARIABLES
	// =========

	private static Random random = new Random(); //used in mutation
	private static int defaultWeighting = 100; //used for all weightings when weightings are not specified
	private static int defaultTotalResources = 100; //used when totalResources is not specified
	private static int defaultMutability = 50; //used when mutability is not specified
	public static String title = "MUT\tAGT\tCOM\tSTE\tWLD"; //used for output
	
	// ======
	// FIELDS
	// ======


	private int totalResources; //how many resources an Agent has to contribute
	private int mutability; //out of 100

	private int agentWeighting; //out of 100
	private int communityWeighting; //out of 100
	private int stateWeighting; //out of 100
	private int worldWeighting; //out of 100

	private double agentContribution; //calculated from agentWeighting and totalResources
	private double communityContribution; //calculated from communityWeighting and totalResources
	private double stateContribution; //calculated from stateWeighting and totalResources
	private double worldContribution; //calculated from worldWeighting and totalResources

	private double allocation; //baseline is agentContribution, possibly modified by higher domains

	public Agent next = null; //used in AgentList (a type of Linked List)
		
	// ============
	// CONSTRUCTORS
	// ============

	/**
	 * Constructor for objects of class Agent (no values defined - default characteristics)
	 */
	public Agent() {
		//this.setMutability(random.nextInt(101));
		//this.setTotalResources(100);
		//this.setagentWeighting(random.nextInt(101));
		//this.setcommunityWeighting(random.nextInt(101));
		//this.setstateWeighting(random.nextInt(101));
		//this.setworldWeighting(random.nextInt(101));
		//this.setContributions();

		this.setMutability(defaultMutability);
		this.setTotalResources(defaultTotalResources);
		this.setagentWeighting(defaultWeighting);
		this.setcommunityWeighting(defaultWeighting);
		this.setstateWeighting(defaultWeighting);
		this.setworldWeighting(defaultWeighting);
		this.setContributions();
		this.resetAllocation();
		this.next = null;
	}
	
	/**
	 * Constructor for objects of class Agent (values defined - custom Agent)
	 * @param  mutability as an integer
	 * @param  totalResources as an integer
	 * @param  agentWeighting as an integer
	 * @param  communityWeighting as an integer
	 * @param  stateWeighting as an integer
	 * @param  worldWeighting as an integer
	 */
	public Agent(
		int mutability,	
		int totalResources, 
		int agentWeighting,
		int communityWeighting,
		int stateWeighting,
		int worldWeighting
	) {
		this.setMutability(mutability);
		this.setTotalResources(totalResources);
		this.setagentWeighting(agentWeighting);
		this.setcommunityWeighting(communityWeighting);
		this.setstateWeighting(stateWeighting);
		this.setworldWeighting(worldWeighting);
		this.setContributions();
		this.resetAllocation();
		this.next = null;	
	}

	/**
	 * Returns a clone of an existing Agent
	 * @param a1 as an Agent
	 * @return Agent
	 */
	public static Agent clone(Agent a1) {
		//System.out.println("cloning agent...");
		Agent a2 = new Agent(
			a1.getMutability(), 
			a1.getTotalResources(), 
			a1.getagentWeighting(), 
			a1.getcommunityWeighting(), 
			a1.getstateWeighting(), 
			a1.getworldWeighting()
		);
		return a2;
	}
			
	// =======
	// SETTERS
	// =======

	/**
	 * Set totalResources to a value
	 * @param  totalResources as an integer
	 */
	private void setTotalResources(int totalResources) {
		this.totalResources = totalResources;
	}

	/**
	 * Set mutability to a value
	 * @param  mutability as an integer between 0 and 100
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
	 * Set agentWeighting to a predefined percentage
	 * @param  agentWeighting as an integer between 0 and 100
	 */
	private void setagentWeighting(int agentWeighting) {
		if (agentWeighting < 0){
			this.agentWeighting = 0;
		} else if (agentWeighting > 100){
			this.agentWeighting = 100;
		} else {
			this.agentWeighting = agentWeighting;
		}
	}
	
	/**
	 * Set communityWeighting to a predefined percentage
	 * @param  communityWeighting as an integer between 0 and 100
	 */
	private void setcommunityWeighting(int communityWeighting) {
		if (communityWeighting < 0){
			this.communityWeighting = 0;
		} else if (communityWeighting > 100){
			this.communityWeighting = 100;
		} else {
			this.communityWeighting = communityWeighting;
		}
	}

	/**
	 * Set stateWeighting to a predefined percentage
	 * @param  stateWeighting as an integer between 0 and 100
	 */
	private void setstateWeighting(int stateWeighting) {
		if (stateWeighting < 0){
			this.stateWeighting = 0;
		} else if (stateWeighting > 100){
			this.stateWeighting = 100;
		} else {
			this.stateWeighting = stateWeighting;
		}
	}

	/**
	 * Set worldWeighting to a predefined percentage
	 * @param  worldWeighting as an integer between 0 and 100
	 */
	private void setworldWeighting(int worldWeighting) {
		if (worldWeighting < 0){
			this.worldWeighting = 0;
		} else if (worldWeighting > 100){
			this.worldWeighting = 100;
		} else {
			this.worldWeighting = worldWeighting;
		}
	}

	/**
	 * Set contributed resources based on weightings
	 */
	private void setContributions() {
		int totalContribution = agentWeighting + communityWeighting + stateWeighting + worldWeighting;
		this.agentContribution = agentWeighting * 1.0 / totalContribution;
		this.communityContribution = communityWeighting * 1.0 / totalContribution;
		this.stateContribution = stateWeighting * 1.0 / totalContribution;
		this.worldContribution = worldWeighting * 1.0 / totalContribution;
	}
	
	/**
	 * Sets allocation equal to agentContribution
	 */
	public void resetAllocation() {
		this.allocation = this.agentContribution;
	}

	// =======
	// GETTERS
	// =======

	/**
	 * Get Agent totalResources
	 * @return totalResources as an integer
	 */
	public int getTotalResources() {
		return this.totalResources;
	}

	/**
	 * Get Agent mutability
	 * @return mutability as an integer
	 */
	public int getMutability() {
		return this.mutability;
	}

	/**
	 * Get Agent agentWeighting
	 * @return agentWeighting as an integer
	 */
	public int getagentWeighting() {
		return this.agentWeighting;
	}

	/**
	 * Get Agent communityWeighting
	 * @return communityWeighting as an integer
	 */
	public int getcommunityWeighting() {
		return this.communityWeighting;
	}

	/**
	 * Get Agent stateWeighting
	 * @return stateWeighting as an integer
	 */
	public int getstateWeighting() {
		return this.stateWeighting;
	}

	/**
	 * Get Agent worldWeighting
	 * @return worldWeighting as an integer
	 */
	public int getworldWeighting() {
		return this.worldWeighting;
	}

	/**
	 * Get Agent agentContribution
	 * @return agentContribution as an integer
	 */
	public double getagentContribution() {
		return this.agentContribution;
	}

	/**
	 * Get Agent communityContribution
	 * @return communityContribution as an integer
	 */
	public double getcommunityContribution() {
		return this.communityContribution;
	}

	/**
	 * Get Agent stateContribution
	 * @return stateContribution as an integer
	 */
	public double getstateContribution() {
		return this.stateContribution;
	}

	/**
	 * Get Agent worldContribution
	 * @return worldContribution as an integer
	 */
	public double getworldContribution() {
		return this.worldContribution;
	}

	/**
	 * Returns Agent allocation
	 * @return allocation as a double
	 */
	public double getAllocation() {
		return this.allocation;
	}

	// =========
	// UTILITIES
	// =========

	/**
	 * Increments allocation by n
	 * @param n as a double
	 */
	public void incrementAllocation(double n) {
		this.allocation += n;
	}

	/**
	 * Creates a new Agent from an existing one
	 * @param agent as an Agent
	 * @return Agent
	 */
	public static Agent reproduce(Agent agent) {
		
		int mutability = mutate(agent.mutability, agent.mutability);
		int totalResources = 100;
		int agentWeighting = mutate(agent.agentWeighting, mutability);
		int communityWeighting = mutate(agent.communityWeighting, mutability);
		int stateWeighting = mutate(agent.stateWeighting, mutability);
		int worldWeighting = mutate(agent.worldWeighting, mutability);

		return new Agent(
			mutability,
			totalResources,
			agentWeighting,
			communityWeighting,
			stateWeighting,
			worldWeighting
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

	
	@Override
	/**
	 * toString method for Agent
	 * @return String
	 */
	public String toString() {
		return String.format(
			"%d\t%d\t%d\t%d\t%d", 
			this.mutability,
			this.agentWeighting, 
			this.communityWeighting, 
			this.stateWeighting, 
			this.worldWeighting);
	}

	// ============
	// MISC METHODS
	// ============


}