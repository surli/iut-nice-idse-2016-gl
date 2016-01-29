package services;

import fr.unice.idse.services.HelperRest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jeremie on 29/01/2016.
 */
public class HelperTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(HelperRest.class);
    }

    @Test
    public void test(){
        Response response = target("/helper/games").request().get();
        assertEquals(200, response.getStatus());
    }
}
