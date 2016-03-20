package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import fr.unice.idse.model.player.IAEasy;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;
import fr.unice.idse.model.player.*;

public class PlayerTest {

	private Player player;
	private IAEasy player2;
	
	@Before
	public void initialize(){
		player = new Player("test","");
		IAEasy player2 = new IAEasy("Test","tok2",1);
		
		ArrayList<Card> cards=new ArrayList<Card>();
		
		cards.add(new Card(Value.DrawTwo, Color.Green));
		cards.add(new Card(Value.DrawFour, Color.Black));
		cards.add(new Card(Value.Six, Color.Red));
		cards.add(new Card(Value.Eight, Color.Blue));
		cards.add(new Card(Value.Six, Color.Blue));
		cards.add(new Card(Value.Two, Color.Green));
		
		ArrayList<Card> mainIA = new ArrayList<Card>();
		
		mainIA.add(new Card(Value.Two, Color.Blue));

		player.setCards(cards);
		player.sortCards();
		player2.setCards(mainIA);
		
	}

	@Test
	public void testDuTriDesCartesDuJoueur()
	{
		ArrayList<Card> expected=new ArrayList<Card>();
		
		expected.add(new Card(Value.Six, Color.Blue));
		expected.add(new Card(Value.Eight, Color.Blue));
		expected.add(new Card(Value.Six, Color.Red));
		expected.add(new Card(Value.Two, Color.Green));
		expected.add(new Card(Value.DrawTwo, Color.Green));
		expected.add(new Card(Value.DrawFour, Color.Black));
		
		assertEquals(expected, player.getCards());
	}
	
	@Test
	public void  testDuChangeColorDuJoueur()
	{
		Color colorExpected = Color.Blue;
		
		assertEquals(colorExpected, IAEasy.chooseColor(player2.getCards()));
		
	}
}
