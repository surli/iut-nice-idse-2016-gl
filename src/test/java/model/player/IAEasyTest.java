package model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.Stack;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class IAEasyTest {
    //TODO changeColor, chooseCardToPlay

    private fr.unice.idse.model.player.IAEasy iaEasy;
    //private Stack stackJeu;
    private Game game;

    @Before
    public void initialize(){
        iaEasy = new fr.unice.idse.model.player.IAEasy("test","",0);

        ArrayList<Card> cards=new ArrayList<Card>();

        cards.add(new Card(Value.DrawTwo, Color.Green));
        cards.add(new Card(Value.DrawFour, Color.Black));
        cards.add(new Card(Value.Six, Color.Red));
        cards.add(new Card(Value.Eight, Color.Blue));
        cards.add(new Card(Value.Six, Color.Blue));
        cards.add(new Card(Value.Two, Color.Green));

        //stackJeu = new Stack();
        //stackJeu.addCard(new Card(Value.Five, Color.Red));

        iaEasy.setCards(cards);
    }


    @Test
    public void chooseColor() {
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
