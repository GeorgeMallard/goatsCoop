// TODO

// top level group
// getters and setters for Settings
// agent reproduction
// simulation begin
// TEST

// gui alter settings and run sim:
// --change depth
// --change default group sizes
// --change default agent weightings
// --number of iterations (or stopping condition)
// TEST

// agent contributions passed up the chain
// TEST

// culling and repopulation of agents
// TEST

// add settings:
// --average or absolute contribution
// --mutation settings (mutable mutation, advanced mutation)
// --allow agent negative values

// displaying data
// --weightings / contributions



public class GUI {
	
	// STRINGS FOR MENUS
	// =================
	private static final String SELECT_OPTION = "Select an option: ";
	private static final String RUN_SIMULATION = "Run simulation";
	private static final String QUIT = "Quit";
	private static final String GOODBYE_MSG = "Goodbye";

	// =================
	// SETTING VARIABLES
	// =================

	// AGENTS SETTINGS
	// ===============
	private static int defaultAgentWeighting = 100;
	private static int defaultCommunityWeighting = 100;
	private static int defaultStateWeighting = 100;
	private static int defaultWorldWeighting = 100;

	// GROUPS SETTINGS
	// ===============
	private static int groupDepth = 2;
	private static int stateSize = 20;
	private static int communitySize = 10;

	// SIMULATION SETTINGS
	// ===================
	private static int iterationsPerRound = 10000;
	private static int rounds = 1;

	// ===========
	// MAIN METHOD
	// ===========
	public static void main(String[] args) {
		mainMenu();	
	}

	// ============
	// MENU METHODS
	// ============

	public static void mainMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			value = Menu.create(
				SELECT_OPTION, 
				RUN_SIMULATION,
				QUIT
			);
			switch (value) {
				case 1:
					runSimulation();
					break;
				case 0:
					System.out.println(GOODBYE_MSG);
					cont = false;
					break;
			}	
		}
	}

	// ===============
	// UTILITY METHODS
	// ===============

	public static void runSimulation() {
		State.setDefaultSize(stateSize);
		Community.setDefaultSize(communitySize);
		
		//*Community.setDefaultResources(0.0);
				
		State ste1 = new State();
		
		//*ste1.displayCommunitiesInfo();
		
		System.out.println();
		for (int i = 0; i < rounds; i++){
			ste1.iterate(iterationsPerRound);
			ste1.displayCommunitiesInfo();
		}
		
		//*ste1.communities[0].agentReport();
		
	}

	
}