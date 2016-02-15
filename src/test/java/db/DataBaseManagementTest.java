package db;

import static org.junit.Assert.*;
import java.sql.SQLException;
import org.junit.Test;
import fr.unice.idse.db.DataBaseManagement;;

public class DataBaseManagementTest {

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
	public void createUserIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertTrue(dataBaseManagement.createUser("titi", "titi@gmail.com", "mypass"));
		dataBaseManagement.end();
	}
	
	@Test
	public void deleteUserWithPseudoIsCorrect() throws SQLException {
		dataBaseManagement.connect();
		assertTrue(dataBaseManagement.deleteUserWithPseudo("titi"));
		dataBaseManagement.end();
	}
}
