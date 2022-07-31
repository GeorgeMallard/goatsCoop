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

		if (Settings.getGroupDepth() == 1) {
			topLevelGroup = new AgentGroup(1, Settings.getGroupSize(1), true, null);
		} else if (Settings.getGroupDepth() > 1) {
			topLevelGroup = new GroupGroup(Settings.getGroupDepth(), Settings.getGroupSize(Settings.getGroupDepth()), true, null);
		}

		topLevelGroup.gatherAllocations();


	}

}
