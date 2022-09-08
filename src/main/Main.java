package src.main;
// TO DO

// data output
// --update GUI for variable (GUI class)
// --output as .txt file (Group class, I/O class)
// TEST

/**
 * Main class
 * Contains main method
 * @author Chris Litting
 * @version 1.0
 */
public class Main {
    
  // ===========
	// MAIN METHOD
	// ===========

  /**
   * Main method
   * @param args as a String array
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      GUI.mainMenu();
    }	else {
      Input.parseArgs(args);
      Simulation.run();
    }
  }

}
