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

public class GetAlternativesTest extends JerseyTest{
    @Override
    protected Application configure() {
        return new ResourceConfig(GameRest.class);
    }

    Model model;
    JSONObject jsonObject;

    @Before
    public void init() {
        model = Model.getInstance();
        model.setGames(new ArrayList<>());
        model.setPlayers(new ArrayList<>());
        model.createPlayer("toto", "token");
        jsonObject = new JSONObject();
    }

    @Test
    public void retourneToutesLesAlternativesExistante() throws JSONException{
        Response response = target("/game/alternative").request().header("token", "token").get();
        assertEquals(200, response.getStatus());
        jsonObject = new JSONObject(response.readEntity(String.class));
        assertTrue(jsonObject.has("alternatives"));
        assertEquals(2, jsonObject.getJSONArray("alternatives").length());
    }
}
