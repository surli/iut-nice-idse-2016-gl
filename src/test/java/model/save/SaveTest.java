package model.save;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.model.save.Save;

public class SaveTest extends Save{
	/*
	@Test
	public void testSaveNewGame() throws SQLException {
		Game game = new Game(new Player("test", "token1"), "test", 3);
		game.addPlayer(new Player("test1", "token2"));
		game.addPlayer(new Player("test2", "token3"));
		game.init();
		
		saveNewGame(game);
	}
	*/
	
	/*
	 INSERT INTO `cards` (`c_id`, `c_color`, `c_value`) VALUES
		(1, 0, 1),
		(2, 0, 2),
		(3, 0, 3),
		(4, 0, 4),
		(5, 0, 5),
		(6, 0, 6),
		(7, 0, 7),
		(8, 0, 8),
		(9, 0, 9),
		(10, 0, 10),
		(11, 0, 11),
		(12, 0, 12),
		(13, 1, 1),
		(14, 1, 2),
		(15, 1, 3),
		(16, 1, 4),
		(17, 1, 5),
		(18, 1, 6),
		(19, 1, 7),
		(20, 1, 8),
		(21, 1, 9),
		(22, 1, 10),
		(23, 1, 11),
		(24, 1, 12),
		(25, 1, 1),
		(26, 2, 2),
		(27, 2, 3),
		(28, 2, 4),
		(29, 2, 5),
		(30, 2, 6),
		(31, 2, 7),
		(32, 2, 8),
		(33, 2, 9),
		(34, 2, 10),
		(35, 2, 11),
		(36, 2, 12),
		(37, 3, 1),
		(38, 3, 2),
		(39, 3, 3),
		(40, 3, 4),
		(41, 3, 5),
		(42, 3, 6),
		(43, 3, 7),
		(44, 3, 8),
		(45, 3, 9),
		(46, 3, 10),
		(47, 3, 11),
		(48, 3, 12),
		(58, 4, 14),
		(59, 4, 13),
		(61, 0, 0),
		(62, 1, 0),
		(63, 2, 0),
		(64, 3, 0);
	 */
}
