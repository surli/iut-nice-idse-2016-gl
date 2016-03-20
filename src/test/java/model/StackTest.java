package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.unice.idse.model.*;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public class StackTest {

	Stack stack;
	Deck deck;
	
	@Before
	public void initialize(){
		stack = new Stack(); 
	}

	@Test
	public void talonInitialiseEstVide(){
		assertTrue(stack.isEmpty());
	}
	
	@Test
	public void methodeCountCardsRenvoieLeBonNombreDeCartes(){
		stack.addCard(new Card(Value.One,Color.Green));
		stack.addCard(new Card(Value.Four,Color.Red));
		stack.addCard(new Card(Value.Six,Color.Yellow));
		
		assertEquals(stack.countCards(),stack.getStack().size());
	}
	
	@Test
	public void methodeIsEmptyRenvoieVraiSiLeTalonEstVide(){
		stack.clearStack();
		assertTrue(stack.isEmpty());
	}
	
	@Test
	public void methodeIsEmptyRenvoieFauxSiLeTalonNEstPasVide(){
		stack.addCard(new Card(Value.Four,Color.Red));
		stack.addCard(new Card(Value.Six,Color.Yellow));
		assertFalse(stack.isEmpty());
	}
}
