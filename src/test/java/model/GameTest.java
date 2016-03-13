package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.Player;


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
}
