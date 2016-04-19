package model.player;

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
    }

    @Test
    public void testChooseColor() {
        Color expected = Color.Green;

        assertEquals(expected, iaEasy.chooseColor(iaEasy.getCards()));
    }


    /*
    @Test
    public void changeColor() {
    }
    */

    /* BESOIN DE CREER UNE GAME POUR TESTER LE FONCTIONNEMET DE L IA
    @Test
    public void chooseCardToPlay() {
        Card expected = new Card(Value.Six, Color.Red);

        assertEquals(expected, iaEasy.chooseCardToPlay(iaEasy.getCards(), game.playableCards(), game));
    }
    */
}
