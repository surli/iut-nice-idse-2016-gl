package services.GameRestTest;

import fr.unice.idse.db.DataBaseOrigin;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.services.GameRest;

import org.codehaus.jettison.json.JSONException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.*;

import javax.ws.rs.core.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RemovePlayerTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(GameRest.class);
    }

    public Model model;

    @Before
    public void init() throws SQLException {
        DataBaseOrigin dataBaseOrigin = DataBaseOrigin.getInstance("sqlite");
        dataBaseOrigin.resetDatabaseSQLite();
        model = Model.getInstance();
        model.setGames(new ArrayList<Game>());
        model.setPlayers(new ArrayList<Player>());
        model.createPlayer("toto", "token");
        model.addGame(model.getPlayerFromList("token"), "tata", 4);
    }

    @Test
    public void retireUnJoueurQuiEstDansUnePartieNonCommencer() {
        for(int i = 0; i < 3; i++){
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        Response response = target("/game/tata/azert1").request().header("token", "token1").delete();
        assertEquals(200, response.getStatus());
        assertTrue(model.playerExistsInList("azert1"));
    }

    @Test
    public void retireUnJoueurDUnePartieQuiACommencer() throws JSONException{

        for(int i = 0; i < 3; i++){
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        assertEquals(4, model.findGameByName("tata").getPlayers().size());

        assertTrue(model.findGameByName("tata").start());
        Response response = target("/game/tata/azert1").request().header("token", "token1").delete();
        assertEquals(200, response.getStatus());

        for(int i = 0; i < 3; i++) {
            assertTrue(model.playerExistsInList("azert" + i));
            assertTrue(model.playerExistsInListByToken("token" + i));
        }
        assertTrue(model.playerExistsInList("toto"));
        assertTrue(model.playerExistsInListByToken("token"));
        assertFalse(model.existsGame("tata"));
    }

    @Test
    public void retirerHostDeLaPartieQuandIlEstToutSeul() {
        Response response = target("/game/tata/toto").request().header("token", "token").delete();
        assertEquals(200, response.getStatus());
        assertFalse(model.existsGame("tata"));
        assertTrue(model.playerExistsInList("toto"));
    }

    @Test
    public void retirerHostDeLaPartieEtChangeHost() {
        for(int i = 0; i < 3; i++){
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
        Response response = target("/game/tata/toto").request().header("token", "token").delete();
        assertEquals(200, response.getStatus());
        assertTrue(model.existsGame("tata"));
        assertEquals("azert0", model.findGameByName("tata").getHost().getName());
        assertTrue(model.playerExistsInList("toto"));
        for(int i = 0; i < 3; i++)
            assertFalse(model.playerExistsInList("azert"+i));
    }
}
