package db;

import static org.junit.Assert.*;
import java.sql.SQLException;
import org.junit.Test;
import fr.unice.idse.db.*;

public class DataBaseManagementTest {
	/*
	DataBaseUser dataBaseUser = new DataBaseUser();
	DataBaseCard DataBaseCard = new DataBaseCard();
	DataBaseGame DataBaseGame = new DataBaseGame();
	
	@Test
	public void ifUserAlreadyExistPseudoEmailIsCorrect() throws SQLException {
		assertFalse(dataBaseUser.ifUserAlreadyExistPseudoEmail("toto@gmail.com", "toto"));
	}

	@Test
	public void ifUserAlreadyExistPseudoIsCorrect() throws SQLException {
		assertFalse(dataBaseUser.ifUserAlreadyExistPseudo("titi"));
	}

	@Test
	public void addDeleteUpdateUserIsCorrect() throws SQLException {
		assertTrue(dataBaseUser.addUser("titi", "titi@gmail.com", "mypass", 4));
		assertTrue(dataBaseUser.userLoginIsCorrect("titi@gmail.com", "mypass"));
		assertTrue(dataBaseUser.getPseudoWithEmail("titi@gmail.com").equals("titi"));
		assertTrue(dataBaseUser.updateUserPseudo("titi@gmail.com", "mypass", "newtiti"));
		assertTrue(dataBaseUser.updateUserEmail("titi@gmail.com", "mypass", "newtiti@gmail.com"));
		assertTrue(dataBaseUser.updateUserPassword("newtiti@gmail.com", "mypass", "mynewpass"));
		assertTrue(dataBaseUser.updateUserBanned("newtiti@gmail.com", "mynewpass", 1));
		assertTrue(dataBaseUser.deleteUserWithPseudo("newtiti"));
	}

	@Test
	public void addDeleteCardIsCorrect() throws SQLException {
		assertTrue(DataBaseCard.addCard("Zero", "Blue"));
		assertTrue(DataBaseCard.deleteCard("Zero", "Blue"));
	}

	@Test
	public void ifgameAlreadyExistNameIsCorrect() throws SQLException {
		assertFalse(DataBaseGame.ifGameAlreadyExistName("Game idse"));
	}

	@Test
	public void addDeleteGameIsCorrect() throws SQLException {
		assertTrue(DataBaseGame.addGame("GameStatsIdse", 4, 0));
		assertTrue(DataBaseGame.deleteGameWithName("GameStatsIdse"));
	}*/
}
