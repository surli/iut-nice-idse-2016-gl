package model.db.dao;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.unice.idse.db.dao.Connexion;
import fr.unice.idse.db.dao.UserDAO;
import fr.unice.idse.db.dao.object.UserObject;

@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {
	@Mock
	private Connexion mockConnexion;
	@Mock
	private Connection mockConnection;
	@Mock
	private PreparedStatement mockPreparedStatement;
	@Mock
	private ResultSet mockResultSet;
	@Mock
	private UserObject mockUser;
	private int userId = 100;
	
	public UserDAOTest() {
	}
	
	@Before
	public void setup() throws SQLException {
		when(mockConnexion.getConnection()).thenReturn(mockConnection);
		doNothing().when(mockConnection).commit();
		when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
		
		doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
		doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());
		when(mockPreparedStatement.execute()).thenReturn(Boolean.TRUE);
		when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
		
		when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
		when(mockResultSet.getInt("GENERATED_KEY")).thenReturn(userId);
	}

	@Test
	public void testCreateWithNoException() throws SQLException {
		UserDAO userDAO = new UserDAO(mockConnexion.getConnection());
		userDAO.create(new UserObject());
		
		verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
		verify(mockPreparedStatement, times(3)).setString(anyInt(), anyString());
		verify(mockPreparedStatement, times(1)).setInt(anyInt(), anyInt());
		verify(mockPreparedStatement, times(1)).execute();
		verify(mockConnection, times(1)).commit();
		verify(mockResultSet, times(1)).next();
		verify(mockResultSet, times(1)).getInt(eq("GENERATED_KEY"));
	}
	
	@Test(expected=SQLException.class)
	public void textCreateWithPreparedStatementException() throws SQLException {
		when(mockConnection.prepareStatement(anyString(), anyInt())).thenThrow(new SQLException());
		try {
			UserDAO userDAO = new UserDAO(mockConnexion.getConnection());
			userDAO.create(new UserObject());
		} catch(SQLException e) {	
			verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
			verify(mockPreparedStatement, times(0)).setString(anyInt(), anyString());
			verify(mockPreparedStatement, times(0)).setInt(anyInt(), anyInt());
			verify(mockPreparedStatement, times(0)).execute();
			verify(mockConnection, times(0)).commit();
			verify(mockResultSet, times(0)).next();
			verify(mockResultSet, times(0)).getInt(eq("GENERATED_KEY"));
			throw e;
		}
	}

}
