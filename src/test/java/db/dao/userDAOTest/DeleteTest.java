package db.dao.userDAOTest;

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

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.unice.idse.db.dao.UserDAO;
import fr.unice.idse.db.dao.object.UserObject;

@RunWith(MockitoJUnitRunner.class)
public class DeleteTest {
	@Mock
	private Connection mockConnection;
	@Mock
	private PreparedStatement mockPreparedStatement;
	@Mock
	private UserObject mockUser;

	public DeleteTest() {
	}

	@Before
	public void setup() throws SQLException {
		doNothing().when(mockConnection).commit();
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
		doNothing().when(mockPreparedStatement).setInt(anyInt(), anyInt());
		when(mockPreparedStatement.execute()).thenReturn(Boolean.FALSE);
		doNothing().when(mockPreparedStatement).close();
	}

	@Test
	public void testDeleteWithoutException() throws SQLException {
		UserDAO mockUserDao = new UserDAO(mockConnection);
		mockUserDao = spy(mockUserDao);

		doReturn(mockUser).when(mockUserDao).find(anyInt());

		assertTrue(mockUserDao.delete(mockUser));

		verify(mockConnection, times(1)).prepareStatement(anyString());
		verify(mockPreparedStatement, times(1)).setInt(anyInt(), anyInt());
		verify(mockPreparedStatement, times(1)).execute();
		verify(mockConnection, times(1)).commit();
		verify(mockPreparedStatement, times(1)).close();
	}

	@Test(expected = SQLException.class)
	public void testDeleteWithException() throws SQLException {
		when(mockConnection.prepareStatement(anyString())).thenThrow(
				new SQLException("Mock exception"));
		try {
			UserDAO mockUserDao = new UserDAO(mockConnection);
			mockUserDao = spy(mockUserDao);

			doReturn(mockUser).when(mockUserDao).find(anyInt());

			assertTrue(mockUserDao.delete(mockUser));
		} catch (SQLException e) {
			verify(mockConnection, times(1)).prepareStatement(anyString());
			verify(mockPreparedStatement, times(0)).setInt(anyInt(), anyInt());
			verify(mockPreparedStatement, times(0)).execute();
			verify(mockConnection, times(0)).commit();
			verify(mockPreparedStatement, times(0)).close();
			throw e;
		}
	}

	@Test
	public void textCreateWithNonExistingPseudo() throws SQLException {
		UserDAO mockUserDao = new UserDAO(mockConnection);
		mockUserDao = spy(mockUserDao);

		doReturn(null).when(mockUserDao).find(anyInt());

		assertFalse(mockUserDao.delete(mockUser));

		verify(mockConnection, times(0)).prepareStatement(anyString());
		verify(mockPreparedStatement, times(0)).setInt(anyInt(), anyInt());
		verify(mockPreparedStatement, times(0)).execute();
		verify(mockConnection, times(0)).commit();
		verify(mockPreparedStatement, times(0)).close();
	}

}
