// TODO

// top level group
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
	
	// =========
	// CONSTANTS
	// =========
	private static final String SELECT_OPTION = "Select an option: ";
	private static final String RUN_SIMULATION = "Run simulation";
	private static final String QUIT = "Quit";
	private static final String GOODBYE_MSG = "Goodbye";

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