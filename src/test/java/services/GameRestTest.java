package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import fr.unice.idse.constante.Config;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.Player;
import fr.unice.idse.model.Stack;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;
import fr.unice.idse.services.GameRest;



public class GameRestTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(GameRest.class);
	}

	public Model model;

	@Before
	public void init() {
		model = Model.getInstance();
		model.setGames(new ArrayList<>());
        model.setPlayers(new ArrayList<>());
        model.createPlayerBis("toto", "token");
		model.addGame(model.getPlayerFromList("token"), "tata", 4);
	}

	@Test
	public void retourneLeJoueurActuelDeLaPartie() throws JSONException {
		// Init the game
		model.findGameByName("tata").addPlayer(model.createPlayer("marcel", "token2"));
        model.findGameByName("tata").addPlayer(model.createPlayer("chris", "token3"));
        model.findGameByName("tata").addPlayer(model.createPlayer("maurice", "token4"));
        model.findGameByName("tata").start();

		// Test the methods
		Response response = target("/game/tata/command").request().get();
		JSONObject json = new JSONObject(response.readEntity(String.class));

		// Assert
		assertEquals(200, response.getStatus());
		assertEquals("toto", json.getString("playerName"));
	}

	@Test
	public void retourneErreur401SiLaPartieEstPasDemarre() throws JSONException {
		// Test the methods
		Response response = target("/game/tata/command").request().get();
		JSONObject json = new JSONObject(response.readEntity(String.class));

		// Assert
		assertEquals(401, response.getStatus());
		assertEquals("Game has not begin", json.getString("error"));
	}

	@Test
    public void retourneFalseSiLaPartieExisteMaisPasCommencer() throws JSONException{
        Response response = target("/game/tata").request().get();
        assertEquals(200, response.getStatus());
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertFalse(json.getBoolean("state"));
    }

    @Test
    public void retourneTrueSiLaPartieEstLanceeAvecTousLesJoueurs() throws JSONException{
        for(int i =0; i < 3; i++)
            model.addPlayerToGame("tata", model.createPlayer("azert"+i, "token"+i));
        assertTrue(model.findGameByName("tata").start());

        Response response = target("/game/tata").request().get();
        assertEquals(200, response.getStatus());
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertTrue(json.getBoolean("state"));
        assertEquals(4, json.getJSONArray("players").length());
    }

    @Test
    public void retourneUneErreur404SiPartieNexistePas() {
        Response response = target("/game/sdsdsdss").request().get();
        assertEquals(404, response.getStatus());
    }

    @Test
    public void ajouteUnJoueurInexistantDansUnePartie() throws JSONException{
        model.createPlayerBis("titi", "jdqsdhsqd");
        String json = "{playerName: 'titi'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata").request().header("token", "jdqsdhsqd").put(jsonEntity);

        assertEquals(200, response.getStatus());
        JSONObject jsonresponse = new JSONObject(response.readEntity(String.class));
        assertTrue(jsonresponse.getBoolean("status"));
    }

    @Test
    public void ajouterUnJoueurExistantDansUnePartie() throws JSONException{
        String json = "{playerName: 'toto'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/toto").request().put(jsonEntity);

        assertEquals(405, response.getStatus());
    }

    @Test
    public void getHandDunJoueur() throws JSONException{
        for(int i = 0; i < 3; i++)
            model.addPlayerToGame("tata", model.createPlayer("azert"+i,"token"+i));

        model.findGameByName("tata").start();
        Response response = target("/game/tata/toto").request().get();
        assertEquals(200, response.getStatus());
        JSONObject jsonresponse = new JSONObject(response.readEntity(String.class));
        assertEquals(7, jsonresponse.getJSONArray("cartes").length());
    } 
    

    @Test
    public void lancerUnePartieSansTousLesJoueurs() throws JSONException{
        String json = "{playerName: 'toto'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/command").request().put(jsonEntity);

        assertEquals(500, response.getStatus());
        assertEquals("Game not tucked", response.readEntity(String.class));
    }

    @Test
    public void lancerUnePartieQuiADejaCommencer() throws JSONException{
        for(int i = 0; i < 3; i++)
            model.addPlayerToGame("tata", model.createPlayer("azert"+i,"token"+i));
        model.findGameByName("tata").start();

        String json = "{playerName: 'toto'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/command").request().put(jsonEntity);

        assertEquals(500, response.getStatus());
        assertEquals("Game started", response.readEntity(String.class));
    }

    @Test
    public void lancerUnePartieAvecTousLesJoueurs() throws JSONException{
        for(int i = 0; i < 3; i++)
            model.addPlayerToGame("tata", model.createPlayer("azert"+i, "token"+i));

        String json = "{playerName: 'toto'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/command").request().put(jsonEntity);

        assertEquals(200, response.getStatus());
        JSONObject jsonresponse = new JSONObject(response.readEntity(String.class));
        System.out.println(jsonresponse);
        assertTrue(jsonresponse.getBoolean("status"));
    }

    @Test
    public void createTest(){
        /**
         * Creation dun tableau formaté JSON avec les 3 parametres
         */
        assertTrue(model.createPlayerBis("marcel", "token1233"));
        String json = "{game: 'superfly', player: 'marcel', numberplayers:4}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);

        /**
         * on verifie que le code de retour est bien 200/succes
         */
        Response response = target("/game").request().header("token", "token1233").post(jsonEntity);
        assertEquals(200, response.getStatus());
    }
    
    /*
     * ******************************************************************************************************
     * **************************************** List card begin test ****************************************
     * ******************************************************************************************************
     */

    /**
     * Doit retourner un tableau vide vu qu'aucune partie n'a été créé
     * @throws JSONException
     */
    @Test
    public void retourneUnTableauVideSiAucuneGame() throws JSONException{
        model.setGames(new ArrayList<Game>());
        Response response = target("/game").request().get();
        assertEquals(200, response.getStatus());

        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals(0, json.getJSONArray("games").length());
    }

    @Test
    public void retourneUnTableauAvecUneGame() throws JSONException{
        Response response = target("/game").request().get();
        assertEquals(200, response.getStatus());

        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals(1, json.getJSONArray("games").length());
    }
    
    @Test
    public void pickacardTest() throws JSONException{
    	model.findGameByName("tata").start();
    	String json = "{game:'tata', playerName: 'toto'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/toto").request().post(jsonEntity);
    	assertEquals(200, response.getStatus());
    }

    /*
     * ******************************************************************************************************
     * ***************************************** List card end test *****************************************
     * ******************************************************************************************************
     * *************************************** Jouer card begin test ****************************************
     * ******************************************************************************************************
     */
    
    @Test
    public void retourne405SiLaPartieNExistePas() throws JSONException{
    	Response response = target("/game/test/john").request().put(Entity.entity("{}", MediaType.APPLICATION_JSON));
    	assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The game does not exist", json.getString("error"));
    }
    
    @Test
    public void retourne405SiLaPartieNEstPasCommencee() throws JSONException{
    	Response response = target("/game/tata/john").request().put(Entity.entity("{}", MediaType.APPLICATION_JSON));
    	assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The game does hasn't begun", json.getString("error"));
    }
    
    @Test
    public void retourne405SiLeJoeurNExistePas() throws JSONException{
		// Init the game
		model.findGameByName("tata").addPlayer(model.createPlayer("marcel", "token1"));
        model.findGameByName("tata").addPlayer(model.createPlayer("chris", "token2"));
        model.findGameByName("tata").addPlayer(model.createPlayer("maurice", "token3"));
        model.findGameByName("tata").start();
		
    	Response response = target("/game/tata/john").request().put(Entity.entity("{}", MediaType.APPLICATION_JSON));
    	assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The player does not exist", json.getString("error"));
    }
    
    @Test
    public void retourne405SiLeJoeurNExistePasDansCettePartie() throws JSONException{
		// Init the game
        model.findGameByName("tata").addPlayer(model.createPlayer("marcel", "token1"));
        model.findGameByName("tata").addPlayer(model.createPlayer("chris", "token2"));
        model.findGameByName("tata").addPlayer(model.createPlayer("maurice", "token3"));
        model.findGameByName("tata").start();
		
		// Init a second game
		model.addGame(model.createPlayer("john", "token4"), "test", 4);

        model.findGameByName("test").addPlayer(model.createPlayer("marcel2", "token5"));
        model.findGameByName("test").addPlayer(model.createPlayer("chris2", "token6"));
        model.findGameByName("test").addPlayer(model.createPlayer("maurice2", "token7"));
		
    	Response response = target("/game/tata/john").request().put(Entity.entity("{}", MediaType.APPLICATION_JSON));
    	assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The player does not exist", json.getString("error"));
    }
    
    @Test
    public void retourne405SiLeJSONEnvoyerEstInvalide() throws JSONException{
		// Init the game
        model.findGameByName("tata").addPlayer(model.createPlayer("marcel", "token1"));
        model.findGameByName("tata").addPlayer(model.createPlayer("chris", "token2"));
        model.findGameByName("tata").addPlayer(model.createPlayer("maurice", "token3"));
        model.findGameByName("tata").start();
		
    	Response response = target("/game/tata/toto").request().put(Entity.entity("{}", MediaType.APPLICATION_JSON));
    	assertEquals(405, response.getStatus());

        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The json object does not follow the rules", json.getString("error"));
    }
    
    @Test
    public void retourne405SiLeJSONEnvoyerEstInvalideDusALaValueManquante() throws JSONException{
		// Init the game
        model.findGameByName("tata").addPlayer(model.createPlayer("marcel", "token1"));
        model.findGameByName("tata").addPlayer(model.createPlayer("chris", "token2"));
        model.findGameByName("tata").addPlayer(model.createPlayer("maurice", "token3"));
        model.findGameByName("tata").start();
		
    	Response response = target("/game/tata/toto").request().put(Entity.entity("{\"color\":\"Blue\"}", MediaType.APPLICATION_JSON));
    	assertEquals(405, response.getStatus());

        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The json object does not follow the rules", json.getString("error"));
        assertEquals(false, json.has("value"));
    }
    
    @Test
    public void retourne405SiLeJSONEnvoyerEstInvalideDusALaColorManquante() throws JSONException{
		// Init the game
        model.findGameByName("tata").addPlayer(model.createPlayer("marcel", "token1"));
        model.findGameByName("tata").addPlayer(model.createPlayer("chris", "token2"));
        model.findGameByName("tata").addPlayer(model.createPlayer("maurice", "token3"));
        model.findGameByName("tata").start();
		
    	Response response = target("/game/tata/toto").request().put(Entity.entity("{\"value\":\"2\"}", MediaType.APPLICATION_JSON));
    	assertEquals(405, response.getStatus());

        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The json object does not follow the rules", json.getString("error"));
        assertEquals(false, json.has("color"));
    }
    
    
    @Test
    public void retourne405SiLeJoeurNePeutPasJouer() throws JSONException{
		// Init the game
        model.findGameByName("tata").addPlayer(model.createPlayer("marcel", "token1"));
        model.findGameByName("tata").addPlayer(model.createPlayer("chris", "token2"));
        model.findGameByName("tata").addPlayer(model.createPlayer("maurice", "token3"));
        model.findGameByName("tata").start();
		
		
    	Response response = target("/game/tata/marcel").request().put(Entity.entity("{\"value\":6, \"color\":\"Blue\"}", MediaType.APPLICATION_JSON));
    	assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The player can't play", json.getString("error"));
    }
   
    
    @Test
    public void retourne405SiLeJoueurNePossedePasLaCarte() throws JSONException{
		// Init the game
        model.findGameByName("tata").addPlayer(model.createPlayer("marcel", "token1"));
        model.findGameByName("tata").addPlayer(model.createPlayer("chris", "token2"));
        model.findGameByName("tata").addPlayer(model.createPlayer("maurice", "token3"));
        model.findGameByName("tata").start();

        model.findGameByName("tata").getBoard().getActualPlayer().getCards().add(new Card(Value.Five, Color.Blue));
        model.findGameByName("tata").getBoard().getStack().changeColor(Color.Red);
        model.findGameByName("tata").getBoard().getStack().addCard(new Card(Value.Five, Color.Red));

        Response response = target("/game/tata/toto").request().put(Entity.entity("{\"value\":11, \"color\":\"Black\"}", MediaType.APPLICATION_JSON));
    	assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The player does not possese this card", json.getString("error"));
    }
    
    @Test
    public void retourne405SiLaCarteNEstPasJouable() throws JSONException {
		// Init the game
        model.findGameByName("tata").addPlayer(model.createPlayer("marcel", "token1"));
        model.findGameByName("tata").addPlayer(model.createPlayer("chris", "token2"));
        model.findGameByName("tata").addPlayer(model.createPlayer("maurice", "token3"));
        model.findGameByName("tata").start();
		
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(Value.Two, Color.Blue));
		cards.add(new Card(Value.Eight, Color.Blue));
        model.findGameByName("tata").getBoard().getActualPlayer().setCards(cards);
        model.findGameByName("tata").getBoard().getStack().changeColor(Color.Red);
		ArrayList<Card> stack = new ArrayList<Card>();
		stack.add(new Card(Value.Eight, Color.Red));
        model.findGameByName("tata").getBoard().getStack().setStack(stack);
		
    	Response response = target("/game/tata/toto").request().put(Entity.entity("{\"value\":2, \"color\":\"Blue\"}", MediaType.APPLICATION_JSON));
    	
    	/*
    	assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The card can't be played", json.getString("error"));
        */
    }
    
    @Test
    public void retourne200SiTouteLesConditionSontValider() throws JSONException{
		// Init the game
        model.findGameByName("tata").addPlayer(model.createPlayer("marcel", "token1"));
        model.findGameByName("tata").addPlayer(model.createPlayer("chris", "token2"));
        model.findGameByName("tata").addPlayer(model.createPlayer("maurice", "token3"));
        model.findGameByName("tata").start();

        model.findGameByName("tata").getBoard().getActualPlayer().getCards().add(new Card(Value.Five, Color.Blue));
        model.findGameByName("tata").getBoard().getStack().addCard(new Card(Value.Five, Color.Red));
    	Response response = target("/game/tata/toto").request().put(Entity.entity("{\"value\":5, \"color\":\"Blue\"}", MediaType.APPLICATION_JSON));

    	assertEquals(200, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The card was succesfully played", json.getString("success"));
    }
    
    
    /*
     * ******************************************************************************************************
     * *************************************** Jouer card end test ****************************************
     * ******************************************************************************************************
     */
}
