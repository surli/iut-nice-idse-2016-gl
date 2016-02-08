package fr.unice.idse.services;

import java.util.Arrays;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;

import fr.unice.idse.constante.Config;
import fr.unice.idse.model.Deck;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.Card;
import fr.unice.idse.model.Color;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.Player;

/**
 * /game
 * │   ├── GET             Liste des parties (Fait)
 * │   ├── POST            Créer une partie (Fait)
 * │   ├── /{gamename}
 * │   │   ├── GET         Retourne l'état de la game (Fait)
 * │   │   ├── PUT         Ajoute un joueur dans la partie (Fait)
 * │   │   ├── DELETE      Supprime une partie
 * │   │   ├── /command
 * │   │   │   ├── GET     Retourne le joueur courant (Fait)
 * │   │   │   ├── PUT     Lance une partie (Que l'host) (Fait)
 * │   │   ├── /{pseudo}
 * │   │   │   ├── GET     Retoune la main du joueur (Fait)
 * │   │   │   ├── POST    Pioche une carte
 * │   │   │   ├── PUT     Joue une carte
 */

@Path("/game")
public class GameRest {


    /**
     * Méthode permettant de lister toutes les parties existantes
     * Retour : {games : [
     *                      [name : String,
     *                       numberPlayers : String]
     *                   ]}
     * @return Response
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGame(){
        Model model = Model.getInstance();
        String [] list = new String[model.getGames().size()];
        for (int i = 0; i < model.getGames().size(); i++){
            list[i] = "{\"name\" : \""+model.getGames().get(i).getGameName()+"\", " +
                    "\"numberPlayers\" : \""+model.getGames().get(i).numberOfPlayers()+"/"+model.getGames().get(i).getNumberPlayers()+"\"}";
        }
        return Response.status(200).entity("{\"games\" : "+ Arrays.toString(list)+"}").build();
    }

    /**
     * Méthode en POST permettant la création de partie.
     * Signature : {game: String, player: String(pseudo du joueur)}
     * Le nom de la game doit être suppérieur à 3 caractères;
     * Vérifie si la partie existe ou non. Renvoie {message: boolean}
     * @return Response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGame(String objJSON) throws JSONException {
        // Cration de tous les objets
        Model model = Model.getInstance();
        JSONObject json = new JSONObject(objJSON);

        /*
        // verification du token
        if(!json.has("_token"))
            return Response.status(401).entity("{\"error\" : \"Invalid token\"}").build();
        if(!Config._token.equals(json.getString("_token")))
            return Response.status(401).entity("{\"error\" : \"Invalid token\"}").build();
         */
        
        // verification du champ game
        if(!json.has("game"))
            return Response.status(405).entity("{\"error\" : \"Invalid parameter\"}").build();
        String game = json.getString("game");
        if(game.length() < 3)
            return Response.status(405).entity("{\"error\" : \"Invalid parameter\"}").build();
        if(!json.has("player"))
            return Response.status(405).entity("{\"error\" : \"Invalid parameter\"}").build();
        Player player = model.createPlayer(json.getString("player"), "");
        if(player == null)
            return Response.status(405).entity("{\"error\" : \"Joueur existant\"").build();

        // creation de la game
        if(!model.addGame(player, game,4))
            return Response.status(500).entity("{\"message\": false}").build();

        return Response.status(200).entity("{\"message\": true}").build();
    }

    /**
     * Retourne si la partie a commencée
     * gamename : Nom de la partie
     *
     * Retourn {state: Boolean}
     * @param gamename Nom de partie
     * @return Response
     */
    @GET
    @Path("{gamename}")
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
     * Signature : {pseudo: String}
     * La partie doit être existante.
     * Renvoie {status: boolean}
     * @return Response
     */
    @PUT
    @Path("{gamename}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlayer(@PathParam("gamename") String gamename, String objJSON) throws JSONException{
        // Initialisation des objets
        Model model = Model.getInstance();
        Game game = model.findGameByName(gamename);
        JSONObject json = new JSONObject(objJSON);

        if(game == null)
            return Response.status(404).entity("Partie inconnu").build();

        // verification du token
        /*
        if(!json.has("_token"))
            return Response.status(401).entity("Invalid token").build();
        if(!Config._token.equals(json.getString("_token")))
            return Response.status(401).entity("Invalid token").build();
		*/
        
        // verification du joueur
        if(!json.has("pseudo"))
            return Response.status(405).entity("Missing or invalid parameters").build();
        Player player = model.createPlayer(json.getString("pseudo"),"");
        if(player == null)
            return Response.status(405).entity("Missing or invalid parameters").build();

        // verification game status
        if(game.gameBegin())
            return Response.status(500).entity("Game started").build();

        return Response.status(200).entity("{\"status\" : "+model.addPlayerToGame(gamename, player)+"}").build();
    }


    @PUT
    @Path("{gamename}/command")
    @Produces(MediaType.APPLICATION_JSON)
    public Response beginGame(@PathParam("gamename") String gamename, String objJSON) throws JSONException{
        // Initialisation des objets
        Model model = Model.getInstance();
        Game game = model.findGameByName(gamename);
        JSONObject json = new JSONObject(objJSON);

        if(game == null)
            return Response.status(404).entity("Partie inconnu").build();

        // verification du token
        /*
        if(!json.has("_token"))
            return Response.status(401).entity("Invalid token").build();
        if(!Config._token.equals(json.getString("_token")))
            return Response.status(401).entity("Invalid token").build();
		*/

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
    @Path("{gamename}/command")
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
    	Player currentPlayer = model.findGameByName(gamename).getBoard().getActualPlayer();

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
    @Path("/{gameName}/{pseudo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response handplayer(@PathParam("pseudo") String pseudo,@PathParam("gameName") String gameName ) throws JSONException{
         Model model = Model.getInstance();
         Player player = model.findPlayerByName(gameName, pseudo);
         if(player==null){
             return Response.status(405).entity("No player with : "+pseudo).build();
         }
         
        int taille =  player.getCards().size();

        String [] list = new String[taille];
        for (int i = 0; i < taille; i++){
            list[i] = "{\"number\" : \""+player.getCards().get(i).getValue()+"\", " +
                       "\"familly\" : \""+player.getCards().get(i).getColor()+"\"," +
                       "\"position\" : \""+ i +"\"}";
        }
        return Response.status(200).entity("{\"cartes\": "+ Arrays.toString(list)+" }").build();
    }
    
    /**
     * Methode piocher une carte
     * Verif user actuel est bien l'utilisateur
     */
    
    @POST
    @Path("/{gameName}/{pseudo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pickacard(@PathParam("gameName")String gameName,@PathParam("pseudo")String pseudo) throws JSONException {
        // Cration de tous les objets
        Model model = Model.getInstance();
        Player player = model.findPlayerByName(gameName, pseudo);
        Game game = model.findGameByName(gameName);
        
       Player verifplayer = game.getBoard().getActualPlayer();
       
       if(!player.equals(verifplayer)){
    	  return Response.status(405).entity("Joueur non autorisé à piocher").build();
       }
        
        game.getBoard().drawCard();
        
        return Response.status(200).entity("carte ajoutée à la main du joueur").build();
    }
    
    /**
     * Méthode en PUT permettant de jouer une carte
     * La partie doit être existante et commencée.
     * @param pseudo
     * @param gameName
     * @param strJSON {"value": int, "color": str, "actionCard": null}
     * @return Response 200 | 422 | 405
     * @throws JSONException 
     */
    @PUT 
    @Path("/{gameName}/{pseudo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response playCard(@PathParam("pseudo") String pseudo,@PathParam("gameName") String gameName, String strJSON ) throws JSONException{
    	Model model = Model.getInstance();      
    	// Verification que la partie existe et est commencée
    	if(!model.existsGame(gameName)) {
    		return Response.status(405).entity("{\"error\": \"The game does not exist\"}").build();
    	} 
    	if(!model.findGameByName(gameName).gameBegin()) {
    		return Response.status(405).entity("{\"error\": \"The game does hasn't begun\"}").build();
    	}
    	
    	// Verification que le joueur existe et st present dans la partie
    	Player player = model.findPlayerByName(gameName, pseudo);
    	if(player == null) {
    		return Response.status(405).entity("{\"error\": \"The player does not exist\"}").build();
    	} 
    	
    	// Verification du JSON
    	JSONObject json = new JSONObject(strJSON);
    	if(!json.has("value") || !json.has("color")) {
    		return Response.status(405).entity("{\"error\": \"The json object does not follow the rules\"}").build();
    	}
    	
    	// Verifie que le joueur peut jouer
    	if(!model.findGameByName(gameName).getBoard().askPlayerCanPlay(player)) {
    		return Response.status(405).entity("{\"error\": \"The player can't play\"}").build();
    	}
    	

    	// Verifie que le joueur possede la carte
    	Card card = new Card(json.getInt("value"), Color.valueOf(json.getString("color")));
    	if(!player.getCards().contains(card)) {
    		return Response.status(405).entity("{\"error\": \"The player does not possese this card\"}").build();
    	}
    	
    	// Verifie que la carte est jouable
    	if(!model.findGameByName(gameName).getBoard().askPlayableCard(card)) {
    		return Response.status(405).entity("{\"error\": \"The card can't be played\"}").build();
    	}
    	
    	// Finalement la carte est jouer
        model.findGameByName(gameName).getBoard().poseCard(card);
    	
        return Response.status(200).entity("{\"success\":\"The card was succesfully played\"}").build();
    }
}
