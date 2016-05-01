package fr.unice.idse.model.save;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.unice.idse.db.dao.CardDAO;
import fr.unice.idse.db.dao.DAOFactory;
import fr.unice.idse.db.dao.GameDAO;
import fr.unice.idse.db.dao.MatchDAO;
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
	protected void saveNewGame(Game game) throws SQLException {
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
		turn.setInverded(game.getDirection());
		DAOFactory.getTurnDAO().create(turn);

		// Add stack
		Card topCard = game.getStack().getStack().get(0);
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

	private void saveTurn(Game game) throws SQLException {
	
			GameObject gameObject = ((GameDAO)DAOFactory.getGameDAO()).find(game.getGameName());
	
			int gameId= gameObject.getId();


			/*
		     * MatchId a partir du GameId
		     */
			
			MatchObject matchObject = ((MatchDAO)DAOFactory.getMatchDAO()).findbyGameId(gameId);
			
		    int matchId = matchObject.getId();
		        
		 
			TurnObject turn = new TurnObject();
			turn.setIdMatch(matchId);
			UserObject user = ((UserDAO)DAOFactory.getUserDAO()).find(game.getActualPlayer().getName());
			turn.setIdUser(user.getId());
			turn.setInverded(game.getDirection());
			DAOFactory.getTurnDAO().create(turn);
		    
			
		 
			Card topCard = game.getStack().getStack().get(0);
		 
			StackObject stack = new StackObject();
			stack.setIdCard(((CardDAO)DAOFactory.getCardDAO()).find(topCard.getColor().getNumber(), topCard.getValue().getNumber()).getId());
			stack.setIdMatch(matchId);
			stack.setIdTurn(turn.getId());
			DAOFactory.getStackDAO().create(stack);
			 
		    

		 
		    List<Player> arrayPlayer = game.getPlayers();
		 
		    for (int i = 0; i < arrayPlayer.size(); i++) {
		 
				UserObject userPlayer = ((UserDAO)DAOFactory.getUserDAO()).find(arrayPlayer.get(i).getName());
		    	
				HandPlayerObject hand = new HandPlayerObject();
				hand.setIdMatch(matchId);
				hand.setIdTurn(turn.getId());
				hand.setIdUser(userPlayer.getId());
				hand.setCard(arrayPlayer.get(i).getCards());
				DAOFactory.getHandPlayerDAO().create(hand);
				
		   
		      }
		 
		    }
		 
		
		
	}

