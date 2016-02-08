package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.Player;
import fr.unice.idse.services.GameRest;
import fr.unice.idse.services.PlayerRest;
import groovy.lang.DelegatesTo.Target;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

public class PlayerRestTest extends JerseyTest {
	private Model model;
	
	@Override
	protected Application configure() {
		return new ResourceConfig(PlayerRest.class);
	}
	
	@Before
	public void init() {
		model = Model.getInstance();
		model.setPlayers(new ArrayList<Player>());
	}
	
    /*
     * ******************************************************************************************************
     * *************************************** List player test ****************************************
     * ******************************************************************************************************
     */
	
	@Test
	public void retourneUnTableauVideSiAucunJoueurNEstPresent() throws JSONException {
		Response response = target("/player").request().get();
		JSONObject json = new JSONObject(response.readEntity(String.class));
		
	    assertEquals(200, response.getStatus());
	    assertEquals(0, json.getJSONArray("players").length());
	}
	
	@Test
	public void retourneLaListeDeJoueur() throws JSONException {
		Model model = Model.getInstance();
		model.getPlayers().add(model.createPlayer("John", "dunnowhatitis"));
		model.getPlayers().add(model.createPlayer("Marcel", "wellexplain"));
		
		Response response = target("/player").request().get();
		JSONObject json = new JSONObject(response.readEntity(String.class));
		
	    assertEquals(200, response.getStatus());
	    assertEquals(2, json.getJSONArray("players").length());
	}

}
