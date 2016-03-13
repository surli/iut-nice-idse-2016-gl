package services.GameRestTest;

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

import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.player.Player;
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
        model.setGames(new ArrayList<Game>());
        model.setPlayers(new ArrayList<Player>());
        model.createPlayer("toto", "token");
        model.addGame(model.getPlayerFromList("token"), "tata", 4);
    }

    @Test
    public void retourneLeJoueurActuelDeLaPartie() throws JSONException {
        // Init the game
        for(int i = 0; i < 3; i++){
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        assertTrue(model.findGameByName("tata").start());

        // Test the methods
        Response response = target("/game/tata/command").request().header("token", "token").get();
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
        Response response = target("/game/tata").request().header("token", "token").get();
        assertEquals(200, response.getStatus());
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertFalse(json.getBoolean("state"));
    }

    @Test
    public void retourneTrueSiLaPartieEstLanceeAvecTousLesJoueurs() throws JSONException{
        for(int i = 0; i < 3; i++){
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        assertTrue(model.findGameByName("tata").start());

        Response response = target("/game/tata").request().header("token", "token").get();
        assertEquals(200, response.getStatus());
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertTrue(json.getBoolean("state"));
        assertEquals(4, json.getJSONArray("players").length());
    }

    @Test
    public void retourneUneErreur404SiPartieNexistePas() {
        Response response = target("/game/sdsdsdss").request().header("token", "token").get();
        assertEquals(405, response.getStatus());
    }

    @Test
    public void ajouteUnJoueurInexistantDansUnePartie() throws JSONException{
        model.createPlayer("titi", "jdqsdhsqd");
        String json = "{playerName: 'titi'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata").request().header("token", "jdqsdhsqd").put(jsonEntity);

        assertEquals(200, response.getStatus());
        JSONObject jsonresponse = new JSONObject(response.readEntity(String.class));
        assertTrue(jsonresponse.getBoolean("status"));
    }

    @Test
    public void ajouteUnJoueurExistantDansUnePartiePleine() throws JSONException{
        for(int i = 0; i < 3; i++) {
            assertTrue(model.createPlayer("azert" + i, "token" + i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        model.createPlayer("Aladin", "letokendelamort");
        String json = "{playerName : 'Aladin'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata").request().header("token", "letokendelamort").put(jsonEntity);

        JSONObject lareponsejson = new JSONObject(response.readEntity(String.class));
        assertEquals(200, response.getStatus());
        assertFalse(lareponsejson.getBoolean("status"));
    }

    @Test
    public void getHandDunJoueur() throws JSONException{
        for(int i = 0; i < 3; i++) {
            assertTrue(model.createPlayer("azert" + i, "token" + i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        assertTrue(model.findGameByName("tata").start());

        Response response = target("/game/tata/toto").request().header("token", "token").get();
        assertEquals(200, response.getStatus());
        JSONObject jsonresponse = new JSONObject(response.readEntity(String.class));
        assertEquals(7, jsonresponse.getJSONArray("cartes").length());
    }


    @Test
    public void lancerUnePartieSansTousLesJoueurs() throws JSONException{
        String json = "{playerName: 'toto'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/command").request().header("token", "token").put(jsonEntity);

        assertEquals(500, response.getStatus());
        JSONObject reponse = new JSONObject(response.readEntity(String.class));
        assertEquals("Game not tucked", reponse.getString("error"));
    }

    @Test
    public void lancerUnePartieQuiADejaCommencer() throws JSONException{
        for(int i = 0; i < 3; i++) {
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        assertTrue(model.findGameByName("tata").start());

        String json = "{playerName: 'toto'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/command").request().header("token", "token").put(jsonEntity);

        assertEquals(500, response.getStatus());
        JSONObject reponse = new JSONObject(response.readEntity(String.class));
        assertEquals("Game started", reponse.getString("error"));
    }

    @Test
    public void lancerUnePartieAvecTousLesJoueurs() throws JSONException{
        for(int i = 0; i < 3; i++) {
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }

        String json = "{playerName: 'toto'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/command").request().header("token", "token").put(jsonEntity);

        assertEquals(200, response.getStatus());
        JSONObject reponse = new JSONObject(response.readEntity(String.class));
        assertTrue(reponse.getBoolean("status"));
    }

    @Test
    public void createGameTest(){
        /**
         * Creation dun tableau formaté JSON avec les 3 parametres
         */
        assertTrue(model.createPlayer("marcel", "token1233"));
        String json = "{game: 'superfly', player: 'marcel', numberplayers:4}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);

        /**
         * on verifie que le code de retour est bien 200/succes
         */
        Response response = target("/game").request().header("token", "token1233").post(jsonEntity);
        assertEquals(200, response.getStatus());
    }
    
    @Test
    public void createGameMissingGameTest() throws JSONException{
    	
    	assertTrue(model.createPlayer("marcel", "token1223"));
        String json = "{ player: 'marcel', numberplayers:4}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);

        /**
         * on verifie que le code de retour est bien 405/error
         */
        Response response = target("/game").request().header("token", "token1223").post(jsonEntity);
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));

        assertEquals(405, response.getStatus());
        assertEquals("Invalid parameter game", jsonObject.getString("error"));
    	
    }

    @Test
    public void createGameGameLengthTest() throws JSONException{
    	assertTrue(model.createPlayer("marcel", "token1223"));
        String json = "{game:'su', player: 'marcel', numberplayers:4}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);

        /**
         * on verifie que le code de retour est bien 405/error
         */
        Response response = target("/game").request().header("token", "token1223").post(jsonEntity);
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));

        assertEquals(405, response.getStatus());
        assertEquals("Invalid parameter game length", jsonObject.getString("error"));

    }
    
    @Test
    public void createGamePlayerMissingTest() throws JSONException{
        String json = "{ game:'superfly', numberplayers:4}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);

        /**
         * on verifie que le code de retour est bien 405/error
         */
        Response response = target("/game").request().header("token", "token1223").post(jsonEntity);
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));

        assertEquals(405, response.getStatus());	
        assertEquals("Invalid parameter player", jsonObject.getString("error"));
    }
    
    
    @Test
    public void createGameNumberplayersMissingTest() throws JSONException{
    	assertTrue(model.createPlayer("marcel", "token1223"));
        String json = "{game:'superfly', player:'marcel'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);

        /**
         * on verifie que le code de retour est bien 405/error
         */
        Response response = target("/game").request().header("token", "token1223").post(jsonEntity);
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));
        assertEquals(405, response.getStatus());
        assertEquals("Invalid parameter numberplayers", jsonObject.getString("error"));
        
    }
    
    @Test
    public void createGameNumberplayersToHighTest() throws JSONException{
    	assertTrue(model.createPlayer("marcel", "token1223"));

        String json = "{game:'superfly', player:'marcel', numberplayers:8}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);

        /**
         * on verifie que le code de retour est bien 405/error
         */
        Response response = target("/game").request().header("token", "token1223").post(jsonEntity);
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));

        assertEquals(405, response.getStatus());	
        assertEquals("Numberplayers must be 2 to 6 numberplayers", jsonObject.getString("error"));

    }
    
    @Test
    public void createGameTokenMissingTest() throws JSONException{
    	assertTrue(model.createPlayer("marcel", "token1223"));

        String json = "{game:'superfly', player:'marcel', numberplayers:4}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);

        /**
         * on verifie que le code de retour est bien 405/error
         */
        Response response = target("/game").request().header("", "").post(jsonEntity);
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));

        assertEquals(405, response.getStatus());	
        assertEquals("Missing parameters token", jsonObject.getString("error"));

    }
    
    @Test
    public void createGameExistInListTest() throws JSONException{

        String json = "{game:'superfly', player:'marcel', numberplayers:4}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);

        /**
         * on verifie que le code de retour est bien 405/error
         */
        Response response = target("/game").request().header("token", "token1223").post(jsonEntity);
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));

        assertEquals(405, response.getStatus());	
        assertEquals("Joueur inexistant", jsonObject.getString("error"));

    }
    
    @Test
    public void createGameTokenInvalidTest() throws JSONException{
    	assertTrue(model.createPlayer("marcel", "token1223"));
    	
    	
        String json = "{game:'superfly', player:'paul', numberplayers:4}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);

        /**
         * on verifie que le code de retour est bien 405/error
         */
        Response response = target("/game").request().header("token", "token1223").post(jsonEntity);
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));

        assertEquals(405, response.getStatus());	
        assertEquals("Token invalid", jsonObject.getString("error"));

    }
    
    @Test
    public void createGameAddTest() throws JSONException{
    	assertTrue(model.createPlayer("marcel", "token1223"));
    	assertTrue(model.addGame(model.getPlayerFromList("token1223"), "superfly", 4));
    	
        String json = "{game:'superfly', player:'marcel', numberplayers:4}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);

        /**
         * on verifie que le code de retour est bien 405/error
         */
        Response response = target("/game").request().header("token", "token1223").post(jsonEntity);
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));

        assertEquals(500, response.getStatus());	
        assertEquals("false", jsonObject.getString("message"));

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
        assertTrue(model.createPlayer("Aladin", "azertyuiop"));
        Response response = target("/game").request().header("token", "azertyuiop").get();
        assertEquals(200, response.getStatus());

        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals(0, json.getJSONArray("games").length());
    }

    @Test
    public void retourneUnTableauAvecUneGame() throws JSONException{
        assertTrue(model.createPlayer("Aladin", "azertyuiop"));
        Response response = target("/game").request().header("token", "azertyuiop").get();
        assertEquals(200, response.getStatus());

        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals(1, json.getJSONArray("games").length());
    }

    @Test
    public void pickacardTest() throws JSONException{
        for(int i = 0; i < 3; i++) {
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        assertTrue(model.findGameByName("tata").start());

        Entity<String> jsonEntity = Entity.entity(null, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/toto").request().header("token", "token").post(jsonEntity);
        assertEquals(200, response.getStatus());
    }




    @Test
    public void pickacardGameNullTest() throws JSONException{

        Entity<String> jsonEntity = Entity.entity(null, MediaType.APPLICATION_JSON);

        Response response = target("/game//azert1").request().header("token", "token").post(jsonEntity);

        assertEquals(404, response.getStatus()); 

    }


    
    @Test
    public void pickacardTokenNullTest() throws JSONException{
        for(int i = 0; i < 3; i++) {
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        assertTrue(model.findGameByName("tata").start());

        Entity<String> jsonEntity = Entity.entity(null, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/azert1").request().header("", "").post(jsonEntity);
        assertEquals(404, response.getStatus()); 
        
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("Token not found", json.getString("error"));

    }
    
    @Test
    public void pickacardPlayerNullTest() throws JSONException{
        for(int i = 0; i < 3; i++) {
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        assertTrue(model.findGameByName("tata").start());

        Entity<String> jsonEntity = Entity.entity(null, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/azert1").request().header("token", "").post(jsonEntity);
        assertEquals(405, response.getStatus()); 
        
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("Player not found with this token", json.getString("error"));

    }

    @Test
    public void pickacardTokenPlayerTest() throws JSONException{
        for(int i = 0; i < 3; i++) {
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        assertTrue(model.findGameByName("tata").start());

        Entity<String> jsonEntity = Entity.entity(null, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/azert2").request().header("token", "token1").post(jsonEntity);
        assertEquals(405, response.getStatus()); 
        
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("Invalid token for this player", json.getString("error"));

    }

    @Test
    public void pickacardGameStartTest() throws JSONException{
        for(int i = 0; i < 3; i++) {
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }

        Entity<String> jsonEntity = Entity.entity(null, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/azert1").request().header("token", "token1").post(jsonEntity);
        assertEquals(405, response.getStatus()); 
        
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("Game not started", json.getString("error"));

    }
    
    @Test
    public void pickacardActualPlayerTest() throws JSONException{
        for(int i = 0; i < 3; i++) {
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        assertTrue(model.findGameByName("tata").start());

        Entity<String> jsonEntity = Entity.entity(null, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/azert1").request().header("token", "token1").post(jsonEntity);
        assertEquals(405, response.getStatus()); 
        
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("It's not this player to play", json.getString("error"));

    }

    
    /*
     * ******************************************************************************************************
     * ***************************************** List card end test *****************************************
     * ******************************************************************************************************
     */


}