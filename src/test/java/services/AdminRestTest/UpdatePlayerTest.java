package services.AdminRestTest;


import fr.unice.idse.db.DataBaseOrigin;
import fr.unice.idse.db.DataBaseUser;
import fr.unice.idse.model.Model;
import fr.unice.idse.services.AdminRest;
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
import static org.junit.Assert.assertTrue;

public class UpdatePlayerTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(AdminRest.class);
    }

    Model model;
    JSONObject jsonObject;

    @Before
    public void init() throws SQLException{
        DataBaseOrigin dataBaseOrigin = DataBaseOrigin.getInstance("sqlite");
        dataBaseOrigin.resetDatabaseSQLite();
        model = Model.getInstance();
        model.setGames(new ArrayList<>());
        model.setPlayers(new ArrayList<>());

        DataBaseUser dataBaseUser = new DataBaseUser();
        assertTrue(dataBaseUser.addUser("toto", "toto@toto.fr", "passtoto", 3));
        assertTrue(model.createPlayer("admin", "admin"));
        assertTrue(model.createPlayer("toto", "toto"));
        jsonObject = new JSONObject();
    }

    @Test
    public void updateButNotAdmin() throws JSONException {
        jsonObject.put("rang", 4);
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/admin/player/toto").request().header("token", "toto").post(jsonEntity);
        assertEquals(405, response.getStatus());
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("Player is not admin",  json.getString("error"));
    }

    @Test
    public void updateUserButMissingRang() throws JSONException{
        jsonObject.put("oups", 6);
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/admin/player/toto").request().header("token", "admin").post(jsonEntity);
        assertEquals(405, response.getStatus());
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("Missing rang",  json.getString("error"));
    }


    @Test
    public void updateUserButBadRang() throws JSONException {
        jsonObject.put("rang", 6);
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/admin/player/toto").request().header("token", "admin").post(jsonEntity);
        assertEquals(405, response.getStatus());
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("Bad value of rang",  json.getString("error"));
    }

    @Test
    public void updateUserButBadPlayerName() throws JSONException {
        jsonObject.put("rang", 4);
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/admin/player/titi").request().header("token", "admin").post(jsonEntity);
        assertEquals(405, response.getStatus());
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("Player not exist in database",  json.getString("error"));
    }

    @Test
    public void updateUserSuccefull() throws JSONException {
        jsonObject.put("rang", 4);
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/admin/player/toto").request().header("token", "admin").post(jsonEntity);
        assertEquals(200, response.getStatus());
    }

}
