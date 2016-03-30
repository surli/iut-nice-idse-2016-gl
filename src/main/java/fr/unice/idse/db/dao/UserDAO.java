package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.idse.db.dao.object.UserObject;

// TODO : Speak about the password thing

public class UserDAO extends DAO<UserObject> {
	private Logger logger = LoggerFactory.getLogger(UserDAO.class);

	public UserDAO() {
		this.conn = new Connexion().getConnection();
	}
	
	public UserDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean create(UserObject obj) throws SQLException {
		try {
			String query = "INSERT INTO users (u_pseudo, u_email, u_password, u_statut) VALUES (?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			
			int count = 1;
			stmt.setString(count++, obj.getPseudo());
			stmt.setString(count++, obj.getEmail());
			stmt.setString(count++, obj.getPassword());
			stmt.setInt(count, obj.getStatus());
			
			stmt.execute();
			conn.commit();
			stmt.getGeneratedKeys().next();
			obj.setId(stmt.getGeneratedKeys().getInt("GENERATED_KEY"));
			return true;
		} catch (SQLException e) {
			logger.warn(e.getMessage(), e.getCause());
			throw e;
		}

	}

	/**
	 * Delete an user folowing is pseudos
	 */
	@Override
	public boolean delete(UserObject obj) {
		try {
			if (find(obj.getId()) == null) {
				throw new Exception("Imposible to delete a non existing data");
			}
			String query = String.format(
					"DELETE FROM users WHERE u_pseudo = %s", obj.getPseudo());
			Statement stmt = conn.createStatement();
			if (!stmt.execute(query)) {
				throw new SQLException();
			}
			conn.close();
			return true;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e.getCause());
			return false;
		}
	}

	/**
	 * Update the user data
	 */
	@Override
	public boolean update(UserObject obj) {
		try {
			if (find(obj.getId()) != null) {
				throw new Exception(
						"Impossible to update an non existing object");
			}

			String query = String
					.format("UPDATE users SET u_pseudo=%s, u_email=%s, u_password=%s, u_statut=%b, u_banned=%i WHERE u_id=%i,",
							obj.getPseudo(), obj.getEmail(), obj.getPassword(),
							obj.getStatus(), obj.isBanned(), obj.getId());
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(query);
			return true;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e.getCause());
			return false;
		}
	}

	@Override
	public UserObject find(int id) {
		UserObject value = new UserObject();
		try {
			String query = "SELECT u_pseudo, u_email, u_password, u_statut, u_banned FROM users WHERE u_id = ?";
			PreparedStatement stmt = this.conn.prepareStatement(query);
			stmt.setInt(1, id);
			ResultSet result = stmt.executeQuery();
			if (result.next())
				value = new UserObject(id, 
						result.getString("u_pseudo"),
						result.getString("u_email"),
						result.getString("u_password"),
						result.getInt("u_statut"),
						result.getBoolean("u_banned"));

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e.getCause());
		}
		return value;
	}

	public UserObject find(String pseudo) {
		UserObject value = new UserObject();
		try {
			ResultSet result = this.conn.createStatement().executeQuery(
					String.format("SELECT u_id, u_email, u_password, u_statut, u_banned  FROM users WHERE u_pseudo = %s", pseudo));
			if (result.first())
				value = new UserObject(
						result.getInt("u_id"), 
						pseudo,
						result.getString("u_email"),
						result.getString("u_password"),
						result.getInt("u_statut"),
						result.getBoolean("u_banned"));

		} catch (SQLException e) {
			logger.warn(e.getMessage(), e.getCause());
		}
		return value;
	}

}
