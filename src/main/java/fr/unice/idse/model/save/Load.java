package fr.unice.idse.model.save;

import java.util.ArrayList;
import fr.unice.idse.db.DataBaseManagement;
import fr.unice.idse.model.card.Card;

public class Load {
	protected DataBaseManagement dbm;
	protected Integer gameId;
	protected Integer matchId;
	protected ArrayList listTurn;
	protected ArrayList listPlayers;
	protected ArrayList listHandplayers;
	protected ArrayList listStack;
	protected ArrayList listDeck;
	
	public Load(Integer gameId, Integer matchId) {
		this.gameId=gameId;
		this.matchId=matchId;
		ArrayList listPlayers= new ArrayList<>();
		ArrayList listTurn = new ArrayList<>();
		ArrayList listHandplayers = new ArrayList<>();
		ArrayList listStack = new ArrayList<>();
		ArrayList listDeck = new ArrayList<>();

	}
	
	
	@SuppressWarnings("unused")
	private String relancePartie(String gameName){
		
		/*
		 * nom du game => g_id
		 * g_id => p_id_user
		 * p_id_user => h_id_user
		 * 
		 * Tour id : t_id
		 * Stack id : s_t_id
		 * Sens du tour: t_sens
		 */
		int gameId = dbm.getIdgameWithName(gameName);
		
		
		ArrayList listPlayers = dbm.getIdUserAndPositionWithGameId(gameId);
		
		
		
		return "marcel";
	}
}
