package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.idse.db.dao.object.GameObject;
import fr.unice.idse.db.dao.object.PlayerObject;

public class GameDAO extends DAO<GameObject> {
	private Logger logger = LoggerFactory.getLogger(GameDAO.class);

	public GameDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean create(GameObject obj) throws SQLException {
		try {
			if (find(obj.getNom()) != null) {
				logger.warn("This game name is already in use");
				return false;
			}
			String query = "INSERT INTO games (g_nom, g_nbr_max_joueur, g_nbr_max_ia, g_etat) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = getConnection().prepareStatement(query,
					PreparedStatement.RETURN_GENERATED_KEYS);

			int count = 1;
			stmt.setString(count++, obj.getNom());
			stmt.setInt(count++, obj.getNbMaxJoueurs());
			stmt.setInt(count++, obj.getNbMaxIa());
			stmt.setInt(count, obj.getStatus());

			stmt.execute();
			getConnection().commit();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs != null && rs.next()) {
				obj.setId((int) rs.getInt("GENERATED_KEY"));
				rs.close();
			}
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
		try {
			GameObject value = null;
			String query = "SELECT g_nom, g_nbr_max_joueur, g_nbr_max_ia, g_etat FROM games WHERE g_id = ?";
			PreparedStatement stmt = this.getConnection().prepareStatement(
					query);
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			if (result.first()) {
				value = new GameObject(id, result.getString("g_nom"),
						result.getInt("g_nbr_max_joueur"),
						result.getInt("g_nbr_max_ia"), result.getInt("g_etat"));
			}
			stmt.close();
			return value;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e.getCause());
			throw e;
		}
	}

	public GameObject find(String nom) throws SQLException {
		try {
			GameObject value = null;
			String query = "SELECT g_id, g_nbr_max_joueur, g_nbr_max_ia, g_etat FROM games WHERE g_nom = ?";
			PreparedStatement stmt = this.getConnection().prepareStatement(
					query);
			stmt.setString(1, nom);
			ResultSet result = stmt.executeQuery();
			if (result.first()) {
				value = new GameObject(result.getInt("g_id"), nom,
						result.getInt("g_nbr_max_joueur"),
						result.getInt("g_nbr_max_ia"), result.getInt("g_etat"));
			}
			stmt.close();
			return value;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e.getCause());
			throw e;
		}
	}

}
