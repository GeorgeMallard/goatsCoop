package src.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Contains unit tests for Group class
 * @author Chris Litting
 * @version 1.0
 */
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

    // ==========
    // COMPARE TO
    // ==========

    @Test
    public void compareToLowerRankedGroupReturnsMinusOne() {
        src.main.Group testGroup1 = new src.main.Group(level, size, populate, parentGroup, 100, capacity);
        src.main.Group testGroup2 = new src.main.Group(level, size, populate, parentGroup, 100, capacity);
        testGroup1.setContribution(testGroup1.getLevel() -1, 1);
        testGroup2.setContribution(testGroup2.getLevel() -1, 0);
        assertTrue(testGroup1.compareTo(testGroup2) == -1);
    }

    @Test
    public void compareToHigherRankedGroupReturnsOne() {
        src.main.Group testGroup1 = new src.main.Group(level, size, populate, parentGroup, 100, capacity);
        src.main.Group testGroup2 = new src.main.Group(level, size, populate, parentGroup, 100, capacity);
        testGroup1.setContribution(testGroup1.getLevel() -1, 1);
        testGroup2.setContribution(testGroup2.getLevel() -1, 0);
        assertTrue(testGroup2.compareTo(testGroup1) == 1);
    }

    @Test
    public void compareToEqualRankedGroupReturnsZero() {
        src.main.Group testGroup1 = new src.main.Group(level, size, populate, parentGroup, 100, capacity);
        src.main.Group testGroup2 = new src.main.Group(level, size, populate, parentGroup, 100, capacity);
        assertTrue(testGroup1.compareTo(testGroup2) == 0);
    }

}
