package fr.unice.idse.model.save;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.idse.db.DataBaseGame;
import fr.unice.idse.db.dao.CardDAO;
import fr.unice.idse.db.dao.DAOFactory;
import fr.unice.idse.db.dao.UserDAO;
import fr.unice.idse.db.dao.object.GameObject;
import fr.unice.idse.db.dao.object.HandPlayerObject;
import fr.unice.idse.db.dao.object.MatchObject;
import fr.unice.idse.db.dao.object.PlayerObject;
import fr.unice.idse.db.dao.object.StackObject;
import fr.unice.idse.db.dao.object.TurnObject;
import fr.unice.idse.db.dao.object.UserObject;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.player.Player;

public class Save implements Observer {
	private Logger logger = LoggerFactory.getLogger(Save.class);
	private static Save instance;
	
	protected Save() {
	}

	public static Save getInstance() {
		if(instance == null) {
			instance = new Save();
		}
		return instance;
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
			logger.error(e.getMessage(), e.getCause());	
		}
	}

	/*
	 * Sauvegarde d'une nouvelle partie
	 * @param Game game
	 */
	private void saveNewGame(Game game) throws SQLException {
		// Add Game
		GameObject gameObject = new GameObject();
		gameObject.setNom(game.getGameName());
		gameObject.setStatus(1);
		gameObject.setNbMaxIa(0);
		gameObject.setNbMaxJoueurs(game.getNumberPlayers());		
		DAOFactory.getGameDAO().create(gameObject);

		// Add players
		List<PlayerObject> players = new ArrayList<>();
		for (int i = 0; i < game.getPlayers().size(); i++) {
			UserObject user = ((UserDAO)DAOFactory.getUserDAO()).find(game.getPlayers().get(i).getName());
			players.add(new PlayerObject(gameObject.getId(), user.getId(), i));
			DAOFactory.getPlayerDAO().create(players.get(i));
		}
		
		// Add match
		MatchObject match = new MatchObject();
		match.setIdGame(gameObject.getId());
		DAOFactory.getMatchDAO().create(match);

		// Add turn
		TurnObject turn = new TurnObject();
		turn.setIdMatch(match.getId());
		turn.setIdUser(players.get(0).getIdUser());
		turn.setInverded(game.getBoard().getDirection());
		DAOFactory.getTurnDAO().create(turn);

		// Add stack
		Card topCard = game.getBoard().getStack().getStack().get(0);
		StackObject stack = new StackObject();
		stack.setIdCard(((CardDAO)DAOFactory.getCardDAO()).find(topCard.getColor().getNumber(), topCard.getValue().getNumber()).getId());
		stack.setIdMatch(match.getId());
		stack.setIdTurn(turn.getId());
		DAOFactory.getStackDAO().create(stack);
		
		// Add hand
		for (int i = 0; i < players.size(); i++) {
			HandPlayerObject hand = new HandPlayerObject();
			hand.setIdMatch(match.getId());
			hand.setIdTurn(turn.getId());
			hand.setIdUser(players.get(i).getIdUser());
			hand.setCard(game.getPlayers().get(i).getCards());
			DAOFactory.getHandPlayerDAO().create(hand);
		}
	}

	private void saveTurn(Game game) throws Exception {
		/*
		 * Game id a partir du gameName
		 */
		String gameName = game.getGameName();
		DataBaseGame gameDAO = null;
		int gameId = gameDAO.getIdgameWithName(gameName);
		
		/*
		 * MatchId a partir du GameId
		 */
		int matchId = gameDAO.getIdMatchWithGameId(gameId);
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
