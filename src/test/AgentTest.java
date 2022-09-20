package src.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Contains unit tests for Agent class
 * @author Chris Litting
 * @version 1.0
 */
public class AgentTest {
    
    // =========
    // MOCK DATA
    // =========

    private static src.main.Group parentGroup = null;
    private static int mutability = 50;
    private static int[] weightings = {0, 0, 0};

    // ================
    // EQUALS AND CLONE
    // ================

    @Test
    public void agentEqualsIdenticalAgent() {
        src.main.Agent testAgent1 = new src.main.Agent(mutability, weightings, parentGroup);
        src.main.Agent testAgent2 = new src.main.Agent(mutability, weightings, parentGroup);
        assertTrue(testAgent1.equals(testAgent2));
    }

    @Test
    public void cloneProducesIdenticalAgent() {
        src.main.Agent testAgent1 = new src.main.Agent(mutability, weightings, parentGroup);
        assertTrue(testAgent1.equals((src.main.Agent)testAgent1.clone(null)));
    }
    
    // =============
    // MUTATE ENTITY
    // =============

    @Test
    public void groupStillSameAfterMutationIfMutationFactorIsZero() {
        src.main.Agent testAgent1 = new src.main.Agent(0, weightings, parentGroup);
        src.main.Agent testAgent2 = new src.main.Agent(0, weightings, parentGroup);
        testAgent2.mutateEntity();
        assertTrue(testAgent1.equals(testAgent2));
    }

    @Test
    public void groupNotSameAfterMutationIfMutationFactorIsHundred() {
        src.main.Agent testAgent1 = new src.main.Agent(100, weightings, parentGroup);
        src.main.Agent testAgent2 = new src.main.Agent(100, weightings, parentGroup);
        testAgent2.mutateEntity();
        assertFalse(testAgent1.equals(testAgent2));
    }

    // ==========
    // COMPARE TO
    // ==========

    @Test
    public void compareToLowerRankedAgentReturnsMinusOne() {
        src.main.Agent testAgent1 = new src.main.Agent(mutability, weightings, parentGroup);
        src.main.Agent testAgent2 = new src.main.Agent(mutability, weightings, parentGroup);
        testAgent1.setContribution(0, 1);
        testAgent2.setContribution(0, 0);
        assertTrue(testAgent1.compareTo(testAgent2) == -1);
    }

    @Test
    public void compareToHigherRankedAgentReturnsOne() {
        src.main.Agent testAgent1 = new src.main.Agent(mutability, weightings, parentGroup);
        src.main.Agent testAgent2 = new src.main.Agent(mutability, weightings, parentGroup);
        testAgent1.setContribution(0, 1);
        testAgent2.setContribution(0, 0);
        assertTrue(testAgent2.compareTo(testAgent1) == 1);
    }

    @Test
    public void compareToEqualRankedAgentReturnsZero() {
        src.main.Agent testAgent1 = new src.main.Agent(mutability, weightings, parentGroup);
        src.main.Agent testAgent2 = new src.main.Agent(mutability, weightings, parentGroup);
        assertTrue(testAgent1.compareTo(testAgent2) == 0);
    }

}
