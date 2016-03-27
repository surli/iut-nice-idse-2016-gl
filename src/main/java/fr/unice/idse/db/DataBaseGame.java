package fr.unice.idse.db;

import java.sql.SQLException;

public class DataBaseGame extends DataBaseOrigin {
	
	public int getIdgameWithName(String gameName) {
		String query = "SELECT g_id FROM games WHERE g_nom = ?";
		if (executeSQL(query, gameName))
			try {
				return rs.getInt(1);
			} catch (SQLException e) {
			}
		return 0;
	}

	public int getIdMatchWithGameId(int gameId) {
		String query = "SELECT m_id FROM matchs WHERE m_g_id = ?";
		if (executeSQL(query, gameId))
			try {
				return rs.getInt(1);
			} catch (SQLException e) {
			}
		return 0;
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
