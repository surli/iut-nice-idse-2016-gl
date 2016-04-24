package model.player;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import fr.unice.idse.model.player.IAEasy;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public class PlayerTest {

	private Player player;
	
	@Before
	public void initialize(){
		player = new Player("test","");
		
		ArrayList<Card> cards=new ArrayList<Card>();
		
		cards.add(new Card(Value.DrawTwo, Color.Green));
		cards.add(new Card(Value.DrawFour, Color.Black));
		cards.add(new Card(Value.Six, Color.Red));
		cards.add(new Card(Value.Eight, Color.Blue));
		cards.add(new Card(Value.Six, Color.Blue));
		cards.add(new Card(Value.Two, Color.Green));

		player.setCards(cards);
		player.sortCards();
	}

	@Test
	public void testDuTriDesCartesDuJoueur() {
		ArrayList<Card> expected = new ArrayList<Card>();

		expected.add(new Card(Value.Six, Color.Blue));
		expected.add(new Card(Value.Eight, Color.Blue));
		expected.add(new Card(Value.Two, Color.Green));
		expected.add(new Card(Value.DrawTwo, Color.Green));
		expected.add(new Card(Value.Six, Color.Red));
		expected.add(new Card(Value.DrawFour, Color.Black));

		assertEquals(expected, player.getCards());
	}
}
