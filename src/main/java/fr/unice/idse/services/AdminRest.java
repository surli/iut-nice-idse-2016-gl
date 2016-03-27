package fr.unice.idse.services;

import fr.unice.idse.db.DataBaseUser;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;
import fr.unice.idse.model.player.Player;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("admin")
public class AdminRest extends OriginRest{

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

        // verification du token
        if(token == null){
            jsonReturn.put("error", "Token not found");
            return sendResponse(405, jsonReturn.toString(), "GET");
        }
        Player player = model.getPlayerFromList(token);
        if(player == null){
            jsonReturn.put("error", "Player not found");
            return sendResponse(405, jsonReturn.toString(), "GET");
        }

        // Vérification admin
        DataBaseUser dataBaseUser = new DataBaseUser();
        if(dataBaseUser.getRang(player.getName()) != 4){
            jsonReturn.put("error", "Player is not admin");
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
}
