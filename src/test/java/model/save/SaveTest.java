package model.save;

import static org.junit.Assert.*;
import org.junit.Test;
import fr.unice.idse.model.save.Save;

public class SaveTest extends Save{
	
	public SaveTest(){
		  super();
	}

	@Test
	public void testInitClass()
	{	
		assertNotNull(bq);
	}
}
