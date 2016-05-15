package model.player;

import fr.unice.idse.model.player.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class IAFactoryTest {

    /* ------------ testSetIA ----------------------------- */
    @Test
	public void testSetIA(){
        IA monTestIAEasy = IAFactory.getIA("testSetIAEasy", 1);

        IA monTestIAMedium = IAFactory.getIA("testSetIAMedium", 2);

        IA monTestIAHard = IAFactory.getIA("testSetIAHard", 3);

        assertTrue(monTestIAEasy instanceof IAEasy);
        assertTrue(monTestIAMedium instanceof IAMedium);
        assertTrue(monTestIAHard instanceof IAHard);
    }
}
