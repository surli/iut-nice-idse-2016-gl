package fr.unice.idse.model.save;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;

import fr.unice.idse.db.DataBaseManagement;
import fr.unice.idse.model.Board;
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
        if(arg instanceof Board) {

            this.saveNewGame((Game)o);

        }else if(o instanceof Game){

            this.saveTurn((Game)o);

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

		Integer  turnId = BusinessQuery.newTurn(matchId, false, firstPLayer);
		
		ArrayList<Card> topCard = game.getBoard().getStack().getStack();
			String valueTopCard = topCard.get(0).getValue().toString();
			String colorTopCard = topCard.get(0).getColor().toString();
			
		Integer cardId = dbm.getIdCard(valueTopCard, colorTopCard);
			
		BusinessQuery.addCardToStack(matchId, turnId, cardId);
		
		
		 for(int i=0; i<array_player.size();i++){
	
			BusinessQuery.addCardToPlayerHand(matchId,  dbm.getIdUserWithPseudo(array_player.get(i).getName()), cardId, turnId);
		 }
	 
	}
	
	private void saveTurn(Game game){
		
		/*
		 * Player id a partir du nom de l'actualPlayer
		 */
		String actualPlayer = game.getBoard().getActualPlayer().getName();
		int playerId = dbm.getIdUserWithPseudo(actualPlayer);
		
		/*
		 * Game id a partir du gameName
		 */
		String gameName = game.getGameName();
		int gameId = dbm.getIdgameWithName(gameName);
		
		/*
		 * MatchId a partir du GameId
		 */
		int matchId = dbm.getIdMatchWithGameId(gameId);
		
		boolean inversed = game.getBoard().getDirection();
	

		int turnId = BusinessQuery.newTurn(matchId, inversed, playerId);
	
		ArrayList<Card> topCard = game.getBoard().getStack().getStack();
			String valueTopCard = topCard.get(0).getValue().toString();
			String colorTopCard = topCard.get(0).getColor().toString();
		
		Integer cardId = dbm.getIdCard(valueTopCard, colorTopCard);
	
		BusinessQuery.addCardToStack(matchId, turnId, cardId);
		
		
		List<Player>array_player=game.getPlayers();

		 for(int i=0; i<array_player.size();i++){
				
				BusinessQuery.addCardToPlayerHand(matchId,  dbm.getIdUserWithPseudo(array_player.get(i).getName()), cardId, turnId);
			 }
	}

	
}
