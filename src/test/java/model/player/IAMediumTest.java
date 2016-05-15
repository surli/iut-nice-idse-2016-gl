package model.player;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;
import fr.unice.idse.model.player.IAMedium;
import fr.unice.idse.model.player.Player;
public class IAMediumTest {
    
    private IAMedium iaMedium;
    private IAMedium iaMedium2;
    private Game game;
    private Player host;
    @Before
    public void initialize(){
        iaMedium  = new IAMedium("testIAMedium", 2);
        iaMedium2 = new IAMedium("testIAMediumGame", 2);
        ArrayList<Card> cards  = new ArrayList<Card>();
        ArrayList<Card> cards2 = new ArrayList<Card>();
        cards.add(new Card(Value.Wild, Color.Black));
        cards.add(new Card(Value.Six, Color.Red));
        cards.add(new Card(Value.DrawFour, Color.Black));
        cards.add(new Card(Value.Eight, Color.Blue));
        cards.add(new Card(Value.Six, Color.Blue));
        cards.add(new Card(Value.Seven, Color.Green));
        
        cards2.add(new Card(Value.Six, Color.Red));
        cards2.add(new Card(Value.Seven, Color.Green));
        cards2.add(new Card(Value.Eight, Color.Blue));
        iaMedium.setCards(cards);
        iaMedium2.setCards(cards2);
        host = new Player("host","host");
        game = new Game(host,"game",4);
        game.addPlayer(iaMedium2);
        game.getStack().addCard(new Card(Value.Four, Color.Green));
        game.setActualColor(Color.Green);
    }
    
    
    /* ------------ testMyCard ----------------------------- */
    @Test
    public void testMyCard() {
        Card expected = new Card(Value.Nine, Color.Red);
        iaMedium.setMyCard(expected);
        assertEquals(expected, iaMedium.getMyCard());
    }
    
    /* ------------ testTurnPlay ----------------------------- */
    @Test
    public void testTurnPlay() {
        boolean expected = true;
        iaMedium.setTurnPlay(true);
        assertEquals(expected, iaMedium.getTurnPlay());
    }
    
    /* ------------ testChooseColor ----------------------------- */
    @Test
    public void testChooseColor() {
        Color expected = Color.Red;
        iaMedium.setBestColor(Color.Red);
        assertEquals(expected, iaMedium.chooseColor(iaMedium.getCards()));
    }
    
    /* ------------ testChangeColor -----------------------------  */
    @Test
    public void testChangeColor() {
        Color colorExpected = Color.Red;
        iaMedium.setBestColor(Color.Red);
        iaMedium.playCard(game, iaMedium.getCards().get(0), iaMedium.getCards(), true); // Change la couleur qui est jouée
        assertEquals(colorExpected, game.getActualColor());
    }
}