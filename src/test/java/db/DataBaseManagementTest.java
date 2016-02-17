package db;

import static org.junit.Assert.*;
import java.sql.SQLException;
import org.junit.Test;
import fr.unice.idse.db.DataBaseManagement;;

public class DataBaseManagementTest {
	/*
	DataBaseManagement dataBaseManagement = new DataBaseManagement();

	@Test
	public void userLoginIsCorrectIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertTrue(dataBaseManagement.userLoginIsCorrect("test@gmail.com", "12345"));
		dataBaseManagement.end();
	}

	@Test
	public void getPseudoWithEmailIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertTrue(dataBaseManagement.getPseudoWithEmail("test@gmail.com").equals("test"));
		dataBaseManagement.end();
	}

	@Test
	public void ifUserAlreadyExistPseudoEmailIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertFalse(dataBaseManagement.ifUserAlreadyExistPseudoEmail("toto@gmail.com", "toto"));
		dataBaseManagement.end();
	}

	@Test
	public void ifUserAlreadyExistPseudoIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertFalse(dataBaseManagement.ifUserAlreadyExistPseudo("toto@gmail.com"));
		dataBaseManagement.end();
	}

	@Test
	public void addDeleteUpdateUserIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertTrue(dataBaseManagement.addUser("titi", "titi@gmail.com", "mypass"));
		assertTrue(dataBaseManagement.updateUserPseudo( "titi@gmail.com", "mypass", "newtiti"));
		assertTrue(dataBaseManagement.updateUserEmail( "titi@gmail.com", "mypass", "newtiti@gmail.com"));
		assertTrue(dataBaseManagement.updateUserPassword( "newtiti@gmail.com", "mypass", "mynewpass"));
		assertTrue(dataBaseManagement.deleteUserWithPseudo("newtiti"));
		dataBaseManagement.end();
	}

	@Test
	public void addDeleteCardIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertTrue(dataBaseManagement.addCard("zero", "blue"));
		assertTrue(dataBaseManagement.deleteCard("zero", "blue"));
		dataBaseManagement.end();
	}
	@Test
	public void ifgameAlreadyExistNameIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertFalse(dataBaseManagement.fgameAlreadyExistName("Game idse"));
		dataBaseManagement.end();
	}
	@Test
	public void addGameCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertTrue(dataBaseManagement.addGames("GameStatsIdse", "4", "0"));
		dataBaseManagement.end();
	}*/
}
