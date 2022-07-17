//imports

/**
 * State class
 * Creates States that hold Communities
 * @author  Chris Litting
 * @version 2.0
 */
public class State {
	
	// =========
	// VARIABLES
	// =========
	private static int defaultSize = 20; //used when size is not specified
	private static int defaultCarryingCapacity = defaultSize / 2;
	private static int iteration = 0;
	/*
	private static int defaultTotalResources = 250; //used when totalResources is not specified
	
	*/
		
	// ======
	// FIELDS
	// ======
	private int size; //how many Communities can be accommodated
	private int carryingCapacity; //how many Communities survive each iteration
	private int communityCount; //tracks how many Communities there are currently
	public Community[] communities; //list of Communities contained
	
	/*
	private int totalResources; //how many resources a State has to contribute
	public AgentList migrationList; //stores excess Agents for redistribution
	*/
		
	// ============
	// CONSTRUCTORS
	// ============
	
	/**
	 * Constructor for objects of class State
	 */
	public State() {
		this.setSize(defaultSize);
		this.setCarryingCapacity(defaultCarryingCapacity);
		this.setCommunityCount(0);
		//this.setTotalResources(defaultTotalResources);
		this.populateState();
		//this.migrationList = new AgentList();
	}
	
	// =======
	// SETTERS
	// =======
	
	public static void setDefaultSize(int n) {
		//System.out.println("default State size set to " + n);
		defaultSize = n;
		defaultCarryingCapacity = n /2;
	}


	/**
	 * Sets size of State
	 * @param size as an int
	 */
	public void setSize(int size) {
		this.size = size;
		this.communities = new Community[size];
	}

	/**
	 * Sets carryingCapacity of State
	 * @param carryingCapacity as an int
	 */
	public void setCarryingCapacity(int carryingCapacity) {
		this.carryingCapacity = carryingCapacity;
	}


	public void setCommunityCount(int n) {
		this.communityCount = n;
	}

	
	
	/**
	 * Sets totalResources for State
	 * @param totalResources as an int
	 */
	/*
	public void setTotalResources(int totalResources) {
		this.totalResources = totalResources;
	}
	*/
	
	// =======
	// GETTERS
	// =======
	
	/**
	 * Returns size of State (how many Communities it contains)
	 * @return size as an int
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Returns carryingCapacity of State (how many Communities survive each iteration)
	 * @return carryingCapacity as an int
	 */
	public int getCarryingCapacity() {
		return this.carryingCapacity;
	}

	/**
	 * Returns totalResources of State
	 * @return totalResources as an int
	 */
	/*
	public int getTotalResources() {
		return this.totalResources;
	}
	*/

	// =========
	// UTILITIES
	// =========
	
	// ADD AND REMOVE COMMUNITIES =====================

	public void addCommunity(Community community) {
		if (this.communityCount < this.size) {
			this.communities[communityCount] = community;
			this.communityCount++;
		}
	}

	public void removeCommunity() {
		if (this.communityCount > 0) {
			this.communities[communityCount - 1] = null;
			this.communityCount--;
		}
	}



	// POPULATE STATE =================================

	/**
	 * Populates State with default Communities
	 */
	public void populateState() {
		while (this.communityCount < this.size) {
			addCommunity(new Community(true));
		}
	}
	
	// ALLOCATE RESOURCES =============================

	/**
	 * Allocates resources to Communities based on their communityContribution
	 */
	/*
	public void allocateResources() {
		double communityContributions = 0;
		for (int i = 0; i < this.size; i++) {
			communityContributions += communities[i].getCommunityContribution();
		}
		for (int i = 0; i < this.size; i++) {
			communities[i].setAllocation(communities[i].getCommunityContribution() / communityContributions * this.totalResources);
			//System.out.println(i + " allocation: " + communities[i].getCarryingCapacity());
		}
	}
	*/

	// MIGRATION ======================================

	/**
	 * Gathers migrants from all Communities into State migrationList
	 */
	/*
	public void gatherMigrants() {
		//System.out.println("=======================================");
		//System.out.println("gathering migrants...");
		for (Community x : communities) {
			//System.out.println("gathering " + x.migrationList.length() + " migrants");
			this.migrationList.appendList(x.migrationList);
			//System.out.println("total " + this.migrationList.length() + " migrants");
			x.migrationList.clear();
		}
	}
	*/

	/*
	public boolean allocateMigrants() {
		//System.out.println("=======================================");
		//System.out.println("allocating " + this.migrationList.length() + " migrants...");
		for (Community x : communities) {
			//int pop = x.getPopulation();
			int size = x.getSize();
			while (x.getPopulation() < size && !this.migrationList.isEmpty()) {
				//System.out.println("allocation found!");
				x.addAgent(this.migrationList.head);
				this.migrationList.removeAgent();
				//System.out.println(this.migrationList.length() + " migrants left");
			}
		}
		return true;
	}
	*/


	
	// ITERATIONS =====================================

	/**
	 * Performs n iterations
	 * @param n as an int
	 */
	public void iterate(int n) {
		for (int i = 0; i < n; i++) {
			//allocateResources();
			cullCommunities();
			cullAgents();
			repopulateCommunities();
			repopulateAgents();
			//iterateCommunities(1);
			//gatherMigrants();
			//allocateMigrants();
			//this.migrationList.clear();
			iteration++;
		}
	}

	/**
	 * 
	 * @param n
	 */
	public void cullAgents() {
		for (int i = 0; i < this.communityCount; i++) {
			this.communities[i].cullAgents();
		}
	}

	public void repopulateAgents() {
		for (int i = 0; i < this.communityCount; i++) {
			this.communities[i].repopulateAgents();
		}
	}


	/**
	 * Performs n iterations on all Communities
	 * @param n as an int
	 */
	/*
	public void iterateCommunities(int n) {
		for (int i = 0; i < this.size; i++) {
			communities[i].iterate(n);
		}
	}
	*/

	// SORTING COMMUNITIES BY COMMUNITY CONTRIBUTION =============================

	/**
	 * Sorts communities in descending order of communityContribution
	 */
	public void sortCommunities() {
		Community[] pop = copyArray(this.communities, 0, this.communityCount - 1);
		mergeSort(pop);
		for (int i = 0; i < pop.length; i++) {
			this.communities[i] = pop[i];
		}
	}

	// CULLING COMMUNITIES ==============================================

	/**
	 * Removes all Agents that exceed the carryingCapacity of the Community
	 */
	public void cullCommunities() {
		//System.out.println("culling agents! population: " + this.population + " carryingCapacity: " + this.carryingCapacity);
		sortCommunities();
		while (this.communityCount > this.carryingCapacity) {
			removeCommunity();
		}
	}

	// REPOPULATING COMMUNITIES ==========================================

	/**
	 * Allows Agents to reproduce and repopulate the Community. Excess Agents are added to migrationList
	 */
	public void repopulateCommunities() {
		//System.out.println("repopulating! cmty count: " + this.communityCount);
		int i = 0;
		int n = this.communityCount; //total number of Agents to reproduce, equal to population at beginning of process
		while (i < n && i + n < this.size) {
			addCommunity(Community.clone(communities[i]));
			i++;
		}
		//System.out.println("finished repopulating! cmty count: " + this.communityCount);
	}

	// ======
	// OUTPUT
	// ======

	/**
	 * Outputs information about Communities
	 */
	public void displayCommunitiesInfo() {
		System.out.println("iteration: " + iteration);
		System.out.println(Community.title);
		for (int i = 0; i < this.size; i++) {
			System.out.println(communities[i].toString());
		}
		System.out.println();
	}

	// ============
	// MISC METHODS
	// ============

	/**
	 * Merge Sort algorithm
	 * @param  A as an array of Agents
	 * @return array of Agents (sorted by agentWeighting)
	 */
	public Community[] mergeSort(Community[] A){
		int n = A.length;
		if (n <= 1){
			return A;
		} else {
			int m = n/2;
			Community[] B = copyArray(A, 0, m-1);
			Community[] C = copyArray(A, m, n-1);
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
	public static Community[] merge(Community[] sub1, Community[] sub2, Community[] A){
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
			} else if (sub1[ptr1].getCommunityContribution() > sub2[ptr2].getCommunityContribution()){
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
	public static Community[] copyArray(Community[] A, int start, int end){
		int n = end - start + 1;
		Community[] B = new Community[n];
		for (int i = 0; i < n; i++){
			B[i] = A[i + start];
		}
		return B;
	}



}