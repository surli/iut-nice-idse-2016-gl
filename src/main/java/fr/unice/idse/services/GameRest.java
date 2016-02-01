package fr.unice.idse.services;

import fr.unice.idse.constante.Config;
import fr.unice.idse.model.Color;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.Player;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/game")
public class GameRest {

    /**
     * Retourne si la partie a commencée
     * gamename : Nom de la partie
     *
     * Retourn {state: Boolean}
     * @param gamename Nom de partie
     * @return Response
     */
    @GET
    @Path("{gamename}/gamestate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isStarted(@PathParam("gamename") String gamename){
        Model model = Model.getInstance();
        Game game = model.findGameByName(gamename);

        if(game == null)
            return Response.status(404).entity("Partie inconnu").build();

        return Response.status(200).entity("{\"state\": "+game.getBoard().gameBegin()+"}").build();
    }

    /**
     * Méthode en POST permettant l'ajout d'un joueur dans une partie
     * Signature : {_token: token, pseudo: String}
     * La partie doit être existante.
     * Renvoie {status: boolean}
     * @return Response
     */
    @POST
    @Path("{gamename}/addplayer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlayer(@PathParam("gamename") String gamename, String objJSON) throws JSONException{
        // Initialisation des objets
        Model model = Model.getInstance();
        Game game = model.findGameByName(gamename);
        JSONObject json = new JSONObject(objJSON);

        if(game == null)
            return Response.status(404).entity("Partie inconnu").build();

        // verification du token
        if(!json.has("_token"))
            return Response.status(401).entity("Invalid token").build();
        if(!Config._token.equals(json.getString("_token")))
            return Response.status(401).entity("Invalid token").build();

        // verification du joueur
        if(!json.has("pseudo"))
            return Response.status(405).entity("Missing or invalid parameters").build();
        Player player = model.createPlayer(json.getString("pseudo"));
        if(player == null)
            return Response.status(405).entity("Missing or invalid parameters").build();

        // verification game status
        if(game.gameBegin())
            return Response.status(500).entity("Game started").build();

        return Response.status(200).entity("{\"status\" : "+model.addPlayerToGame(gamename, player)+"}").build();
    }


    @POST
    @Path("{gamename}/begingame")
    @Produces(MediaType.APPLICATION_JSON)
    public Response beginGame(@PathParam("gamename") String gamename, String objJSON) throws JSONException{
        // Initialisation des objets
        Model model = Model.getInstance();
        Game game = model.findGameByName(gamename);
        JSONObject json = new JSONObject(objJSON);

        if(game == null)
            return Response.status(404).entity("Partie inconnu").build();

        // verification du token
        if(!json.has("_token"))
            return Response.status(401).entity("Invalid token").build();
        if(!Config._token.equals(json.getString("_token")))
            return Response.status(401).entity("Invalid token").build();

        // verification du pseudo
        if(!json.has("pseudo"))
            return Response.status(405).entity("Missing or invalid parameters").build();
        if(model.findPlayerByName(gamename, json.getString("pseudo")) == null)
            return Response.status(405).entity("Missing or invalid parameters").build();

        if(model.findGameByName(gamename).gameBegin())
            return Response.status(500).entity("Game started").build();

        if(model.findGameByName(gamename).getNumberPlayers() == model.findGameByName(gamename).getBoard().getPlayers().size())
            if(model.startGame(gamename, json.getString("pseudo")))
                return Response.status(200).entity("{\"status\": true}").build();

        return Response.status(500).entity("Game not tucked").build();
    }
    
    /**
     * Méthode en GET permettant de recuperer le joueur devant jouer
     * La partie doit être existante.
     * Renvoie {"pseudo": String}
     * @return Response
     */
    @GET
    @Path("{gamename}/actualplayer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualPlayer(@PathParam("gamename") String gamename) {
        // Initialisation des objets
        Model model = Model.getInstance();
        Game game = model.findGameByName(gamename);
        
        // Verifie si le jeu a commencer
    	if(!game.gameBegin()){
    		return Response.status(401).entity("{\"error\":\"Game has not begin\"}").build();
    	}
    	
    	// Recherche le joueur actuel
    	Player currentPlayer = null;
    	for(Player player : game.getPlayers()) {
    		if(player.isTurn()) {
    			currentPlayer = player;
    		}
    	}
    	
    	// Verifie qu'un joueur courant existe
    	if(currentPlayer == null) {
    		return Response.status(422).entity("{\"error\":\"No current player has been set\"}").build();
    	}
    	
    	return Response.status(200).entity("{\"pseudo\":\"" + currentPlayer.getName() + "\"}").build();
    }
    /*
     * @param playerName
     * @param gameName
     * @return
     * @throws JSONException 
     */
    
    @GET 
    @Path("/hand/{pseudo}/{gameName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response handplayer(@PathParam("playerName") String playerName,@PathParam("gameName") String gameName ) throws JSONException{
    	 Model model = Model.getInstance();
    	 Player player = model.findPlayerByName(gameName, playerName);
       if(player==null){
    	   return Response.status(405).entity("No player with : "+playerName).build();
       }
        
        Color color = null;
        int value = 0;
        int i;
        int taille =  player.getCards().size();
        
		JSONObject obj = new JSONObject();
 
		JSONArray listofPLayercards = new JSONArray();
		
		
 		for (i = 0; i < taille; i++) {
 			color=player.getCards().get(i).getColor();
 			value=player.getCards().get(i).getValue();
 			listofPLayercards.put("\"number\": "+value+"");
 			listofPLayercards.put("\"familly\": "+color+"");
 			listofPLayercards.put("\"idcard\": "+i+"");
 			obj.put("carte", listofPLayercards);
 		}
 		
	     return Response.ok(obj, MediaType.APPLICATION_JSON).build();
    }
}
