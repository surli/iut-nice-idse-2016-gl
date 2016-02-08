package fr.unice.idse.services;


import javax.ws.rs.core.Response;

public class OriginRest {

    public Response sendResponse(int status, String entity, String methode){
        return Response
                .status(status)
                .entity(entity)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", methode)
                .build();
    }
}
