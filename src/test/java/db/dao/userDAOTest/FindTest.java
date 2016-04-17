package db.dao.userDAOTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyBoolean;
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
public class FindTest {
	@Mock
	private Connection mockConnection;
	@Mock
	private PreparedStatement mockPreparedStatement;
	@Mock
	private ResultSet mockResultSet;
	@Mock
	private UserObject mockUser;
	private int userId = 100;
	private String userPseudo = "Mock";
	
	public FindTest() {
	}
	
	@Before
	public void setup() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
		
		doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());
		doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
		when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
		
		when(mockResultSet.getInt(anyString())).thenReturn(1);
		when(mockResultSet.getString(anyString())).thenReturn("Mock");
		when(mockResultSet.getBoolean(anyString())).thenReturn(false);
		doNothing().when(mockPreparedStatement).close();
	}

	@Test
	public void testFindIdWithNoException() throws SQLException {
		UserDAO mockUserDao = new UserDAO(mockConnection);
		mockUserDao = spy(mockUserDao);
		
		when(mockResultSet.first()).thenReturn(Boolean.TRUE);
		
		assertNotNull(mockUserDao.find(userId));
		
		verify(mockConnection, times(1)).prepareStatement(anyString());
		verify(mockPreparedStatement, times(1)).setInt(anyInt(), anyInt());
		verify(mockPreparedStatement, times(1)).executeQuery();
		verify(mockResultSet, times(1)).first();
		verify(mockResultSet, times(1)).getInt(anyString());
		verify(mockResultSet, times(3)).getString(anyString());
		verify(mockResultSet, times(1)).getBoolean(anyString());
		verify(mockPreparedStatement, times(1)).close();
	}
	
	@Test(expected=SQLException.class)
	public void textFindIdWithPreparedStatementException() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Mock exception"));
		try {
			UserDAO mockUserDao = new UserDAO(mockConnection);
			mockUserDao = spy(mockUserDao);
			
			when(mockResultSet.first()).thenReturn(Boolean.TRUE);
			
			mockUserDao.find(userId);
		} catch(SQLException e) {	
			verify(mockConnection, times(1)).prepareStatement(anyString());
			verify(mockPreparedStatement, times(0)).setInt(anyInt(), anyInt());
			verify(mockPreparedStatement, times(0)).executeQuery();
			verify(mockResultSet, times(0)).first();
			verify(mockResultSet, times(0)).getInt(anyString());
			verify(mockResultSet, times(0)).getString(anyString());
			verify(mockResultSet, times(0)).getBoolean(anyString());
			verify(mockPreparedStatement, times(0)).close();
			throw e;
		}
	}
	
	@Test
	public void testFindIdReturnNullIfNoResult() throws SQLException {
		UserDAO mockUserDao = new UserDAO(mockConnection);
		mockUserDao = spy(mockUserDao);
		
		when(mockResultSet.first()).thenReturn(Boolean.FALSE);
		
		assertNull(mockUserDao.find(userId));
		
		verify(mockConnection, times(1)).prepareStatement(anyString());
		verify(mockPreparedStatement, times(1)).setInt(anyInt(), anyInt());
		verify(mockPreparedStatement, times(1)).executeQuery();
		verify(mockResultSet, times(1)).first();
		verify(mockResultSet, times(0)).getInt(anyString());
		verify(mockResultSet, times(0)).getString(anyString());
		verify(mockResultSet, times(0)).getBoolean(anyString());
		verify(mockPreparedStatement, times(1)).close();
	}


	@Test
	public void testFindPseudoWithNoException() throws SQLException {
		UserDAO mockUserDao = new UserDAO(mockConnection);
		mockUserDao = spy(mockUserDao);
		
		when(mockResultSet.first()).thenReturn(Boolean.TRUE);
		
		assertNotNull(mockUserDao.find(userPseudo));
		verify(mockConnection, times(1)).prepareStatement(anyString());
		verify(mockPreparedStatement, times(1)).setString(anyInt(), anyString());
		verify(mockPreparedStatement, times(1)).executeQuery();
		verify(mockResultSet, times(1)).first();
		verify(mockResultSet, times(2)).getInt(anyString());
		verify(mockResultSet, times(2)).getString(anyString());
		verify(mockResultSet, times(1)).getBoolean(anyString());
		verify(mockPreparedStatement, times(1)).close();
	}
	
	@Test(expected=SQLException.class)
	public void textFindPseudoWithPreparedStatementException() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Mock exception"));
		try {
			UserDAO mockUserDao = new UserDAO(mockConnection);
			mockUserDao = spy(mockUserDao);
			
			when(mockResultSet.first()).thenReturn(Boolean.TRUE);
			
			mockUserDao.find(userPseudo);
		} catch(SQLException e) {	
			verify(mockConnection, times(1)).prepareStatement(anyString());
			verify(mockPreparedStatement, times(0)).setString(anyInt(), anyString());
			verify(mockPreparedStatement, times(0)).executeQuery();
			verify(mockResultSet, times(0)).first();
			verify(mockResultSet, times(0)).getInt(anyString());
			verify(mockResultSet, times(0)).getString(anyString());
			verify(mockResultSet, times(0)).getBoolean(anyString());
			verify(mockPreparedStatement, times(0)).close();
			throw e;
		}
	}
	
	@Test
	public void testFindPseudoReturnNullIfNoResult() throws SQLException {
		UserDAO mockUserDao = new UserDAO(mockConnection);
		mockUserDao = spy(mockUserDao);
		
		when(mockResultSet.first()).thenReturn(Boolean.FALSE);
		
		assertNull(mockUserDao.find(userPseudo));
		
		verify(mockConnection, times(1)).prepareStatement(anyString());
		verify(mockPreparedStatement, times(1)).setString(anyInt(), anyString());
		verify(mockPreparedStatement, times(1)).executeQuery();
		verify(mockResultSet, times(1)).first();
		verify(mockResultSet, times(0)).getInt(anyString());
		verify(mockResultSet, times(0)).getString(anyString());
		verify(mockResultSet, times(0)).getBoolean(anyString());
		verify(mockPreparedStatement, times(1)).close();
	}
}
