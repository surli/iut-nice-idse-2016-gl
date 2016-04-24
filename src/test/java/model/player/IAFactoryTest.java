package model.player;

import fr.unice.idse.model.player.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IAFactoryTest {

    @Test
    public void testGetIA(){
        IAEasy expectedEasy = new IAEasy("testGetIAEasy", "", 1);
        IA monTestIAEasy = IAFactory.getIA("testGetIAEasy", "", 1);

        IAMedium expectedMedium = new IAMedium("testGetIAMedium", "", 2);
        IA monTestIAMedium = IAFactory.getIA("testGetIAMedium", "", 2);

        IAHard expectedHard = new IAHard("testGetIAHard", "", 3);
        IA monTestIAHard = IAFactory.getIA("testGetIAHard", "", 3);

        assertEquals(expectedEasy, monTestIAEasy);
        assertEquals(expectedMedium, monTestIAMedium);
        assertEquals(expectedHard, monTestIAHard);
    }
}
