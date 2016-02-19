package fr.unice.idse.services;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import fr.unice.idse.model.Model;
import fr.unice.idse.model.Player;

/**
 * /player 
 * ├── GET             Retourne la liste des joueurs
 * ├── /{playerName} 
 * │   ├── GET         Retourne les informations du joueur
 */

@Path("/player")
public class PlayerRest extends OriginRest {

    /**
     * Méthode permettant de recuperer le joueur
     * Retour : {players : [
     *                      [pseudo : String,
     *                       email : String]
     *                   ]}
     * @return Response
     * @throws JSONException 
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPlayer() throws JSONException{
        Model model = Model.getInstance();
        JSONArray ja = new JSONArray();
        for (Player player : model.getPlayers()){
           JSONObject jo = new JSONObject();
           jo.put("pseudo", player.getName());
           jo.put("email", "N/A");
           ja.put(jo);
        }
        JSONObject json = new JSONObject();
        json.put("players", ja);
        return sendResponse(200, json.toString(), "GET");
    }
    
    /**
     * Méthode permettant de recuperer un joueur selon son id
     * Retour 200 Ok : {pseudo : String, email : String}
     * @return Response
     * @throws JSONException 
     */
    @GET
    @Path("/{pseudo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayer(@PathParam("pseudo") String pseudo) throws JSONException{
        Model model = Model.getInstance();
        if(!model.playerExistsInList(pseudo)) {
        	return sendResponse(405, "{\"error\":\"This player does not exist or is not connected\"}", "GET");
        }
        
        Player player = null;
        for (int i = 0; i < model.getPlayers().size() && player == null; i++){
        	 if(model.getPlayers().get(i).getName().equals(pseudo)) {
        		 player = model.getPlayers().get(i);
        	 }
        }
        
        JSONObject json = new JSONObject();
   		json.put("pseudo", player.getName());
       	json.put("email", "N/A");
        return sendResponse(200, json.toString(), "GET");
    }
}
