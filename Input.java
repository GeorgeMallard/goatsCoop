import java.util.Scanner;

public class Input {
    
    public static int intInput(int min, int max) {
        Scanner in = new Scanner(System.in);
        int input = 0;
        while (!in.hasNextInt()) {
            System.out.println("Please enter a number from " + min + " to " + max);
            in.nextLine();
        }
        input = in.nextInt();
        in.nextLine();
        while (input < min || input > max) {
            System.out.println("Please enter a number from " + min + " to " + max);
        }
        in.close();
        return input;
    }




}
