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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class AddPlayerTest extends JerseyTest {

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
        model.createPlayer("toto", "token");
        model.createPlayer("titi", "token6");
        model.addGame(model.getPlayerFromList("token"), "tata", 4);
    }

    @Test
    public void ajouteUnJoueurInexistantDansUnePartie() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("playerName", "titi");
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/game/tata").request().header("token", "token6").put(jsonEntity);

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
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("playerName", "titi");

        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/game/tata").request().header("token", "token6").put(jsonEntity);

        JSONObject lareponsejson = new JSONObject(response.readEntity(String.class));
        assertEquals(200, response.getStatus());
        assertFalse(lareponsejson.getBoolean("status"));
    }
}
