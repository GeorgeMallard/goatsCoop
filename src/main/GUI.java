package src.main;
/**
 * Displays interface for users to change settings and run simulation
 * @author Chris Litting
 * @version 1.1
 */
public class GUI {
	
	// =========
	// CONSTANTS
	// =========
	
	// ===MAIN TITLE===
	private static final String MAIN_TITLE = "\n=====================\nMULTI-LEVEL SELECTION\nusing EVOLVING AGENTS\n=====================\nAuthor: %s\nVersion: %s";
	// ===GENERAL MENUS===
	private static final String ON = "ON";
	private static final String OFF = "OFF";
	private static final String BACK = "Back";
	private static final String SELECT_OPTION = "\nSelect an option: ";
	// ===MAIN MENU===
	private static final String MAIN_MENU_TITLE = "MAIN MENU\n=========";
	private static final String SIMULATION = "Simulation";
	private static final String SETTINGS = "Settings";
	private static final String QUIT = "Quit";
	private static final String QUIT_CONFIRM = "Are you sure you want to quit? (y/n): ";
	private static final String GOODBYE_MSG = "\nPROGRAM CLOSED\n==============\n";
	// ===SIMULATION MENU===
	private static final String SIMULATION_MENU_TITLE = "SIMULATION\n==========";
	private static final String AGENTS_HEADING = "Agents\n------";
	private static final String GROUPS_HEADING = "Groups\n------";
	private static final String SIMULATION_HEADING = "Simulation Length\n-----------------";
	private static final String SIMULATION_PREVIEW = "[Steps: %d | Runs: %d | Rounds: %d | Iterations: %d]";
	private static final String VARIABLE_HEADING = "Variable\n--------";
	private static final String VARIABLE_PREVIEW = "[Variable Level: %d | Size Increment: %d | Capacity Increment: %d]";
	private static final String RUN_SIMULATION = "Run Simulation";
	private static final String SIMULATION_RUN_CONFIRM = "Confirm Run Simulation (y/n): ";
	private static final String SIMULATION_START = "Starting Simulation...";
	private static final String SIMULATION_COMPLETE = "...Simulation Complete";
	private static final String EXPORT_ARGS = "Export Code";
	// ===SETTINGS MENU===
	private static final String SETTINGS_MENU_TITLE = "SETTINGS\n========";
	private static final String GROUP_SETTINGS = "Group Settings";
	private static final String AGENT_SETTINGS = "Agent Settings";
	private static final String SIMULATION_SETTINGS = "Simulation Settings";
	// ===SIMULATION SETTINGS MENU===
	private static final String SIMULATION_SETTINGS_MENU_TITLE = "SIMULATION SETTINGS\n===================";	
	private static final String ITERATIONS = "Iterations: %,d";
	private static final String ROUNDS = "Rounds: %d";
	private static final String ENTER_ITERATIONS = "Enter number of iterations per round (1 - " + Settings.getMaxIterations() + "): ";
	private static final String ENTER_ROUNDS = "Enter number of rounds (1 - " + Settings.getMaxRounds() + "): ";
	private static final String RUNS = "Runs: %d";
	private static final String ENTER_RUNS = "Enter number of runs: ";
	private static final String STEPS = "Steps: %d";
	private static final String ENTER_STEPS = "Enter number of steps: ";
	private static final String VARIABLE_LEVEL = "Variable Level: %d";
	private static final String ENTER_VARIABLE_LEVEL = "Enter variable level: ";
	private static final String SIZE_INCREMENT = "Size Increment: %d";
	private static final String ENTER_SIZE_INCREMENT = "Enter size increment: ";
	private static final String CAPACITY_INCREMENT = "Capacity Increment: %d";
	private static final String ENTER_CAPACITY_INCREMENT = "Enter capacity increment: ";
	private static final String ENHANCED_MUTATION = "Enhanced Mutation: %s";
	// ===AGENT SETTINGS MENU===
	private static final String AGENT_SETTINGS_MENU_TITLE = "AGENT SETTINGS\n==============";
	private static final String INITIAL_MUTABILITY = "Initial Mutability: %d";
	private static final String MUTABLE_MUTABILITY = "Mutable Mutability: %s";
	private static final String ENTER_MUTABILITY = "Enter Agent Mutability (0 - 100): ";
	private static final String CHANGE_ALL_AGENT_WEIGHTINGS = "Change All Agent Weightings";
	private static final String ENTER_WEIGHTINGS = "Enter new weighting for all levels (0 - 100): ";
	private static final String AGENT_WEIGHTING = "[Level: %d | Initial Weighting: %d]";
	private static final String ENTER_WEIGHTING = "Enter weighting for Level %d: ";
	// ===GROUP SETTINGS MENU===
	private static final String GROUP_SETTINGS_TITLE = "GROUP SETTINGS\n==============";
	private static final String GROUP_DEPTH = "Group Depth: %d";
	private static final String ENTER_GROUP_DEPTH = "Enter new group depth (1 - " + Settings.getMaxGroupDepth() + "): ";
	private static final String CHANGE_ALL_GROUP_SIZES = "Change All Group Sizes";
	private static final String ENTER_GROUP_SIZES = "Enter new size for all groups (%d - " + Settings.getMaxGroupSize() + "): ";
	private static final String CHANGE_ALL_GROUP_CAPACITIES = "Change All Group Capacities";
	private static final String ENTER_GROUP_CAPACITIES = "Enter new capacity for all groups (1 - %d): ";
	private static final String CHANGE_ALL_GROUP_MUTABILITIES = "Change All Group Mutabilities";
	private static final String ENTER_GROUP_MUTABILITIES = "Enter mutability for all groups (0 - 100): ";
	private static final String CHANGE_ALL_GROUP_MUTABLE_MUTABILITIES = "Toggle All Group Mutable Mutability. Default: %s";
	private static final String GROUP_OPTION = "[Level: %d | Size: %d | Capacity %d | Mutability: %d | Mutable Mutability: %s]";
	// ===GROUP EDIT MENU===
	private static final String GROUP_EDIT_MENU_TITLE = "LEVEL %d GROUP SETTINGS\n======================";
	private static final String GROUP_SIZE = "Size: %d";
	private static final String ENTER_GROUP_SIZE =  "Enter size for Level %d groups (2 - " + Settings.getMaxGroupSize() + "): ";
	private static final String GROUP_CAPACITY = "Capacity: %d";
	private static final String ENTER_GROUP_CAPACITY = "Enter capacity for level %d groups (1 - %d): ";
	private static final String GROUP_MUTABILITY = "Mutability: %d";
	private static final String ENTER_GROUP_MUTABILITY = "Enter mutability for level %d groups (0 - 100): ";
	private static final String GROUP_MUTABLE_MUTABILITY = "Mutable mutability: %s";
	
	// ============
	// MENU METHODS
	// ============

	/**
	 * Displays main menu
	 */
	public static void mainMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = {
				SIMULATION,
				SETTINGS
			};
			value = createMenu(
				MAIN_MENU_TITLE,
				SELECT_OPTION,
				QUIT,
				options
			);
			switch (value) {
				case 1:
					simulationMMenu();
					break;
				case 2:
					settingsMenu();
					break;
				case 0:
				default:
					if (Input.readBoolean(QUIT_CONFIRM)) {
						closeProgram();
						cont = false;
					}
					break;
			}	
		}
	}

	/**
	 * Displays simulation menu
	 */
	private static void simulationMMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = {
				RUN_SIMULATION,
				EXPORT_ARGS
			};
			value = createMenu(
				SIMULATION_MENU_TITLE + generatePreview(),
				SELECT_OPTION,
				BACK,
				options
			);
			switch (value) {
				case 1:
					if (Input.readBoolean(SIMULATION_RUN_CONFIRM)) {
						System.out.println(SIMULATION_START);
						Simulation.run();
						System.out.println(SIMULATION_COMPLETE);
					}
					break;
				case 2:
					exportCode();
					break;
				case 0:
				default:
					cont = false;
					break;
			}
		}
	}

	/**
	 * Displays settings menu
	 */
	private static void settingsMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = {
				SIMULATION_SETTINGS,
				AGENT_SETTINGS,
				GROUP_SETTINGS	
			};
			value = createMenu(
				SETTINGS_MENU_TITLE,
				SELECT_OPTION,
				BACK,
				options				
			);
			switch (value) {
				case 1:
					simulationSettingsMenu();
					break;
				case 2:
					agentSettingsMenu();
					break;
				case 3:
					groupSettingsMenu();
					break;
				case 0:
				default:
					cont = false;
					break;
			}
		}
	}

	/**
	 * Displays simulation settings menu
	 */
	private static void simulationSettingsMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = {
				String.format(ITERATIONS, Settings.getIterations()),
				String.format(ROUNDS, Settings.getRounds()),
				String.format(RUNS, Settings.getRuns()),
				String.format(STEPS, Settings.getSteps()),
				String.format(VARIABLE_LEVEL, Settings.getVariableLevel()),
				String.format(SIZE_INCREMENT, Settings.getSizeIncrement()),
				String.format(CAPACITY_INCREMENT, Settings.getCapacityIncrement()),
				String.format(ENHANCED_MUTATION, Settings.getEnhancedMutationString(ON, OFF))
			};
			value = createMenu(
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
				case 3:
					Settings.setRuns(Input.readIntBetween(ENTER_RUNS, 1, Settings.getMaxRuns()));
					break;
				case 4:
					Settings.setSteps(Input.readIntBetween(ENTER_STEPS, 1, Settings.getCurrentMaxSteps()));
					break;
				case 5:
					Settings.setVariableLevel(Input.readIntBetween(ENTER_VARIABLE_LEVEL, 1, Settings.getGroupDepth()));
					break;
				case 6:
					Settings.setSizeIncrement(
						Input.readIntBetween(
							ENTER_SIZE_INCREMENT, 
							0, 
							(Settings.getMaxGroupSize() - Settings.getGroupSize(Settings.getVariableLevel())) / Settings.getSteps()
						)
					);
					break;
				case 7:
					Settings.setCapacityIncrement(
						Input.readIntBetween(
							ENTER_CAPACITY_INCREMENT, 
							0, 
							((Settings.getGroupSize(Settings.getVariableLevel()) * Settings.getSteps()) - Settings.getGroupCapacity(Settings.getVariableLevel())) / Settings.getSteps()
						)
					);
					break;
				case 8:
					Settings.toggleEnhancedMutation();
					break;
				case 0:
				default:
					cont = false;
					break;
			}
		}
	}

	/**
	 * Displays agent settings menu
	 */
	private static void agentSettingsMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = new String[3 + Settings.getGroupDepth()];
			options[0] = String.format(INITIAL_MUTABILITY, Settings.getInitialMutability(0));
			options[1] = String.format(MUTABLE_MUTABILITY, (Settings.getMutableMutability(0) ? ON : OFF));
			options[2] = CHANGE_ALL_AGENT_WEIGHTINGS;
			for (int i = 0; i < Settings.getGroupDepth(); i++) {
				options[i + 3] = String.format(String.format(AGENT_WEIGHTING, i, Settings.getAgentInitialWeighting(i)));
			}				
			value = createMenu(
				AGENT_SETTINGS_MENU_TITLE,
				SELECT_OPTION, 
				BACK,
				options
			);
			switch (value) {
				case 1:
					Settings.setInitialMutability(0, Input.readIntBetween(ENTER_MUTABILITY, 1, 100));
					break;
				case 2:
					Settings.setMutableMutability(0, !Settings.getMutableMutability(0));
					break;
				case 3:
					Settings.setAllAgentWeightings(Input.readIntBetween(ENTER_WEIGHTINGS, 0, 100));
					break;
				case 0:
					cont = false;
					break;
				default:
					Settings.setAgentInitialWeighting(value - 4, Input.readIntBetween(String.format(ENTER_WEIGHTING, value - 4), 0, 100));
					break;
			}
		}
	}

	/**
	 * Displays group settings menu
	 */
	private static void groupSettingsMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = new String[Settings.getGroupDepth() + 5];
			options[0] = String.format(GROUP_DEPTH, Settings.getGroupDepth());
			options[1] = CHANGE_ALL_GROUP_SIZES;
			options[2] = CHANGE_ALL_GROUP_CAPACITIES;
			options[3] = CHANGE_ALL_GROUP_MUTABILITIES;
			options[4] = String.format(CHANGE_ALL_GROUP_MUTABLE_MUTABILITIES, Settings.getDefaultMutableMutabilityString(ON, OFF));
			for (int i = 1; i <= Settings.getGroupDepth(); i++) {
				options[i + 4] = String.format(
					GROUP_OPTION, 
					i, 
					Settings.getGroupSize(i), 
					Settings.getGroupCapacity(i), 
					Settings.getInitialMutability(i), 
					Settings.getMutableMutabilityString(i, ON, OFF)
				);
			}	
			value = createMenu(
				GROUP_SETTINGS_TITLE,
				SELECT_OPTION, 
				BACK,
				options
			);
			switch (value) {
				case 1:
					Settings.setGroupDepth(Input.readIntBetween(ENTER_GROUP_DEPTH, 1, Settings.getMaxGroupDepth()));
					break;
				case 2:
					Settings.setAllGroupSizes(Input.readIntBetween(String.format(ENTER_GROUP_SIZES, Settings.getHighestGroupCapacity()), Settings.getHighestGroupCapacity(), Settings.getMaxGroupSize()));
					break;
				case 3:
					Settings.setAllGroupCapacities(Input.readIntBetween(String.format(ENTER_GROUP_CAPACITIES, Settings.getLowestGroupSize()), 1, Settings.getLowestGroupSize()));
					break;
				case 4:
					Settings.setAllGroupMutabilities(Input.readIntBetween(ENTER_GROUP_MUTABILITIES, 0, 100));
					break;
				case 5:
					Settings.toggleGroupMutableMutabilities();
					break;
				case 0:
					cont = false;
					break;
				default:
					groupEditMenu(value - 5);	//group select
					break;
			}
		}
	}

	/**
	 * Displays group settings menu for given group level
	 * @param level as an int
	 */
	private static void groupEditMenu(int level) {
		assert (level > 0) : "GUI selected level must be greater than 0. Level: " + level;
		assert (level <= Settings.getGroupDepth()) : "GUI seleceted level cannot exceed group depth. Level: " + level + ". Group depth: " + Settings.getGroupDepth();
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = {
				String.format(GROUP_SIZE, Settings.getGroupSize(level)),
				String.format(GROUP_CAPACITY, Settings.getGroupCapacity(level)),
				String.format(GROUP_MUTABILITY, Settings.getInitialMutability(level)),
				String.format(GROUP_MUTABLE_MUTABILITY, (Settings.getMutableMutability(level) ? ON : OFF))
			};
			value = createMenu(
				String.format(GROUP_EDIT_MENU_TITLE, level),
				SELECT_OPTION,
				BACK,
				options
			);
			switch (value) {
				case 1:
					Settings.setGroupSize(level, Input.readIntBetween(String.format(ENTER_GROUP_SIZE, level), 2, Settings.getMaxGroupSize()));
					break;
				case 2:
					Settings.setGroupCapacity(level, Input.readIntBetween(String.format(ENTER_GROUP_CAPACITY, level, Settings.getGroupSize(level)), 1, Settings.getGroupSize(level)));
					break;
				case 3:
					Settings.setInitialMutability(level, Input.readIntBetween(String.format(ENTER_GROUP_MUTABILITY, level), 0, 100));
					break;
				case 4:
					Settings.toggleMutableMutability(level);
					break;
				case 0:
				default:
					cont = false;
					break;
			}
		}
	}

	// =============
	// OTHER METHODS
	// =============

	/**
     * Creates and displays a bespoke number-based menu, and takes input from the user
     * @param title as a String (displayed at top of menu)
     * @param prompt as a String (displayed on input line)
     * @param zeroOption as a String (provides text for option 0, usually 'Back', 'Quit' etc.)
     * @param options as a String array 
     * @return int
     */
    private static int createMenu(String title, String prompt, String zeroOption, String[] options) {
        System.out.println("\n" + title);
        for (int i = 0; i < options.length; i++) {
            System.out.println(String.format("%d. %s", i + 1, options[i]));
        }
        if (zeroOption == "") {
            zeroOption = "<-";
        }
        System.out.println(String.format("%d. %s", 0, zeroOption));
        return Input.readIntBetween(prompt, 0, options.length);
    }

	/**
	 * Displays main program title
	 */
	public static void mainTitle(String author, String version) {
		System.out.println(String.format(MAIN_TITLE, author, version));
	}
	
	/**
	 * Returns simulation data as a String
	 * @return String
	 */
	private static String generatePreview() {
		String str = "";
		str += "\n\n";

		str += AGENTS_HEADING;
		str += "\n";
		for (int i = 0; i < Settings.getGroupDepth(); i++) {
			str += String.format(String.format(AGENT_WEIGHTING, i, Settings.getAgentInitialWeighting(i)));
			str+= "\n";
		}
		str += "\n";
		
		str += GROUPS_HEADING;
		str += "\n";
		str += "[" + String.format(GROUP_DEPTH, Settings.getGroupDepth()) + "]";
		str += "\n";
		for (int i = 1; i <= Settings.getGroupDepth(); i++) {
			str += String.format(
				GROUP_OPTION, 
				i, 
				Settings.getGroupSize(i), 
				Settings.getGroupCapacity(i), 
				Settings.getInitialMutability(i), 
				Settings.getMutableMutabilityString(i, ON, OFF)
			);
			str += "\n";
		}	
		str += "\n";
		
		str += SIMULATION_HEADING;
		str += "\n";
		str += String.format(
			SIMULATION_PREVIEW, 
			Settings.getSteps(),
			Settings.getRuns(), 
			Settings.getRounds(), 
			Settings.getIterations()	
		);
		str += "\n\n";

		str += VARIABLE_HEADING;
		str += "\n";
		str += String.format(
			VARIABLE_PREVIEW, 
			Settings.getVariableLevel(), 
			Settings.getSizeIncrement(), 
			Settings.getCapacityIncrement()
		);
		str += "\n";

		return str;
	}

	/**
	 * Creates a text file with args representing current settings
	 */
	private static void exportCode() {
		String filename = Output.createDateTimeFile("code");
		Output.writeToFile(filename, Settings.generateArgs());
	}

	/**
	 * Auto-runs simulation, bypassing menus
	 */
	public static void autoRun() {
		System.out.println("\n" + SIMULATION_START);
		Simulation.run();
		System.out.println(SIMULATION_COMPLETE);
	}

	/**
	 * Displays goodbye message
	 */
	public static void closeProgram() {
		System.out.println(GOODBYE_MSG);
	}
	
}
