package Program;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AgentTest {
    
    // =========
    // MOCK DATA
    // =========

    private static Group parentGroup = null;
    private static int mutability = 50;
    private static int[] weightings = {0, 0, 0};

    // ================
    // EQUALS AND CLONE
    // ================

    @Test
    public void agentEqualsIdenticalAgent() {
        Agent testAgent1 = new Agent(mutability, weightings, parentGroup);
        Agent testAgent2 = new Agent(mutability, weightings, parentGroup);
        assertTrue(testAgent1.equals(testAgent2));
    }

    @Test
    public void cloneProducesIdenticalAgent() {
        Agent testAgent1 = new Agent(mutability, weightings, parentGroup);
        assertTrue(testAgent1.equals((Agent)testAgent1.clone(null)));
    }
    
    // =============
    // MUTATE ENTITY
    // =============

    @Test
    public void groupStillSameAfterMutationIfMutationFactorIsZero() {
        Agent testAgent1 = new Agent(0, weightings, parentGroup);
        Agent testAgent2 = new Agent(0, weightings, parentGroup);
        testAgent2.mutateEntity();
        assertTrue(testAgent1.equals(testAgent2));
    }

    @Test
    public void groupNotSameAfterMutationIfMutationFactorIsHundred() {
        Agent testAgent1 = new Agent(100, weightings, parentGroup);
        Agent testAgent2 = new Agent(100, weightings, parentGroup);
        testAgent2.mutateEntity();
        assertFalse(testAgent1.equals(testAgent2));
    }


}
