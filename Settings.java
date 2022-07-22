import java.util.ArrayList;
import java.util.Arrays;

public class Settings {
    
    // FIELDS
    // ======

    // "MAGIC" NUMBERS - maximums and defaults
    private static final int maxDepth = 5;          // change this number to alter max group levels
    private static final int maxGroupSize = 100;    // change this number to alter max group size
    private static final int defaultGroupSize = 10; // change this number to alter default group size
    // REGULAR FIELDS
    private static int depth = 3;
    private static ArrayList<Integer> sizes = new ArrayList<Integer>(Arrays.asList(10, 10, 10));

    // SETTERS
    // =======
    public static void setDepth(int newDepth) {
        if (newDepth > 0 && newDepth < maxDepth && newDepth != depth) {
            if (newDepth < depth) {
                for (int i = 0; i < depth - newDepth; i++) {
                    sizes.remove(newDepth);
                }
            } else {
                for (int i = 0; i < newDepth - depth; i++) {
                    sizes.add(depth, defaultGroupSize);
                }
            }
        }
    }

    public static void setGroupSize(int groupLevel, int newSize) {
        if (groupLevel > 0 && groupLevel <= sizes.size() && newSize <= maxGroupSize) {
            sizes.set(groupLevel - 1, newSize);
        }  
    }

    // GETTERS
    // =======
    public static int getDepth() {
        return depth;
    }

    public static int getGroupSize(int groupLevel) {
        return sizes.get(groupLevel - 1);
    }

}
