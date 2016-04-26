package model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;
import fr.unice.idse.model.player.IA;
import fr.unice.idse.model.player.IAFactory;
import fr.unice.idse.model.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class IAHardTest {
    private IA iaHard;
    private Game game;
    private Player host;

    @Before
    public void initialize(){
        iaHard = IAFactory.getIA("testIAHard", "", 3);
        ArrayList<Card> cards=new ArrayList<Card>();

        cards.add(new Card(Value.Three, Color.Blue));
        cards.add(new Card(Value.Six, Color.Green));

        iaHard.setCards(cards);

        host = new Player("host","host");
        game = new Game(host,"game",4);

        game.addPlayer(iaHard);

    }

    /* ------------ testSearchColorCard ----------------------------- */
    @Test
    public void testSearchColorCardTrue() {

        boolean expected = true;
        boolean colorExist = iaHard.searchColorCard(iaHard.getCards(), Color.Green);
        assertEquals(expected, colorExist);
    }

    @Test
    public void testSearchColorCardFalse() {

        boolean expected = false;
        boolean colorExist = iaHard.searchColorCard(iaHard.getCards(), Color.Red);
        assertEquals(expected, colorExist);
    }

    /* ------------ testSearchValueCard ----------------------------- */

    @Test
    public void testSearchValueCardTrue() {

        boolean expected = true;
        boolean valueExist = iaHard.searchValueCard(iaHard.getCards(), Value.Six);
        assertEquals(expected, valueExist);
    }

    @Test
    public void testSearchValueCardFalse() {

        boolean expected = false;
        boolean valueExist = iaHard.searchValueCard(iaHard.getCards(), Value.Nine);
        assertEquals(expected, valueExist);
    }
}
