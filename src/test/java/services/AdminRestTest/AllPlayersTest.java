package services.AdminRestTest;

import fr.unice.idse.db.DataBaseOrigin;
import fr.unice.idse.db.DataBaseUser;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.player.Player;
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


public class AllPlayersTest extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(AdminRest.class);
    }

    Model model;
    JSONObject jsonObject;

    @Before
    public void init() throws SQLException {
        DataBaseOrigin dataBaseOrigin = DataBaseOrigin.getInstance("sqlite");
        dataBaseOrigin.resetDatabaseSQLite();

        model = Model.getInstance();
        model.setGames(new ArrayList<Game>());
        model.setPlayers(new ArrayList<Player>());

        DataBaseUser dataBaseUser = new DataBaseUser();
        assertTrue(dataBaseUser.addUser("tutu", "tutu@tutu.fr", "tutu", 3));
        assertTrue(dataBaseUser.addUser("toto", "toto@toto.fr", "toto", 3));
        assertTrue(dataBaseUser.addUser("tata", "tata@tata.fr", "tata", 3));
        assertTrue(dataBaseUser.addUser("titi", "titi@titi.fr", "titi", 3));
        assertTrue(model.createPlayer("admin", "admin"));
        assertTrue(model.createPlayer("tutu", "tutu"));
        jsonObject = new JSONObject();
    }

    @Test
    public void rechercheTousLesUtilisateurs() throws JSONException{
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/admin/player").request().header("token", "admin").post(jsonEntity);
        JSONObject jsonResult = new JSONObject(response.readEntity(String.class));
        assertEquals(200, response.getStatus());
        assertEquals(4, jsonResult.getJSONArray("users").length());
    }

    @Test
    public void rechercheUtilisateursAvecFiltre() throws JSONException{
        jsonObject.put("search", "to");
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/admin/player").request().header("token", "admin").post(jsonEntity);
        JSONObject jsonResult = new JSONObject(response.readEntity(String.class));
        assertEquals(200, response.getStatus());
        assertEquals(1, jsonResult.getJSONArray("users").length());
    }

    @Test
    public void rechercheUtilisateursAvecFiltrePasDeResultat() throws JSONException{
        jsonObject.put("search", "tototototo");
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/admin/player").request().header("token", "admin").post(jsonEntity);
        JSONObject jsonResult = new JSONObject(response.readEntity(String.class));
        assertEquals(405, response.getStatus());
        assertEquals("No result", jsonResult.getString("error"));
    }
}
