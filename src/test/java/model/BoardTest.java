package model;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.Player;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public class BoardTest 
{

	@Test
	public void testInitBoard() 
	{
		Board board = new Board();
		board.getPlayers().add(new Player("toto",""));
		board.getPlayers().add(new Player("test",""));
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
		board.getStack().addCard(new Card(Value.Eight, Color.Black));
		board.changeColor(board.getStack().topCard().getColor());
		assertTrue(board.askPlayableCard(new Card(Value.Eight, Color.Red)));
	}
	
	@Test
	public void testCarteMauvaiseValeurEstNonJouable()
	{
		Board board = new Board();
		board.getStack().addCard(new Card(Value.Eight, Color.Blue));
		board.changeColor(board.getStack().topCard().getColor());
		assertFalse(board.askPlayableCard(new Card(Value.Seven, Color.Yellow)));
	}
	
	@Test
	public void testCarteBonneCouleurEtMauvaiseValeurEstJouable()
	{
		Board board = new Board();
		board.getStack().addCard(new Card(Value.Eight, Color.Red));
		board.changeColor(board.getStack().topCard().getColor());
		assertTrue(board.askPlayableCard(new Card(Value.Seven, Color.Red)));
	}
	
	@Test
	public void testCarteMauvaiseCouleurEtMauvaiseValeurEstNonJouable()
	{
		Board board = new Board();
		board.getStack().addCard(new Card(Value.Eight, Color.Green));
		board.changeColor(board.getStack().topCard().getColor());
		assertFalse(board.askPlayableCard(new Card(Value.Seven, Color.Red)));
	}
	
	@Test
	public void testJoueurPeutJouer()
	{
		Board board = new Board();
		board.getPlayers().add(new Player("toto",""));
		board.getActualPlayer().getCards().add(new Card(Value.Seven, Color.Red));
		board.getStack().addCard(new Card(Value.Eight, Color.Red));
		board.changeColor(board.getStack().topCard().getColor());
		assertTrue(board.askPlayerCanPlay(board.getActualPlayer()));
	}
	
	@Test
	public void testChangeDeSens()
	{
		Board board = new Board();
		Player toto = new Player("toto","");
		Player test = new Player("test","");
		Player titi = new Player("titi","");
		board.getPlayers().add(toto);
		board.getPlayers().add(test);
		board.getPlayers().add(titi);
		assertTrue(board.getActualPlayer().equals(toto));
		board.nextPlayer();
		assertTrue(board.getActualPlayer().equals(test));
		board.changeMeaning();
		board.nextPlayer();
		assertTrue(board.getActualPlayer().equals(toto));
		board.nextPlayer();
		assertTrue(board.getActualPlayer().equals(titi));
	}
	
	@Test
	public void testPoseUneBonneCarte()
	{
		Board board = new Board();
		Player toto = new Player("toto","");
		Card carteaposer = new Card(Value.Zero, Color.Red);
		toto.getCards().add(carteaposer);
		board.getStack().getStack().add(new Card(Value.Zero, Color.Blue));
		board.getPlayers().add(toto);
		board.poseCard(toto.getCards().get(0));
		assertTrue(toto.getCards().isEmpty());
		assertTrue(carteaposer.equals(board.getStack().topCard()));
	}
	
	@Test
	public void testPoseUneMauvaiseCarte()
	{
		Board board = new Board();
		Player toto = new Player("toto","");
		Card carteaposer = new Card(Value.Zero, Color.Red);
		toto.getCards().add(carteaposer);
		board.getStack().getStack().add(new Card(Value.One, Color.Blue));
		board.getPlayers().add(toto);
		board.poseCard(toto.getCards().get(0));
		assertFalse(toto.getCards().isEmpty());
		assertFalse(carteaposer.equals(board.getStack().topCard()));
	}
	
	@Test
	public void testPiocheUneCarte()
	{
		Board board = new Board();
		Player toto = new Player("toto","");
		board.getStack().getStack().add(new Card(Value.One, Color.Blue));
		board.getPlayers().add(toto);
		board.drawCard();
		assertFalse(toto.getCards().isEmpty());

	}
	
	@Test
	public void testPiochePlusieursCartes()
	{
		Board board = new Board();
		Player toto = new Player("toto","");
		board.getStack().getStack().add(new Card(Value.One, Color.Blue));
		board.getStack().getStack().add(new Card(Value.Two, Color.Blue));
		board.getStack().getStack().add(new Card(Value.Three, Color.Blue));
		board.getPlayers().add(toto);
		board.setCptDrawCard(3);
		board.drawCard();
		assertTrue(toto.getCards().size() == 3);
	}
}
