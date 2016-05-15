package model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;
import fr.unice.idse.model.player.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class IAEasyTest {

    private IAEasy iaEasy;
    private IAEasy iaEasy2;
    private Game game;
    private Player host;

    @Before
    public void initialize(){

        iaEasy = new IAEasy("testIAEasy", 1);
        iaEasy2 = new IAEasy("testIAEasyGame", 1);

        ArrayList<Card> cards =new ArrayList<Card>();
        ArrayList<Card> cards2 =new ArrayList<Card>();

        cards.add(new Card(Value.Wild, Color.Black));
        cards.add(new Card(Value.Six, Color.Red));
        cards.add(new Card(Value.DrawFour, Color.Black));
        cards.add(new Card(Value.Eight, Color.Blue));
        cards.add(new Card(Value.Six, Color.Blue));
        cards.add(new Card(Value.Seven, Color.Green));

        cards2.add(new Card(Value.Six, Color.Red));
        cards2.add(new Card(Value.Seven, Color.Green));
        cards2.add(new Card(Value.Eight, Color.Blue));
        cards.add(new Card(Value.Wild, Color.Black));

        iaEasy.setCards(cards);
        iaEasy2.setCards(cards2);

        host = new Player("host","host");
        game = new Game(host,"game",4);

        game.addPlayer(iaEasy2);
        game.getStack().addCard(new Card(Value.Four, Color.Green));
        game.setActualColor(Color.Green);

    }

    /* ------------ testThinking ----------------------------- */
    @Test
    public void testThinking() {
        Card expected = new Card(Value.Seven, Color.Green);
        iaEasy2.thinking(game);
        assertEquals(expected, game.getStack().topCard());
    }

    /* ------------ testMyCard ----------------------------- */
    @Test
    public void testMyCard() {
        Card expected = new Card(Value.Nine, Color.Red);
        iaEasy.setMyCard(expected);
        assertEquals(expected, iaEasy.getMyCard());
    }

    /* ------------ testTurnPlay ----------------------------- */
    @Test
    public void testTurnPlay() {
        boolean expected = true;
        iaEasy.setTurnPlay(true);
        assertEquals(expected, iaEasy.getTurnPlay());
    }


    /* ------------ testChooseCardToPlay ----------------------------- */
    @Test
    public void testChooseCardToPlay() {
        Card expected = new Card(Value.Wild, Color.Black);
        assertEquals(expected, iaEasy.chooseCardToPlay(iaEasy.getCards()));
    }

    /* ------------ testChooseColor ----------------------------- */
    @Test
    public void testChooseColor() {
        Color expected = Color.Red;
        assertEquals(expected, iaEasy.chooseColor(iaEasy.getCards()));
    }

    /* ------------ testChangeColor ----------------------------- */
    @Test
    public void testChangeColor() {
        Color colorExpected = Color.Red;
        iaEasy.playCard(game, iaEasy.getCards().get(0), iaEasy.getCards(), true); // Change couleur qui est joué

        assertEquals(colorExpected, game.getActualColor());
    }
}
