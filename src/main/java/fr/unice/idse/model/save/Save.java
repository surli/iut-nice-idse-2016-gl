package fr.unice.idse.model.save;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;

import fr.unice.idse.db.DataBaseManagement;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.player.Player;

public class Save implements Observer {
	protected BusinessQuery bq;
	protected DataBaseManagement dbm;

	public Save() {
		bq = new BusinessQuery();
	}

	public BusinessQuery getDbm() {
		return bq;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Game) {
			this.saveNewGame((Game)o);
		}
	}
	
	/*
	 * Sauvegarde d'une nouvelle partie
	 * @param Game game
	 */
	private void saveNewGame(Game game) {
		// gl & hf
		//	BusinessQuery.newGame(new Game(new Player("Roger", "Espadon"), "Test", 3));
		Integer gameId= BusinessQuery.newGame(game);
		
		List<Player>array_player=game.getPlayers();
		
		 for(int i=0; i<array_player.size();i++){
			 BusinessQuery.addPlayerToGame(gameId, dbm.getIdUserWithPseudo(array_player.get(i).getName()) , i);
		 }

		 BusinessQuery.newMatch(gameId);
		 BusinessQuery.newTurn(matchId, inversed, playerId);
	}
	
}
