package model.player;

import fr.unice.idse.model.player.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IAFactoryTest {

    /* ------------ testSetIA ----------------------------- */
    @Test
	public void testSetIA(){
        IAEasy expectedEasy = new IAEasy("testSetIAEasy", 1);
        IA monTestIAEasy = IAFactory.setIA("testSetIAEasy", 1);

        IAMedium expectedMedium = new IAMedium("testSetIAMedium", 2);
        IA monTestIAMedium = IAFactory.setIA("testSetIAMedium", 2);

        IAHard expectedHard = new IAHard("testSetIAHard", 3);
        IA monTestIAHard = IAFactory.setIA("testSetIAHard", 3);

        assertEquals(expectedEasy, monTestIAEasy);
        assertEquals(expectedMedium, monTestIAMedium);
        assertEquals(expectedHard, monTestIAHard);
    }
}
