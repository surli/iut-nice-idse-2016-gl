package services.GameRestTest;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.Game;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayAGameRest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(GameRest.class);
    }

    Model model;
    JSONObject jsonObject;
    JSONObject jsonResponse;
    Response response;
    Entity<String> jsonEntity;

    @Before
    public void init(){
        model = Model.getInstance();
        model.setGames(new ArrayList<>());
        model.setPlayers(new ArrayList<>());
        for(int i = 1; i < 5; i++)
            assertTrue(model.createPlayer("joueur"+i, "token"+i));
        jsonObject = new JSONObject();
    }

    @Test
    public void finirUnePartie() throws JSONException {
        // Creation de la partie
        jsonObject.put("game", "lagame");
        jsonObject.put("player", "joueur1");
        jsonObject.put("numberplayers", 4);

        jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);

        response = target("/game").request().header("token", "token1").post(jsonEntity);
        assertEquals(200, response.getStatus());

        // Rejoindre la partie
        for(int i = 2; i < 5; i++) {
            jsonObject = new JSONObject();
            jsonObject.put("playerName", "joueur"+i);
            jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
            response = target("/game/lagame").request().header("token", "token"+i).put(jsonEntity);
            assertEquals(200, response.getStatus());
        }

        // Demarrer la partie
        jsonObject.put("playerName", "joueur1");
        jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        response = target("/game/lagame/command").request().header("token", "token1").put(jsonEntity);
        assertEquals(200, response.getStatus());
        jsonResponse = new JSONObject(response.readEntity(String.class));
        assertTrue(jsonResponse.getBoolean("status"));

        // On regarde le status de la game
        response = target("/game/lagame").request().header("token", "token1").get();
        assertEquals(200, response.getStatus());
        jsonResponse = new JSONObject(response.readEntity(String.class));
        assertTrue(jsonResponse.getBoolean("state"));
        assertEquals(4, jsonResponse.getJSONArray("players").length());
        assertFalse(jsonResponse.getBoolean("gameEnd"));

        // On triche, et on change les cartes du premier joueur
        Board board = model.findGameByName("lagame").getBoard();
        board.getPlayers().get(0).setCards(new ArrayList<>());
        board.getPlayers().get(0).getCards().add(new Card(Value.Five, Color.Red));
        board.getStack().addCard(new Card(Value.Five, Color.Green));
        board.setActualColor(Color.Green);

        jsonObject = new JSONObject();
        jsonObject.put("value", "Five");
        jsonObject.put("color", "Red");
        jsonEntity = Entity.entity(jsonObject.toString(), MediaType.APPLICATION_JSON);
        response = target("/game/lagame/joueur1").request().header("token", "token1").put(jsonEntity);
        assertEquals(200, response.getStatus());

        // On regarde le status de la game
        response = target("/game/lagame").request().header("token", "token1").get();
        assertEquals(200, response.getStatus());
        jsonResponse = new JSONObject(response.readEntity(String.class));
        assertTrue(jsonResponse.getBoolean("state"));
        assertEquals(4, jsonResponse.getJSONArray("players").length());
        assertTrue(jsonResponse.getBoolean("gameEnd"));
    }
}
