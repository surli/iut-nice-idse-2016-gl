package services;


import fr.unice.idse.db.DataBaseGame;
import fr.unice.idse.db.DataBaseOrigin;
import fr.unice.idse.db.DataBaseUser;
import fr.unice.idse.model.Model;
import fr.unice.idse.services.AuthRest;
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
import java.sql.SQLException;
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
    public void init() throws SQLException {
        DataBaseOrigin dataBaseOrigin = DataBaseOrigin.getInstance("sqlite");
        dataBaseOrigin.resetDatabaseSQLite();
        model = Model.getInstance();
        model.setGames(new ArrayList<>());
        model.setPlayers(new ArrayList<>());
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
        response = target("/auth").request().post(jsonEntity);
        assertEquals(405, response.getStatus());
        assertEquals(1, model.getPlayers().size());
    }

    @Test
    public void deconnecterUnJouerQuiExiste() throws JSONException{
        jsonObject.put("playername", "cruptus");
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/auth").request().post(jsonEntity);
        assertEquals(200, response.getStatus());
        assertEquals(1, model.getPlayers().size());

        JSONObject jsonResult = new JSONObject(response.readEntity(String.class));
        response = target("/auth").request().header("token", jsonResult.getString("token")).delete();

        jsonResult = new JSONObject(response.readEntity(String.class));
        System.out.println(jsonResult);
        assertEquals(200, response.getStatus());
        assertEquals(0, model.getPlayers().size());
    }

    @Test
    public void inscriptionIncorrect() throws JSONException {
        jsonObject.put("email", "toto@toto.fr");
        jsonObject.put("playerName", "toto");
        jsonObject.put("password", "azerty");
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/auth/signup").request().post(jsonEntity);
        assertEquals(200, response.getStatus());

        DataBaseUser dataBaseUser = new DataBaseUser();
        assertEquals(2, dataBaseUser.allUser("").getJSONArray("users").length());
    }
}
