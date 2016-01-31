package fr.unice.idse.services;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/game")
public class GameRest {

    @POST
    @Path("{gamename}/gamestate")
    @Produces(MediaType.APPLICATION_JSON)
    public void isStarted(@PathParam("gamename") String gamename, String objJSON){
        Model model = Model.getInstance();
        Game game = model.findGameByName(gamename);


    }
}
