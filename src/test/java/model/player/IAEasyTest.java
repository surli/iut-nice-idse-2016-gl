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
    //TODO changeColor, chooseCardToPlay

    private IA iaEasy;
    private IAEasy iaEasy2;
    private Game game;
    private Player host;

    @Before
    public void initialize(){

        iaEasy = IAFactory.getIA("testGetIA", "", 1);

        ArrayList<Card> cards=new ArrayList<Card>();

        cards.add(new Card(Value.DrawTwo, Color.Green));
        cards.add(new Card(Value.DrawFour, Color.Black));
        cards.add(new Card(Value.Six, Color.Red));
        cards.add(new Card(Value.Eight, Color.Blue));
        cards.add(new Card(Value.Six, Color.Blue));
        cards.add(new Card(Value.Two, Color.Green));

        iaEasy.setCards(cards);

        host=new Player("host","host");
        game = new Game(host,"game",4);

        iaEasy2 = new IAEasy("testIA2", "", 1);
        ArrayList<Card> cards2 = new ArrayList<Card>();

        cards2.add(new Card(Value.Wild, Color.Black));
        cards2.add(new Card(Value.Six, Color.Red));

        iaEasy2.setCards(cards2);

        game.addPlayer(iaEasy2);
        game.getStack().addCard(new Card(Value.Four, Color.Green));

    }

    @Test
    public void testChooseColor() {
        Color expected = Color.Green;

        assertEquals(expected, iaEasy.chooseColor(iaEasy.getCards()));
    }

    @Test
    public void testChangeColor() {
        Color colorExpected = Color.Red;
        iaEasy2.playCard(game, iaEasy2.getCards().get(0), iaEasy2.getCards(), true); // Change couleur qui est joué

        assertEquals(colorExpected, game.getActualColor());
    }
}
