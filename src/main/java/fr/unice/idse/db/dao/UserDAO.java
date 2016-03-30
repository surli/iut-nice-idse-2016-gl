package fr.unice.idse.db.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.idse.db.dao.object.UserObject;

public class UserDAO extends DAO<UserObject> {
	private Logger logger = LoggerFactory.getLogger(UserDAO.class);

	public UserDAO(Connexion conn) {
		this.conn = Connexion.getInstance();
	}

	@Override
	public boolean create(UserObject obj) {
		try {
			if (find(obj.getId()) != null) {
				throw new Exception("The user already exist");
			}
			String query = String
					.format("INSERT INTO users (u_pseudo, u_email, u_password, u_statut) VALUES (%s, %s, %s, %s)",
							obj.getPseudo(), obj.getEmail(), obj.getPassword(),
							obj.getStatus());
			Statement stmt = conn.prepareStatement(query);
			stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			stmt.getGeneratedKeys().next();
			obj.setId(stmt.getGeneratedKeys().getInt(0));
			return true;
		} catch (Exception e) {
			logger.warn(e.getMessage(), e.getCause());
			return false;
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
			ResultSet result = this.conn.createStatement().executeQuery(
					String.format("SELECT * FROM users WHERE u_id = %i", id));
			if (result.first())
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
					String.format("SELECT * FROM users WHERE u_pseudo = %s", pseudo));
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
