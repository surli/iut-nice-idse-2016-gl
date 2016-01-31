package model;

import fr.unice.idse.model.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class DeckTest {

	Deck deck;
	
	@Before
	public void initialize(){
		deck = new Deck(); 
	}

	@Test
	public void initializedDeckIsEmpty(){
		assertTrue(deck.isEmpty());
	}
	
	@Test
	public void deckIsNotEmptyAfterInitialize(){
		deck.initDeck();
		assertFalse(deck.isEmpty());
	}
	
	@Test
	public void shuffleMethodCorrectlyShufflesTheDeck(){
		Deck deck2 = new Deck();
		deck.initDeck();
		deck2 = (Deck)deck.clone();
		deck.shuffle();
		assertNotEquals(deck, deck2);
	}
	
	@Test
	public void countCardsMethodReturnsTheExactCountOfCards(){
		deck.initDeck();
		ArrayList<Card> cards = new ArrayList<Card>(deck.getDeck());
		
		assertEquals(cards.size(),deck.countCards());
	}
	
	@Test
	public void topCardMethodReturnsTheCorrectCard(){
		deck.initDeck();
		assertEquals(deck.getDeck().get(deck.countCards()-1),deck.topCard());
	}
	
	@Test
	public void checkIfAllCardsArePresentsAfterInitialize(){
		deck.initDeck();
		int yellow = 0;
		int red = 0;
		int blue = 0;
		int green = 0;
		
		for(int i=0; i<deck.countCards();i++){
			if(deck.getDeck().get(i).getColor().equals(Color.Yellow)){
				yellow++;
			}
			if(deck.getDeck().get(i).getColor().equals(Color.Yellow)){
				red++;
			}
			if(deck.getDeck().get(i).getColor().equals(Color.Yellow)){
				green++;
			}
			if(deck.getDeck().get(i).getColor().equals(Color.Yellow)){
				blue++;
			}
		}
		
		assertEquals(yellow,19);
		assertEquals(red,19);
		assertEquals(green,19);
		assertEquals(blue,19);
	}
	
	@Test
	public void deckIsEmptyAfterClearDeckMethod(){
		deck.initDeck();
		deck.clearDeck();
		assertTrue(deck.isEmpty());
	}
}
