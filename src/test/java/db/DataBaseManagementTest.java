package db;

import static org.junit.Assert.*;
import java.sql.SQLException;
import org.junit.Test;
import fr.unice.idse.db.DataBaseManagement;;

public class DataBaseManagementTest {
	/*
	DataBaseManagement dataBaseManagement = new DataBaseManagement();

	@Test
	public void ifUserAlreadyExistPseudoEmailIsCorrect() throws SQLException {
		assertFalse(dataBaseManagement.ifUserAlreadyExistPseudoEmail("toto@gmail.com", "toto"));
	}

	@Test
	public void ifUserAlreadyExistPseudoIsCorrect() throws SQLException {
		assertFalse(dataBaseManagement.ifUserAlreadyExistPseudo("toto"));
	}

	@Test
	public void addDeleteUpdateUserIsCorrect() throws SQLException {
		assertTrue(dataBaseManagement.addUser("titi", "titi@gmail.com", "mypass", "member"));
		assertTrue(dataBaseManagement.userLoginIsCorrect("titi@gmail.com", "mypass"));
		assertTrue(dataBaseManagement.getPseudoWithEmail("titi@gmail.com").equals("titi"));
		assertTrue(dataBaseManagement.updateUserPseudo("titi@gmail.com", "mypass", "newtiti"));
		assertTrue(dataBaseManagement.updateUserEmail("titi@gmail.com", "mypass", "newtiti@gmail.com"));
		assertTrue(dataBaseManagement.updateUserPassword("newtiti@gmail.com", "mypass", "mynewpass"));
		assertTrue(dataBaseManagement.updateUserType("newtiti@gmail.com", "mynewpass", "admin"));
		assertTrue(dataBaseManagement.deleteUserWithPseudo("newtiti"));
	}

	@Test
	public void addDeleteCardIsCorrect() throws SQLException {
		assertTrue(dataBaseManagement.addCard("zero", "blue"));
		assertTrue(dataBaseManagement.deleteCard("zero", "blue"));
	}

	@Test
	public void ifgameAlreadyExistNameIsCorrect() throws SQLException {
		assertFalse(dataBaseManagement.ifGameAlreadyExistName("Game idse"));
	}

	@Test
	public void addDeleteGameIsCorrect() throws SQLException {
		assertTrue(dataBaseManagement.addGame("GameStatsIdse", 4, 0));
		assertTrue(dataBaseManagement.deleteGameWithName("GameStatsIdse"));
	}*/
}
