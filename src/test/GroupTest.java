package src.test;

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
    private static src.main.Group parentGroup = null;
    private static int mutability = 50;
    private static int capacity = 10;

    // ================
    // EQUALS AND CLONE
    // ================

    @Test
    public void GroupEqualsIdenticalGroup() {
        src.main.Group testGroup1 = new src.main.Group(level, size, populate, parentGroup, mutability, capacity);
        src.main.Group testGroup2 = new src.main.Group(level, size, populate, parentGroup, mutability, capacity);
        assertTrue(testGroup1.equals(testGroup2));
    }

    @Test
    public void cloneProducesIdenticalGroup() {
        src.main.Group testGroup1 = new src.main.Group(level, size, populate, parentGroup, mutability, capacity);
        assertTrue(testGroup1.equals((src.main.Group)testGroup1.clone(null)));
    }
    
    // =============
    // MUTATE ENTITY
    // =============

    @Test
    public void groupStillSameAfterMutationIfMutationFactorIsZero() {
        src.main.Group testGroup1 = new src.main.Group(level, size, populate, parentGroup, 0, capacity);
        src.main.Group testGroup2 = new src.main.Group(level, size, populate, parentGroup, 0, capacity);
        testGroup2.mutateEntity();
        assertTrue(testGroup1.equals(testGroup2));
    }

    @Test
    public void groupNotSameAfterMutationIfMutationFactorIsHundred() {
        src.main.Group testGroup1 = new src.main.Group(level, size, populate, parentGroup, 100, capacity);
        src.main.Group testGroup2 = new src.main.Group(level, size, populate, parentGroup, 100, capacity);
        testGroup2.mutateEntity();
        assertFalse(testGroup1.equals(testGroup2));
    }

}
