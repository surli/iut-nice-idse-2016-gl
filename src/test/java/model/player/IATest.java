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

public class IATest {

    private IA iaEasy;
    private IA iaHard;
    private IA iaDifficulty;
    private Game game;
    private Game game2;
    private Player host;
    private Player host2;

    @Before
    public void initialize(){

        iaEasy = IAFactory.getIA("testIAEasy", "", 1);
        iaHard = IAFactory.getIA("testIAHard", "", 3);
        iaDifficulty = IAFactory.getIA("testIADifficulty", "", 3);

        ArrayList<Card> cards=new ArrayList<Card>();

        cards.add(new Card(Value.Three, Color.Blue));
        cards.add(new Card(Value.Six, Color.Green));

        iaEasy.setCards(cards);
        iaHard.setCards(cards);

        host = new Player("host","host");
        game = new Game(host,"game",4);

        host2 = new Player("host2","host2");
        game2 = new Game(host2,"game",4);

        game.addPlayer(iaHard);
        game.getStack().addCard(new Card(Value.Four, Color.Blue));
        game.setActualColor(Color.Blue);

        game2.addPlayer(iaEasy);
        game2.getStack().addCard(new Card(Value.Eight, Color.Red));
        game2.setActualColor(Color.Red);
        game2.getDeck().getDeck().add(new Card(Value.Five, Color.Blue));
    }

    /* ------------ testDifficulty ----------------------------- */
    @Test
    public void testGetDifficulty() {
        assertEquals(3, iaHard.getDifficulty());
    }

    @Test
    public void testSetDifficulty() {
        iaDifficulty.setDifficulty(2);
        assertEquals(2, iaDifficulty.getDifficulty());
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

    /* ------------ testPlayCard ----------------------------- */
    @Test
    public  void testPlayCard() {
        Card cardExpected = new Card(Value.Three, Color.Blue);
        iaEasy.playCard(game, iaEasy.getCards().get(0), iaEasy.getCards(), true);
        assertEquals(cardExpected, game.getStack().topCard());
    }

    @Test
    public  void testPlayCardDrawCard() {
        int sizeExpected = iaEasy.getCards().size() +1; // +1 Correspond à la carte pioche
        iaEasy.playCard(game2, iaEasy.getCards().get(0), iaEasy.getCards(), false);

        assertEquals(sizeExpected, iaEasy.getCards().size());
    }
}