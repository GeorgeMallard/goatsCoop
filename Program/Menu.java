package Program;
/**
 * Menu class
 * Used for creating menus in the GUI
 * @author Chris Litting
 * @version 1.0
 */
public class Menu {
    
    // =======
    // METHODS
    // =======

    /**
     * Creates and displays a bespoke number-based menu, and takes input from the user
     * @param title as a String (displayed at top of menu)
     * @param prompt as a String (displayed on input line)
     * @param zeroOption as a String (provides text for option 0, usually 'Back', 'Quit' etc.)
     * @param options as a String array 
     * @return int
     */
    public static int create(String title, String prompt, String zeroOption, String[] options) {
        System.out.println("\n" + title);
        for (int i = 0; i < options.length; i++) {
            System.out.println(String.format("%d. %s", i + 1, options[i]));
        }
        if (zeroOption == "") {
            zeroOption = "<-";
        }
        System.out.println(String.format("%d. %s", 0, zeroOption));
        return Input.readIntBetween(prompt, 0, options.length);
    }

}
