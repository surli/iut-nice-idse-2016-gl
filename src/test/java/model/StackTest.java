package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import fr.unice.idse.model.*;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public class StackTest {

	Stack stack;
	Deck deck;
	Card oneBlue, twoRed, threeYellow, fourGreen, drawTwo, wild;
	
	@Before
	public void initialize(){
		stack = new Stack(); 
		deck = new Deck();
		oneBlue = new Card(Value.One, Color.Blue);
		twoRed = new Card(Value.Two, Color.Red);
		threeYellow = new Card(Value.Three, Color.Yellow);
		fourGreen = new Card(Value.Four, Color.Green);
		drawTwo = new Card(Value.DrawTwo, Color.Black);
		wild = new Card(Value.Wild, Color.Black);
	}

	@Test
	public void talonInitialiseEstVide(){
		assertTrue(stack.isEmpty());
	}
	
	@Test
	public void addCardAjouteBienLaCarteAuTalon(){
		stack.addCard(oneBlue);
		assertEquals(oneBlue, stack.topCard());
	}
	
	@Test
	public void methodeCountCardsRenvoieLeBonNombreDeCartes(){
		stack.addCard(oneBlue);
		stack.addCard(twoRed);
		stack.addCard(threeYellow);
		stack.addCard(drawTwo);
		
		assertEquals(stack.countCards(),stack.getStack().size());
	}
	
	@Test
	public void methodeIsEmptyRenvoieVraiSiLeTalonEstVide(){
		stack.clearStack();
		assertTrue(stack.isEmpty());
	}
	
	@Test
	public void methodeIsEmptyRenvoieFauxSiLeTalonNEstPasVide(){
		stack.addCard(oneBlue);
		stack.addCard(twoRed);
		assertFalse(stack.isEmpty());
	}
	
	@Test
	public void methodeClearStackVideLeTalon(){
		stack.addCard(threeYellow);
		stack.addCard(drawTwo);
		stack.clearStack();
		assertTrue(stack.isEmpty());
	}
	
	@Test
	public void topCardRetourneLaCarteSuperieure(){
		stack.addCard(oneBlue);
		stack.addCard(twoRed);
		stack.addCard(threeYellow);
		stack.addCard(drawTwo);
		assertEquals(drawTwo, stack.topCard());
	}
	
	@Test
	public void changeColorChangeBienLaCouleurDeLaCarteSuperieure(){
		stack.addCard(oneBlue);
		stack.addCard(twoRed);
		stack.changeColor(Color.Yellow);
		assertEquals(Color.Yellow, stack.topCard().getColor());
	}
	
	@Test
	public void initStackInitialiseLeTalonAvecUneCarte(){
		deck.initDeck();
		stack.initStack(deck);
		assertEquals(1, stack.countCards());
	}
	
	@Test
	public void setStackRemplitLeTalonAvecUneArrayListDonnee(){
		ArrayList<Card> listCard = new ArrayList<Card>();
		listCard.add(oneBlue);
		listCard.add(twoRed);
		listCard.add(threeYellow);
		stack.setStack(listCard);
		assertEquals(listCard, stack.getStack());
	}
}
