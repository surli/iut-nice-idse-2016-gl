package services.AdminRestTest;


import org.codehaus.jettison.json.JSONException;

import fr.unice.idse.db.DataBaseOrigin;
import fr.unice.idse.db.DataBaseUser;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.services.AdminRest;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BanPlayerTest extends JerseyTest{
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
        assertTrue(dataBaseUser.addUser("tutu", "tutu@tutu.fr", "passtutu", 3));
        assertTrue(model.createPlayer("admin", "admin"));
        assertTrue(model.createPlayer("tutu", "tutu"));
        jsonObject = new JSONObject();
    }

    @Test
    public void notBooleanInParameter() throws JSONException {
        jsonObject.put("ban", 6);
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/admin/player/tutu").request().header("token", "admin").put(jsonEntity);
        assertEquals(500, response.getStatus());
    }

    @Test
    public void banUserAndUnbanAfter() throws JSONException {
        DataBaseUser dataBaseUser = new DataBaseUser();
        assertFalse(dataBaseUser.getBan("tutu").getBoolean("ban"));
        jsonObject.put("ban", true);
        Entity<String> jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        Response response = target("/admin/player/tutu").request().header("token", "admin").put(jsonEntity);
        assertEquals(200, response.getStatus());

        assertTrue(dataBaseUser.getBan("tutu").getBoolean("ban"));

        jsonObject.remove("ban");
        jsonObject.put("ban", false);
        jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        response = target("/admin/player/tutu").request().header("token", "admin").put(jsonEntity);
        assertEquals(200, response.getStatus());
        assertFalse(dataBaseUser.getBan("tutu").getBoolean("ban"));
    }


}
