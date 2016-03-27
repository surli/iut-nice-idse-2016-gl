package fr.unice.idse.model.save;

import fr.unice.idse.db.DataBaseOrigin;
import fr.unice.idse.model.Game;

/**
 * This class regroup the method for saving a game
 * @author damien
 */

public class BusinessQuery {
	private static DataBaseOrigin dbm = new DataBaseOrigin();;
	
	public static int newGame(Game game) {
		String query = String.format("INSERT INTO games (g_nom,g_nbr_max_joueur,g_nbr_max_ia) VALUES ('%s', %s, %s)",game.getGameName(), game.getNumberPlayers(), 0);
		return dbm.insert(query);
	}
	
	public static void addPlayerToGame(int gameId, int playerId, int position) {
		String query = String.format("INSERT INTO players_in_game (p_g_id,p_id_user,p_position) VALUES (%s, %s, %s)", gameId, playerId, position);
		dbm.exec(query);
	}
	
	public static int newMatch(int gameId) {
		String query = String.format("INSERT INTO matchs (m_g_id) VALUES ('%s')",gameId);
		return dbm.insert(query);
	}
	
	public static int newTurn(int matchId, boolean inversed, int playerId) {
		String query = String.format("INSERT INTO turns(t_m_id, t_sens, id_user_ready) VALUES (%s, %s, %s)",matchId, inversed, playerId);
		return dbm.insert(query);
	}

	public static void addCardToStack(int matchId, int turnId, int cardId) {
		String query = String.format("INSERT INTO stack (s_t_id,s_m_id,s_c_id) VALUES (%s, %s, %s)", turnId, matchId, cardId);
		dbm.exec(query);
	}
	
	public static void addCardToPlayerHand(int matchId, int userId, int cardId, int turnId) {
		String query = String.format("INSERT INTO hands_players_in_game (h_id_match,h_id_user,h_id_card,h_tour) VALUES (%s, %s, %s, %s)", matchId, userId, cardId, turnId);
		dbm.exec(query);
	}
	
}
