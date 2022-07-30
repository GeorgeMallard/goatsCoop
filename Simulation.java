public class Simulation {
    
	static Group topLevelGroup;

    public static void run() {

		if (Settings.getGroupDepth() == 1) {
			topLevelGroup = new AgentGroup(1, Settings.getGroupSize(1), true, null);
		} else if (Settings.getGroupDepth() > 1) {
			topLevelGroup = new GroupGroup(Settings.getGroupDepth(), Settings.getGroupSize(Settings.getGroupDepth()), true, null);
		}

		topLevelGroup.gatherAllocations();


	}


}
