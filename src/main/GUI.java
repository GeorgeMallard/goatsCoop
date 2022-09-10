package src.main;
/**
 * GUI class
 * Creates interactive interface for users to change settings and run simulation
 * @author Chris Litting
 * @version 1.1
 */
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
	private static final String QUIT_CONFIRM = "\nAre you sure you want to quit? (y/n): ";
	private static final String GOODBYE_MSG = "\nPROGRAM CLOSED\n==============\n";
	// ===SETTINGS MENU===
	private static final String SETTINGS_MENU_TITLE = "SETTINGS\n========";
	private static final String GROUP_DEPTH = "Group Depth: ";
	private static final String ENHANCED_MUTATION = "Enhanced Mutation: ";
	private static final String ENTER_GROUP_DEPTH = "Enter new group depth (1 - " + Settings.getMaxGroupDepth() + "): ";
	private static final String GROUP_DEFAULT_SETTINGS = "General Group Settings";
	private static final String GROUP_OPTION = "Level %d Group [Size: %d | Capacity %d | Mutability: %d | Mutable Mutability: %s]";
	//private static final String GROUP_SETTINGS = "Group Settings";
	private static final String AGENT_SETTINGS = "Agent Settings";
	private static final String SIMULATION_SETTINGS = "Simulation Settings";
	// ===SIMULATION SETTINGS MENU===
	private static final String SIMULATION_SETTINGS_MENU_TITLE = "SIMULATION SETTINGS\n===================";	
	private static final String ITERATIONS = "Iterations per Round: %,d";
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
	// ===AGENT SETTINGS MENU===
	private static final String AGENT_SETTINGS_MENU_TITLE = "AGENT SETTINGS\n==============";
	private static final String INITIAL_MUTABILITY = "Initial Mutability: ";
	private static final String MUTABLE_MUTABILITY = "Mutable Mutability: ";
	private static final String ENTER_MUTABILITY = "Enter Agent Mutability (0 - 100): ";
	private static final String CHANGE_ALL_AGENT_WEIGHTINGS = "Change all Agent weightings";
	private static final String ENTER_WEIGHTINGS = "Enter new weighting for all levels (0 - 100): ";
	private static final String AGENT_WEIGHTING = "Level %d Initial Weighting: %d";
	private static final String ENTER_WEIGHTING = "Enter weighting for Level %d: ";
	// ===GROUP DEFAULT SETTINGS MENU===
	private static final String GROUP_DEFAULT_SETTINGS_TITLE = "GROUP SETTINGS\n==============";
	private static final String CHANGE_ALL_GROUP_SIZES = "Change size for all groups";
	private static final String ENTER_GROUP_SIZES = "Enter new size for all groups (%d - " + Settings.getMaxGroupSize() + "): ";
	private static final String CHANGE_ALL_GROUP_CAPACITIES = "Change capacity for all groups";
	private static final String ENTER_GROUP_CAPACITIES = "Enter new capacity for all groups (1 - %d): ";
	private static final String CHANGE_ALL_GROUP_MUTABILITIES = "Change mutability for all groups";
	private static final String ENTER_GROUP_MUTABILITIES = "Enter mutability for all groups (0 - 100): ";
	private static final String CHANGE_ALL_GROUP_MUTABLE_MUTABILITIES = "Toggle mutable mutability for all groups";
	// ===GROUP SETTINGS MENU===
	private static final String GROUP_SETTINGS_MENU_TITLE = "LEVEL %d GROUP SETTINGS\n======================";
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

	/**
	 * Displays settings menu
	 */
	public static void settingsMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = new String[Settings.getGroupDepth() + 5];
			options[0] = SIMULATION_SETTINGS;
			options[1] = GROUP_DEPTH + Settings.getGroupDepth();
			options[2] = ENHANCED_MUTATION + Settings.getEnhancedMutationString(ON, OFF);
			options[3] = AGENT_SETTINGS;
			options[4] = GROUP_DEFAULT_SETTINGS;
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
	
			value = Menu.create(
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
					Settings.setGroupDepth(Input.readIntBetween(ENTER_GROUP_DEPTH, 1, Settings.getMaxGroupDepth()));
					break;
				case 3:
					Settings.toggleEnhancedMutation();
					break;
				case 4:
					agentSettingsMenu();
					break;
				case 5:
					groupDefaultSettingsMenu();
					break;
				case 0:
					cont = false;
					break;
				default:
					groupSettingsMenu(value - 5);	//group select
					break;
			}
		}
	}

	/**
	 * Displays simulation settings menu
	 */
	public static void simulationSettingsMenu() {
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
				String.format(CAPACITY_INCREMENT, Settings.getCapacityIncrmeent())
			};
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
				case 3:
					Settings.setRuns(Input.readIntBetween(ENTER_RUNS, 1, Settings.getMaxRuns()));
					break;
				case 4:
					Settings.setSteps(Input.readIntBetween(ENTER_STEPS, 1, Settings.getMaxSteps()));
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
	public static void agentSettingsMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = new String[3 + Settings.getGroupDepth()];
			options[0] = INITIAL_MUTABILITY + Settings.getInitialMutability(0);
			options[1] = MUTABLE_MUTABILITY + (Settings.getMutableMutability(0) ? ON : OFF);
			options[2] = CHANGE_ALL_AGENT_WEIGHTINGS;
			for (int i = 0; i < Settings.getGroupDepth(); i++) {
				options[i + 3] = String.format(String.format(AGENT_WEIGHTING, i, Settings.getAgentInitialWeighting(i)));
			}				
			value = Menu.create(
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
	 * Displays group default settings menu
	 */
	public static void groupDefaultSettingsMenu() {
		boolean cont = true;
		int value = -1;
		while (cont) {
			String[] options = {
				CHANGE_ALL_GROUP_SIZES,
				CHANGE_ALL_GROUP_CAPACITIES,
				CHANGE_ALL_GROUP_MUTABILITIES,
				CHANGE_ALL_GROUP_MUTABLE_MUTABILITIES
			};
			value = Menu.create(
				GROUP_DEFAULT_SETTINGS_TITLE,
				SELECT_OPTION, 
				BACK,
				options
			);
			switch (value) {
				case 1:
					Settings.setAllGroupSizes(Input.readIntBetween(String.format(ENTER_GROUP_SIZES, Settings.getHighestGroupCapacity()), Settings.getHighestGroupCapacity(), Settings.getMaxGroupSize()));
					break;
				case 2:
					Settings.setAllGroupCapacities(Input.readIntBetween(String.format(ENTER_GROUP_CAPACITIES, Settings.getLowestGroupSize()), 1, Settings.getLowestGroupSize()));
					break;
				case 3:
					Settings.setAllGroupMutabilities(Input.readIntBetween(ENTER_GROUP_MUTABILITIES, 0, 100));
					break;
				case 4:
					Settings.toggleGroupMutableMutabilities();
					break;
				case 0:
				default:
					cont = false;
					break;
			}
		}
	}

	/**
	 * Displays group settings menu for given group level
	 * @param level as an int
	 */
	public static void groupSettingsMenu(int level) {
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
			value = Menu.create(
				String.format(GROUP_SETTINGS_MENU_TITLE, level),
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
	
}
