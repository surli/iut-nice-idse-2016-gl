package fr.unice.idse.services;


import fr.unice.idse.model.Model;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.smartcardio.ResponseAPDU;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Path("auth")
public class AuthRest extends OriginRest{

    /**
     * Authentifie un utilisateur Guest
     * @param stringJson String
     * @return Response
     * @throws JSONException
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response authPlayerGuest(String stringJson) throws JSONException {
        Model model = Model.getInstance();
        JSONObject jsonObject = new JSONObject(stringJson);
        JSONObject jsonReturn = new JSONObject();


        if(!jsonObject.has("playername")){
            jsonReturn.put("error", "Missing parameter playername");
            return sendResponse(405, jsonReturn.toString(), "POST");
        }

        // Génération du token
        String token = generateToken(jsonObject.getString("playername"));
        if(model.createPlayer(jsonObject.getString("playername"), token)){
            jsonReturn.put("token", token);
            return sendResponse(200, jsonReturn.toString(), "POST");
        }

        jsonReturn.put("error", "Player already exist");
        return sendResponse(405, jsonReturn.toString(), "POST");
    }

    /**
     * Authentifie un utilisateur avec ses identifiants
     * @param json String
     * @return Response
     * @throws JSONException
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response authPlayer(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject jsonResult = new JSONObject();
        Model model = Model.getInstance();

        if(!jsonObject.has("email")){
            jsonResult.put("error", "Missing parameter email");
            return sendResponse(405, jsonResult.toString(), "PUT");
        }

        if(!jsonObject.has("password")){
            jsonResult.put("error", "Missing parameter password");
            return sendResponse(405, jsonResult.toString(), "PUT");
        }

        

        String token = generateToken(jsonObject.getString("email"));
        if(model.createPlayer("", token)){
            jsonResult.put("token", token);
            return sendResponse(200, jsonResult.toString(), "PUT");
        }

        jsonResult.put("error", "Player already exist");
        return sendResponse(405, jsonResult.toString(), "PUT");
    }


}
