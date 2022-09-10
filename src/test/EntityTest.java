package src.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EntityTest {

    // =============
    // MUTATE METHOD
    // =============

    @Test
    public void valueIsUnchangedWhenMutationFactorIsZero() {
        assertEquals(50, src.main.Entity.mutateValue(50, 0, 0, 100));
    }

    @Test
    public void valueChangesByOneWhenMutationFactorIsOneHundred() {
        int x = src.main.Entity.mutateValue(50, 100, 0, 100);
        assertTrue(x == 49 || x == 51);
    }
    
    @Test
    public void valueMutatesUpFromMinWhenMin() {
        int x = src.main.Entity.mutateValue(0, 100, 0, 100);
        assertTrue(x == 1);
    }

    @Test
    public void valueMutatesDownFromMax() {
        int x = src.main.Entity.mutateValue(100, 100, 0, 100);
        assertTrue(x == 99);
    }

    @Test
    public void minAndMaxCanBeExtremeValues() {
        assertEquals(50, src.main.Entity.mutateValue(50, 0, -2147483648, 2147483647));
    }


    @Test
    public void valueDoesntChangeWhenMinEqualsMax() {
        assertEquals(50, src.main.Entity.mutateValue(50, 100, 50, 50));
    }
    
}
