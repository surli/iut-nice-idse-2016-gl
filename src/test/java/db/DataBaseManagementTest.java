package db;

import static org.junit.Assert.*;
import java.sql.SQLException;
import org.junit.Test;
import fr.unice.idse.db.DataBaseManagement;;

public class DataBaseManagementTest {
	
	DataBaseManagement dataBaseManagement = new DataBaseManagement();

	@Test
	public void ifUserAlreadyExistPseudoEmailIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertFalse(dataBaseManagement.ifUserAlreadyExistPseudoEmail("toto@gmail.com", "toto"));
		dataBaseManagement.end();
	}

	@Test
	public void ifUserAlreadyExistPseudoIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertFalse(dataBaseManagement.ifUserAlreadyExistPseudo("toto"));
		dataBaseManagement.end();
	}

	@Test
	public void addDeleteUpdateUserIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		int autoIncrement = dataBaseManagement.getCurrentAutoIncrementValueWithTableName("users");
		assertTrue(dataBaseManagement.addUser("titi", "titi@gmail.com", "mypass"));
		assertTrue(dataBaseManagement.getIdUserWithPseudo("titi") == autoIncrement);
		assertTrue(dataBaseManagement.userLoginIsCorrect("titi@gmail.com", "mypass"));
		assertTrue(dataBaseManagement.getPseudoWithEmail("titi@gmail.com").equals("titi"));
		assertTrue(dataBaseManagement.updateUserPseudo("titi@gmail.com", "mypass", "newtiti"));
		assertTrue(dataBaseManagement.updateUserEmail("titi@gmail.com", "mypass", "newtiti@gmail.com"));
		assertTrue(dataBaseManagement.updateUserPassword("newtiti@gmail.com", "mypass", "mynewpass"));
		assertTrue(dataBaseManagement.deleteUserWithPseudo("newtiti"));
		dataBaseManagement.end();
	}

	@Test
	public void addDeleteCardIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		int autoIncrement = dataBaseManagement.getCurrentAutoIncrementValueWithTableName("cards");
		assertTrue(dataBaseManagement.addCard("zero", "blue"));
		assertTrue(dataBaseManagement.getIdCard("zero", "blue") == autoIncrement);
		assertTrue(dataBaseManagement.deleteCard("zero", "blue"));
		dataBaseManagement.end();
	}

	@Test
	public void ifgameAlreadyExistNameIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertFalse(dataBaseManagement.ifGameAlreadyExistName("Game idse"));
		dataBaseManagement.end();
	}

	@Test
	public void addDeleteGameIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertTrue(dataBaseManagement.addGame("GameStatsIdse", 4, 0));
		assertTrue(dataBaseManagement.deleteGameWithName("GameStatsIdse"));
		dataBaseManagement.end();
	}
}
