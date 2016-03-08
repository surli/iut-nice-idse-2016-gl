package services;

import fr.unice.idse.constante.Config;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.Player;
import fr.unice.idse.services.AuthRest;
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


public class AuthRestTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(AuthRest.class);
    }

    Model model;
    JSONObject jsonObject;

    @Before
    public void init() {
        model = Model.getInstance();
        model.setGames(new ArrayList<Game>());
        model.setPlayers(new ArrayList<Player>());
        jsonObject = new JSONObject();
    }

    @Test
    public void ajouteUnJoueurReturnOk() throws JSONException {
        jsonObject.put("playername", "cruptus");
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/auth").request().post(jsonEntity);
        assertEquals(200, response.getStatus());
        assertEquals(1, model.getPlayers().size());
    }

    @Test
    public void ajouteDeuxJoueurMemeNom() throws JSONException{
        jsonObject.put("playername", "cruptus");
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/auth").request().post(jsonEntity);
        assertEquals(200, response.getStatus());

        jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response2 = target("/auth").request().post(jsonEntity);
        assertEquals(405, response2.getStatus());
        assertEquals(1, model.getPlayers().size());
    }
}
