package fr.unice.idse.services;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
     * Méthode permettant de lister toutes les parties existantes
     * Retour : {games : [
     *                      [name : String,
     *                       numberPlayers : String]
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
}
