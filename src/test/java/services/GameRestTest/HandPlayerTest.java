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

public class HandPlayerTest extends JerseyTest{

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
            assertTrue(model.createPlayer("azert" + i, "token" + i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        assertTrue(model.findGameByName("tata").start());
    }

    @Test
    public void getHandDunJoueur() throws JSONException {


        Response response = target("/game/tata/toto").request().header("token", "token").get();
        assertEquals(200, response.getStatus());
        JSONObject jsonresponse = new JSONObject(response.readEntity(String.class));
        assertEquals(7, jsonresponse.getJSONArray("cartes").length());
    }
}
