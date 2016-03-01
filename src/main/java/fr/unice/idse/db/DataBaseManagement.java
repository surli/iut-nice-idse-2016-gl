package fr.unice.idse.db;

import fr.unice.idse.constante.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DataBaseManagement {
	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public DataBaseManagement() {
		rs = null;
		ps = null;
		con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(Config.url, Config.user, Config.pass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
		}
	}

	public void finalize() {
		try {
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
		}
	}

	public boolean sqlBased(String... args) {
		String query = args[0];
		boolean select = false;
		if (query.indexOf("SELECT") != -1)
			select = true;
		try {
			ps = con.prepareStatement(query);
			for (int i = 1; i < args.length; i++) {
				if (StringUtils.isNumeric(args[i]))
					ps.setInt(i, Integer.valueOf(args[i]));
				else
					ps.setString(i, args[i]);
			}
			if (select) {
				rs = ps.executeQuery();
				if (rs.next())
					return true;
			} else {
				ps.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getPseudoWithEmail(String email) {
		try {
			String query = "SELECT u_pseudo FROM users WHERE u_email = ?";
			if (sqlBased(query, email))
				return rs.getString("u_pseudo");
		}catch (SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	public int getCurrentAutoIncrementValueWithTableName(String tableName) throws SQLException {
		String query = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = uno AND TABLE_NAME = ?";
		if (sqlBased(query, tableName))
			return rs.getInt(1);

		return 0;
	}

	public int getIdUserWithPseudo(String pseudo) throws SQLException {
		String query = "SELECT u_id FROM users WHERE u_pseudo = ?";

		if (sqlBased(query, pseudo))
			return rs.getInt(1);
		return 0;
	}

	public int countCardsWithThisValueAndThisColor(String value, String color) throws SQLException {
		String query = "SELECT COUNT(*) FROM cards WHERE c_value = ? AND c_color = ?";
		if (sqlBased(query, value, color))
			return rs.getInt(1);
		return 0;
	}

	public int getIdCard(String value, String color) throws SQLException {
		String query = "SELECT c_id FROM cards WHERE c_value = ? AND c_color = ?";
		if (sqlBased(query, value, color))
			return rs.getInt(1);

		return 0;
	}

	public boolean userLoginIsCorrect(String email, String password) {
		try {
			String query = "SELECT u_email, u_password FROM users WHERE u_email = ? AND u_password = ?";
			if (sqlBased(query, email, password))
				if (rs.getString("u_email").equals(email) && rs.getString("u_password").equals(password))
					return true;
				else
					return false;
		}catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	public boolean ifUserAlreadyExistPseudoEmail(String pseudo, String email) {
		String query = "SELECT u_pseudo, u_email FROM users WHERE u_pseudo = ? OR u_email = ?";
		if (sqlBased(query, pseudo, email))
			return true;
		return false;
	}

	public boolean ifUserAlreadyExistPseudo(String pseudo) {
		String query = "SELECT u_pseudo FROM users WHERE u_pseudo = ?";
		if (sqlBased(query, pseudo))
			return true;
		return false;
	}

	public boolean ifUserAlreadyExistEmail(String email) {
		String query = "SELECT u_pseudo FROM users WHERE u_pseudo = ?";
		if (sqlBased(query, email))
			return true;
		return false;
	}

	public boolean addUser(String pseudo, String email, String password) {
		if (!ifUserAlreadyExistPseudoEmail(pseudo, email)) {
			String query = "INSERT INTO users (u_pseudo, u_email, u_password) VALUES (?, ?, ?)";
			if (sqlBased(query, pseudo, email, password))
				return true;
		}
		return false;
	}

	public boolean deleteUserWithPseudo(String pseudo) {
		if (ifUserAlreadyExistPseudo(pseudo)) {
			String query = "DELETE FROM users WHERE u_pseudo = ?";
			if (sqlBased(query, pseudo))
				return true;
		}
		return false;
	}

	/*
	 * Possible value : zero, one, two, three, four, five, six, seven, eight,
	 * nine, skip, reverse, drawtwo, drawfour, wild && Possible color :
	 * blue,green, red, yellow, black
	 */

	public boolean addCard(String value, String color) throws SQLException {
		int nbCards = countCardsWithThisValueAndThisColor(value, color);
		String query = "INSERT INTO cards (c_value, c_color) VALUES (?, ?)";
		if (sqlBased(query, value, color))
			if (countCardsWithThisValueAndThisColor(value, color) == nbCards + 1)
				return true;
		return false;
	}

	public boolean deleteCard(String value, String color) throws SQLException {
		int nbCards = countCardsWithThisValueAndThisColor(value, color);
		if (nbCards == 0)
			return false;
		int id = getIdCard(value, color);
		if (id == 0)
			return false;
		String query = "DELETE FROM cards WHERE c_id = ?";
		if (sqlBased(query, Integer.toString(id)))
			if (countCardsWithThisValueAndThisColor(value, color) == nbCards - 1)
				return true;
		return false;
	}

	public boolean updateUserEmail(String oldEmail, String password, String newEmail) throws SQLException {
		if (userLoginIsCorrect(oldEmail, password) && !ifUserAlreadyExistEmail(newEmail)) {
			String query = "UPDATE users SET u_email = ? WHERE u_email = ?";
			if (sqlBased(query, newEmail, oldEmail))
				if (userLoginIsCorrect(newEmail, password))
					return true;
		}
		return false;
	}

	public boolean updateUserPseudo(String email, String password, String newPseudo) throws SQLException {
		if (userLoginIsCorrect(email, password) && !ifUserAlreadyExistPseudo(newPseudo)) {
			String query = "UPDATE users SET u_pseudo = ? WHERE u_email = ?";
			if (sqlBased(query, newPseudo, email))
				if (getPseudoWithEmail(email).equals(newPseudo))
					return true;
		}
		return false;
	}

	public boolean updateUserPassword(String email, String oldPassword, String newPassword) throws SQLException {
		if (userLoginIsCorrect(email, oldPassword)) {
			String query = "UPDATE users SET u_password = ? WHERE u_email = ?";
			if (sqlBased(query, newPassword, email))
				if (userLoginIsCorrect(email, newPassword))
					return true;
		}
		return false;
	}

	public boolean ifGameAlreadyExistName(String name) {
		String query = "SELECT g_nom FROM games WHERE g_nom = ?";
		if (sqlBased(query, name))
			return true;
		return false;
	}

	public boolean addGame(String name, int nbrMaxJoueur, int nbrMaxIA) {
		if (!ifGameAlreadyExistName(name)) {
			String query = "INSERT INTO games (g_nom,g_nbr_max_joueur,g_nbr_max_ia) VALUES (?, ?, ?)";
			if (sqlBased(query, name, Integer.toString(nbrMaxJoueur), Integer.toString(nbrMaxIA)))
				return true;
		}
		return false;
	}

	public boolean deleteGameWithName(String nameOfTheGame) {
		if (ifGameAlreadyExistName(nameOfTheGame)) {
			String query = "DELETE FROM games WHERE g_nom = ?";
			if (sqlBased(query, nameOfTheGame))
				return true;
		}
		return false;
	}
}
