package fr.unice.idse.services;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ws.rs.core.Response;

import fr.unice.idse.constante.Config;

public class OriginRest {

    public Response sendResponse(int status, String entity, String methode){
        return Response
                .status(status)
                .entity(entity)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "Cache-Control, Pragma, Origin, Authorization, Content-Type, X-Requested-With")
                .header("Access-Control-Allow-Methods", methode)
                .build();
    }

    public String generateToken(String pseudo){
        Date date = new Date();
        Random random = new Random();
        KeySpec spec = new PBEKeySpec((pseudo+date.toString()).toCharArray(), Config._salt.getBytes(), random.nextInt(100)+1, 128);
        return hash(spec);
    }

    public String generatePassword(String password){
        KeySpec spec = new PBEKeySpec((password).toCharArray(), Config._salt.getBytes(), 20, 128);
        return hash(spec);
    }

    private String hash(KeySpec spec){
        try {
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = f.generateSecret(spec).getEncoded();
            Base64.Encoder enc = Base64.getEncoder();
            return enc.encodeToString(hash);
        } catch( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }
}
