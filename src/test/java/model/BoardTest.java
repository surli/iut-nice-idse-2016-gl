package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.player.Player;
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
	
	@Test
	public void testRotationSensHoraireDesJeuxDeCartesDesJoueurs()
	{
		Board board = new Board();
		Player p1 = new Player("p1", "");
		Player p2 = new Player("p2", "");
		Player p3 = new Player("p3", "");
		Player p4 = new Player("p4", "");
		
		ArrayList<Player> players=new ArrayList<Player>();
		
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		
		ArrayList<Card> deck1 = new ArrayList<Card>();
		ArrayList<Card> deck2 = new ArrayList<Card>();
		ArrayList<Card> deck3 = new ArrayList<Card>();
		ArrayList<Card> deck4 = new ArrayList<Card>();

		deck1.add(new Card(Value.Five ,Color.Blue));
		deck1.add(new Card(Value.Six ,Color.Blue));
		
		deck2.add(new Card(Value.Five ,Color.Green));
		deck2.add(new Card(Value.Six ,Color.Green));
		
		deck3.add(new Card(Value.Five ,Color.Red));
		deck3.add(new Card(Value.Six ,Color.Red));
		
		deck4.add(new Card(Value.Five ,Color.Yellow));
		deck4.add(new Card(Value.Six ,Color.Yellow));

		p1.setCards(deck1);
		p2.setCards(deck2);
		p3.setCards(deck3);
		p4.setCards(deck4);
		
		board.setPlayers(players);
		board.rotatePlayerDecks();
		
		assertEquals(deck4,p1.getCards());
		assertEquals(deck1,p2.getCards());
		assertEquals(deck2,p3.getCards());
		assertEquals(deck3,p4.getCards());
	}
	
	@Test
	public void testRotationSensNonHoraireDesJeuxDeCartesDesJoueurs()
	{
		Board board = new Board();
		
		board.changeMeaning();
		
		Player p1 = new Player("p1", "");
		Player p2 = new Player("p2", "");
		Player p3 = new Player("p3", "");
		Player p4 = new Player("p4", "");
		
		ArrayList<Player> players=new ArrayList<Player>();
		
		players.add(p1);
		players.add(p2);
		players.add(p3);
		players.add(p4);
		
		ArrayList<Card> deck1 = new ArrayList<Card>();
		ArrayList<Card> deck2 = new ArrayList<Card>();
		ArrayList<Card> deck3 = new ArrayList<Card>();
		ArrayList<Card> deck4 = new ArrayList<Card>();

		deck1.add(new Card(Value.Five ,Color.Blue));
		deck1.add(new Card(Value.Six ,Color.Blue));
		
		deck2.add(new Card(Value.Five ,Color.Green));
		deck2.add(new Card(Value.Six ,Color.Green));
		
		deck3.add(new Card(Value.Five ,Color.Red));
		deck3.add(new Card(Value.Six ,Color.Red));
		
		deck4.add(new Card(Value.Five ,Color.Yellow));
		deck4.add(new Card(Value.Six ,Color.Yellow));

		p1.setCards(deck1);
		p2.setCards(deck2);
		p3.setCards(deck3);
		p4.setCards(deck4);
		
		board.setPlayers(players);
		board.rotatePlayerDecks();
		
		assertEquals(deck2,p1.getCards());
		assertEquals(deck3,p2.getCards());
		assertEquals(deck4,p3.getCards());
		assertEquals(deck1,p4.getCards());
	}
}