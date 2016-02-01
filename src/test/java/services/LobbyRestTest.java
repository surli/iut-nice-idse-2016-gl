package services;

import com.sun.org.apache.xpath.internal.operations.Mod;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.services.LobbyRest;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


public class LobbyRestTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(LobbyRest.class);
    }

    Model model;

    @Before
    public void init(){
        model = Model.getInstance();
        model.setGames(new ArrayList<Game>());
    }

    @Test
    public void retourneStatus200PourAfficherToutesLesGames(){
        Response response = target("/lobby/games").request().get();
        assertEquals(200, response.getStatus());
    }

    /**
     * Doit retourner un tableau vide vu qu'aucune partie n'a été créé
     * @throws JSONException
     */
    @Test
    public void retourneUnTableauVideSiAucuneGame() throws JSONException{
        Response response = target("/lobby/games").request().get();
        assertEquals(200, response.getStatus());

        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals(0, json.getJSONArray("games").length());
    }

    @Test
    public void retourneUnTableauAvecUneGame() throws JSONException{
        model.addGame(model.createPlayer("toto"), "laPartie",4);

        Response response = target("/lobby/games").request().get();
        assertEquals(200, response.getStatus());

        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals(1, json.getJSONArray("games").length());
    }
}
