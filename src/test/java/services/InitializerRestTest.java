package services;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.junit.Test;

import fr.unice.idse.constante.Config;

public class InitializerRestTest {

		/**
		 * Test du lancement de la partie en Rest
		 * On passe comme argument un token, un nom de partie, un nom de joueur
		 * On test que si tout les parametres sont bon on a un code retour de 200
		 */
	
		@Test
		public void createTest(){
	
		/**
		 * Creation dun tableau formaté JSON avec les 3 parametres	
		 */
			
		Map<String, Object>  jsonAsMap = new HashMap<>();
		jsonAsMap.put("_token", "hbj7BB7Y6B87T282B87T27N90A098");
		jsonAsMap.put("game", "Superfly");
		jsonAsMap.put("playername", "marcel");
		
	
			
		given().
		contentType(com.jayway.restassured.http.ContentType.JSON).
		body(jsonAsMap).
        when(). 
        post("http://localhost:8080/rest/initializer/creategame").
    	/**
		 * on verifie que le code de retour est bien 200/succes	
		 */
        then().statusCode(200);
		}
		

	//@Test
	//verifier authentification si besoin
	//given().auth().basic(username, password).when().get("/secured").then().statusCode(200);
	}


