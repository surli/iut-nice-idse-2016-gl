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
 * │   ├── game             Liste des parties (Fait)
 * │   │   ├── GET
 * │   │   ├── /{gamename}
 * │   │   │   ├── GET         Retourne l'état de la game avec les joueurs
 * │   │   │   ├── DELETE      Supprime une partie
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

    @Path("game/{gameName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response infoGames(@HeaderParam("token") String token, @PathParam("gameName") String gameName) throws JSONException{
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
}
