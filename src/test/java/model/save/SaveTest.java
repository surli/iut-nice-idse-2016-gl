package model.save;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.model.save.Save;

public class SaveTest extends Save{
	
	@Test
	public void testSaveNewGame() throws SQLException {
		Game game = new Game(new Player("test", "token1"), "test", 3);
		game.addPlayer(new Player("test1", "token2"));
		game.addPlayer(new Player("test2", "token3"));
		game.init();
		
		saveNewGame(game);
	}
	
}
