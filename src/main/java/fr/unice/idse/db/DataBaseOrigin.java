package fr.unice.idse.db;

import fr.unice.idse.constante.*;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;

public class DataBaseOrigin {
	protected Connection con = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;

	public DataBaseOrigin() {
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
	 * 
	 * @param query
	 *            An Insert query string
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
	 * 
	 * @param query
	 *            An executable query
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
				else
					ps.setString(i, argsToString[i]);
			}
			if (select) {
				// for query used a SELECT
				rs = ps.executeQuery();
				if (rs.next())
					return true;
				else
					return false;
			} else {
				// for query used INSERT, DELETE or UPDATE
				if (ps.executeUpdate() > 0)
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
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
}