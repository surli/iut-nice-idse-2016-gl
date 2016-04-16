package fr.unice.idse.db;

import java.sql.SQLException;
import java.util.ArrayList;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataBaseUser {
	private Logger logger = LoggerFactory.getLogger(DataBaseUser.class);
	private DataBaseOrigin dataBaseOrigin;

	public DataBaseUser(){
		dataBaseOrigin = DataBaseOrigin.getInstance();
	}
	public DataBaseUser(String connector){
		dataBaseOrigin = DataBaseOrigin.getInstance(connector);
	}

	public int getIdUserWithPseudo(String pseudo) {
		String query = "SELECT u_id FROM users WHERE u_pseudo = ?";
		if (dataBaseOrigin.executeSQL(query, pseudo))
			try {
				return dataBaseOrigin.rs.getInt(1);
			} catch (SQLException e) {
			}
		return 0;
	}
	
	public String getPseudoWithEmail(String email) {
		String query = "SELECT u_pseudo FROM users WHERE u_email = ?";
		if (dataBaseOrigin.executeSQL(query, email))
			try {
				return dataBaseOrigin.rs.getString("u_pseudo");
			} catch (SQLException e) {
			}
		return null;
	}

	/**
	 * Vérification de login et renvoie les informations nécessaires s'il existe
	 * @param email String
	 * @param password	String
     * @return JSONObject
     */
	public JSONObject verifLogin(String email, String password) {
		JSONObject jsonObject = new JSONObject();
		String query = "SELECT u_pseudo, u_statut, u_banned FROM users WHERE u_email = ? AND u_password = ?";
		try {
			if (dataBaseOrigin.executeSQL(query, email, password)) {
				jsonObject.put("pseudo", dataBaseOrigin.rs.getString(1));
				jsonObject.put("rang", dataBaseOrigin.rs.getInt(2));
				jsonObject.put("banned", dataBaseOrigin.rs.getBoolean(3));
			}
		} catch (JSONException e) {
			logger.error(e.getMessage(), e.getCause());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e.getCause());
		}
		return jsonObject;
	}

	public JSONObject allUser(){
		JSONObject jsonObject = new JSONObject();
		ArrayList<JSONObject> players = new ArrayList<>();
		String query = "SELECT u_id, u_email, u_pseudo, u_statut, u_banned FROM users";
		try{
			if(dataBaseOrigin.executeSQL(query)){
				do{
					JSONObject tmp = new JSONObject();
					tmp.put("id", dataBaseOrigin.rs.getInt(1));
					tmp.put("email", dataBaseOrigin.rs.getString(2));
					tmp.put("pseudo", dataBaseOrigin.rs.getString(3));
					tmp.put("role", dataBaseOrigin.rs.getInt(4));
					tmp.put("banned", dataBaseOrigin.rs.getBoolean(5));
					players.add(tmp);
				}while (dataBaseOrigin.rs.next());

				jsonObject.put("users", players);
			}
		} catch (JSONException e) {
			logger.error(e.getMessage(), e.getCause());
		} catch (SQLException e) {
			logger.error(e.getMessage(), e.getCause());
		}
		return jsonObject;
	}

	public int getRang(String pseudo){
		String query = "SELECT u_statut FROM users WHERE u_pseudo = ?";
		try {
			if(dataBaseOrigin.executeSQL(query, pseudo)){
				return dataBaseOrigin.rs.getInt(1);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		return -1;
	}

	public boolean updateRang(String pseudo, int rang){
		String query = "UPDATE users SET u_statut = ? WHERE u_pseudo = ?";
		return dataBaseOrigin.executeSQL(query, rang, pseudo);
	}

	
	public boolean userLoginIsCorrect(String email, String password) {
		String query = "SELECT u_email, u_password FROM users WHERE u_email = ? AND u_password = ?";
		return (dataBaseOrigin.executeSQL(query, email, password));
	}

	public boolean ifUserAlreadyExistPseudoEmail(String pseudo, String email) {
		String query = "SELECT u_pseudo, u_email FROM users WHERE u_pseudo = ? OR u_email = ?";
		return (dataBaseOrigin.executeSQL(query, pseudo, email));
	}

	public boolean ifUserAlreadyExistPseudo(String pseudo) {
		String query = "SELECT u_pseudo FROM users WHERE u_pseudo = ?";
		return (dataBaseOrigin.executeSQL(query, pseudo));
	}

	public boolean ifUserAlreadyExistEmail(String email) {
		String query = "SELECT u_pseudo FROM users WHERE u_pseudo = ?";
		return (dataBaseOrigin.executeSQL(query, email));
	}

	// 1 bot 2 guest 3 member 4 admin
	public boolean addUser(String pseudo, String email, String password, int statut) {
		if (!ifUserAlreadyExistPseudoEmail(pseudo, email) && dataBaseOrigin.isSafeStatut(statut)) {
			String query = "INSERT INTO users (u_pseudo, u_email, u_password, u_statut) VALUES (?, ?, ?, ?)";
			if (dataBaseOrigin.executeSQL(query, pseudo, email, password, statut))
				return true;
		}
		return false;
	}

	public boolean deleteUserWithPseudo(String pseudo) {
		if (ifUserAlreadyExistPseudo(pseudo)) {
			String query = "DELETE FROM users WHERE u_pseudo = ?";
			if (dataBaseOrigin.executeSQL(query, pseudo))
				return true;
		}
		return false;
	}
	
	public boolean updateUserEmail(String oldEmail, String password, String newEmail) {
		if (userLoginIsCorrect(oldEmail, password) && !ifUserAlreadyExistEmail(newEmail)) {
			String query = "UPDATE users SET u_email = ? WHERE u_email = ?";
			if (dataBaseOrigin.executeSQL(query, newEmail, oldEmail))
				if (userLoginIsCorrect(newEmail, password))
					return true;
		}
		return false;
	}

	public boolean updateUserPseudo(String email, String password, String newPseudo) {
		if (userLoginIsCorrect(email, password) && !ifUserAlreadyExistPseudo(newPseudo)) {
			String query = "UPDATE users SET u_pseudo = ? WHERE u_email = ?";
			if (dataBaseOrigin.executeSQL(query, newPseudo, email))
				if (getPseudoWithEmail(email).equals(newPseudo))
					return true;
		}
		return false;
	}

	public boolean updateUserPassword(String email, String oldPassword, String newPassword) {
		if (userLoginIsCorrect(email, oldPassword)) {
			String query = "UPDATE users SET u_password = ? WHERE u_email = ?";
			if (dataBaseOrigin.executeSQL(query, newPassword, email))
				if (userLoginIsCorrect(email, newPassword))
					return true;
		}
		return false;
	}

	public boolean updateBan(String pseudo, int ban) {
		String query = "UPDATE users SET u_banned = ? WHERE u_pseudo = ?";
		return dataBaseOrigin.executeSQL(query, ban, pseudo);
	}

	public JSONObject getBan(String pseudo){
		JSONObject jsonObject = new JSONObject();
		String query = "SELECT u_banned FROM users WHERE u_pseudo = ?";
		try{
			if(dataBaseOrigin.executeSQL(query, pseudo))
				jsonObject.put("ban", dataBaseOrigin.rs.getBoolean(1));
		} catch (JSONException e){
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		}
		return jsonObject;
	}
}
