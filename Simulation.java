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

	static Group topLevelGroup;		//creates a pointer to the single Group at the top of the hierarchy

	// =======
	// METHODS
	// =======

	/**
	 * Runs simulation
	 */
    public static void run() {

		topLevelGroup = new Group(
			Settings.getGroupDepth(), 
			Settings.getGroupSize(Settings.getGroupDepth()), 
			true, 
			null
		);
		
		for (int i = 0; i < Settings.getRounds(); i++) {
			for (int j = 0; j < Settings.getIterations(); j++) {
				topLevelGroup.gatherContributions();
				topLevelGroup.sortChildren();
				//cull
				//repopulate
				//report
				//reset contributions
			}
		}
	}

}
