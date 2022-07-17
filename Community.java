//import java.util.Random;

/**
 * Community class
 * Creates Communities that hold Agents
 * @author  Chris Litting
 * @version 2.0
 */
public class Community {
	
	// =========
	// VARIABLES
	// =========
	//private static Random random = new Random(); //currently not used?
	public static String title = "POP\tMUT\tAGT\tCMY\tSTE\tWLD"; //used for output
	public static int defaultSize = 20; //default size for Communities when not specified
	public static int defaultCarryingCapacity = defaultSize / 2;
	public static double defaultResources = 0;
	
	// ======
	// FIELDS
	// ======
	private int size; //population cap (how many Agents can be accommodated)
	private int population = 0; //population size
	private int carryingCapacity; //calculated every iteration, determines how many Agents survive the round
	//private double allocation; //allocated by the State every iteration
	public Agent[] agents; //list of Agents accommodated
	//public AgentList migrationList; //used for excess Agents after repopulation
	public double resources = defaultResources;

	// ============
	// CONSTRUCTORS
	// ============
	
	/**
	 * Constructor for objects of class Community with no values defined. Can be populated or not
	 */
	public Community(boolean populate){
		this.setSize(defaultSize);
		if (populate) {
			this.populateCommunity();
		}
		//this.setAllocation(0.0);
		this.setCarryingCapacity(defaultCarryingCapacity);
		//this.migrationList = new AgentList();
	}

	/**
	 * Constructor for objects of class Community (values defined - custom Community)
	 */
	/*
	public Community(int size){
		this.setSize(size);
		this.populateCommunity();
		this.setAllocation(0.0);
		this.setCarryingCapacity();
		this.migrationList = new AgentList();
	}
	*/

	// =======
	// SETTERS
	// =======
	
	/**
	 * Sets default size for all Communities (must be even)
	 * @param size as an int
	 */
	public static void setDefaultSize(int size) {
		//System.out.println("default Community size set to " + n);
		if (size % 2 != 0) {
			size++;
		}
		defaultSize = size;
		defaultCarryingCapacity = size / 2;
	}

	/**
	 * Sets default resources for all Communities --not really used now
	 * @param n
	 */
	public static void setDefaultResources(double n) {
		defaultResources = n;
	}


	/**
	 * Set Community size
	 * @param  size as an integer
	 */
	private void setSize(int size){
		this.size = size;
		agents = new Agent[size];
	}

	/**
	 * Sets Community carryingCapacity
	 * @param n as a double
	 */
	public void setCarryingCapacity(int carryCap) {
		if (carryCap > this.size) {
			this.carryingCapacity = this.size;
		} else if (carryCap < 0) {
			this.carryingCapacity = 0;
		}
		this.carryingCapacity = carryCap;
	}

	/**
	 * Sets allocation
	 * @param n as a double
	 */
	/*
	public void setAllocation(double n) {
		this.allocation = n;
	}
	*/

	// =======
	// GETTERS
	// =======

	/**
	 * Get Community size
	 * @return Community size as an integer
	 */
	public int getSize(){
		return this.size;
	}

	/**
	 * Get Community carryingCapacity
	 * @return carryingCapacity as an int
	 */
	public int getCarryingCapacity() {
		return this.carryingCapacity;
	}

	/**
	 * Returns Community population
	 * @return population as an int
	 */
	public int getPopulation() {
		return this.population;
	}

	/**
	 * Returns allocation
	 * @return allocation as a double
	 */
	/*
	public double getAllocation() {
		return this.allocation;
	}
	*/

	// =========
	// UTILITIES
	// =========
	
	// ADDING AND REMOVING AGENTS ========================================

	/**
	 * Populates Community with default Agents
	 */
	private void populateCommunity(){
		for (int i = 0; i < this.size; i++){
			this.addAgent(new Agent());
		}
	}
		
	/**
	 * Adds an Agent to agents ArrayList 
	 * @param  agent as a Agent
	 */
	public void addAgent(Agent agent) {
		if (this.population < this.size) {
			this.agents[this.population] = agent;
			this.population++;
			//System.out.println("agent added! population " + this.population);
		} else {
			System.out.println("something went wrong. we're trying to add an Agent to a full Community");
		}
	}
	
	/**
	 * Removes an Agent from agents ArrayList 
	 * @param  index as an integer
	 */
	public void removeAgent(int i) {
		if (this.agents[i] != null) {
			this.agents[i] = null;
			this.population--;
			//System.out.println("agent removed! population " + this.population);
		}
	}

	// COMMUNITY INFO =====================================================

	/**
	 * Returns total communityContribution from all Agents
	 * @return contribution as a double
	 */
	public double getCommunityContribution() {
		double total = 0;
		for (int i = 0; i < agents.length; i++) {
			if (agents[i] != null) {
				total += agents[i].getcommunityContribution();
			}
		}
		return total;
	}

	// ITERATING ========================================================

	/**
	 * Performs one iteration
	 * @param n (number of iterations) as an int
	 */
	/*
	public void iterate(int n) {
		for (int i = 0; i < n; i++) {
			//setCarryingCapacity();
			//communityAllocation();
			sortAgents();
			cullAgents();
			resetAgentResources();
			repopulateAgents();
		}
	}
	*/

	// ALLOCATING RESOURCES TO AGENTS
	
	/**
	 * Allocates resources to Agents --not really used now
	 */
	public void communityAllocation() {
		for (int i = 0; i < this.population; i++) {
			agents[i].incrementAllocation((agents[i].getcommunityContribution() * this.resources / this.getCommunityContribution()) );
		}
	}
	
	/**
	 * Sets Agent resources back to their starting value, equal to agentContribution
	 */
	public void resetAgentResources() {
		for (int i = 0; i < this.population; i++) {
			agents[i].resetAllocation();
		}
	}
	
	// CULLING AGENTS ===================================================

	/**
	 * Removes all Agents that exceed the carryingCapacity of the Community
	 */
	public void cullAgents() {
		//System.out.println("culling agents! population: " + this.population + " carryingCapacity: " + this.carryingCapacity);
		communityAllocation();
		sortAgents();
		for (int i = this.carryingCapacity; i < this.agents.length; i++) {
			this.removeAgent(i);
		}
		resetAgentResources();
	}

	// REPOPULATING AGENTS ==============================================

	/**
	 * Allows Agents to reproduce and repopulate the Community. (Excess Agents are added to migrationList)--deprecated
	 */
	public void repopulateAgents() {
		//System.out.println("repopulating! population: " + this.population);
		int i = 0;
		int n = this.population; //total number of Agents to reproduce, equal to population at beginning of process
		while (i < n && i + n < this.size) {
			addAgent(Agent.reproduce(agents[i]));
			i++;
		}
		/*
		while (i < n) {
			migrationList.appendAgent(Agent.reproduce(agents[i]));
			i++;
		}
		*/
		//System.out.println("finished repopulating! population: " + this.population);
	}

	// SORTING AGENTS BY AGENT CONTRIBUTION =============================

	/**
	 * Sorts agents in descending order of agentContribution
	 */
	public void sortAgents() {
		Agent[] pop = copyArray(this.agents, 0, this.population - 1);
		mergeSort(pop);
		for (int i = 0; i < pop.length; i++) {
			this.agents[i] = pop[i];
		}
	}

	// CLONING COMMUNITIES

	/**
	 * Creates a copy of an existing Community
	 * @param c1 as a Community
	 * @return Community
	 */
	public static Community clone(Community c1) {
		Community c2 = new Community(false);
		for (int i = 0; i < c1.getPopulation(); i++) {
			c2.addAgent(Agent.clone(c1.agents[i]));
		}
		return c2;
	}

	// ======
	// OUTPUT
	// ======

	
	@Override
	/**
	 * Returns info on Community (mostly Agent averages)
	 * @return info as a String
	 */
	 public String toString() {

		if (this.population <= 0) {
			return "0";
		}

		//System.out.println("calculating info...");
		int mutability = 0; 
		int agentWeighting = 0; 
		int communityWeighting = 0; 
		int stateWeighting = 0; 
		int worldWeighting = 0; 

		for (int i = 0; i < agents.length; i++) {
			if (agents[i] != null) {
				mutability += agents[i].getMutability(); 
				agentWeighting += agents[i].getagentWeighting(); 
				communityWeighting += agents[i].getcommunityWeighting(); 
				stateWeighting += agents[i].getstateWeighting(); 
				worldWeighting += agents[i].getworldWeighting();
			}
		}

		double aveMutability = mutability * 1.0 / this.population;
		//System.out.println("aveMutability: " + aveMutability + " / population: " + this.population);
		double aveagentWeighting = agentWeighting * 1.0 / this.population;
		double avecommunityWeighting = communityWeighting * 1.0 / this.population;
		double avestateWeighting = stateWeighting * 1.0 / this.population;
		double aveworldWeighting = worldWeighting * 1.0 / this.population;

		return String.format(
			"%d\t%.1f\t%.1f\t%.1f\t%.1f\t%.1f",
			this.population,
			aveMutability,
			aveagentWeighting,
			avecommunityWeighting,
			avestateWeighting,
			aveworldWeighting
		);
	}

	/**
	 * Produces a printout of all Agents in the Community
	 */
	public void agentReport() {
		System.out.println(Agent.title);
		for (int i = 0; i < this.population; i++) {
			System.out.println(agents[i].toString());
		}
	}

	// ============
	// MISC METHODS
	// ============

	/**
	 * Merge Sort algorithm
	 * @param  A as an array of Agents
	 * @return array of Agents (sorted by agentWeighting)
	 */
	public Agent[] mergeSort(Agent[] A){
		int n = A.length;
		if (n <= 1){
			return A;
		} else {
			int m = n/2;
			Agent[] B = copyArray(A, 0, m-1);
			Agent[] C = copyArray(A, m, n-1);
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
	 * @return array of Agents (sorted by agentWeighting)
	 */
	public static Agent[] merge(Agent[] sub1, Agent[] sub2, Agent[] A){
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
			} else if (sub1[ptr1].getAllocation() > sub2[ptr2].getAllocation()){
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
	public static Agent[] copyArray(Agent[] A, int start, int end){
		int n = end - start + 1;
		Agent[] B = new Agent[n];
		for (int i = 0; i < n; i++){
			B[i] = A[i + start];
		}
		return B;
	}
			
}
	