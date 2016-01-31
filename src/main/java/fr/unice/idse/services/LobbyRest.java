package fr.unice.idse.services;
import fr.unice.idse.model.Model;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

@Path("/lobby")
public class LobbyRest {

    /**
     * Méthode permettant de lister toutes les parties existantes
     * Retour : {games : [
     *                      [name : String,
     *                       numberPlayers : String]
     *                   ]}
     * @return Response
     */
    @GET
    @Path("/games")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listGame(){
        Model model = Model.getInstance();
        String [] list = new String[model.getGames().size()];
        for (int i = 0; i < model.getGames().size(); i++){
            list[i] = "{name : '"+model.getGames().get(i).getGameName()+"', " +
                       "numberPlayers : '"+model.getGames().get(i).numberOfPlayers()+"/"+model.getGames().get(i).getMaxPlayer()+"'}";
        }
        return Response.status(200).entity("{games : "+ Arrays.toString(list)+"}").build();
    }
}
