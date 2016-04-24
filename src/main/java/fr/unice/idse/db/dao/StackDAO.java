package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.idse.db.dao.object.PlayerObject;
import fr.unice.idse.db.dao.object.StackObject;

public class StackDAO extends DAO<StackObject>{
	private Logger logger = LoggerFactory.getLogger(UserDAO.class);

	public StackDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean create(StackObject obj) throws SQLException {
		try {
	
			String query = "INSERT INTO stack (s_t_id, s_m_id, s_c_id) VALUES (?, ?, ?)";
			PreparedStatement stmt = getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			
			int count = 1;
			stmt.setInt(count++, obj.getIdTurn());
			stmt.setInt(count++, obj.getIdMatch());
			stmt.setInt(count, obj.getIdCard());

			
			stmt.execute();
			getConnection().commit();
			stmt.close();
			return true;
		} catch (SQLException e) {
			logger.error(e.getMessage(), e.getSQLState());
			throw e;
		}
	}

	@Override
	public boolean delete(StackObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(StackObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.unice.idse.db.dao.DAO#find(int)
	 * int id est l'id du Stack
	 */
	@Override
	public StackObject find(int id) throws SQLException {
		try {
			StackObject value = null;
			String query = "SELECT s_t_id, s_m_id, s_c_id FROM stack WHERE s_t_id = ?";
			PreparedStatement stmt = this.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			if (result.first()) {
				value = new StackObject(
						result.getInt("s_m_id"),
						id,
						result.getInt("s_c_id"));
			}
			stmt.close();
			return value;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e.getCause());
			throw e;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see fr.unice.idse.db.dao.DAO#find(int)
	 * int id est l'id du match
	 */
	public ArrayList<StackObject> findsByMatchId(int id) throws SQLException {
		try {
			ArrayList<StackObject> value = new ArrayList<StackObject>();
			String query = "SELECT s_t_id, s_m_id, s_c_id FROM stack WHERE s_m_id = ?";
			PreparedStatement stmt = this.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			
			while(result.next()){
				value.add(new StackObject(result.getInt("s_m_id"),id,result.getInt("s_c_id")));
			}

			stmt.close();
			return value;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e.getCause());
			throw e;
		}
	}
	
}
