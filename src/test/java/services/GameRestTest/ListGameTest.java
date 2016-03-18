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

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListGameTest extends JerseyTest{

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
        model.createPlayer("toto1", "token1");
    }

    @Test
    public void retourneUnTableauVideSiAucuneGame() throws JSONException {
        Response response = target("/game").request().header("token", "token").get();
        assertEquals(200, response.getStatus());

        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals(0, json.getJSONArray("games").length());
    }

    @Test
    public void retourneUnTableauAvecUneGame() throws JSONException{
        assertTrue(model.addGame(model.getPlayerFromList("token"), "tata", 4));

        Response response = target("/game").request().header("token", "token1").get();
        assertEquals(200, response.getStatus());

        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals(1, json.getJSONArray("games").length());
    }
}
