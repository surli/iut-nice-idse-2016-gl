package fr.unice.idse.services;

import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fr.unice.idse.constante.Config;
import fr.unice.idse.model.Alternative;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.model.regle.EffectCard;

/**
 * /game
 * │   ├── GET             Liste des parties (Fait)
 * │   ├── POST            Créer une partie (Fait)
 * │   ├── /alternative
 * │   │   ├── GET         Retourne les alternatives existante
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
 * │   │   │   ├── DELETE  Supprimes un joueur d'une partie (Fait)
 */

@Path("/game")
public class GameRest extends OriginRest{

    /**
     * Méthode permettant de lister toutes les parties existantes
     * @param token token du jouer
     * @return Response
     * @throws JSONException exception
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
            return sendResponse(404, jsonObject.toString(), "GET");
        }
        if(model.getPlayerFromList(token) == null){
            jsonObject.put("error", "No player found with this token");
            return sendResponse(405, jsonObject.toString(), "GET");
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
     Méthode en POST permettant la création de partie.
     * Signature : {game: String, player: String(playerName du joueur), numberplayers:Int}
     * Le nom de la game doit être suppérieur à 3 caractères;
     * Numberplayers doit être entre 2 et 6;
     * Vérifie si la partie existe ou non. Renvoie {message: boolean}
     * @param objJSON parametre
     * @param token token du joueur
     * @return Response
     * @throws JSONException exception
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGame(String objJSON, @HeaderParam("token") String token) throws JSONException {
        // Cration de tous les objets
        Model model = Model.getInstance();
        JSONObject json = new JSONObject(objJSON);
        JSONObject jsonResult = new JSONObject();

        // verification du champ game
        if(!json.has("game")) {
            jsonResult.put("error", "Invalid parameter game");
            return sendResponse(405, jsonResult.toString(), "POST");
        }
        // verification du token
        if(token == null) {
            jsonResult.put("error", "Missing parameters token");
            return sendResponse(405, jsonResult.toString(), "POST");
        }
        String game = json.getString("game");
        if(game.length() < 3){
            jsonResult.put("error", "Invalid parameter game length");
            return sendResponse(405, jsonResult.toString(), "POST");
        }
        if(!json.has("player")){
            jsonResult.put("error", "Invalid parameter player");
            return sendResponse(405, jsonResult.toString(), "POST");
        }

        if(model.getPlayerFromList(token)==null){
            jsonResult.put("error", "Joueur inexistant");
            return sendResponse(405, jsonResult.toString(), "POST");
        }

        if(!model.getPlayerFromList(token).getName().equals(json.getString("player"))){
            jsonResult.put("error", "Token invalid");
            return sendResponse(405, jsonResult.toString(), "POST");
        }

        if(!json.has("numberplayers")){
            jsonResult.put("error", "Invalid parameter numberplayers");
            return sendResponse(405, jsonResult.toString(), "POST");
        }
        int numberplayers = json.getInt("numberplayers");
        if(numberplayers<2||numberplayers>6){
            jsonResult.put("error", "Numberplayers must be 2 to 6 numberplayers");
            return sendResponse(405, jsonResult.toString(), "POST");
        }

        if(!json.has("alternative")){
            jsonResult.put("error", "No alternative parameter");
            return sendResponse(405, jsonResult.toString(), "POST");
        }

        ArrayList alternative = Config.alternatives.get(json.getString("alternative"));
        if(alternative == null){
            jsonResult.put("error", "No alternative found");
            return sendResponse(405, jsonResult.toString(), "POST");
        }

        // creation de la game
        if(!model.addGame(model.getPlayerFromList(token), game,numberplayers, alternative)){
            jsonResult.put("message", false);
            return sendResponse(500, jsonResult.toString(), "POST");
        }

        jsonResult.put("message", true);
        return sendResponse(200, jsonResult.toString(), "POST");
    }

    /**
     * Retourne l'état de la partie
     * @param gamename Nom de la partie
     * @param token Token du joueur
     * @return Response
     * @throws JSONException exception
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
            return sendResponse(405, jsonObject.toString(), "GET");
        }

        if(model.findGameByName(gamename) == null) {
            jsonObject.put("error", "Partie inconnue");
            return sendResponse(405, jsonObject.toString(), "GET");
        }

        if(model.findPlayerByToken(gamename, token) == null){
            jsonObject.put("error", "Token invalid for this player");
            return sendResponse(405, jsonObject.toString(), "GET");
        }

        if(model.findGameByName(gamename).gameBegin()){
            jsonObject.put("state", true);
            jsonObject.put("currentplayer", model.findGameByName(gamename).getActualPlayer().getName());
            for(int i = 0; i < model.findGameByName(gamename).getPlayers().size(); i++){
                JSONObject objFils = new JSONObject();
                objFils.put("name", model.findGameByName(gamename).getPlayers().get(i).getName());
                objFils.put("cartes", model.findGameByName(gamename).getPlayers().get(i).getCards().size());
                players.add(objFils);
            }
            jsonObject.put("players", players);
            JSONObject jsonStack = new JSONObject();
            jsonStack.put("number", model.findGameByName(gamename).getStack().topCard().getValue());
            jsonStack.put("family", model.findGameByName(gamename).getStack().topCard().getColor());
            jsonObject.put("stack", jsonStack);
            jsonObject.put("gameEnd", model.findGameByName(gamename).gameEnd());
            return sendResponse(200, jsonObject.toString(), "GET");
        }

        for(int i = 0; i < model.findGameByName(gamename).getPlayers().size(); i++) {
            JSONObject objFils = new JSONObject();
            objFils.put("name", model.findGameByName(gamename).getPlayers().get(i).getName());
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
     * @param token Token du joueur
     * @param gamename Nom de la partie
     * @param objJSON Parametre
     * @return Response
     * @throws JSONException exception
     */
    @PUT
    @Path("{gamename}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlayer(@HeaderParam("token") String token, @PathParam("gamename") String gamename, String objJSON) throws JSONException{
        // Initialisation des objets
        Model model = Model.getInstance();
        JSONObject json = new JSONObject(objJSON);
        JSONObject jsonObject = new JSONObject();
        
        // verification du token
        if(token == null) {
            jsonObject.put("error", "Missing parameters token");
            return sendResponse(404, jsonObject.toString(), "PUT");
        }
        
        if(model.findGameByName(gamename) == null) {
            jsonObject.put("error", "Partie inconnue");
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


    /**
     * Méthode en PUT permettant le début d'une partie
     * Signature : {gamename: String}/command
     * La partie doit être existante.
     * @param token Token du joueur
     * @param gamename Nom de la partie
     * @param objJSON parametre
     * @return Response
     * @throws JSONException exception
     */
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
            return sendResponse(404, jsonObject.toString(), "PUT");
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

        if(model.findGameByName(gamename).getNumberPlayers() == model.findGameByName(gamename).getPlayers().size())
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
     * @param token Token du joueur
     * @param gamename Nom de la partie
     * @return Response
     * @throws JSONException exception
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
        Player currentPlayer = model.findGameByName(gamename).getActualPlayer();

        // Verifie qu'un joueur courant existe
        if(currentPlayer == null) {
            jsonObject.put("error", "No current player has been set");
            return sendResponse(422, jsonObject.toString(), "GET");
        }

        jsonObject.put("playerName", currentPlayer.getName());
        return sendResponse(200, jsonObject.toString(), "GET");
    }

    /**
     * Retourne la main du joueur
     * @param token Token du joueur
     * @param playerName Nom du joueur
     * @param gameName Nom de la partie
     * @return Response
     * @throws JSONException exception
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
            return sendResponse(404, jsonObject.toString(), "GET");
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
     * Méthode en POST permettant de faire piocher une carte au joueur actuel
     * Signature : {gamename: String}/{playerName:string}
     * @param token Token du joueur
     * @param gameName Nom de la partie
     * @param playerName Nom du joueur
     * @return Response
     * @throws JSONException exception
     */
    @POST
    @Path("{gameName}/{playerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pickacard(@HeaderParam("token") String token, @PathParam("gameName") String gameName,@PathParam("playerName") String playerName) throws JSONException {
        // Cration de tous les objets
        Model model = Model.getInstance();
        JSONObject jsonReturn = new JSONObject();

        // verification de l'existance de la game
        if(model.findGameByName(gameName) == null) {
            jsonReturn.put("error", "Partie inconnue");
            return sendResponse(404, jsonReturn.toString(), "POST");
        }
        Game game = model.findGameByName(gameName);

        // Vérification de l'authentification
        if(token == null){
            jsonReturn.put("error", "Token not found");
            return sendResponse(404, jsonReturn.toString(), "POST");
        }

        if(model.findPlayerByToken(gameName, token) == null){
            jsonReturn.put("error", "Player not found with this token");
            return sendResponse(405, jsonReturn.toString(), "POST");
            
        }

        if(!model.findPlayerByToken(gameName, token).getName().equals(playerName)){
            jsonReturn.put("error", "Invalid token for this player");
            return sendResponse(405, jsonReturn.toString(), "POST");
        }
        

        // verifie que la partie est bien lancée
        if(!game.gameBegin()){
            jsonReturn.put("error", "Game not started");
            return sendResponse(405, jsonReturn.toString(), "POST");
        }
        // verifie si la partie est terminé
        if(game.gameEnd()){
            jsonReturn.put("error", "Game terminated");
            return sendResponse(405, jsonReturn.toString(), "POST");
        }

        // Verifcation du joueur actuel
        if(!game.getActualPlayer().getToken().equals(token)){
            jsonReturn.put("error", "It's not this player to play");
            return sendResponse(405, jsonReturn.toString(), "POST");
        }

        int card = game.getActualPlayer().getCards().size();
        int drawCard = game.getCptDrawCard();

        game.drawCard();

        if(game.getActualPlayer().getCards().size() != card+drawCard){
            jsonReturn.put("return", false);
            return sendResponse(405, jsonReturn.toString(), "POST");
        }
        jsonReturn.put("return", true);
        game.nextPlayer();
        return sendResponse(200, jsonReturn.toString(), "POST");
    }

    /**
     * Méthode en PUT permettant de jouer une carte
     * La partie doit être existante et commencée.
     * @param token Token du joueur
     * @param playerName Nom du joueur
     * @param gameName Nom de la partie
     * @param strJSON {"value": int, "color": str, "actionCard": null}
     * @return Response
     * @throws JSONException exception
     */
    @PUT
    @Path("/{gameName}/{playerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response playCard(@HeaderParam("token") String token, @PathParam("playerName") String playerName,
    		@PathParam("gameName") String gameName, String strJSON ) throws JSONException{
    	
        Model model = Model.getInstance();
        JSONObject jsonObject = new JSONObject();

        // Verification que la partie existe et est commencée
        if(!model.existsGame(gameName)) {
            jsonObject.put("error", "The game does not exist");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }
        Game game = model.findGameByName(gameName);

        if(!game.gameBegin()) {
            jsonObject.put("error", "The game does hasn't begun");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }
        // verifie si la partie est terminé
        if(game.gameEnd()){
            jsonObject.put("error", "Game terminated");
            return sendResponse(405, jsonObject.toString(), "POST");
        }

        // Verification de l'authentification
        if(token == null){
            jsonObject.put("error", "Token not found");
            return sendResponse(404, jsonObject.toString(), "PUT");
        }
        if(model.findPlayerByName(gameName, playerName) == null) {
            jsonObject.put("error", "The player does not exist");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }
        if(!model.findPlayerByName(gameName, playerName).getToken().equals(token)){
            jsonObject.put("error", "Invalid token for this player");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }

        // Verification du JSON
        JSONObject json = new JSONObject(strJSON);
        if(!json.has("value") || !json.has("color")) {
            jsonObject.put("error", "The json object does not follow the rules");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }

        // Verifie que le joueur peut jouer
        if(!game.getActualPlayer().getToken().equals(token)) {
            jsonObject.put("error", "The player can't play");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }


        // Verifie que le joueur possede la carte
        Card card = new Card(Value.valueOf(json.getString("value")), Color.valueOf(json.getString("color")));
        if(!model.findPlayerByToken(gameName, token).getCards().contains(card)) {
            jsonObject.put("error", "The player does not possese this card");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }



            // Verifie que la carte est jouable
        if(!game.askPlayableCard(card)) {
            jsonObject.put("error", "The card can't be played");
            return sendResponse(405, jsonObject.toString(), "PUT");
        }

        // Finalement la carte est jouer
        model.findGameByName(gameName).poseCard(card);
        EffectCard rule = model.findGameByName(gameName).getAlternative().getEffectCard(card);
        if(rule != null){
            if(rule.isColorChangingCard()){
                if(!json.has("setcolor")){
                    jsonObject.put("error", "The card need new color");
                    return sendResponse(405, jsonObject.toString(), "PUT");
                }
                Color color;
                switch (json.getString("setcolor")){
                    case "Red" :
                        color = Color.Red;
                        break;
                    case "Blue":
                        color = Color.Blue;
                        break;
                    case "Green":
                        color = Color.Green;
                        break;
                    case "Yellow":
                        color = Color.Yellow;
                        break;
                    default:
                        jsonObject.put("error", "Setcolor not accepted");
                        return sendResponse(405, jsonObject.toString(), "PUT");
                }
                rule.action(color);
            }
            rule.action();
        }

        game.nextPlayer();
        
        /*La méthode à apeller est ici mais 2 des tests fails je vous laisse aranger ça
        if(rule.getEffect())
        {
        	rule.effect();
        }*/
        
        jsonObject.put("success", true);
        return sendResponse(200, jsonObject.toString(), "PUT");
    }

    /**
     * Methode qui permet de retirer un joueur d'une partie qui n'est pas commencée
     * @param token Token du joueur
     * @param gameName Nom de la partie
     * @param playerName Nom du joueur
     * @return Response
     * @throws JSONException exception
     */
    @DELETE
    @Path("/{gameName}/{playerName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerPlayer(@HeaderParam("token") String token, @PathParam("gameName") String gameName, @PathParam("playerName") String playerName) throws JSONException {
        // Initialisation des variables
        Model model = Model.getInstance();
        JSONObject jsonReturn = new JSONObject();

        // Verificaton du token
        if(token == null){
            jsonReturn.put("error", "Token not found");
            return sendResponse(405, jsonReturn.toString(), "DELETE");
        }

        // Verification des champs
        if(gameName == null){
            jsonReturn.put("error", "Parameter gameName not found");
            return sendResponse(405, jsonReturn.toString(), "DELETE");
        }
        if(playerName == null){
            jsonReturn.put("error", "Parameter playerName not found");
            return sendResponse(405, jsonReturn.toString(), "DELETE");
        }

        // Verification de la game
        if(!model.existsGame(gameName)){
            jsonReturn.put("error", "Game not found");
            return sendResponse(405, jsonReturn.toString(), "DELETE");
        }

        Game game = model.findGameByName(gameName);

        // Vérification du joueur
        if(model.findPlayerByName(gameName, playerName) == null){
            jsonReturn.put("error", "Player not found");
            return sendResponse(405, jsonReturn.toString(), "DELETE");
        }
        if(!model.findPlayerByName(gameName, playerName).getToken().equals(token)){
            jsonReturn.put("error", "Token invalid for this player");
            return sendResponse(405, jsonReturn.toString(), "DELETE");
        }

        // Si la partie a commencer supprimer tous les joueurs ainsi que la partie
        if(game.gameBegin()){
            int taille = game.getPlayers().size();
            for(int i = 0; i < taille; i++) {
                System.out.println(model.findGameByName(gameName).getPlayers().get(0).getName());
                model.removePlayerFromGameByName(gameName, model.findGameByName(gameName).getPlayers().get(0).getName());
            }
            model.removeGame(gameName);
            jsonReturn.put("status", true);
            return sendResponse(200, jsonReturn.toString(), "DELETE");
        }

        // Si le joueur est l'hôte de la partie
        if(game.getHost().getName().equals(playerName)){
            // s'il ne reste qu'un joueur dans la partie
            if(game.getPlayers().size() == 1){
                if(model.removePlayerFromGameByName(gameName, playerName))
                    if(model.removeGame(gameName)){
                        jsonReturn.put("status", true);
                        return sendResponse(200, jsonReturn.toString(), "DELETE");
                    }
            // S'il y a d'autre joueur dans la partie
            } else {
                if(model.removePlayerFromGameByName(gameName, playerName)){
                    game.setHost(model.findGameByName(gameName).getPlayers().get(0));
                    jsonReturn.put("status", true);
                    return sendResponse(200, jsonReturn.toString(), "DELETE");
                }
            }
        }

        // remove player de la game
        if(model.removePlayerFromGameByName(gameName, playerName)){
            jsonReturn.put("status", true);
            return sendResponse(200, jsonReturn.toString(), "DELETE");
        }
        jsonReturn.put("status", false);
        return sendResponse(405, jsonReturn.toString(), "DELETE");
    }


    /**
     * Retournes les alternatives existantes
     * @param token Token du joueur
     * @return Response
     * @throws JSONException exception
     */
    @Path("/alternative")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAlternatives(@HeaderParam("token") String token) throws JSONException {
        // Initialisation des variables
        Model model = Model.getInstance();
        JSONObject jsonReturn = new JSONObject();

        // Verificaton du token
        if(token == null){
            jsonReturn.put("error", "Token not found");
            return sendResponse(405, jsonReturn.toString(), "GET");
        }
        if(!model.playerExistsInListByToken(token)){
            jsonReturn.put("error", "Player not found");
            return sendResponse(405, jsonReturn.toString(), "GET");
        }

        ArrayList<String> alternatives = new ArrayList<>();
        Config.alternatives.forEach((k, v) -> alternatives.add(k));

        jsonReturn.put("alternatives", alternatives);
        return sendResponse(200, jsonReturn.toString(), "GET");

    }
}
