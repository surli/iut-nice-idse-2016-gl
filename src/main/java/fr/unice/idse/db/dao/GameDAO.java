package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.idse.db.dao.object.GameObject;

public class GameDAO  extends DAO<GameObject>{
	private Logger logger = LoggerFactory.getLogger(UserDAO.class);

	public GameDAO(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public boolean create(GameObject obj) throws SQLException {
		try {
			if(find(obj.getNom()) != null) {
				logger.warn("This game name is already in use");
				return false;
			}
			String query = "INSERT INTO games (g_nom, g_nbr_max_joueur, g_nbr_max_ia, g_etat) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			
			int count = 1;
			stmt.setString(count++, obj.getNom());
			stmt.setInt(count++, obj.getNbMaxJoueurs());
			stmt.setInt(count++, obj.getNbMaxIa());
			stmt.setInt(count, obj.getStatus());
			
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
	public boolean delete(GameObject obj) throws SQLException {
		// TODO : Implement
		return false;
	}

	@Override
	public boolean update(GameObject obj) throws SQLException {
		// TODO : Implement
		return false;
	}

	@Override
	public GameObject find(int id) throws SQLException {
		// TODO : Implement
		return null;
	}
	

	private Object find(String nom) {
		// TODO : Implement
		return null;
	}


}
