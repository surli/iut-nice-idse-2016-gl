package model;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public class CardTest {

	@Test
	public void carteDeMemeValeur() {
		Card card1=new Card(Value.Five, Color.Blue);
		Card card2=new Card(Value.Five, Color.Green);
		
		assertTrue(card1.sameValue(card2));
	}
	
	@Test
	public void cartePasDeMemeValeur() {
		Card card1=new Card(Value.Five, Color.Blue);
		Card card2=new Card(Value.Six, Color.Green);
		
		assertFalse(card1.sameValue(card2));
	}

	@Test
	public void carteDeMemeCouleur() {
		Card card1=new Card(Value.Six, Color.Green);
		Card card2=new Card(Value.Five, Color.Green);
		
		assertTrue(card1.sameColor(card2));
	}
	
	@Test
	public void cartePasDeMemeCouleur() {
		Card card1=new Card(Value.Five, Color.Blue);
		Card card2=new Card(Value.Five, Color.Green);
		
		assertFalse(card1.sameColor(card2));
	}
}
