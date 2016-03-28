package fr.unice.idse.model.save;

import fr.unice.idse.db.DataBaseCard;
import fr.unice.idse.db.DataBaseGame;
import fr.unice.idse.db.DataBaseUser;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.player.Player;

/**
 * This class regroup the method for saving a game
 */

public class BusinessQuery {
	private static DataBaseGame dbmG = new DataBaseGame();
	private static DataBaseUser dbmU = new DataBaseUser();
	private static DataBaseCard dbmC = new DataBaseCard();

	/**
	 * Insert a new game in the base
	 * @param game The game instance
	 * @return int : The game's Id 
	 * @author damien
	 * @throws Exception if the game name already exist in the base
	 */
	public static int newGame(Game game) throws Exception {
		if(dbmG.ifGameAlreadyExistName(game.getGameName())) {
			throw new Exception("ERROR : Game name already exist");
		}
		String query = String.format("INSERT INTO games (g_nom,g_nbr_max_joueur,g_nbr_max_ia) VALUES ('%s', %s, %s)",game.getGameName(), game.getNumberPlayers(), 0);
		return dbmG.insert(query);
	}
	
	/**
	 * Add a player in the Game 
	 * @param game The game which the player should be added 
	 * @param player The player to add in the game 
	 * @param position The slot of the player
	 * @throws Exception 
	 */
	public static void addPlayerToGame(Game game, Player player, int position) throws Exception {
		int playerId = dbmU.getIdUserWithPseudo(player.getName());
		if(playerId == 0) {
			throw new Exception("ERROR : Player does not exist in the base");
		}
		int gameId = dbmG.getIdgameWithName(game.getGameName());
		if(gameId == 0) {
			throw new Exception("ERROR : Game does not exist in the base");
		}
		String query = String.format("INSERT INTO players_in_game (p_g_id,p_id_user,p_position) VALUES (%s, %s, %s)", gameId, playerId, position);
		dbmG.exec(query);
	}
	
	/**
	 * Add a match to a game
	 * @param game The game which the match should be added
	 * @return int The match id in the base 
	 * @throws Exception 
	 */
	public static int newMatch(Game game) throws Exception {
		int gameId = dbmG.getIdgameWithName(game.getGameName());
		if(gameId == 0) {
			throw new Exception("ERROR : Game does not exist in the base");
		}
		String query = String.format("INSERT INTO matchs (m_g_id) VALUES ('%s')",gameId);
		return dbmG.insert(query);
	}
	
	/**
	 * Add a new turn to the match
	 * @param player The player playing in this turn
	 * @param matchId The id of wich match the turn should be added
	 * @param inversed true if the game is inversed
	 * @return int The turn's id
	 * @throws Exception
	 */
	public static int newTurn(Player player, int matchId, boolean inversed) throws Exception {
		int playerId = dbmU.getIdUserWithPseudo(player.getName());
		if(playerId == 0) {
			throw new Exception("ERROR : Player does not exist in the base");
		}
		String query = String.format("INSERT INTO turns(t_m_id, t_sens, id_user_ready) VALUES (%s, %s, %s)",matchId, inversed, playerId);
		return dbmG.insert(query);
	}

	/**
	 * Add a card to the stack
	 * @param card The card to add to the stack
	 * @param matchId The id of the match
	 * @param turnId The turn where the card was added to the stack
	 * @throws Exception
	 */
	public static void addCardToStack(Card card, int matchId, int turnId) throws Exception {
		int cardId = dbmC.getIdCard(card.getValue().toString(), card.getColor().toString());
		if(cardId == 0) {
			throw new Exception("ERROR : Card does not exist in the base");
		}
		
		String query = String.format("INSERT INTO stack (s_t_id,s_m_id,s_c_id) VALUES (%s, %s, %s)", turnId, matchId, cardId);
		dbmG.exec(query);
	}
	
	/**
	 * Add a card to the player hand in the base
	 * @param player The player which the card should be added 
	 * @param card The card to add at the layer's hand
	 * @param matchId The match id 
	 * @param turnId The turn id
	 * @throws Exception
	 */
	public static void addCardToPlayerHand(Player player, Card card, int matchId, int turnId) throws Exception {
		int cardId = dbmC.getIdCard(card.getValue().toString(), card.getColor().toString());
		if(cardId == 0) {
			throw new Exception("ERROR : Card does not exist in the base");
		}
		int playerId = dbmU.getIdUserWithPseudo(player.getName());
		if(playerId == 0) {
			throw new Exception("ERROR : Player does not exist in the base");
		}
		
		String query = String.format("INSERT INTO hands_players_in_game (h_id_match,h_id_user,h_id_card,h_tour) VALUES (%s, %s, %s, %s)", matchId, playerId, cardId, turnId);
		dbmG.exec(query);
	}

}
