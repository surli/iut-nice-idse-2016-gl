package services;


import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.services.GameRest;
import fr.unice.idse.services.InitializerRest;
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

public class GameRestTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(GameRest.class);
    }

    Model model;

    @Before
    public void init(){
        model = Model.getInstance();
        model.setGames(new ArrayList<Game>());
        model.addGame(model.createPlayer("toto"),"tata", 4);
    }

    @Test
    public void retourneFalseSiLaPartieExisteMaisPasCommencer() throws JSONException{
        Response response = target("/game/tata/gamestate").request().get();
        assertEquals(200, response.getStatus());
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals(false, json.getBoolean("state"));
    }

    @Test
    public void retourneUneErreur500SiPartieNexistePas() {
        Response response = target("/game/sdsdsdss/gamestate").request().get();
        assertEquals(500, response.getStatus());
    }

}
