package model.player;

import fr.unice.idse.model.player.IA;
import fr.unice.idse.model.player.IAFactory;
import fr.unice.idse.model.player.IAMedium;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IAFactoryTest {

    @Test
    public void testGetIA(){
        IAMedium expected = new IAMedium("testGetIA", "", 2);
        IA monTestIA = IAFactory.getIA("testGetIA", "", 2);

        assertEquals(expected, monTestIA);
    }
}
