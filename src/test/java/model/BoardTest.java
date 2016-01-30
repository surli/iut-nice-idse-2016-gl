package model;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.Player;

public class BoardTest 
{

	@Test
	public void testInitBoard() 
	{
		Board board = new Board();
		board.getPlayers().add(new Player("toto"));
		board.getPlayers().add(new Player("test"));
		board.init();
		assertFalse(board.getDeck().isEmpty());
		assertTrue(board.getPlayers().get(0).getCards().size()>0);
		assertTrue(board.getPlayers().get(1).getCards().size()>0);
		assertTrue(board.getStack().getStack().size()>0);
	}

}
