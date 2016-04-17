package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.idse.db.dao.object.MatchObject;

public class MatchDAO extends DAO<MatchObject> {
	private Logger logger = LoggerFactory.getLogger(MatchDAO.class);

	public MatchDAO(Connection conn) {
		this.conn = conn;
	}
	
	
	@Override
	public boolean create(MatchObject obj) throws SQLException {
		try {
			
			String query = "INSERT INTO matchs (m_g_id) VALUES (?)";
			PreparedStatement stmt = getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			
			int count = 1;
			stmt.setInt(count, obj.getIdGame());
			
			stmt.execute();
			getConnection().commit();
			stmt.getGeneratedKeys().next();
			obj.setId(stmt.getGeneratedKeys().getInt("GENERATED_KEY"));
			stmt.close();
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e.getSQLState());
			throw e;
		}
	}

	@Override
	public boolean delete(MatchObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(MatchObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MatchObject find(int id) throws SQLException {
		try {
			MatchObject value = null;
			String query = "SELECT m_g_id FROM matchs WHERE m_g_id = ?";
			PreparedStatement stmt = this.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			if (result.first()) {
				value = new MatchObject(
						id);
			}
			stmt.close();
			return value;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e.getCause());
			throw e;
		}
	}
	
	public MatchObject findbyGameId(int id) throws SQLException {
		//TODO Implement
			return null;
		
	}

}
