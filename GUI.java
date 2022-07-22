// TODO
// fix iteration count bug
// change from weighting to contribution
// agent settings
// --variable group numbers
// --starting values
// --mutable mutability
// --crazy mutations
// allow agent negative values
// group class
// group settings
// --average or absolute
// cty settings
// --size
// ste settings
// --size
// wld settings
// --size


public class GUI {
	
	// STRINGS FOR MENUS
	// =================
	private static final String SELECT_OPTION = "Select an option: ";
	private static final String RUN_SIMULATION = "Run simulation";
	private static final String QUIT = "Quit";
	private static final String GOODBYE_MSG = "Goodbye";

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

	public static void main(String[] args) {
		mainMenu();	
	}

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