// TO DO
// main menu


public class GUI {
	
	private static final String SELECT_OPTION = "Please select an option";
	public static void main(String[] args) {

		mainMenu();
		//State.setDefaultSize(20);
		//Community.setDefaultSize(10);
		
		//*Community.setDefaultResources(0.0);

		/*
		final int iterationsPerRound = 10000;
		final int rounds = 1;
		
		State ste1 = new State();
		*/
		//*ste1.displayCommunitiesInfo();
		/*
		System.out.println();
		for (int i = 0; i < rounds; i++){
			ste1.iterate(iterationsPerRound);
			ste1.displayCommunitiesInfo();
		}
		*/

		//*ste1.communities[0].agentReport();
		
		
	}

	public static void mainMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			value = Menu.create(SELECT_OPTION, "do this", "do that", "quit");
			System.out.println("Option selected: " + value);

			if (value == 0) {
				cont = false;
			}
		}
	}

	public static void runSimulation() {

	}

	
}