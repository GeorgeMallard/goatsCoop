public class Menu {
    
    public static int create(String title, String prompt, String zeroOption, String[] args) {
        System.out.println("\n" + title);
        for (int i = 0; i < args.length; i++) {
            System.out.println(String.format("%d. %s", i + 1, args[i]));
        }
        System.out.println(String.format("%d. %s", 0, zeroOption));
        return Input.readIntBetween(prompt, 0, args.length);
    }

}
