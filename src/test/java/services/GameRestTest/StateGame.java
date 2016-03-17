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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class StateGame extends JerseyTest{
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
        model.addGame(model.getPlayerFromList("token"), "tata", 3);
        for(int i = 0; i < 2; i++){
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
    }

    @Test
    public void retourneFalseSiLaPartieExisteMaisPasCommencer() throws JSONException {
        Response response = target("/game/tata").request().header("token", "token").get();
        assertEquals(200, response.getStatus());
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertFalse(json.getBoolean("state"));
    }

    @Test
    public void retourneTrueSiLaPartieEstLanceeAvecTousLesJoueurs() throws JSONException{

        assertTrue(model.findGameByName("tata").start());

        Response response = target("/game/tata").request().header("token", "token").get();
        assertEquals(200, response.getStatus());
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertTrue(json.getBoolean("state"));
        assertEquals(3, json.getJSONArray("players").length());
    }

    @Test
    public void retourneUneErreur405SiPartieNexistePas() {
        Response response = target("/game/sdsdsdss").request().header("token", "token").get();
        assertEquals(405, response.getStatus());
    }
}
