package src.main;

/**
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
    GUI.mainTitle("Chris Litting", "1.0");
    if (args.length == 0) {
      GUI.mainMenu();
    }	else {
      try {
        Input.parseArgs(args);
        GUI.autoRun();
      } catch (Exception e) {
        System.out.println("\n" + e.getMessage());
      }
      GUI.closeProgram();
     
    }
  }

}
