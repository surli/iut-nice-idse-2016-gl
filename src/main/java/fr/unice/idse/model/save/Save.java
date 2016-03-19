package fr.unice.idse.model.save;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;

import fr.unice.idse.db.DataBaseManagement;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;
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

		Integer matchId = BusinessQuery.newMatch(gameId);

		int firstPLayer=dbm.getIdUserWithPseudo(array_player.get(0).getName());

		Integer  turnId = BusinessQuery.newTurn(matchId, false, dbm.getIdUserWithPseudo(array_player.get(0).getName()));
		
		ArrayList<Card> topCard = game.getBoard().getStack().getStack();
			String valueTopCard = topCard.get(0).getValue().toString();
			String colorTopCard = topCard.get(0).getColor().toString();
			
		Integer cardId = dbm.getIdCard(valueTopCard, colorTopCard);
			
		BusinessQuery.addCardToStack(matchId, turnId, cardId);
		
		 for(int i=0; i<array_player.size();i++){
	
			BusinessQuery.addCardToPlayerHand(matchId,  dbm.getIdUserWithPseudo(array_player.get(i).getName()), cardId, turnId);
		 }
	 
	}
	
}
