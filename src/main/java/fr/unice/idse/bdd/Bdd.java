package fr.unice.idse.bdd;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

public class Bdd {
	// a modifier
	private String url = "jdbc:mysql://localhost/uno";
	private String user = "root";
	private String pass = "root";
	private Connection con = null;
	private Statement statement = null;

	public void connect() throws SQLException {
		this.con = null;
		this.statement = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con = DriverManager.getConnection(url, user, pass);
		statement = con.createStatement();
	}

	public void end() {
		try {
			/* Fermeture de la connexion */
			this.con.close();
			this.statement.close();
			System.out.println("Connection closed");
		} catch (SQLException ignore) {
			/* ignorer les erreurs a la fermeture */
		}
	}

	public boolean userLogin(String email, String password) {
		try {
			ResultSet resultQuery = this.statement.executeQuery(
					"SELECT email,password FROM users WHERE email =" + email + "AND password =" + password + ";");
			if (resultQuery.getString("email") == email && resultQuery.getString("password") == password)
				return true;				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
