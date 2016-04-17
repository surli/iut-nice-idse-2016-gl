package fr.unice.idse.services;

import fr.unice.idse.db.DataBaseUser;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.player.Player;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * /admin
 * │   ├── game                 Liste des parties (Fait)
 * │   │   ├── GET
 * │   │   ├── /{gamename}
 * │   │   │   ├── GET          Retourne l'état de la game avec les joueurs
 * │   │   │   ├── DELETE       Supprime une partie
 * │   ├── player               Liste des players
 * │   │   ├── GET
 * │   │   ├── /{playerName}
 * │   │   │   ├── POST         Modifie le rand d'un utilisateur
 */

@Path("admin")
public class AdminRest extends OriginRest{

    /**
     * Methode permettant de verifier si l'utilisateur est un admin
     * @param token String
     * @return String
     */
    private String verifAdmin(String token){
        if(token == null){
            return "Token not found";
        }
        Player player = Model.getInstance().getPlayerFromList(token);
        if(player == null){
            return "Player not found";
        }

        // Vérification admin
        DataBaseUser dataBaseUser = new DataBaseUser();
        if(dataBaseUser.getRang(player.getName()) != 4){
            return  "Player is not admin";
        }
        return null;
    }

    /**
     * Renvoie toutes les parties (commencée / en attente)
     * @param token String
     * @return Response
     * @throws JSONException
     */
    @Path("game")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGames(@HeaderParam("token") String token) throws JSONException{
        Model model = Model.getInstance();
        JSONObject jsonReturn = new JSONObject();

        // verification de l'admin
        String verif = verifAdmin(token);
        if(verif != null){
            jsonReturn.put("error", verif);
            return sendResponse(405, jsonReturn.toString(), "GET");
        }

        // Renvoie toutes les parties actuelles
        ArrayList<JSONObject> list = new ArrayList<>();
        for (Game game : model.getGames()) {
            JSONObject jsonFils = new JSONObject();
            jsonFils.put("gamename", game.getGameName());
            jsonFils.put("state", game.gameBegin());
            jsonFils.put("numberplayer", game.numberOfPlayers());
            jsonFils.put("maxplayer", game.getNumberPlayers());
            list.add(jsonFils);
        }
        jsonReturn.put("games", list);

        return sendResponse(200, jsonReturn.toString(), "GET");
    }

    /**
     * Retourne l'état d'une partie avec ses joueurs
     * @param token String
     * @param gameName String
     * @return Response
     * @throws JSONException
     */
    @Path("game/{gameName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response infoGame(@HeaderParam("token") String token, @PathParam("gameName") String gameName) throws JSONException{
        Model model = Model.getInstance();
        JSONObject jsonReturn = new JSONObject();

        // verification de l'admin
        String verif = verifAdmin(token);
        if(verif != null){
            jsonReturn.put("error", verif);
            return sendResponse(405, jsonReturn.toString(), "GET");
        }

        // verification de la game
        if(!model.existsGame(gameName)){
            jsonReturn.put("error", "Game not exist");
            return sendResponse(405, jsonReturn.toString(), "GET");
        }

        // retourne les informtations
        Game game = model.findGameByName(gameName);
        jsonReturn.put("gameName", gameName);
        jsonReturn.put("state", game.gameBegin());
        ArrayList<String> players = new ArrayList<>();
        for(Player player : game.getPlayers())
            players.add(player.getName());
        jsonReturn.put("players", players);

        return sendResponse(200, jsonReturn.toString(), "GET");
    }

    /**
     * Route permettant de supprimer une partie
     * @param token String
     * @param gameName String
     * @return Response
     * @throws JSONException
     */
    @Path("game/{gameName}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGame(@HeaderParam("token") String token, @PathParam("gameName") String gameName) throws JSONException{
        Model model = Model.getInstance();
        JSONObject jsonReturn = new JSONObject();

        // verification de l'admin
        String verif = verifAdmin(token);
        if(verif != null){
            jsonReturn.put("error", verif);
            return sendResponse(405, jsonReturn.toString(), "DELETE");
        }

        // verification de la game
        if(!model.existsGame(gameName)){
            jsonReturn.put("error", "Game not exist");
            return sendResponse(405, jsonReturn.toString(), "DELETE");
        }

        if(model.removeGame(gameName)){
            jsonReturn.put("state", true);
            return sendResponse(200, jsonReturn.toString(), "DELETE");
        }
        jsonReturn.put("error", "Error when delete game");
        return sendResponse(405, jsonReturn.toString(), "DELETE");
    }

    /**
     * Route permettant de récupérer tous les joueurs
     * @param token String
     * @return Response
     * @throws JSONException
     */
    @Path("player")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allPlayers(@HeaderParam("token") String token) throws JSONException{
        DataBaseUser dataBaseUser = new DataBaseUser();
        JSONObject jsonReturn = new JSONObject();

        // verification de l'admin
        String verif = verifAdmin(token);
        if(verif != null){
            jsonReturn.put("error", verif);
            return sendResponse(405, jsonReturn.toString(), "GET");
        }

        return sendResponse(200, dataBaseUser.allUser().toString(), "GET");
    }

    /**
     * Route pour modifier le rang d'un utilisateur
     * @param token String
     * @param playerName String
     * @param json String
     * @return Response
     * @throws JSONException
     */
    @Path("player/{playerName}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePlayer(@HeaderParam("token") String token, @PathParam("playerName") String playerName, String json) throws JSONException {
        JSONObject jsonReturn = new JSONObject();
        JSONObject jsonObject = new JSONObject(json);
        DataBaseUser dataBaseUser = new DataBaseUser();

        // verification de l'admin
        String verif = verifAdmin(token);
        if(verif != null){
            jsonReturn.put("error", verif);
            return sendResponse(405, jsonReturn.toString(), "POST");
        }

        // verification du rang
        if(!jsonObject.has("rang")){
            jsonReturn.put("error", "Missing rang");
            return sendResponse(405, jsonReturn.toString(), "POST");
        }
        if(jsonObject.getInt("rang") < 3 || jsonObject.getInt("rang") > 4){
            jsonReturn.put("error", "Bad value of rang");
            return sendResponse(405, jsonReturn.toString(), "POST");
        }

        // verification du playername
        if(!dataBaseUser.ifUserAlreadyExistPseudo(playerName)){
            jsonReturn.put("error", "Player not exist in database");
            return sendResponse(405, jsonReturn.toString(), "POST");
        }

        // MAJ dans la bdd
        if(dataBaseUser.updateRang(playerName, jsonObject.getInt("rang"))){
            jsonReturn.put("success", "Player updated");
            return sendResponse(200, jsonReturn.toString(), "POST");
        }

        return sendResponse(405, "Player not updated", "POST");
    }

    @Path("player/{playerName}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response banPlayer(@HeaderParam("token") String token, @PathParam("playerName") String playerName, String json) throws JSONException{
        JSONObject jsonReturn = new JSONObject();
        JSONObject jsonObject = new JSONObject(json);
        DataBaseUser dataBaseUser = new DataBaseUser();

        // verification de l'admin
        String verif = verifAdmin(token);
        if(verif != null){
            jsonReturn.put("error", verif);
            return sendResponse(405, jsonReturn.toString(), "PUT");
        }

        // verification du json
        if(!jsonObject.has("ban")){
            jsonReturn.put("error", "Missing parameter ban");
            return sendResponse(405, jsonReturn.toString(), "PUT");
        }

        // verification du playername
        if(!dataBaseUser.ifUserAlreadyExistPseudo(playerName)) {
            jsonReturn.put("error", "Player not exist in database");
            return sendResponse(405, jsonReturn.toString(), "PUT");
        }

        if(dataBaseUser.updateBan(playerName, jsonObject.getBoolean("ban") ? 1 : 0)){
            jsonReturn.put("success", "Player banned");
            return sendResponse(200, jsonReturn.toString(), "PUT");
        }

        return sendResponse(405, "Player not banned", "PUT");
    }


}
