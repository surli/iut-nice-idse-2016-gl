package fr.unice.idse.services;


import fr.unice.idse.db.DataBaseManagement;
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
import java.util.regex.Pattern;

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
        DataBaseManagement dataBase = new DataBaseManagement();
        dataBase.connect();
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        if(!jsonObject.has("email")){
            jsonResult.put("error", "Missing parameter email");
            return sendResponse(405, jsonResult.toString(), "PUT");
        }

        if(!jsonObject.has("password")){
            jsonResult.put("error", "Missing parameter password");
            return sendResponse(405, jsonResult.toString(), "PUT");
        }

        if(!pattern.matcher(jsonObject.getString("email")).matches()){
            jsonResult.put("error", "Email invalid");
            return sendResponse(405, jsonResult.toString(), "PUT");
        }

        if(!dataBase.userLoginIsCorrect(jsonObject.getString("email"), generatePassword(jsonObject.getString("password")))){
            dataBase.end();
            jsonResult.put("error", "Email or password incorrect");
            return sendResponse(405, jsonResult.toString(), "PUT");
        }

        String token = generateToken(jsonObject.getString("email"));
        if(model.createPlayer(dataBase.getPseudoWithEmail(jsonObject.getString("email")), token)){
            dataBase.end();
            jsonResult.put("token", token);
            return sendResponse(200, jsonResult.toString(), "PUT");
        }

        jsonResult.put("error", "Player already exist");
        return sendResponse(405, jsonResult.toString(), "PUT");
    }


    /**
     * Créer un joueur dans la base de donnée
     * @param json String
     * @return Response
     * @throws JSONException
     */
    @POST
    @Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(String json) throws JSONException {
        // Initialisation des variables
        JSONObject jsonObject = new JSONObject(json);
        JSONObject jsonResult = new JSONObject();
        Model model = Model.getInstance();
        DataBaseManagement dataBase = new DataBaseManagement();
        dataBase.connect();
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        // Verification de l'existante des variables
        if(!jsonObject.has("email")){
            jsonResult.put("error", "Missing parameter email");
            return sendResponse(405, jsonResult.toString(), "POST");
        }
        if(!jsonObject.has("playerName")){
            jsonResult.put("error", "Missing parameter playerName");
            return sendResponse(405, jsonResult.toString(), "POST");
        }
        if(!jsonObject.has("password")){
            jsonResult.put("error", "Missing parameter password");
            return sendResponse(405, jsonResult.toString(), "POST");
        }

        // Vérification des variables
        if(!pattern.matcher(jsonObject.getString("email")).matches()){
            jsonResult.put("error", "Email invalid");
            return sendResponse(405, jsonResult.toString(), "POST");
        }
        if(jsonObject.getString("playerName").length() < 3) {
            jsonResult.put("error", "playerName invalid size");
            return sendResponse(405, jsonResult.toString(), "POST");
        }

        // Insertion dans la bdd
        if(!dataBase.addUser(jsonObject.getString("playerName"), jsonObject.getString("email"), generatePassword(jsonObject.getString("password")))){
            dataBase.end();
            jsonResult.put("error", "Player already exist");
            return sendResponse(405, jsonResult.toString(), "POST");
        }

        // Ajout dans le model
        String token = generateToken(jsonObject.getString("playerName"));
        if(model.createPlayer(jsonObject.getString("playerName"), token)){
            jsonResult.put("token", token);
            return sendResponse(200, jsonResult.toString(), "POST");
        }

        jsonResult.put("error", "Player already exist");
        return sendResponse(405, jsonResult.toString(), "POST");
    }

}
