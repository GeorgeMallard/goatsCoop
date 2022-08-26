package src.main;
// TO DO

// data output
// --gather data (mutabilities / group capacities)
// --output as .txt file
// --additional unit testing for output
// TEST

// if there's extra time
// --make asserts more specific
// --advanced mutation option

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
