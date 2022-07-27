public class GUI {
	
	// =========
	// CONSTANTS
	// =========
	private static final String SELECT_OPTION = "Select an option: ";
	private static final String RUN_SIMULATION = "Run simulation";
	private static final String QUIT = "Quit";
	private static final String GOODBYE_MSG = "Goodbye";

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
					Simulation.run();
					break;
				case 0:
					System.out.println(GOODBYE_MSG);
					cont = false;
					break;
			}	
		}
	}
	
}