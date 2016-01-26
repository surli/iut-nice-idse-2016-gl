package services;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.junit.Test;

import fr.unice.idse.constante.Config;

public class InitializerRestTest {

		
//		@Test
//		public void creategameTest(){
//			given().when().post("/creategame").then().statusCode(200);
//		}
	
		@Test
		public void createTest(){
	
		Map<String, Object>  jsonAsMap = new HashMap<>();
		jsonAsMap.put("_token", "hbj7BB7Y6B87T282B87T27N90A098");
		jsonAsMap.put("game", "Superfly");
		jsonAsMap.put("playername", "marcel");

			
		given().
		contentType(com.jayway.restassured.http.ContentType.JSON).
		body(jsonAsMap).
        when(). 
        post("http://localhost:8080/rest/initializer/creategame").
        then().statusCode(200);
		}
		

	//@Test
	//verifier authentification si besoin
	//given().auth().basic(username, password).when().get("/secured").then().statusCode(200);
	}


