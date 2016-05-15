package model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;
import fr.unice.idse.model.player.IA;
import fr.unice.idse.model.player.IAFactory;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.model.player.IAHard;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class IAHardTest {
    private IA iaHard;
    private IAHard iaHard1;
    private Game game;
    private Game game1;
    private Player host;
    private Player host1;

    @Before
    public void initialize(){

        iaHard = IAFactory.setIA("testIAHard", 3);


        iaHard1 = new IAHard("testIAHard1", 3);

        ArrayList<Card> cards=new ArrayList<Card>();

        cards.add(new Card(Value.Three, Color.Blue));
        cards.add(new Card(Value.Six, Color.Green));

        iaHard.setCards(cards);

        
        ArrayList<Card> cards1 =new ArrayList<Card>();

        cards1.add(new Card(Value.Five, Color.Yellow));
        cards1.add(new Card(Value.Nine, Color.Red));
        cards1.add(new Card(Value.DrawTwo, Color.Red));
        
        iaHard1.setCards(cards1);
        
        host1 = new Player("host1","host1");
        game1 = new Game(host1,"game1",4);

        game1.addPlayer(iaHard);
        
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
    
    /* ------------ searchCardDrawTwo ----------------------------- */
    @Test
    public void searchCardDrawTwoTrue() {

        boolean expected = true;
        boolean drawTwoExist = iaHard1.searchCardDrawTwo(iaHard1.getCards(), Value.DrawTwo, Color.Red);
        assertEquals(expected, drawTwoExist);
    }

    @Test
    public void searchCardDrawTwoFalse() {

        boolean expected = false;
        boolean drawTwoExist = iaHard1.searchCardDrawTwo(iaHard1.getCards(), Value.DrawTwo, Color.Yellow);
        assertEquals(expected, drawTwoExist);
    }
   
    /*--------------testMyCard---------------------------------------*/
    
    @Test
    public void testMyCard(){
    	Card expected = new Card(Value.Six, Color.Green);
    	iaHard1.setMyCard(expected);
    	assertEquals (expected, iaHard1.getMyCard());
    }
    
    /*--------------testTurnPlay---------------------------------------*/
    @Test
    public void testTurnPlay() {
    	boolean expected = true;
    	iaHard1.setTurnPlay(true);
    	assertEquals(expected,iaHard1.getTurnPlay());
    }
    

    /* ------------ testBestColor ----------------------------- */
    @Test
    public void testBestColor() {
        Color expected = Color.Green;
        iaHard1.setBestColor(Color.Green);
        assertEquals(expected, iaHard1.getBestColor());
    }
    
    /* ------------ testCardContre ----------------------------- */
    @Test
    public void testCardContre() {
        Card expected = new Card(Value.Nine, Color.Yellow);
        iaHard1.setCardContre(new Card(Value.Nine, Color.Yellow));
        assertEquals(expected, iaHard1.getCardContre());
    }
}
