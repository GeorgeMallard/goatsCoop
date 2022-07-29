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
	private static final String GROUP_SIZE = "Level %d Groups Size: %d";
	private static final String ENTER_GROUP_SIZE =  "Enter size for Level %d groups (2 - " + Settings.getMaxGroupSize() + "): ";
	private static final String CHANGE_ALL_GROUP_SIZES = "Change size for all groups";
	private static final String ENTER_GROUP_SIZES = "Enter new size for all groups: ";
	// ===AGENT SETTINGS MENU===
	private static final String AGENT_SETTINGS_MENU_TITLE = "AGENT SETTINGS\n==============";
	private static final String INITIAL_MUTABILITY = "Initial Mutability: ";
	private static final String MUTABLE_MUTABILITY = "Mutable Mutability: ";
	private static final String ENTER_MUTABILITY = "Enter Agent Mutability (1 - 100): ";
	private static final String AGENT_WEIGHTING = "Level %d Initial Weighting: %d";
	private static final String ENTER_WEIGHTING = "Enter weighting for Level %d: ";
	// ===SIMULATION SETTINGS MENU===
	private static final String SIMULATION_SETTINGS_MENU_TITLE = "SIMULATION SETTINGS\n===================";	
	private static final String ITERATIONS = "Iterations per Round: ";
	private static final String ROUNDS = "Rounds: ";
	private static final String ENTER_ITERATIONS = "Enter number of iterations per round (1 - " + Settings.getMaxIterations() + "): ";
	private static final String ENTER_ROUNDS = "Enter number of rounds (1 - " + Settings.getMaxRounds() + "): ";

	// ============
	// MENU METHODS
	// ============

	public static void mainMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = {
				RUN_SIMULATION,
				SETTINGS
			};
			value = Menu.create(
				MAIN_MENU_TITLE,
				SELECT_OPTION,
				QUIT,
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
				SIMULATION_SETTINGS
			};
			value = Menu.create(
				SETTINGS_MENU_TITLE,
				SELECT_OPTION,
				BACK,
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
			String[] options = new String[2 + Settings.getGroupDepth()];
			options[0] = GROUP_DEPTH + Settings.getGroupDepth();
			options[1] = CHANGE_ALL_GROUP_SIZES;
			for (int i = 1; i <= Settings.getGroupDepth(); i++) {
				options[i + 1] = String.format(String.format(GROUP_SIZE, i, Settings.getGroupSize(i)));
			}
			
			value = Menu.create(
				GROUP_SETTINGS_MENU_TITLE,
				SELECT_OPTION,
				BACK,
				options
			);
			switch (value) {
				case 1:
					Settings.setGroupDepth(Input.readIntBetween(ENTER_GROUP_DEPTH, 1, Settings.getMaxGroupDepth()));
					break;
				case 2:
					Settings.setAllGroupSizes(Input.readIntBetween(ENTER_GROUP_SIZES, 1, Settings.getMaxGroupSize()));
					break;
				case 0:
					cont = false;
					break;
				default:
					Settings.setGroupSize(value - 1, Input.readIntBetween(String.format(ENTER_GROUP_SIZE, value - 1), 2, Settings.getMaxGroupSize()));
					break;
			}
		}
	}

	public static void agentSettingsMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {

			String[] options = new String[2 + Settings.getGroupDepth()];
			options[0] = INITIAL_MUTABILITY + Settings.getAgentInitialMutability();
			options[1] = MUTABLE_MUTABILITY + (Settings.getAgentMutableMutability() ? ON : OFF);
			for (int i = 0; i < Settings.getGroupDepth(); i++) {
				options[i + 2] = String.format(String.format(AGENT_WEIGHTING, i, Settings.getAgentInitialWeighting(i)));
			}				
			value = Menu.create(
				AGENT_SETTINGS_MENU_TITLE,
				SELECT_OPTION, 
				BACK,
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
					cont = false;
					break;
				default:
					Settings.setAgentInitialWeighting(value - 3, Input.readIntBetween(String.format(ENTER_WEIGHTING, value - 3), 0, 100));
					break;
			}
		}
	}

	public static void simulationSettingsMenu() {
		boolean cont = true;
		int value = -1;
		String[] options = {
			ITERATIONS,
			ROUNDS
		};
		while (cont) {
			value = Menu.create(
				SIMULATION_SETTINGS_MENU_TITLE,
				SELECT_OPTION,
				BACK, 
				options
			);
			switch (value) {
				case 1:
					Settings.setIterations(Input.readIntBetween(ENTER_ITERATIONS, 1, Settings.getMaxIterations()));
					break;
				case 2:
					Settings.setRounds(Input.readIntBetween(ENTER_ROUNDS, 1, Settings.getMaxRounds()));
					break;
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