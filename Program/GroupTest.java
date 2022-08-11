package Program;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
