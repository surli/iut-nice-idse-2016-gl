package fr.unice.idse.services;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.Model;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/game")
public class GameRest {


    @GET
    @Path("{gamename}/gamestate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isStarted(@PathParam("gamename") String gamename, String objJSON){
        Model model = Model.getInstance();
        Game game = model.findGameByName(gamename);

        if(game == null)
            return Response.status(500).entity("Partie inconnu").build();

        return Response.status(200).entity("{state: "+game.getBoard().gameBegin()+"}").build();

    }
}
