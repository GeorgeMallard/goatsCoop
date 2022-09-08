package src.main;
// TO DO

// data output
// --add alterable variable
// ----add runs
// ----add alter variable
// ----update GUI
// ----update args
// --output as .txt file
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
