package src.main;
/**
 * Simulation class
 * Runs simulation according to current settings
 * @author Chris Litting
 * @version 1.0
 */
public class Simulation {
    
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
					topLevelGroup.report();
				}
			}
			updateVariable();
		}
	}

	public static void updateVariable() {
		Settings.incrementVariableSize();
		Settings.incrementVariableCapacity();
	}

}
