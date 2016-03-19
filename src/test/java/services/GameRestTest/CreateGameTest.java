package services.GameRestTest;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.services.GameRest;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CreateGameTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(GameRest.class);
    }

    Model model;
    JSONObject jsonObject;

    @Before
    public void init() {
        model = Model.getInstance();
        model.setGames(new ArrayList<Game>());
        model.setPlayers(new ArrayList<Player>());
        model.createPlayer("toto", "token");
        model.addGame(model.getPlayerFromList("token"), "tata", 4);
        jsonObject = new JSONObject();
    }

    @Test
    public void createGameTest() throws JSONException{
        /**
         * Creation dun tableau formaté JSON avec les 3 parametres
         */
        assertTrue(model.createPlayer("marcel", "token1233"));
        jsonObject.put("game", "superfly");
        jsonObject.put("player", "marcel");
        jsonObject.put("numberplayers", 4);

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);

        /**
         * on verifie que le code de retour est bien 200/succes
         */
        Response response = target("/game").request().header("token", "token1233").post(jsonEntity);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void createGameMissingGameTest() throws JSONException {

        assertTrue(model.createPlayer("marcel", "token1223"));
        jsonObject.put("player", "marcel");
        jsonObject.put("numberplayers", 4);

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);

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
        jsonObject.put("game", "su");
        jsonObject.put("player", "marcel");
        jsonObject.put("numberplayers", 4);

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);

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
        jsonObject.put("game", "superfly");
        jsonObject.put("numberplayers", 4);

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);

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
        jsonObject.put("game", "superfly");
        jsonObject.put("player", "marcel");

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);

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

        jsonObject.put("game", "superfly");
        jsonObject.put("player", "marcel");
        jsonObject.put("numberplayers", 8);

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);

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

        jsonObject.put("game", "superfly");
        jsonObject.put("player", "marcel");
        jsonObject.put("numberplayers", 4);

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);

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

        jsonObject.put("game", "superfly");
        jsonObject.put("player", "marcel");
        jsonObject.put("numberplayers", 4);

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);

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

        jsonObject.put("game", "superfly");
        jsonObject.put("player", "toto");
        jsonObject.put("numberplayers", 4);

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);

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

        jsonObject.put("game", "superfly");
        jsonObject.put("player", "marcel");
        jsonObject.put("numberplayers", 4);

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);

        /**
         * on verifie que le code de retour est bien 405/error
         */
        Response response = target("/game").request().header("token", "token1223").post(jsonEntity);
        JSONObject jsonObject = new JSONObject(response.readEntity(String.class));
        assertEquals(405, response.getStatus());
        assertEquals("Joueur inexistant", jsonObject.getString("error"));

    }
}
