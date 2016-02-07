package fr.unice.idse.services;

import java.util.Arrays;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import fr.unice.idse.constante.Config;
import fr.unice.idse.model.Deck;
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

        // verification du token
        if(!json.has("_token"))
            return Response.status(401).entity("{\"error\" : \"Invalid token\"}").build();
        if(!Config._token.equals(json.getString("_token")))
            return Response.status(401).entity("{\"error\" : \"Invalid token\"}").build();

        // verification du champ game
        if(!json.has("game"))
            return Response.status(405).entity("{\"error\" : \"Invalid parameter\"}").build();
        String game = json.getString("game");
        if(game.length() < 3)
            return Response.status(405).entity("{\"error\" : \"Invalid parameter\"}").build();
        if(!json.has("player"))
            return Response.status(405).entity("{\"error\" : \"Invalid parameter\"}").build();
        Player player = model.createPlayer(json.getString("player"));
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
        Player player = model.createPlayer(json.getString("pseudo"));
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
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response pickacard(String gameName, String pseudo) throws JSONException {
        // Cration de tous les objets
        Model model = Model.getInstance();
        Player player = model.findPlayerByName(gameName, pseudo);
        Game game = model.findGameByName(gameName);
        
        game.getBoard().pioche();
        
        return Response.status(200).entity("carte ajoutée à la main du joueur").build();
    }
    
}
