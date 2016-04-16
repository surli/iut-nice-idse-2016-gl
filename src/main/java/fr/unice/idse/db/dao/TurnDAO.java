package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.idse.db.dao.object.StackObject;
import fr.unice.idse.db.dao.object.TurnObject;

public class TurnDAO extends DAO<TurnObject>{
	
	private Logger logger = LoggerFactory.getLogger(UserDAO.class);

	public TurnDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean create(TurnObject obj) throws SQLException {
		try {
			
			String query = "INSERT INTO turns (t_m_id, t_sens, id_user_ready) VALUES (?, ?, ?)";
			PreparedStatement stmt = getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			
			int count = 1;
			stmt.setInt(count++, obj.getIdMatch());
			stmt.setBoolean(count++, obj.isInverded());
			stmt.setInt(count, obj.getIdUser());

			
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
	public boolean delete(TurnObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(TurnObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TurnObject find(int id) throws SQLException {
		try {
			TurnObject value = null;
			String query = "SELECT t_id, t_m_id, t_sens, id_user_ready FROM stack WHERE t_id = ?";
			PreparedStatement stmt = this.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			if (result.first()) {
				value = new TurnObject(
						id,
						result.getInt("t_m_id"),
						result.getBoolean("t_sens"),
						result.getInt("id_user_ready"));
			}
			stmt.close();
			return value;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e.getCause());
			throw e;
		}
	}
	
	public TurnObject findbyGameId(int id) throws SQLException {
		// TODO Implement
		return null;
		}
	

}
