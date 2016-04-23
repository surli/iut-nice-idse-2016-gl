package fr.unice.idse.model.save;

import java.util.ArrayList;
import java.util.Map;

import fr.unice.idse.db.DataBaseCard;
import fr.unice.idse.db.DataBaseGame;
import fr.unice.idse.db.dao.CardDAO;
import fr.unice.idse.db.dao.DAOFactory;
import fr.unice.idse.db.dao.GameDAO;
import fr.unice.idse.db.dao.MatchDAO;
import fr.unice.idse.db.dao.PlayerDAO;
import fr.unice.idse.db.dao.UserDAO;
import fr.unice.idse.db.dao.object.GameObject;
import fr.unice.idse.db.dao.object.MatchObject;
import fr.unice.idse.db.dao.object.PlayerObject;
import fr.unice.idse.db.dao.object.StackObject;
import fr.unice.idse.db.dao.object.UserObject;
import fr.unice.idse.model.Deck;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.player.Player;

public class Load {


	public Load() {

	}
	
	
	public Game load(String gameName) throws Exception{
		
		GameObject gameObject = ((GameDAO)DAOFactory.getGameDAO()).find(gameName);

		if(gameObject == null) {
			throw new Exception("ERROR : No game exist with this name");
		}
		
		int nbjoueurs = gameObject.getNbMaxJoueurs();
		
		int gameId= gameObject.getId();
		
		ArrayList<PlayerObject> playerObject = ((PlayerDAO)DAOFactory.getPlayerDAO()).findsByGameId(gameId);
		
		int idUser = playerObject.get(0).getIdUser();
		
		UserObject userObject = ((UserDAO)DAOFactory.getUserDAO()).find(idUser);

		String name = userObject.getPseudo();

		Player joueur = new Player(name,"host");
		
		Game game = new Game(joueur,gameName,nbjoueurs);

		
		game.getDeck().initDeck();

		initPlayer(game);
		initHands(game);
		initLoadStack(game);
		
		return game;
	}
	
	
	private void initLoadStack(Game game){
		String gameName = game.getName();
		
		GameObject gameObject = ((GameDAO)DAOFactory.getGameDAO()).find(gameName);

		
		int gameId = gameObject.getId();
		
		MatchObject matchObject = ((MatchDAO)DAOFactory.getMatchDAO()).findbyGameId(gameId);
		
	    int matchId = matchObject.getId();
				
	    
	    StackObject stack = new StackObject();
		stack.setIdCard(((CardDAO)DAOFactory.getCardDAO()).find(topCard.getColor().getNumber(), topCard.getValue().getNumber()).getId());
		stack.setIdMatch(matchId);
		stack.setIdTurn(turn.getId());
		DAOFactory.getStackDAO().create(stack);
		 
	    
		ArrayList<Card> listStack = dbg.getStackWithMatchId(matchId);
		
		
		game.getStack().setStack(listStack);

		Deck deck = game.getDeck();
		
		for(int i =0; i<= listStack.size();i++){
			deck.removeCard(listStack.get(i)); 
		}
		
	}
	
	private void initHands(Game game){
		int gameId = dbg.getIdgameWithName(game.getGameName());
		int matchId = dbg.getIdMatchWithGameId(gameId);
		
		Map<String, ArrayList<Card>> map = dbg.getLastHandPlayers(matchId);
		
		for (Player player : game.getPlayers()) {
			player.setCards(map.get(player.getName()));
		}
		
	}
	
	private void initPlayer(Game game){
		String gameName = game.getName();
		
		int gameId = dbg.getIdgameWithName(gameName);
		
		ArrayList<Player> listPlayers = dbg.getIdUserAndPositionWithGameId(gameId);
		
		game.setPlayers(listPlayers);
		
		
	}
	
}
