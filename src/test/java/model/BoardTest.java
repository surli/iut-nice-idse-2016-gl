package model;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.Card;
import fr.unice.idse.model.Color;
import fr.unice.idse.model.Player;

public class BoardTest 
{

	@Test
	public void testInitBoard() 
	{
		Board board = new Board();
		board.getPlayers().add(new Player("toto"));
		board.getPlayers().add(new Player("test"));
		board.init();
		assertFalse(board.getDeck().isEmpty());
		assertTrue(board.getPlayers().get(0).getCards().size()>0);
		assertTrue(board.getPlayers().get(1).getCards().size()>0);
		assertTrue(board.getStack().getStack().size()>0);
	}

	@Test
	public void testCarteBonneValeurEstJouable()
	{
		Board board = new Board();
		board.getStack().addCard(new Card(8, Color.Noir));
		board.changeColor(board.getStack().topCard().getColor());
		assertTrue(board.AskPlayableCard(new Card(8, Color.Rouge)));
	}
	
	@Test
	public void testCarteMauvaiseValeurEstNonJouable()
	{
		Board board = new Board();
		board.getStack().addCard(new Card(8, Color.Bleu));
		board.changeColor(board.getStack().topCard().getColor());
		assertFalse(board.AskPlayableCard(new Card(7, Color.Jaune)));
	}
	
	@Test
	public void testCarteBonneCouleurEtMauvaiseValeurEstJouable()
	{
		Board board = new Board();
		board.getStack().addCard(new Card(8, Color.Rouge));
		board.changeColor(board.getStack().topCard().getColor());
		assertTrue(board.AskPlayableCard(new Card(7, Color.Rouge)));
	}
	
	@Test
	public void testCarteMauvaiseCouleurEtMauvaiseValeurEstNonJouable()
	{
		Board board = new Board();
		board.getStack().addCard(new Card(8, Color.Vert));
		board.changeColor(board.getStack().topCard().getColor());
		assertFalse(board.AskPlayableCard(new Card(7, Color.Rouge)));
	}
	
	@Test
	public void testJoueurPeutJouer()
	{
		Board board = new Board();
		board.getPlayers().add(new Player("toto"));
		board.getActualPlayer().getCards().add(new Card(7, Color.Rouge));
		board.getStack().addCard(new Card(8, Color.Rouge));
		board.changeColor(board.getStack().topCard().getColor());
		assertTrue(board.AskPlayerCanPlay(board.getActualPlayer()));
	}
}
