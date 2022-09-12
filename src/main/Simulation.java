package src.main;

/**
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

		String filename = Output.createDateTimeFile("results");
		TITLE_ROW = writeTitleRow();
		Output.writeToFile(filename, TITLE_ROW);
		
		for (int l = 0; l < Settings.getSteps(); l++) { 					// STEP BEGIN
			for (int i = 0; i < Settings.getRuns(); i++) { 					// RUN BEGIN

				// ======================
				// Create top level group
				topLevelGroup = new Group(
					Settings.getGroupDepth(), 
					Settings.getGroupSize(Settings.getGroupDepth()), 
					true, 
					null,
					Settings.getInitialMutability(Settings.getGroupDepth()),
					Settings.getGroupCapacity(Settings.getGroupDepth())
				);
				// =====================
			
				for (int j = 0; j < Settings.getRounds(); j++) { 			 // ROUND BEGIN
					iteration(true, false);
					for (int k = 1; k < Settings.getIterations() - 1; k++) { // ITERATION BEGIN
						iteration(false, false);
					} 
					iteration(false, true);					 // ITERATION END
					Output.writeToFile(filename, writeRow());					// row output
				} 															// ROUND END
			} 																// RUN END
			updateVariable();												// variable update
		} 																	// STEP END
	}

	public static void iteration(boolean reset, boolean gather) {
		topLevelGroup.resetData(reset);
		topLevelGroup.gatherData(gather);
		topLevelGroup.sortChildren();
		topLevelGroup.cullChildren();
		topLevelGroup.repopulate();
		topLevelGroup.mutateEntity();	
	}

	/**
	 * Updates the variable level group size and capacity between steps
	 */
	public static void updateVariable() {
		Settings.incrementVariableSize();
		Settings.incrementVariableCapacity();
	}

	/**
	 * Generates title row for output
	 * @return String
	 */
	public static String writeTitleRow() {
		String str = "";
		str += VARIABLE_LEVEL;
		str += " ";
		str += VARIABLE_SIZE;
		str += " ";
		str += VARIABLE_CAPACITY;
		str += " ";
		for (int i = 0; i < Settings.getGroupDepth(); i++) {
			str += (CONTRIBUTION + i);
			str += " ";
		}
		for (int i = 0; i <= Settings.getGroupDepth(); i++) {
			str += (MUTABILITY + i);
			str += " ";
		}
		for (int i = 1; i <= Settings.getGroupDepth(); i++) {
			str += (CAPACITY + i);
			str += " ";
		}
		return (str == null || str.length() == 0)
        ? null 
        : (str.substring(0, str.length() - 1));
	}

	/**
	 * Generates row for output at the end of a round
	 * @return String
	 */
	public static String writeRow() {
		String str = "";
		str += Settings.getVariableLevel();
		str += " ";
		str += Settings.getGroupSize(Settings.getVariableLevel());
		str += " ";
		str += Settings.getGroupCapacity(Settings.getVariableLevel());
		str += " ";
		str += topLevelGroup.toString();
		return str;
	}

}
