package services;

import fr.unice.idse.constante.Config;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.services.AuthRest;
import fr.unice.idse.services.GameRest;
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

    @Before
    public void init() {
        model = Model.getInstance();
        model.setGames(new ArrayList<>());
        model.setPlayers(new ArrayList<>());
    }

    @Test
    public void ajouteUnJoueurReturnOk(){
        String json = "{playername: 'titi'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target("/auth").request().post(jsonEntity);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void ajouteDeuxJoueurMemeNom(){
        String json = "{playername: 'titi'}";
        Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        Response response = target("/auth").request().post(jsonEntity);
        assertEquals(200, response.getStatus());

        json = "{playername: 'titi'}";
        jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);
        response = target("/auth").request().post(jsonEntity);
        assertEquals(405, response.getStatus());
    }
}
