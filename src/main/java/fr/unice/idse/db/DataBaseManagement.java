package fr.unice.idse.db;

import fr.unice.idse.constante.*;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

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
			e.printStackTrace();
		} catch (SQLException e) {
		}
	}
	
	/**
	 * Insert the data and return is key
	 * @param query An Insert query string
	 * @return int The id of the insered query
	 */
	public int insert(String query) {
		int key = -1;
		try {
			Statement statement = con.createStatement();
			statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet res = statement.getGeneratedKeys();
			res.next();
			key = res.getInt(1);
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return key;
	}
	
	/**
	 * Execute a query
	 * @param query An executable query
	 * @return boolean If the satement sucesfully run
	 */
	public boolean exec(String query) {
		boolean run = false;
		try {
			Statement statement = con.createStatement();
			run = statement.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return run;
	}

	public void finalize() {
		try {
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException e) {
		}
	}

	// For alphanumerics and emails formats only
	public boolean isSafeString(String str) {
		return (str.matches("[a-zA-Z]*") || str.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$"));
	}

	// 1 bot 2 guest 3 member 4 admin
	public boolean isSafeStatut(int statut) {
		return (statut > 0 && statut < 5);
	}

	// 0 banned off 1 bannec on
	public boolean isSafeBanned(int banned) {
		return (banned == 0 || banned == 1);
	}

	public String[] convertObjectArrayToStringArray(Object[] o) {
		String[] s = new String[o.length];
		for (int i = 0; i < o.length; i++) {
			s[i] = o[i].toString();
		}
		return s;
	}

	/*
	 * This fonction is present in the others fonctions who need to interact
	 * with de database. She allow to detect the sort of query and primitive
	 * types.
	 */
	public boolean executeSQL(Object... args) {
		String[] argsToString = convertObjectArrayToStringArray(args);
		// The first argument is always the query
		String query = argsToString[0];
		boolean select = false;
		if (query.indexOf("SELECT") != -1)
			select = true;
		try {
			ps = con.prepareStatement(query);
			/*
			 * For each argument, detect if a number or String and put it in the
			 * prepared statement
			 */
			for (int i = 1; i < argsToString.length; i++) {
				if (StringUtils.isNumeric(argsToString[i]))
					ps.setInt(i, Integer.valueOf(argsToString[i]));
				else if (isSafeString(argsToString[i]))
					ps.setString(i, argsToString[i]);
				else
					return false;
			}
			if (select) {
				// for query used a SELECT
				rs = ps.executeQuery();
				if (rs.next())
					return true;
			} else {
				// for query used INSERT, DELETE or UPDATE
				ps.executeUpdate();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public String getPseudoWithEmail(String email) {
		String query = "SELECT u_pseudo FROM users WHERE u_email = ?";
		if (executeSQL(query, email))
			try {
				return rs.getString("u_pseudo");
			} catch (SQLException e) {
			}
		return null;
	}

	public int getCurrentAutoIncrementValueWithTableName(String tableName) {
		String query = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = uno AND TABLE_NAME = ?";
		if (executeSQL(query, tableName))
			try {
				return rs.getInt(1);
			} catch (SQLException e) {
			}

		return 0;
	}

	public int getIdUserWithPseudo(String pseudo) {
		String query = "SELECT u_id FROM users WHERE u_pseudo = ?";
		if (executeSQL(query, pseudo))
			try {
				return rs.getInt(1);
			} catch (SQLException e) {
			}
		return 0;
	}

	public int countCardsWithThisValueAndThisColor(String value, String color) {
		String query = "SELECT COUNT(*) FROM cards WHERE c_value = ? AND c_color = ?";
		if (executeSQL(query, value, color))
			try {
				return rs.getInt(1);
			} catch (SQLException e) {
			}
		return 0;
	}

	public int getIdCard(String value, String color) {
		String query = "SELECT c_id FROM cards WHERE c_value = ? AND c_color = ?";
		if (executeSQL(query, value, color))
			try {
				return rs.getInt(1);
			} catch (SQLException e) {
			}
		return 0;
	}

	public boolean userLoginIsCorrect(String email, String password) {
		String query = "SELECT u_email, u_password FROM users WHERE u_email = ? AND u_password = ?";
		if (executeSQL(query, email, password))
			try {
				if (rs.getString("u_email").equals(email) && rs.getString("u_password").equals(password))
					return true;
				else
					return false;
			} catch (SQLException e) {
			}
		return false;
	}

	public boolean ifUserAlreadyExistPseudoEmail(String pseudo, String email) {
		String query = "SELECT u_pseudo, u_email FROM users WHERE u_pseudo = ? OR u_email = ?";
		return (executeSQL(query, pseudo, email));
	}

	public boolean ifUserAlreadyExistPseudo(String pseudo) {
		String query = "SELECT u_pseudo FROM users WHERE u_pseudo = ?";
		return (executeSQL(query, pseudo));
	}

	public boolean ifUserAlreadyExistEmail(String email) {
		String query = "SELECT u_pseudo FROM users WHERE u_pseudo = ?";
		return (executeSQL(query, email));
	}

	// 1 bot 2 guest 3 member 4 admin
	public boolean addUser(String pseudo, String email, String password, int statut) {
		if (!ifUserAlreadyExistPseudoEmail(pseudo, email) && isSafeStatut(statut)) {
			String query = "INSERT INTO users (u_pseudo, u_email, u_password, u_statut) VALUES (?, ?, ?, ?)";
			if (executeSQL(query, pseudo, email, password, statut))
				return true;
		}
		return false;
	}

	public boolean deleteUserWithPseudo(String pseudo) {
		if (ifUserAlreadyExistPseudo(pseudo)) {
			String query = "DELETE FROM users WHERE u_pseudo = ?";
			if (executeSQL(query, pseudo))
				return true;
		}
		return false;
	}

	/*
	 * Possible value : zero, one, two, three, four, five, six, seven, eight,
	 * nine, skip, reverse, drawtwo, drawfour, wild && Possible color :
	 * blue,green, red, yellow, black
	 */
	public boolean addCard(String value, String color) {
		if (!EnumUtils.isValidEnum(Value.class, value) || !EnumUtils.isValidEnum(Color.class, color))
			return false;
		int nbCards = countCardsWithThisValueAndThisColor(value, color);
		String query = "INSERT INTO cards (c_value, c_color) VALUES (?, ?)";
		if (executeSQL(query, value, color))
			if (countCardsWithThisValueAndThisColor(value, color) == nbCards + 1)
				return true;
		return false;
	}

	public boolean deleteCard(String value, String color) {
		int nbCards = countCardsWithThisValueAndThisColor(value, color);
		if (nbCards == 0)
			return false;
		int id = getIdCard(value, color);
		if (id == 0)
			return false;
		String query = "DELETE FROM cards WHERE c_id = ?";
		if (executeSQL(query, Integer.toString(id)))
			if (countCardsWithThisValueAndThisColor(value, color) == nbCards - 1)
				return true;
		return false;
	}

	public boolean updateUserEmail(String oldEmail, String password, String newEmail) {
		if (userLoginIsCorrect(oldEmail, password) && !ifUserAlreadyExistEmail(newEmail)) {
			String query = "UPDATE users SET u_email = ? WHERE u_email = ?";
			if (executeSQL(query, newEmail, oldEmail))
				if (userLoginIsCorrect(newEmail, password))
					return true;
		}
		return false;
	}

	public boolean updateUserPseudo(String email, String password, String newPseudo) {
		if (userLoginIsCorrect(email, password) && !ifUserAlreadyExistPseudo(newPseudo)) {
			String query = "UPDATE users SET u_pseudo = ? WHERE u_email = ?";
			if (executeSQL(query, newPseudo, email))
				if (getPseudoWithEmail(email).equals(newPseudo))
					return true;
		}
		return false;
	}

	public boolean updateUserPassword(String email, String oldPassword, String newPassword) {
		if (userLoginIsCorrect(email, oldPassword)) {
			String query = "UPDATE users SET u_password = ? WHERE u_email = ?";
			if (executeSQL(query, newPassword, email))
				if (userLoginIsCorrect(email, newPassword))
					return true;
		}
		return false;
	}

	public boolean updateUserBanned(String email, String password, int newBanned) {
		if (userLoginIsCorrect(email, password) && isSafeBanned(newBanned)) {
			String query = "UPDATE users SET u_banned = ? WHERE u_email = ?";
			if (executeSQL(query, newBanned, email))
				return true;
		}
		return false;
	}

	public boolean ifGameAlreadyExistName(String name) {
		String query = "SELECT g_nom FROM games WHERE g_nom = ?";
		return (executeSQL(query, name));
	}

	public boolean addGame(String name, int nbrMaxJoueur, int nbrMaxIA) {
		if (!ifGameAlreadyExistName(name)) {
			String query = "INSERT INTO games (g_nom,g_nbr_max_joueur,g_nbr_max_ia) VALUES (?, ?, ?)";
			if (executeSQL(query, name, Integer.toString(nbrMaxJoueur), Integer.toString(nbrMaxIA)))
				return true;
		}
		return false;
	}

	public boolean deleteGameWithName(String nameOfTheGame) {
		if (ifGameAlreadyExistName(nameOfTheGame)) {
			String query = "DELETE FROM games WHERE g_nom = ?";
			if (executeSQL(query, nameOfTheGame))
				return true;
		}
		return false;
	}
}