package Program;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EntityTest {

    // =============
    // MUTATE METHOD
    // =============

    @Test
    public void valueIsUnchangedWhenMutationFactorIsZero() {
        assertEquals(50, Entity.mutate(50, 0, 0, 100));
    }

    @Test
    public void valueChangesByOneWhenMutationFactorIsOneHundred() {
        int x = Entity.mutate(50, 100, 0, 100);
        assertTrue(x == 49 || x == 51);
    }
    
    @Test
    public void valueMutatesUpFromMinWhenMin() {
        int x = Entity.mutate(0, 100, 0, 100);
        assertTrue(x == 1);
    }

    @Test
    public void valueMutatesDownFromMax() {
        int x = Entity.mutate(100, 100, 0, 100);
        assertTrue(x == 99);
    }

    @Test
    public void minAndMaxCanBeExtremeValues() {
        assertEquals(50, Entity.mutate(50, 0, -2147483648, 2147483647));
    }


    @Test
    public void valueDoesntChangeWhenMinEqualsMax() {
        assertEquals(50, Entity.mutate(50, 100, 50, 50));
    }
    
}
