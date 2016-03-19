package model.save;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;

import fr.unice.idse.db.DataBaseManagement;
import fr.unice.idse.model.Board;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.model.save.Save;

public class SaveTest extends Save{
	

	@Test
	public void testInitClass()
	{
		Board board = new Board();
		
		
		
		assertFalse(board.getDeck().isEmpty());
		assertTrue(board.getPlayers().get(0).getCards().size()>0);
		assertTrue(board.getPlayers().get(1).getCards().size()>0);
		assertTrue(board.getStack().getStack().size()>0);
	}
}
