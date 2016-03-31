package db.dao.userDAOTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.unice.idse.db.dao.UserDAO;
import fr.unice.idse.db.dao.object.UserObject;

@RunWith(MockitoJUnitRunner.class)
public class UpdateTest {
	@Mock
	private Connection mockConnection;
	@Mock
	private PreparedStatement mockPreparedStatement;
	@Mock
	private UserObject mockUser;
	
	public UpdateTest() {
	}
	
	@Before
	public void setup() throws SQLException {
		doNothing().when(mockConnection).commit();
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
		doNothing().when(mockPreparedStatement).close();
		doNothing().when(mockPreparedStatement).setString(anyInt(), anyString());
		doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());
		doNothing().when(mockPreparedStatement).setBoolean(anyInt(), anyBoolean());
		when(mockPreparedStatement.executeUpdate()).thenReturn(0);
	}

	@Test
	public void testUpdateWithNoException() throws SQLException {
		UserDAO mockUserDao = new UserDAO(mockConnection);
		mockUserDao = spy(mockUserDao);
		
		doReturn(mockUser).when(mockUserDao).find(anyInt());
		
		mockUserDao.update(mockUser);
		
		verify(mockConnection, times(1)).prepareStatement(anyString());
		verify(mockPreparedStatement, times(3)).setString(anyInt(), anyString());
		verify(mockPreparedStatement, times(2)).setInt(anyInt(), anyInt());
		verify(mockPreparedStatement, times(1)).setBoolean(anyInt(), anyBoolean());
		verify(mockPreparedStatement, times(1)).executeUpdate();
		verify(mockConnection, times(1)).commit();
		verify(mockPreparedStatement, times(1)).close();
	}
	
	@Test(expected=SQLException.class)
	public void textUpdateWithPreparedStatementException() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenThrow(new SQLException("Mock exception"));
		try {
			UserDAO mockUserDao = new UserDAO(mockConnection);
			mockUserDao = spy(mockUserDao);
			
			doReturn(mockUser).when(mockUserDao).find(anyInt());
			assertTrue(mockUserDao.update(mockUser));
		} catch(SQLException e) {	
			verify(mockConnection, times(1)).prepareStatement(anyString());
			verify(mockPreparedStatement, times(0)).setString(anyInt(), anyString());
			verify(mockPreparedStatement, times(0)).setInt(anyInt(), anyInt());
			verify(mockPreparedStatement, times(0)).setBoolean(anyInt(), anyBoolean());
			verify(mockPreparedStatement, times(0)).executeUpdate();
			verify(mockConnection, times(0)).commit();
			verify(mockPreparedStatement, times(0)).close();
			throw e;
		}
	}
	
	@Test
	public void textUpdateWithNoExistingPseudo() throws SQLException {
		UserDAO mockUserDao = new UserDAO(mockConnection);
		mockUserDao = spy(mockUserDao);
		
		doReturn(null).when(mockUserDao).find(anyInt());
		
		assertFalse(mockUserDao.update(mockUser));
		
		verify(mockConnection, times(0)).prepareStatement(anyString());
		verify(mockPreparedStatement, times(0)).setString(anyInt(), anyString());
		verify(mockPreparedStatement, times(0)).setInt(anyInt(), anyInt());
		verify(mockPreparedStatement, times(0)).setBoolean(anyInt(), anyBoolean());
		verify(mockPreparedStatement, times(0)).executeUpdate();
		verify(mockConnection, times(0)).commit();
		verify(mockPreparedStatement, times(0)).close();
	}
}
