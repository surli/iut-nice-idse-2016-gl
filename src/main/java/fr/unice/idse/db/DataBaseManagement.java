package fr.unice.idse.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class DataBaseManagement {
	/*
	 * need to modify private variables + import external jar library
	 * mysql-connector-java-5.1.38-bin.jar at build path (this jar is locate in
	 * this package !)
	 */
	private String url = "jdbc:mysql://localhost/uno";
	private String user = "root";
	private String pass = "root";
	private Connection con = null;
	private Statement statement = null;
	private ResultSet resultQuery = null;

	public void connect() throws SQLException {
		this.resultQuery = null;
		this.statement = null;
		this.con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.con = (Connection) DriverManager.getConnection(url, user, pass);
		this.statement = con.createStatement();
	}

	public void end() {
		try {
			this.resultQuery.close();
			this.statement.close();
			this.con.close();
		} catch (SQLException ignore) {
		}
	}

	public boolean userLoginIsCorrect(String email, String password) {
		try {
			this.resultQuery = this.statement.executeQuery("SELECT u_email, u_password FROM users WHERE u_email = \""
					+ email + "\" AND u_password = \"" + password + "\"");
			if (resultQuery.getString("u_email") == email && resultQuery.getString("u_password") == password)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String getPseudoWithEmail(String email) {
		try {
			this.resultQuery = this.statement
					.executeQuery("SELECT u_pseudo FROM users WHERE u_email = \"" + email + "\"");
			return resultQuery.getString("u_pseudo");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
