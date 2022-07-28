public class GUI {
	
	// =========
	// CONSTANTS
	// =========
	
	// ===GENERAL MENUS===
	private static final String BACK = "Back";
	// ===MAIN MENU===
	private static final String MAIN_MENU_TITLE = "MAIN MENU\n=========";
	private static final String SELECT_OPTION = "Select an option: ";
	private static final String RUN_SIMULATION = "Run simulation";
	private static final String SETTINGS = "Settings";
	private static final String QUIT = "Quit";
	// ===SETTINGS MENU===
	private static final String SETTINGS_MENU_TITLE = "SETTINGS\n========";

	// ===PROGRAM CLOSE===
	private static final String GOODBYE_MSG = "Goodbye";

	// ============
	// MENU METHODS
	// ============

	public static void mainMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			value = Menu.create(
				MAIN_MENU_TITLE,
				SELECT_OPTION, 
				RUN_SIMULATION,
				SETTINGS,
				QUIT
			);
			switch (value) {
				case 1:
					Simulation.run();
					break;
				case 2:
					settingsMenu();
					break;
				case 0:
				default:
					System.out.println(GOODBYE_MSG);
					cont = false;
					break;
			}	
		}
	}

	public static void settingsMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			value = Menu.create(
				SETTINGS_MENU_TITLE,
				SELECT_OPTION,
				BACK
			);
			switch (value) {
				case 0:
				default:
					System.out.println("Back");
					cont = false;
					break;
			}
		}
	}
	
}