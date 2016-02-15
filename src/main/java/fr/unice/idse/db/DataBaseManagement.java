package fr.unice.idse.db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DataBaseManagement {

	private String url = "jdbc:mysql://localhost/uno";
	private String user = "root";
	private String pass = "root";
	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public void connect() throws SQLException {
		rs = null;
		ps = null;
		con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con = DriverManager.getConnection(url, user, pass);
	}

	public void end() {
		try {
			rs.close();
			ps.close();
			con.close();
		} catch (SQLException ignore) {
		}
	}

	public boolean userLoginIsCorrect(String email, String password) {
		String query = "SELECT u_email, u_password FROM users WHERE u_email = ? AND u_password = ?";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("u_email").equals(email) && rs.getString("u_password").equals(password))
					return true;
				else
					return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public String getPseudoWithEmail(String email) {
		String query = "SELECT u_pseudo FROM users WHERE u_email = ?";
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next())
				return rs.getString("u_pseudo");
			else
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean ifUserAlreadyExistPseudoEmail(String pseudo, String email) {
		String query1 = "SELECT u_pseudo FROM users WHERE u_pseudo = ?";
		String query2 = "SELECT u_email FROM users WHERE u_email = ?";
		try {
			ps = con.prepareStatement(query1);
			ps.setString(1, pseudo);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				ps = con.prepareStatement(query2);
				ps.setString(1, email);
				rs = ps.executeQuery();
				if (rs.next()) {
					return true;
				} else
					return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean ifUserAlreadyExistPseudo(String pseudo) {
		String query1 = "SELECT u_pseudo FROM users WHERE u_pseudo = ?";
		try {
			ps = con.prepareStatement(query1);
			ps.setString(1, pseudo);
			rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean createUser(String pseudo, String email, String password) {
		if (!ifUserAlreadyExistPseudoEmail(pseudo, email)) {
			String query = "INSERT INTO users (u_pseudo, u_email, u_password) VALUES (?, ?, ?)";
			try {
				ps = con.prepareStatement(query);
				ps.setString(1, pseudo);
				ps.setString(2, email);
				ps.setString(3, password);
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		else
			return false;
	}
	
	public boolean deleteUserWithPseudo(String pseudo){
		if(ifUserAlreadyExistPseudo(pseudo)){
			String query = "DELETE FROM users WHERE users.u_pseudo = ?";
			try {
				ps = con.prepareStatement(query);
				ps.setString(1, pseudo);
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		else
			return false;
	}
}
