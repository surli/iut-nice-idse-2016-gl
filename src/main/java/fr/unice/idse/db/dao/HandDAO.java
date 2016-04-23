package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.idse.db.dao.object.HandPlayerObject;

public class HandDAO extends DAO<HandPlayerObject>{
	private Logger logger = LoggerFactory.getLogger(GameDAO.class);
	
	public HandDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean create(HandPlayerObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(HandPlayerObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(HandPlayerObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Return null
	 */
	@Override
	public HandPlayerObject find(int id) throws SQLException {
		return null;
	}
	
	public HandPlayerObject find(int turnId, int userId) throws SQLException {
		try {
			HandPlayerObject value = null;
			String query = "SELECT h_id_match, h_id_card FROM hands_players_in_game WHERE h_id_user = ? AND h_tour = ?;";
			PreparedStatement stmt = this.getConnection().prepareStatement(
					query);
			stmt.setInt(1, turnId);
			stmt.setInt(2, userId);
			ResultSet result = stmt.executeQuery();
			if (result.next()) {
				if(value == null) {
					value = new HandPlayerObject(result.getInt("h_id_match"), turnId, userId);
				}
				value.addCard(((CardDAO)DAOFactory.getCardDAO()).find(result.getInt("h_id_card")));
			}
			stmt.close();
			return value;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e.getCause());
			throw e;
		}
	}
	
}
