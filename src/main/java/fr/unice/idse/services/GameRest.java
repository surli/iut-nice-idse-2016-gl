package fr.unice.idse.services;

import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.Player;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

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
 * │   │   ├── /{playerName}
 * │   │   │   ├── GET     Retoune la main du joueur (Fait)
 * │   │   │   ├── POST    Pioche une carte (Fait)
 * │   │   │   ├── PUT     Joue une carte (Fait)
 */

@Path("/game")
public class GameRest extends OriginRest{


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
    public Response listGame(@HeaderParam("token") String token) throws JSONException{
        // Initialisation des variables
        Model model = Model.getInstance();
        JSONObject jsonObject = new JSONObject();
        ArrayList<JSONObject> games = new ArrayList<JSONObject>();

        // verification du token
        if(token == null) {
            jsonObject.put("error", "No token found");
            return sendResponse(500, jsonObject.toString(), "GET");
        }
        if(model.getPlayerFromList(token) == null){
            jsonObject.put("error", "No player found with this token");
            return sendResponse(500, jsonObject.toString(), "GET");
        }

        // Ajout des games dans la liste
        for (int i = 0; i < model.getGames().size(); i++){
            JSONObject jsonFils = new JSONObject();
            jsonFils.put("gamename", model.getGames().get(i).getGameName());
            jsonFils.put("state", model.getGames().get(i).gameBegin());
            jsonFils.put("numberplayer", model.getGames().get(i).numberOfPlayers());
            jsonFils.put("maxplayer", model.getGames().get(i).getNumberPlayers());
            games.add(jsonFils);
        }
        jsonObject.put("games", games);
        return sendResponse(200, jsonObject.toString(), "GET");
    }

    /**
     * Méthode en POST permettant la création de partie.
     * Signature : {game: String, player: String(playerName du joueur), numberplayers:Int}
     * Le nom de la game doit être suppérieur à 3 caractères;
     * Numberplayers doit être entre 2 et 6;
     * Vérifie si la partie existe ou non. Renvoie {message: boolean}
     * @return Response
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGame(String objJSON, @HeaderParam("token") String token) throws JSONException {
        // Cration de tous les objets
        Model model = Model.getInstance();
        JSONObject json = new JSONObject(objJSON);

        // verification du champ game
        if(!json.has("game"))
            return sendResponse(405, "{\"error\" : \"Invalid parameter game\"}", "POST");
        // verification du token
        if(token == null)
            return sendResponse(405, "{\"error\" : \"Missing parameters token\"}", "POST");

        String game = json.getString("game");
        if(game.length() < 3)
            return sendResponse(405, "{\"error\" : \"Invalid parameter game length\"}", "POST");
        if(!json.has("player"))
            return sendResponse(405, "{\"error\" : \"Invalid parameter player\"}", "POST");

        if(!model.playerExistsInList(json.getString("player")))
            return sendResponse(405, "{\"error\" : \"Joueur inexistant\"}", "POST");
        if(!model.getPlayerFromList(token).getName().equals(json.getString("player")))
            return sendResponse(405, "{\"error\" : \"Token invalid\"}", "POST");

        if(!json.has("numberplayers"))
            return sendResponse(405, "{\"error\" : \"Invalid parameter numberplayers\"}", "POST");
        int numberplayers = json.getInt("numberplayers");
        if(numberplayers<2||numberplayers>6){
            return sendResponse(405, "{\"error\" : \"Numberplayers must be 2 to 6 numberplayers\"}", "POST");
        }

        // creation de la game
        if(!model.addGame(model.getPlayerFromList(token), game,numberplayers))
            return sendResponse(500, "{\"message\": false}", "POST");

        return sendResponse(200, "{\"message\": true}", "POST");
    }

    /**
     * Retourne l'état de la partie
     * gamename : Nom de la partie
     *
     * @param gamename Nom de partie
     * @return Response
     */
    @GET
    @Path("{gamename}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response stateGame(@PathParam("gamename") String gamename, @HeaderParam("token") String token) throws JSONException {
        Model model = Model.getInstance();
        JSONObject jsonObject = new JSONObject();
        ArrayList<JSONObject> players = new ArrayList<JSONObject>();

        if(token == null){
            jsonObject.put("error", "Missing token");
            return sendResponse(500, jsonObject.toString(), "GET");
        }

        if(model.findGameByName(gamename) == null) {
            jsonObject.put("error", "Partie inconnue");
            return sendResponse(404, jsonObject.toString(), "GET");
        }

        if(model.findPlayerByToken(gamename, token) == null){
            jsonObject.put("error", "Token invalid for this player");
            return sendResponse(405, jsonObject.toString(), "GET");
        }

        if(model.findGameByName(gamename).getBoard().gameBegin()){
            jsonObject.put("state", true);
            jsonObject.put("currentplayer", model.findGameByName(gamename).getBoard().getActualPlayer().getName());
            for(int i = 0; i < model.findGameByName(gamename).getBoard().getPlayers().size(); i++){
                JSONObject objFils = new JSONObject();
                objFils.put("name", model.findGameByName(gamename).getBoard().getPlayers().get(i).getName());
                objFils.put("cartes", model.findGameByName(gamename).getBoard().getPlayers().get(i).getCards().size());
                players.add(objFils);
            }
            jsonObject.put("players", players);
            JSONObject jsonStack = new JSONObject();
            jsonStack.put("number", model.findGameByName(gamename).getBoard().getStack().topCard().getValue());
            jsonStack.put("family", model.findGameByName(gamename).getBoard().getStack().topCard().getColor());
            jsonObject.put("stack", jsonStack);
            return sendResponse(200, jsonObject.toString(), "GET");
        }

        for(int i = 0; i < model.findGameByName(gamename).getBoard().getPlayers().size(); i++) {
            JSONObject objFils = new JSONObject();
            objFils.put("name", model.findGameByName(gamename).getBoard().getPlayers().get(i).getName());
            players.add(objFils);
        }

        jsonObject.put("state", false);
        jsonObject.put("players", players);
        jsonObject.put("maxplayers", model.findGameByName(gamename).getNumberPlayers());
        jsonObject.put("host", model.findGameByName(gamename).getHost().getName());

        return sendResponse(200, jsonObject.toString(), "GET");
    }

    /**
     * Méthode en POST permettant l'ajout d'un joueur dans une partie
     * Signature : {playerName: String}
     * La partie doit être existante.
     * Renvoie {status: boolean}
     * @return Response
     */
    @PUT
    @Path("{gamename}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlayer(@HeaderParam("token") String token, @PathParam("gamename") String gamename, String objJSON) throws JSONException{
        // Initialisation des objets
        Model model = Model.getInstance();
        JSONObject json = new JSONObject(objJSON);
        JSONObject jsonObject = new JSONObject();

        if(model.findGameByName(gamename) == null) {
            jsonObject.put("error", "Partie inconnue");
            return sendResponse(404, jsonObject.toString(), "PUT");
        }

        // verification du token
        if(token == null) {
            jsonObject.put("error", "Missing parameters token");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }

        // verification du joueur
        if(!json.has("playerName"))
            return sendResponse(405, "Missing or invalid parameters", "PUT");
        if(model.getPlayerFromList(token) == null){
            jsonObject.put("error", "Player not found with this token");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }
        if(!model.getPlayerFromList(token).getName().equals(json.getString("playerName"))){
            jsonObject.put("error", "Token invalid for this player");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }

        // verification game status
        if(model.findGameByName(gamename).gameBegin()) {
            jsonObject.put("error", "Game started");
            return sendResponse(500, jsonObject.toString(), "PUT");
        }

        jsonObject.put("status", model.addPlayerToGame(gamename, model.getPlayerFromList(token)));
        return sendResponse(200, jsonObject.toString(), "PUT");
    }


    @PUT
    @Path("{gamename}/command")
    @Produces(MediaType.APPLICATION_JSON)
    public Response beginGame(@HeaderParam("token") String token, @PathParam("gamename") String gamename, String objJSON) throws JSONException{
        // Initialisation des objets
        Model model = Model.getInstance();
        JSONObject json = new JSONObject(objJSON);
        JSONObject jsonObject = new JSONObject();

        // Verification de la game
        if(model.findGameByName(gamename) == null) {
            jsonObject.put("error", "Partie inconnue");
            return sendResponse(404, jsonObject.toString(), "PUT");
        }

        // verification du token
        if(token == null){
            jsonObject.put("error", "Token not found");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }

        // verification du playerName
        if(!json.has("playerName")) {
            jsonObject.put("error", "Missing parameters playerName");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }
        if(model.findPlayerByName(gamename, json.getString("playerName")) == null) {
            jsonObject.put("error", "playerName unknow");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }
        if(!model.findPlayerByName(gamename, json.getString("playerName")).getToken().equals(token)){
            jsonObject.put("error", "Token invalid for this player");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }

        if(model.findGameByName(gamename).gameBegin()) {
            jsonObject.put("error", "Game started");
            return sendResponse(500, jsonObject.toString(), "PUT");
        }

        if(model.findGameByName(gamename).getNumberPlayers() == model.findGameByName(gamename).getBoard().getPlayers().size())
            if(model.startGame(gamename, json.getString("playerName"))) {
                jsonObject.put("status", true);
                return sendResponse(200, jsonObject.toString(), "PUT");
            }

        jsonObject.put("error", "Game not tucked");
        return sendResponse(500, jsonObject.toString(), "PUT");
    }

    /**
     * Méthode en GET permettant de recuperer le joueur devant jouer
     * La partie doit être existante.
     * Renvoie {"playerName": String}
     * @return Response
     */
    @GET
    @Path("{gamename}/command")
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualPlayer(@HeaderParam("token") String token, @PathParam("gamename") String gamename) throws JSONException{
        // Initialisation des objets
        Model model = Model.getInstance();
        JSONObject jsonObject = new JSONObject();

        // Verifie si le jeu a commencer
        if(!model.findGameByName(gamename).gameBegin()) {
            jsonObject.put("error", "Game has not begin");
            return sendResponse(401, jsonObject.toString(), "GET");
        }

        if(model.findPlayerByToken(gamename, token) == null){
            jsonObject.put("error", "Invalid token");
            return sendResponse(405, jsonObject.toString(), "GET");
        }

        // Recherche le joueur actuel
        Player currentPlayer = model.findGameByName(gamename).getBoard().getActualPlayer();

        // Verifie qu'un joueur courant existe
        if(currentPlayer == null) {
            jsonObject.put("error", "No current player has been set");
            return sendResponse(422, jsonObject.toString(), "GET");
        }

        jsonObject.put("playerName", currentPlayer.getName());
        return sendResponse(200, jsonObject.toString(), "GET");
    }

    /*
     * @param playerName
     * @param gameName
     * @retur
     * @throws JSONException
     */
    @GET
    @Path("/{gameName}/{playerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response handplayer(@HeaderParam("token") String token, @PathParam("playerName") String playerName, @PathParam("gameName") String gameName) throws JSONException{
        Model model = Model.getInstance();
        Player player = model.findPlayerByName(gameName, playerName);
        JSONObject jsonObject = new JSONObject();
        ArrayList<JSONObject> cartes = new ArrayList<>();

        if(token == null){
            jsonObject.put("error", "Token not found");
            return sendResponse(405, jsonObject.toString(), "GET");
        }
        if(model.findPlayerByName(gameName, playerName) == null) {
            jsonObject.put("error", "No player found");
            return sendResponse(405, jsonObject.toString(), "GET");
        }
        if(!model.findPlayerByName(gameName, playerName).getToken().equals(token)){
            jsonObject.put("error", "Token invalid for this player");
            return sendResponse(405, jsonObject.toString(), "GET");
        }


        for (int i = 0; i < player.getCards().size(); i++){
            JSONObject jsonFils = new JSONObject();
            jsonFils.put("number", player.getCards().get(i).getValue());
            jsonFils.put("family", player.getCards().get(i).getColor());
            jsonFils.put("position", i);
            cartes.add(jsonFils);
        }
        jsonObject.put("cartes", cartes);

        return sendResponse(200, jsonObject.toString(), "GET");
    }

    /**
     * Methode piocher une carte
     * Verif user actuel est bien l'utilisateur
     */

    @POST
    @Path("/{gameName}/{playerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pickacard(@PathParam("gameName")String gameName,@PathParam("playerName")String playerName) throws JSONException {
        // Cration de tous les objets
        Model model = Model.getInstance();
        JSONObject jsonReturn = new JSONObject();
        Player player = model.findPlayerByName(gameName, playerName);
        Player verifplayer = model.findGameByName(gameName).getBoard().getActualPlayer();

        if(!player.equals(verifplayer)) {
            jsonReturn.put("return", false);
            return sendResponse(405, jsonReturn.toString(), "POST");
        }
        model.findGameByName(gameName).getBoard().drawCard();
        jsonReturn.put("return", true);
        return sendResponse(200, jsonReturn.toString(), "POST");
    }

    /**
     * Méthode en PUT permettant de jouer une carte
     * La partie doit être existante et commencée.
     * @param playerName
     * @param gameName
     * @param strJSON {"value": int, "color": str, "actionCard": null}
     * @return Response 200 | 422 | 405
     * @throws JSONException
     */
    @PUT
    @Path("/{gameName}/{playerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response playCard(@PathParam("playerName") String playerName,@PathParam("gameName") String gameName, String strJSON ) throws JSONException{
        Model model = Model.getInstance();
        // Verification que la partie existe et est commencée
        if(!model.existsGame(gameName)) {
            return sendResponse(405, "{\"error\": \"The game does not exist\"}", "PUT");
        }
        if(!model.findGameByName(gameName).gameBegin()) {
            return sendResponse(405, "{\"error\": \"The game does hasn't begun\"}", "PUT");
        }

        // Verification que le joueur existe et st present dans la partie
        Player player = model.findPlayerByName(gameName, playerName);
        if(player == null) {
            return sendResponse(405, "{\"error\": \"The player does not exist\"}", "PUT");
        }

        // Verification du JSON
        JSONObject json = new JSONObject(strJSON);
        if(!json.has("value") || !json.has("color")) {
            return sendResponse(405, "{\"error\": \"The json object does not follow the rules\"}", "PUT");
        }

        // Verifie que le joueur peut jouer
        if(!model.findGameByName(gameName).getBoard().askPlayerCanPlay(player)) {
            return sendResponse(405, "{\"error\": \"The player can't play\"}", "PUT");
        }


        // Verifie que le joueur possede la carte
        Card card = new Card(Value.valueOf(json.getString("value")), Color.valueOf(json.getString("color")));
        if(!player.getCards().contains(card)) {
            return sendResponse(405, "{\"error\": \"The player does not possese this card\"}", "PUT");
        }

        // Verifie que la carte est jouable
        if(!model.findGameByName(gameName).getBoard().askPlayableCard(card)) {
            return sendResponse(405, "{\"error\": \"The card can't be played\"}", "PUT");
        }

        // Finalement la carte est jouer
        model.findGameByName(gameName).getBoard().poseCard(card);

        return sendResponse(200, "{\"success\":\"The card was succesfully played\"}", "PUT");
    }
}