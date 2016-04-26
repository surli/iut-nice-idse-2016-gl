package model.player;

import fr.unice.idse.model.player.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IAFactoryTest {

    /* ------------ testConstructorIAFactory ----------------------------- */
    @Test
    public void testConstructorIAFactory() {
        boolean expected = true;

        boolean testEasy = false;
        IAFactory monIAEasy = new IAFactory("testGetIAEasy", "", 1);
        if(monIAEasy.getName().equals("testGetIAEasy") && monIAEasy.getToken().equals("") && monIAEasy.getDifficulty() == 1) {
            testEasy = true;
        }

        boolean testMedium = false;
        IAFactory monIAMedium = new IAFactory("testGetIAMedium", "", 2);
        if(monIAMedium.getName().equals("testGetIAMedium") && monIAMedium.getToken().equals("") && monIAMedium.getDifficulty() == 2) {
            testMedium = true;
        }

        boolean testHard = false;
        IAFactory monIAHard = new IAFactory("testGetIAHard", "", 3);
        if(monIAHard.getName().equals("testGetIAHard") && monIAHard.getToken().equals("") && monIAHard.getDifficulty() == 3) {
            testHard = true;
        }

        assertEquals(expected, testEasy);
        assertEquals(expected, testMedium);
        assertEquals(expected, testHard);

    }

    /* ------------ testGetIA ----------------------------- */
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
