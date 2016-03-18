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


public class ActualPlayerTest extends JerseyTest {
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
        for(int i = 0; i < 3; i++){
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
    }

    @Test
    public void retourneLeJoueurActuelDeLaPartie() throws JSONException {
        // Init the game
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
}
