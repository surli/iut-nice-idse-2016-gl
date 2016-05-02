package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;
import fr.unice.idse.model.player.Player;


public class GameTest {

	private Game game;
	private Player host;
	@Before
	public void initialize(){
		host=new Player("host","host");
		game = new Game(host,"game",4); 
	}
	
	@Test
	public void contientLeJoueurEnFonctionDuNom(){
		Player p1 = new Player("p1","p1");
		game.addPlayer(p1);
		
		boolean b = game.containsPlayerByName("p1");
		
		assertTrue(b);
	}
	
	@Test
	public void neContientPasLeJoueurEnFonctionDuNom(){
		Player p1 = new Player("p1","p1");
		game.addPlayer(p1);
		
		boolean b = game.containsPlayerByName("p2");
		
		assertFalse(b);
	}

	@Test
	public void ajouterJoueurALaPartie(){
		Player p1 = new Player("p1","p1");
		boolean b = game.addPlayer(p1);
		boolean b2 = game.addPlayer(p1);
				
		ArrayList<Player> players=new ArrayList<Player>();
		players.add(p1);
		
		assertTrue(b);
		assertFalse(b2);		
		assertEquals(players,game.getPlayers());
	}
	
	@Test
	 public void trouveLeJoueurEnFonctionDuNom()
	 {
		Player p1 = new Player("p1","p1");
		game.addPlayer(p1);
		
		assertEquals(p1,game.findPlayerByName("p1"));
	 }
	
	@Test
	 public void neTrouvePasLeJoueurEnFonctionDuNom()
	 {
		Player p1 = new Player("p1","p1");
		game.addPlayer(p1);
		
		assertNull(game.findPlayerByName("p2"));
	 }
	
	@Test
	 public void trouveLeJoueurEnFonctionDuToken()
	 {
		Player p1 = new Player("p1","p1");
		game.addPlayer(p1);
		
		assertEquals(p1,game.findPlayerByToken("p1"));
	 }
	
	@Test
	 public void neTrouvePasLeJoueurEnFonctionDuToken()
	 {
		Player p1 = new Player("p1","p1");
		game.addPlayer(p1);
		
		assertNull(game.findPlayerByToken("p2"));
	 }
	
	@Test
	public void supprimeLeJoueurEnFonctionDuNom()
	{
		Player p1 = new Player("p1","p1");
		game.addPlayer(p1);
		boolean b=game.removePlayerByName("p1");
		boolean b2=game.removePlayerByName("p1");

		assertTrue(b);
		assertFalse(b2);
		assertNull(game.findPlayerByName("p1"));
	}
	
	@Test
	public void supprimeLeJoueurEnFonctionDuToken()
	{
		Player p1 = new Player("p1","p1");
		game.addPlayer(p1);
		boolean b=game.removePlayerByToken("p1");
		boolean b2=game.removePlayerByToken("p1");

		assertTrue(b);
		assertFalse(b2);
		assertNull(game.findPlayerByToken("p1"));
	}
	
	@Test
	public void testInitGame()
	{
		Player toto=new Player("toto","");
		Game game= new Game(toto,"game",2);
		game.getPlayers().add(toto);
		game.getPlayers().add(new Player("test",""));
		game.init();
		assertFalse(game.getDeck().isEmpty());
		assertTrue(game.getPlayers().get(0).getCards().size()>0);
		assertTrue(game.getPlayers().get(1).getCards().size()>0);
		assertTrue(game.getStack().getStack().size()>0);
	}

	@Test
	public void testCarteBonneValeurEstJouable()
	{
		Player toto=new Player("toto","");
		Game game= new Game(toto,"game",2);
		game.getStack().addCard(new Card(Value.Eight, Color.Black));
		game.changeColor(game.getStack().topCard().getColor());
		assertTrue(game.askPlayableCard(new Card(Value.Eight, Color.Red)));
	}

	@Test
	public void testCarteMauvaiseValeurEstNonJouable()
	{
		Player toto=new Player("toto","");
		Game game= new Game(toto,"game",2);
		game.getStack().addCard(new Card(Value.Eight, Color.Blue));
		game.changeColor(game.getStack().topCard().getColor());
		assertFalse(game.askPlayableCard(new Card(Value.Seven, Color.Yellow)));
	}

	@Test
	public void testCarteBonneCouleurEtMauvaiseValeurEstJouable()
	{
		Player toto=new Player("toto","");
		Game game= new Game(toto,"game",2);
		game.getStack().addCard(new Card(Value.Eight, Color.Red));
		game.changeColor(game.getStack().topCard().getColor());
		assertTrue(game.askPlayableCard(new Card(Value.Seven, Color.Red)));
	}

	@Test
	public void testCarteMauvaiseCouleurEtMauvaiseValeurEstNonJouable()
	{
		Player toto=new Player("toto","");
		Game game= new Game(toto,"game",2);
		game.getStack().addCard(new Card(Value.Eight, Color.Green));
		game.changeColor(game.getStack().topCard().getColor());
		assertFalse(game.askPlayableCard(new Card(Value.Seven, Color.Red)));
	}

	@Test
	public void testJoueurPeutJouer()
	{
		Player toto=new Player("toto","");
		Game game= new Game(toto,"game",2);
		game.getPlayers().add(new Player("toto",""));
		game.getActualPlayer().getCards().add(new Card(Value.Seven, Color.Red));
		game.getStack().addCard(new Card(Value.Eight, Color.Red));
		game.changeColor(game.getStack().topCard().getColor());
		assertTrue(game.askPlayerCanPlay(game.getActualPlayer()));
	}

	@Test
	public void testChangeDeSens()
	{
		Player toto=new Player("toto","");
		Player test = new Player("test","");
		Player titi = new Player("titi","");
		Game game= new Game(toto,"game",3);
		game.getPlayers().add(toto);
		game.getPlayers().add(test);
		game.getPlayers().add(titi);
		assertTrue(game.getActualPlayer().equals(toto));
		game.nextPlayer();
		assertTrue(game.getActualPlayer().equals(test));
		game.changeOrientation();
		game.nextPlayer();
		assertTrue(game.getActualPlayer().equals(toto));
		game.nextPlayer();
		assertTrue(game.getActualPlayer().equals(titi));
	}

	@Test
	public void testPoseUneBonneCarte()
	{
		Player toto = new Player("toto","");
		Game game= new Game(toto,"game",2);

		Card carteaposer = new Card(Value.Zero, Color.Red);
		toto.getCards().add(carteaposer);
		game.getStack().getStack().add(new Card(Value.Zero, Color.Blue));
		game.getPlayers().add(toto);
		game.poseCard(toto.getCards().get(0));
		assertTrue(toto.getCards().isEmpty());
		assertTrue(carteaposer.equals(game.getStack().topCard()));
	}

	@Test
	public void testPoseUneMauvaiseCarte()
	{
		Player toto = new Player("toto","");
		Game game= new Game(toto,"game",2);
		Card carteaposer = new Card(Value.Zero, Color.Red);
		toto.getCards().add(carteaposer);
		game.getStack().getStack().add(new Card(Value.One, Color.Blue));
		game.getPlayers().add(toto);
		game.poseCard(toto.getCards().get(0));
		assertFalse(toto.getCards().isEmpty());
		assertFalse(carteaposer.equals(game.getStack().topCard()));
	}

	@Test
	public void testPiocheUneCarte()
	{
		Player toto = new Player("toto","");
		Game game= new Game(toto,"game",2);
		game.getStack().getStack().add(new Card(Value.One, Color.Blue));
		game.getPlayers().add(toto);
		game.drawCard();
		assertFalse(toto.getCards().isEmpty());

	}

	@Test
	public void testPiochePlusieursCartes()
	{
		Player toto = new Player("toto","");
		Game game= new Game(toto,"game",2);
		game.getDeck().getDeck().add(new Card(Value.One, Color.Blue));
		game.getDeck().getDeck().add(new Card(Value.Two, Color.Blue));
		game.getDeck().getDeck().add(new Card(Value.Three, Color.Blue));
		game.getPlayers().add(toto);

		game.setCptDrawCard(3);
		game.drawCard();

		assertTrue(toto.getCards().size() == 3);
	}
	
	/**
	 * Voir description de la méthode rotatePlayerDecks dans la classe Board
	 */
	@Test
	public void testRotationSensHoraireDesJeuxDeCartesDesJoueurs()
	{
		Player p1 = new Player("p1", "");
		Game game= new Game(p1,"game",4);
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
		
		game.setPlayers(players);
		game.rotatePlayersDecks();
		
		assertEquals(deck4,p1.getCards());
		assertEquals(deck1,p2.getCards());
		assertEquals(deck2,p3.getCards());
		assertEquals(deck3,p4.getCards());
	}
	
	/**
	 * Voir description de la méthode rotatePlayerDecks dans la classe Board
	 */
	@Test
	public void testRotationSensNonHoraireDesJeuxDeCartesDesJoueurs()
	{
		Player p1 = new Player("p1", "");
		Player p2 = new Player("p2", "");
		Player p3 = new Player("p3", "");
		Player p4 = new Player("p4", "");
		
		Game game= new Game(p1,"game",4);
		
		game.changeOrientation();
		
		
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
		
		game.setPlayers(players);
		game.rotatePlayersDecks();
		
		assertEquals(deck2,p1.getCards());
		assertEquals(deck3,p2.getCards());
		assertEquals(deck4,p3.getCards());
		assertEquals(deck1,p4.getCards());
	}
}
