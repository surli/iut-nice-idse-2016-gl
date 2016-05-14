package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.idse.db.dao.object.CardObject;
import fr.unice.idse.db.dao.object.PlayerObject;

public class CardDAO extends DAO<CardObject> {
	private Logger logger = LoggerFactory.getLogger(CardDAO.class);
	public CardDAO(Connection conn){
		this.conn = conn;
	}
	
	@Override
	public boolean create(CardObject obj) throws SQLException {
		try {
			
			String query = "INSERT INTO cards (c_value, c_color) VALUES (?, ?)";
			PreparedStatement stmt = getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			
			int count = 1;
			stmt.setInt(count++, obj.getColor());
			stmt.setInt(count, obj.getValue());
			
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
	public boolean delete(CardObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean update(CardObject obj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public CardObject find(int id) throws SQLException {
		try {
			CardObject value = null;
			String query = "SELECT c_id, c_value, c_color FROM cards WHERE c_id = ?";
			PreparedStatement stmt = this.getConnection().prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			if (result.first()) {
				value = new CardObject(
						id,
						result.getInt("c_value"),
						result.getInt("c_color"));
			}
			stmt.close();
			return value;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e.getCause());
			throw e;
		}		
	}
	
	public CardObject find(int color, int value) throws SQLException {
		try {
			CardObject card = null;
			String query = "SELECT c_id FROM cards WHERE c_value = ? AND c_color = ?;";
			PreparedStatement stmt = this.getConnection().prepareStatement(query);
			stmt.setInt(1, color);
			stmt.setInt(2, value);
			ResultSet result = stmt.executeQuery();
			if (result.first()) {
				card = new CardObject(
						result.getInt("c_id"),
						color,
						value);
			}
			stmt.close();
			return card;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e.getCause());
			throw e;
		}		
	}

	
}
