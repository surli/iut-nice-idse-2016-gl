package db.dao.userDAOTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.unice.idse.db.dao.UserDAO;
import fr.unice.idse.db.dao.object.UserObject;

@RunWith(MockitoJUnitRunner.class)
public class CreateTest {
	@Mock
	private Connection mockConnection;
	@Mock
	private PreparedStatement mockPreparedStatement;
	@Mock
	private ResultSet mockResultSet;
	@Mock
	private UserObject mockUser;
	private int userId = 100;
	
	public CreateTest() {
	}
	
	@Before
	public void setup() throws SQLException {
		doNothing().when(mockConnection).commit();
		when(mockConnection.prepareStatement(anyString(), anyInt())).thenReturn(mockPreparedStatement);
		
		doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
		doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());
		when(mockPreparedStatement.execute()).thenReturn(Boolean.TRUE);
		when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
		
		when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
		when(mockResultSet.getInt(anyString())).thenReturn(userId);
	}

	@Test
	public void testCreateWithNoException() throws SQLException {
		UserDAO mockUserDao = new UserDAO(mockConnection);
		mockUserDao = spy(mockUserDao);
		
		doReturn(null).when(mockUserDao).find(anyString());
		
		mockUserDao.create(mockUser);
		
		verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
		verify(mockPreparedStatement, times(3)).setString(anyInt(), anyString());
		verify(mockPreparedStatement, times(1)).setInt(anyInt(), anyInt());
		verify(mockPreparedStatement, times(1)).execute();
		verify(mockConnection, times(1)).commit();
		verify(mockResultSet, times(1)).next();
		verify(mockResultSet, times(1)).getInt(anyString());
	}
	
	@Test(expected=SQLException.class)
	public void textCreateWithPreparedStatementException() throws SQLException {
		when(mockConnection.prepareStatement(anyString(), anyInt())).thenThrow(new SQLException("Mock exception"));
		try {
			UserDAO mockUserDao = new UserDAO(mockConnection);
			mockUserDao = spy(mockUserDao);
			
			doReturn(null).when(mockUserDao).find(anyString());
			assertTrue(mockUserDao.create(mockUser));
		} catch(SQLException e) {	
			verify(mockConnection, times(1)).prepareStatement(anyString(), anyInt());
			verify(mockPreparedStatement, times(0)).setString(anyInt(), anyString());
			verify(mockPreparedStatement, times(0)).setInt(anyInt(), anyInt());
			verify(mockPreparedStatement, times(0)).execute();
			verify(mockConnection, times(0)).commit();
			verify(mockResultSet, times(0)).next();
			verify(mockResultSet, times(0)).getInt(anyString());
			throw e;
		}
	}
	
	@Test
	public void textCreateWithExistingPseudo() throws SQLException {
		UserDAO mockUserDao = new UserDAO(mockConnection);
		mockUserDao = spy(mockUserDao);
		
		doReturn(mockUser).when(mockUserDao).find(anyString());
		
		assertFalse(mockUserDao.create(mockUser));
		
		verify(mockConnection, times(0)).prepareStatement(anyString(), anyInt());
		verify(mockPreparedStatement, times(0)).setString(anyInt(), anyString());
		verify(mockPreparedStatement, times(0)).setInt(anyInt(), anyInt());
		verify(mockPreparedStatement, times(0)).execute();
		verify(mockConnection, times(0)).commit();
		verify(mockResultSet, times(0)).next();
		verify(mockResultSet, times(0)).getInt(anyString());
	}
}
