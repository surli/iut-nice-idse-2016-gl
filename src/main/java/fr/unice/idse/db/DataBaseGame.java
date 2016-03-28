package fr.unice.idse.db;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;
import fr.unice.idse.model.player.Player;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

	public ArrayList<Card> getStackWithMatchId(int matchId) {
		String query = "SELECT c_id, c_value, c_color FROM cards,stack WHERE s_m_id = ?";
		ArrayList<Card> listStackCard = new ArrayList<Card>();

		if (executeSQL(query, matchId))
			try {
				while (rs.next()) {
					listStackCard.add(new Card( Value.valueOf(rs.getString("c_value")), Color.valueOf(rs.getString("c_color"))));
				}
				return listStackCard;
			} catch (SQLException e) {
			}
		return listStackCard;
	}

	public Map<String, ArrayList<Card>> getLastHandPlayers(int matchId) {
		String query = "SELECT u_pseudo, c_value, c_color "
				+ "FROM `hands_players_in_game`, users, cards "
				+ "WHERE `h_id_user`=u_id "
				+ "AND `h_id_card`=c_id "
				+ "AND `h_tour`= (SELECT MAX(t_id) FROM turns WHERE t_m_id = ?)";

		Map<String, ArrayList<Card>> map = new HashMap<String, ArrayList<Card>>();
		if(executeSQL(query, matchId)) {
			try {
				while(rs.next()) {
					String key = rs.getString("u_pseudo");
					if(!map.containsKey(key)) {
						map.put(key, new ArrayList<Card>());
					}
					map.get(key).add(new Card( Value.valueOf(rs.getString("c_value")),Color.valueOf(rs.getString("c_color"))));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public ArrayList<Player> getIdUserAndPositionWithGameId(int gameId) {
		String query = "SELECT u_id, u_pseudo, u_statut, p_g_id, p_position FROM players_in_game, users  WHERE p_g_id = ? ORDER BY p_position";
		ArrayList<Player> listPlayersTemp = new ArrayList<Player>();

		if (executeSQL(query, gameId))
			try {
				while (rs.next()) {
					listPlayersTemp.add(new Player(rs.getString("u_pseudo"), "123azer"));
				}
				return listPlayersTemp;
			} catch (SQLException e) {

			}

		return listPlayersTemp;
	}

	public Game getGameWithName(String gameName) {
		Game game = null;
		if(ifGameAlreadyExistName(gameName)) {
			String query = "SELECT g_nom, g_nbr_max_joueur, u_pseudo "
					+ "FROM  players_in_game` , games, users "
					+ "WHERE  p_g_id = g_id "
					+ "AND p_id_user= u_id "
					+ "AND g_id = ?"
					+ "AND p_position = 0;";
			int gameId = getIdgameWithName(gameName);
			if(executeSQL(query, gameId)) {
				try {
					game = new Game(new Player(rs.getString("u_pseudo"), "dafuq"), rs.getString("g_nom"), rs.getInt("g_nbr_max_joueur"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return game;
	}
}
