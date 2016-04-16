package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.idse.db.dao.object.PlayerObject;
import fr.unice.idse.db.dao.object.UserObject;

public class PlayerDAO extends DAO<PlayerObject> {
	private Logger logger = LoggerFactory.getLogger(PlayerDAO.class);

	public PlayerDAO(Connection conn) {
		this.conn = conn;
	}
	
	
	@Override
	public boolean create(PlayerObject obj) throws SQLException {
		try {	
					String query = "INSERT INTO players_in_game (p_g_id,p_id_user,p_position) VALUES (?,?,?)";
					PreparedStatement stmt = getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
					
					int count = 1;
					stmt.setInt(count++, obj.getIdGame());
					stmt.setInt(count++, obj.getIdUser());
					stmt.setInt(count, obj.getPosition());

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
	public boolean delete(PlayerObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(PlayerObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PlayerObject find(int id) throws SQLException {
		try {
			PlayerObject value = null;
			String query = "SELECT p_g_id, p_id_user, p_position FROM players_in_game WHERE p_id_user = ?";
			PreparedStatement stmt = this.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			if (result.first()) {
				value = new PlayerObject(
						result.getInt("p_g_id"),
						id,
						result.getInt("p_position") 
					);
			}
			stmt.close();
			return value;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e.getCause());
			throw e;
		}
	}
}
