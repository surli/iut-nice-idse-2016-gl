package services.GameRestTest;

import fr.unice.idse.model.Model;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;
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
import static org.junit.Assert.assertTrue;

public class PlayCardTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(GameRest.class);
    }

    public Model model;

    @Before
    public void init() {
        model = Model.getInstance();
        model.setGames(new ArrayList<>());
        model.setPlayers(new ArrayList<>());
        model.createPlayer("toto", "token");
        model.addGame(model.getPlayerFromList("token"), "tata", 4);
        for(int i = 0; i < 3; i++){
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("tata", model.getPlayerFromList("token"+i)));
        }
    }

    @Test
    public void retourne405SiLaPartieNExistePas() throws JSONException {
        Response response = target("/game/test/john").request().header("token", "token").put(Entity.entity("{}", MediaType.APPLICATION_JSON));
        assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The game does not exist", json.getString("error"));
    }

    @Test
    public void retourne405SiLaPartieNEstPasCommencee() throws JSONException{
        Response response = target("/game/tata/john").request().header("token", "token").put(Entity.entity("{}", MediaType.APPLICATION_JSON));
        assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The game does hasn't begun", json.getString("error"));
    }

    @Test
    public void retourne405SiLeJoeurNExistePas() throws JSONException{
        assertTrue(model.findGameByName("tata").start());
        Response response = target("/game/tata/john").request().header("token", "token").put(Entity.entity("{}", MediaType.APPLICATION_JSON));
        assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The player does not exist", json.getString("error"));
    }

    @Test
    public void retourne405SiLeJoeurNExistePasDansCettePartie() throws JSONException{
        // Init the game
        assertTrue(model.createPlayer("john", "tokenultime"));
        assertTrue(model.addGame(model.getPlayerFromList("tokenultime"), "test", 4));
        for(int i = 3; i < 6; i++){
            assertTrue(model.createPlayer("azert"+i, "token"+i));
            assertTrue(model.addPlayerToGame("test", model.getPlayerFromList("token"+i)));
        }
        assertTrue(model.findGameByName("tata").start());

        Response response = target("/game/tata/azert4").request().header("token", "token4").put(Entity.entity("{}", MediaType.APPLICATION_JSON));

        assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The player does not exist", json.getString("error"));
    }

    @Test
    public void retourne405SiLeJSONEnvoyerEstInvalide() throws JSONException{
        assertTrue(model.findGameByName("tata").start());
        Response response = target("/game/tata/toto").request().header("token", "token").put(Entity.entity("{}", MediaType.APPLICATION_JSON));
        assertEquals(405, response.getStatus());

        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The json object does not follow the rules", json.getString("error"));
    }

    @Test
    public void retourne405SiLeJSONEnvoyerEstInvalideDusALaValueManquante() throws JSONException{
        assertTrue(model.findGameByName("tata").start());
        Response response = target("/game/tata/toto").request().header("token", "token").put(Entity.entity("{\"color\":\"Blue\"}", MediaType.APPLICATION_JSON));
        assertEquals(405, response.getStatus());

        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The json object does not follow the rules", json.getString("error"));
        assertEquals(false, json.has("value"));
    }

    @Test
    public void retourne405SiLeJSONEnvoyerEstInvalideDusALaColorManquante() throws JSONException{
        assertTrue(model.findGameByName("tata").start());
        Response response = target("/game/tata/toto").request().header("token", "token").put(Entity.entity("{\"value\":\"2\"}", MediaType.APPLICATION_JSON));
        assertEquals(405, response.getStatus());

        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The json object does not follow the rules", json.getString("error"));
        assertEquals(false, json.has("color"));
    }


    @Test
    public void retourne405SiLeJoeurNePeutPasJouer() throws JSONException{
        assertTrue(model.findGameByName("tata").start());
        Response response = target("/game/tata/azert2").request().header("token", "token2").put(Entity.entity("{\"value\":6, \"color\":\"Blue\"}", MediaType.APPLICATION_JSON));
        assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The player can't play", json.getString("error"));
    }


    @Test
    public void retourne405SiLeJoueurNePossedePasLaCarte() throws JSONException{
        assertTrue(model.findGameByName("tata").start());
        model.findGameByName("tata").getBoard().getActualPlayer().setCards(new ArrayList<Card>());
        model.findGameByName("tata").getBoard().getActualPlayer().getCards().add(new Card(Value.Five, Color.Blue));
        model.findGameByName("tata").getBoard().getActualPlayer().getCards().add(new Card(Value.Three, Color.Blue));
        model.findGameByName("tata").getBoard().getStack().changeColor(Color.Red);
        model.findGameByName("tata").getBoard().getStack().addCard(new Card(Value.Five, Color.Red));

        Response response = target("/game/tata/toto").request().header("token", "token").put(Entity.entity("{\"value\":Reverse, \"color\":\"Black\"}", MediaType.APPLICATION_JSON));
        assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The player does not possese this card", json.getString("error"));
    }

    @Test
    public void retourne405SiLaCarteNEstPasJouable() throws JSONException {
        assertTrue(model.findGameByName("tata").start());
        // cartes du joueur actuel
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Value.Two, Color.Blue));
        cards.add(new Card(Value.Eight, Color.Blue));
        model.findGameByName("tata").getBoard().getActualPlayer().setCards(cards);

        // Change la couleur du stack
        model.findGameByName("tata").getBoard().getStack().changeColor(Color.Red);
        model.findGameByName("tata").getBoard().setActualColor(Color.Red);

        // Cartes du stack
        ArrayList<Card> stack = new ArrayList<>();
        stack.add(new Card(Value.Eight, Color.Red));
        model.findGameByName("tata").getBoard().getStack().setStack(stack);

        Response response = target("/game/tata/toto").request().header("token", "token").put(Entity.entity("{\"value\":Two, \"color\":\"Blue\"}", MediaType.APPLICATION_JSON));


        assertEquals(405, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertEquals("The card can't be played", json.getString("error"));
    }

    @Test
    public void retourne200SiTouteLesConditionSontValider() throws JSONException{
        assertTrue(model.findGameByName("tata").start());
        model.findGameByName("tata").getBoard().getActualPlayer().getCards().add(new Card(Value.Five, Color.Blue));
        model.findGameByName("tata").getBoard().getStack().addCard(new Card(Value.Five, Color.Red));
        model.findGameByName("tata").getBoard().setActualColor(Color.Blue);
        Response response = target("/game/tata/toto").request().header("token", "token").put(Entity.entity("{\"value\":Five, \"color\":\"Blue\"}", MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());
        // Parse la reponse en JSON
        JSONObject json = new JSONObject(response.readEntity(String.class));
        assertTrue(json.getBoolean("success"));
    }
}
