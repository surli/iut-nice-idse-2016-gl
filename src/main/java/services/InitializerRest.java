package java.services;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import org.codehaus.jettison.json.*;

import java.constante.Config;
import java.model.Model;
import java.constante.*;


/**
 * Classe qui concerne tout ce qui est du à l'initialisation
 * - Creation de partie
 */
@Path("/initializer")
public class InitializerRest {

    /**
     * Méthode en POST permettant la création de partie.
     * Signature : {_token: token, game: String}
     * Le nom de la game doit être suppérieur à 3 caractères;
     * Vérifie si la partie existe ou non. Renvoie {message: boolean}
     * @return Response
     */
    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGame(String objJSON) throws JSONException {
        // Cration de toutes les objets
        Model model = Model.getInstance();
        JSONObject json = new JSONObject(objJSON);

        // verification du token
        if(!json.has("_token"))
            return Response.status(500).entity("Invalide token").build();
        if(!Config._token.equals(json.getString("_token")))
            return Response.status(500).entity("Invalide token").build();

        // verification du champ game
        if(!json.has("game"))
            return Response.status(500).entity("Invalide parameter").build();
        String game = json.getString("game");
        if(game.length() < 3)
            return Response.status(500).entity("Invalide parameter").build();

        // creation de la game
        if(!model.addGame(game))
            return Response.status(500).entity("{message: false}").build();

        return Response.status(200).entity("{message: true}").build();
    }

}
