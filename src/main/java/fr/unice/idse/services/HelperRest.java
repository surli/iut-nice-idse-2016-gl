package fr.unice.idse.services;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Path("/helper")
public class HelperRest {

    @GET
    @Path("games")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGame(){
        Model model = Model.getInstance();
        HashMap<String, String> list = new HashMap<>();
        for (Game game : model.getGames()){
            list.put(game.getName(), game.numberOfPlayers() + "/" + game.getMaxPlayer());
        }
        return Response.status(200).entity("").build();
    }
}
