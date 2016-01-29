package services;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import fr.unice.idse.services.InitializerRest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class InitializerRestTest extends JerseyTest {


		@Override
		protected Application configure() {
			return new ResourceConfig(InitializerRest.class);
		}
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
			String json = "{_token: 'hbj7BB7Y6B87T282B87T27N90A098', game: 'superfly', player: 'marcel'}";
			Entity<String> jsonEntity = Entity.entity(json, MediaType.APPLICATION_JSON);

			/**
			 * on verifie que le code de retour est bien 200/succes
			 */
			Response response = target("/initializer/creategame").request().post(jsonEntity);
			assertEquals(200, response.getStatus());
		}
	}



