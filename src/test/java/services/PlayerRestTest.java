package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import fr.unice.idse.constante.Config;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.services.PlayerRest;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

public class PlayerRestTest extends JerseyTest {

	Model model;
	
	@Override
	protected Application configure() {
		return new ResourceConfig(PlayerRest.class);
	}
	
	@Before
	public void init() {
		model = Model.getInstance();
		model.setPlayers(new ArrayList<Player>());
		model.setGames(new ArrayList<Game>());
	}
	
	@Test
	public void retourneUnTableauVideSiAucunJoueurNEstPresent() throws JSONException {
		Response response = target("/player").request().get();
		JSONObject json = new JSONObject(response.readEntity(String.class));
		
	    assertEquals(200, response.getStatus());
	    assertEquals(0, json.getJSONArray("players").length());
	}
	
	@Test
	public void retourneLaListeDeJoueur() throws JSONException {
		model.createPlayer("John", "dunnowhatitis");
		model.createPlayer("Marcel", "wellexplain");
		
		Response response = target("/player").request().get();
		JSONObject json = new JSONObject(response.readEntity(String.class));
		
	    assertEquals(200, response.getStatus());
	    assertEquals(2, json.getJSONArray("players").length());
	}
	
    /*
     * ******************************************************************************************************
     * *************************************** get player test **********************************************
     * ******************************************************************************************************
     */
	
	@Test
	public void retourneErreur405SiLePlayerNExistePasDansLeModel() throws JSONException {
		Response response = target("/player/John").request().get();
		JSONObject json = new JSONObject(response.readEntity(String.class));
		
	    assertEquals(405, response.getStatus());
	    assertEquals("This player does not exist or is not connected", json.getString("error"));
	}
	
	@Test
	public void retourneLesDonneesDuJoueur() throws JSONException {
		model.createPlayer("John", "dunnowhatitis");
		
		Response response = target("/player/John").request().get();
		String tmp = response.readEntity(String.class);
		JSONObject json = new JSONObject(tmp);
		
	    assertEquals(200, response.getStatus());
	    assertEquals("John", json.getString("pseudo"));
	}

}
