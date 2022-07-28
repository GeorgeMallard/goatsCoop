public class GUI {
	
	// =========
	// CONSTANTS
	// =========
	
	// ===GENERAL MENUS===
	private static final String BACK = "Back";
	private static final String ON = "ON";
	private static final String OFF = "OFF";
	// ===MAIN MENU===
	private static final String MAIN_MENU_TITLE = "MAIN MENU\n=========";
	private static final String SELECT_OPTION = "Select an option: ";
	private static final String RUN_SIMULATION = "Run simulation";
	private static final String SETTINGS = "Settings";
	private static final String QUIT = "Quit";
	private static final String QUIT_CONFIRM = "Are you sure you want to quit? (y/n): ";
	private static final String GOODBYE_MSG = "Goodbye";
	// ===SETTINGS MENU===
	private static final String SETTINGS_MENU_TITLE = "SETTINGS\n========";
	private static final String GROUP_SETTINGS = "Group Settings";
	private static final String AGENT_SETTINGS = "Agent Settings";
	private static final String SIMULATION_SETTINGS = "Simulation Settings";
	// ===GROUP SETTINGS MENU===
	private static final String GROUP_SETTINGS_MENU_TITLE = "GROUP SETTINGS\n==============";
	private static final String GROUP_DEPTH = "Group Depth: ";
	private static final String ENTER_GROUP_DEPTH = "Enter new group depth (1 - " + Settings.getMaxGroupDepth() + "): ";
	// ===AGENT SETTINGS MENU===
	private static final String AGENT_SETTINGS_MENU_TITLE = "AGENT SETTINGS\n==============";
	private static final String INITIAL_MUTABILITY = "Initial Mutability: ";
	private static final String MUTABLE_MUTABILITY = "Mutable Mutability: ";
	private static final String ENTER_MUTABILITY = "Enter Agent Mutability: ";

	// ===SIMULATION SETTINGS MENU===
	private static final String SIMULATION_SETTINGS_MENU_TITLE = "SIMULATION SETTINGS\n===================";	

	// ============
	// MENU METHODS
	// ============

	public static void mainMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = {
				RUN_SIMULATION,
				SETTINGS,
				QUIT
			};
			value = Menu.create(
				MAIN_MENU_TITLE,
				SELECT_OPTION,
				options
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
					if (Input.readBoolean(QUIT_CONFIRM)) {
						System.out.println(GOODBYE_MSG);
						cont = false;
					}
					break;
			}	
		}
	}

	public static void settingsMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = {
				GROUP_SETTINGS,
				AGENT_SETTINGS,
				SIMULATION_SETTINGS,
				BACK
			};
			value = Menu.create(
				SETTINGS_MENU_TITLE,
				SELECT_OPTION,
				options				
			);
			switch (value) {
				case 1:
					groupSettingsMenu();
					break;
				case 2:
					agentSettingsMenu();
					break;
				case 3:
					simulationSettingsMenu();
					break;
				case 0:
				default:
					cont = false;
					break;
			}
		}
	}

	public static void groupSettingsMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = {
				GROUP_DEPTH + Settings.getGroupDepth(), 
				BACK
			};
			value = Menu.create(
				GROUP_SETTINGS_MENU_TITLE,
				SELECT_OPTION,
				options
			);
			switch (value) {
				case 1:
					Settings.setGroupDepth(Input.readIntBetween(ENTER_GROUP_DEPTH, 1, Settings.getMaxGroupDepth()));
					break;
				case 0:
				default:
					cont = false;
					break;
			}
		}
	}

	public static void agentSettingsMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = {
				INITIAL_MUTABILITY + Settings.getAgentInitialMutability(),
				MUTABLE_MUTABILITY + (Settings.getAgentMutableMutability() ? ON : OFF),
				BACK
			};
			value = Menu.create(
				AGENT_SETTINGS_MENU_TITLE,
				SELECT_OPTION, 
				options
			);
			switch (value) {
				case 1:
					Settings.setAgentInitialMutability(Input.readIntBetween(ENTER_MUTABILITY, 1, 100));
					break;
				case 2:
					Settings.setAgentMutableMutability(!Settings.getAgentMutableMutability());
					break;
				case 0:
				default:
					cont = false;
					break;
			}
		}
	}

	public static void simulationSettingsMenu() {
		boolean cont = true;
		int value = -1;
		String[] options = {
			BACK
		};
		while (cont) {
			value = Menu.create(
				SIMULATION_SETTINGS_MENU_TITLE,
				SELECT_OPTION, 
				options
			);
			switch (value) {
				case 0:
				default:
					cont = false;
					break;
			}
		}
	}

	/*
	public static void menuTemplate() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = {
				BACK
			};
			value = Menu.create(
				MAIN_MENU_TITLE,
				SELECT_OPTION, 
				BACK
			);
			switch (value) {
				case 0:
				default:
					cont = false;
					break;
			}
		}
	}
	 */
	
}