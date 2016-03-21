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

public class BeginGameTest extends JerseyTest{

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

        for(int i = 0; i < 3; i++) {
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        jsonObject = new JSONObject();
    }

    @Test
    public void lancerUnePartieSansTousLesJoueurs() throws JSONException {
        assertTrue(model.removePlayerFromGameByName("tata", "azert0"));

        jsonObject.put("playerName", "toto");

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/command").request().header("token", "token").put(jsonEntity);

        assertEquals(500, response.getStatus());
        JSONObject reponse = new JSONObject(response.readEntity(String.class));
        assertEquals("Game not tucked", reponse.getString("error"));
    }

    @Test
    public void lancerUnePartieQuiADejaCommencer() throws JSONException{
        assertTrue(model.findGameByName("tata").start());

        jsonObject.put("playerName", "toto");

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/command").request().header("token", "token").put(jsonEntity);

        assertEquals(500, response.getStatus());
        JSONObject reponse = new JSONObject(response.readEntity(String.class));
        assertEquals("Game started", reponse.getString("error"));
    }

    @Test
    public void lancerUnePartieAvecTousLesJoueurs() throws JSONException{
        jsonObject.put("playerName", "toto");

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/game/tata/command").request().header("token", "token").put(jsonEntity);

        assertEquals(200, response.getStatus());
        JSONObject reponse = new JSONObject(response.readEntity(String.class));
        assertTrue(reponse.getBoolean("status"));
    }
}
