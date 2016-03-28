package fr.unice.idse.model.save;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import fr.unice.idse.db.*;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.player.Player;

public class Save implements Observer {
	protected BusinessQuery bq;
	protected DataBaseUser dbu;
	protected DataBaseCard dbc;
	protected DataBaseGame dbg;

	public Save() {
		bq = new BusinessQuery();
	}

	public BusinessQuery getDbm() {
		return bq;
	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			if(!(arg instanceof SaveListEnum)) {
				throw new Exception("ERROR : Expecting a SaveListEnum");
			}
			SaveListEnum sle = (SaveListEnum)arg;
			
			switch (sle) {
				case NewGameSave:
					this.saveNewGame((Game) o);				
					break;
				case SaveTurn:
					this.saveTurn((Game) o);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Sauvegarde d'une nouvelle partie
	 * 
	 * @param Game game
	 */
	private void saveNewGame(Game game) throws Exception {
		BusinessQuery.newGame(game);

		List<Player> arrayPlayer = game.getPlayers();

		for (int i = 0; i < arrayPlayer.size(); i++) {
			BusinessQuery.addPlayerToGame(game, arrayPlayer.get(i), i);
		}

		Integer matchId = BusinessQuery.newMatch(game);
		Integer turnId = BusinessQuery.newTurn(game.getBoard()
				.getActualPlayer(), matchId, false);

		ArrayList<Card> topCard = game.getBoard().getStack().getStack();

		BusinessQuery.addCardToStack(topCard.get(0), matchId, turnId);

		for (int i = 0; i < arrayPlayer.size(); i++) {
			for (int j = 0; j < arrayPlayer.get(i).getCards().size(); j++) {
				BusinessQuery.addCardToPlayerHand(arrayPlayer.get(i),
						arrayPlayer.get(i).getCards().get(j), matchId, turnId);
			}
		}
	}

	private void saveTurn(Game game) throws Exception {
		/*
		 * Game id a partir du gameName
		 */
		String gameName = game.getGameName();
		int gameId = dbg.getIdgameWithName(gameName);
		
		/*
		 * MatchId a partir du GameId
		 */
		int matchId = dbg.getIdMatchWithGameId(gameId);
		boolean inversed = game.getBoard().getDirection();

		int turnId = BusinessQuery.newTurn(game.getBoard().getActualPlayer(),
				matchId, inversed);

		ArrayList<Card> topCard = game.getBoard().getStack().getStack();
		BusinessQuery.addCardToStack(topCard.get(0), matchId, turnId);

		List<Player> arrayPlayer = game.getPlayers();

		for (int i = 0; i < arrayPlayer.size(); i++) {
			for (int j = 0; j < arrayPlayer.get(i).getCards().size(); j++) {
				BusinessQuery.addCardToPlayerHand(arrayPlayer.get(i),
						arrayPlayer.get(i).getCards().get(j), matchId, turnId);
			}
		}
	}

}
