// TODO
// fix iteration count bug
// change from weighting to contribution
// agent settings
// --starting values
// --mutable mutability
// --crazy mutations
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
	
	private static final String SELECT_OPTION = "Select an option: ";
	private static final String RUN_SIMULATION = "Run simulation";
	private static final String QUIT = "Quit";
	private static final String GOODBYE_MSG = "Goodbye";


	private static int stateSize = 20;
	private static int communitySize = 10;
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