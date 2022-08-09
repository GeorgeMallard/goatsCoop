import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class GroupTest {
   
    // =========
    // MOCK DATA
    // =========

    private static int level = 3;
    private static int size = 20;
    private static boolean populate = false;
    private static Group parentGroup = null;
    private static int mutability = 50;
    private static int capacity = 10;

    // =============
    // MUTATE METHOD
    // =============

    @Test
    public void valueIsUnchangedWhenMutationFactorIsZero() {
        assertEquals(50, Group.mutate(50, 0, 0, 100));
    }

    @Test
    public void valueChangesByOneWhenMutationFactorIsOneHundred() {
        int x = Group.mutate(50, 100, 0, 100);
        assertTrue(x == 49 || x == 51);
    }

    // ================================================================================
    // THESE TWO NEED IMPROVEMENT
    
    @Test
    public void valueWillNotMutateBelowMin() {
        int x = Group.mutate(0, 100, 0, 100);
        assertTrue(x == 0 || x == 1);
    }

    @Test
    public void valueWillNotMutateAboveMax() {
        int x = Group.mutate(100, 100, 0, 100);
        assertTrue(x == 100 || x == 99);
    }

    // ================================================================================

    @Test
    public void minGreaterThanMaxThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Group.mutate(50, 50, 51, 50));
    }

    @Test
    public void valueLessThanMinThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Group.mutate(-1, 50, 0, 100));
    }

    @Test
    public void valueGreaterThanMaxThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Group.mutate(101, 50, 0, 100));
    }

    @Test
    public void minAndMaxCanBeExtremeValues() {
        assertEquals(50, Group.mutate(50, 0, -2147483648, 2147483647));
    }

    @Test
    public void mutationFactorBelowZeroThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Group.mutate(50, -1, 0, 100));
    }

    @Test
    public void mutationFactorAboveHundredThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Group.mutate(50, 101, 0, 100));
    }

    @Test
    public void valueDoesntChangeWhenMinEqualsMax() {
        assertEquals(50, Group.mutate(50, 100, 50, 50));
    }

    // ================
    // EQUALS AND CLONE
    // ================

    @Test
    public void groupEqualsIdenticalGroup() {
        Group testGroup1 = new Group(level, size, populate, parentGroup, mutability, capacity);
        Group testGroup2 = new Group(level, size, populate, parentGroup, mutability, capacity);
        assertTrue(testGroup1.equals(testGroup2));
    }

    @Test
    public void cloneProducesIdenticalGroup() {
        Group testGroup1 = new Group(level, size, populate, parentGroup, mutability, capacity);
        assertTrue(testGroup1.equals((Group)testGroup1.clone(null)));
    }
    
    // =============
    // MUTATE ENTITY
    // =============

    @Test
    public void groupStillSameAfterMutationIfMutationFactorIsZero() {
        Group testGroup1 = new Group(level, size, populate, parentGroup, 0, capacity);
        Group testGroup2 = new Group(level, size, populate, parentGroup, 0, capacity);
        testGroup2.mutateEntity();
        assertTrue(testGroup1.equals(testGroup2));
    }

    @Test
    public void groupNotSameAfterMutationIfMutationFactorIsHundred() {
        Group testGroup1 = new Group(level, size, populate, parentGroup, 100, capacity);
        Group testGroup2 = new Group(level, size, populate, parentGroup, 100, capacity);
        testGroup2.mutateEntity();
        assertFalse(testGroup1.equals(testGroup2));
    }

}
