package src.main;
/**
 * Simulation class
 * Runs simulation according to current settings
 * @author Chris Litting
 * @version 1.0
 */
public class Simulation {
    
	// =========
	// CONSTANTS
	// =========

	public static final String VARIABLE_LEVEL = "Variable_Level";
	public static final String VARIABLE_SIZE = "Variable_Size";
	public static final String VARIABLE_CAPACITY = "Variable_Capacity";
	public static final String CONTRIBUTION = "Contribution_";
	public static final String MUTABILITY = "Mutability_";
	public static final String CAPACITY = "Capacity_";
	public static String TITLE_ROW;

	// ======
	// FIELDS
	// ======

	public static Group topLevelGroup;		//creates a pointer to the single Group at the top of the hierarchy

	// =======
	// METHODS
	// =======

	/**
	 * Runs simulation
	 */
    public static void run() {

		TITLE_ROW = writeTitleRow();

		Output.createFile("results");
		Output.writeToFile("results", TITLE_ROW);
		
		for (int l = 0; l < Settings.getSteps(); l++) {
			for (int i = 0; i < Settings.getRuns(); i++) {
				topLevelGroup = new Group(
					Settings.getGroupDepth(), 
					Settings.getGroupSize(Settings.getGroupDepth()), 
					true, 
					null,
					Settings.getInitialMutability(Settings.getGroupDepth()),
					Settings.getGroupCapacity(Settings.getGroupDepth())
				);
			
				for (int j = 0; j < Settings.getRounds(); j++) {
					for (int k = 0; k < Settings.getIterations(); k++) {
						topLevelGroup.resetContributions();
						topLevelGroup.gatherContributions();
						topLevelGroup.sortChildren();
						topLevelGroup.cullChildren();
						topLevelGroup.repopulate();
						topLevelGroup.mutateEntity();	
					}
					Output.writeToFile("results", writeRow());
				}
			}
			updateVariable();
		}
	}

	public static void updateVariable() {
		Settings.incrementVariableSize();
		Settings.incrementVariableCapacity();
	}

	public static String writeTitleRow() {
		String str = "";
		str += VARIABLE_LEVEL;
		str += VARIABLE_SIZE;
		str += VARIABLE_CAPACITY;
		for (int i = 0; i < Settings.getGroupDepth(); i++) {
			str += (CONTRIBUTION + i);
		}
		for (int i = 0; i <= Settings.getGroupDepth(); i++) {
			str += (MUTABILITY + i);
		}
		for (int i = 1; i <= Settings.getGroupDepth(); i++) {
			str += (CAPACITY + i);
		}

		return str;
	}

	public static String writeRow() {
		String str = "";
		str += Settings.getVariableLevel();
		str += Settings.getGroupSize(Settings.getVariableLevel());
		str += Settings.getGroupCapacity(Settings.getVariableLevel());
		str += " ";
		str += topLevelGroup.report();
		return str;
	}


}
