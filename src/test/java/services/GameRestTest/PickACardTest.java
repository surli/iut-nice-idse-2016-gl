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

/**
 * Created by Jeremie on 17/03/2016.
 */
public class PickACardTest extends JerseyTest{

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
        for(int i = 0; i < 3; i++) {
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        assertTrue(model.findGameByName("tata").start());
    }

    @Test
    public void pickacardTest() throws JSONException {
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
        Entity<String> jsonEntity = Entity.entity(null, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/azert1").request().header("", "").post(jsonEntity);
        assertEquals(404, response.getStatus());

        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("Token not found", json.getString("error"));

    }

    @Test
    public void pickacardPlayerNullTest() throws JSONException{
        Entity<String> jsonEntity = Entity.entity(null, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/azert1").request().header("token", "").post(jsonEntity);
        assertEquals(405, response.getStatus());

        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("Player not found with this token", json.getString("error"));

    }

    @Test
    public void pickacardTokenPlayerTest() throws JSONException{
        Entity<String> jsonEntity = Entity.entity(null, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/azert2").request().header("token", "token1").post(jsonEntity);
        assertEquals(405, response.getStatus());

        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("Invalid token for this player", json.getString("error"));

    }

    @Test
    public void pickacardGameStartTest() throws JSONException{
        model.findGameByName("tata").getBoard().setGameBegin(false);

        Entity<String> jsonEntity = Entity.entity(null, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/azert1").request().header("token", "token1").post(jsonEntity);
        assertEquals(405, response.getStatus());

        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("Game not started", json.getString("error"));

    }

    @Test
    public void pickacardActualPlayerTest() throws JSONException{
        Entity<String> jsonEntity = Entity.entity(null, MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/azert1").request().header("token", "token1").post(jsonEntity);
        assertEquals(405, response.getStatus());

        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("It's not this player to play", json.getString("error"));

    }
}
