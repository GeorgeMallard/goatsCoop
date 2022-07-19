import java.util.Scanner;

// TO DO



public class GUI {
	
	public static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {

		State.setDefaultSize(20);
		Community.setDefaultSize(10);
		//Community.setDefaultResources(0.0);


		final int iterationsPerRound = 10000;
		final int rounds = 1;
		
		State ste1 = new State();
		//ste1.displayCommunitiesInfo();
		System.out.println();
		for (int i = 0; i < rounds; i++){
			ste1.iterate(iterationsPerRound);
			ste1.displayCommunitiesInfo();
		}
		//ste1.communities[0].agentReport();
		
		
	}

	public static void mainMenu() {
		
	}

	
}