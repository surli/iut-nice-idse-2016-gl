package fr.unice.idse.model.save;

import java.util.ArrayList;
import java.util.Map;

import fr.unice.idse.db.DataBaseManagement;
import fr.unice.idse.model.Deck;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.player.Player;

public class Load {
	protected DataBaseManagement dbm;
	
	public Load() {
		dbm = new DataBaseManagement();
	}
	
	
	public Game load(String gameName) throws Exception{
		Game game = dbm.getGameWithName(gameName);
		
		if(game == null) {
			throw new Exception("ERROR : No game exist with this name");
		}
		
		game.getBoard().getDeck().initDeck();
		
		initPlayer(game);
		initHands(game);
		initLoadStack(game);
		
		return game;
	}
	
	
	private void initLoadStack(Game game){
		String gameName = game.getName();
		
		int gameId = dbm.getIdgameWithName(gameName);
		
		int matchId = dbm.getIdMatchWithGameId(gameId);
		
		ArrayList<Card> listStack = dbm.getStackWithMatchId(matchId);
		
		game.getBoard().getStack().setStack(listStack);

		Deck deck = game.getBoard().getDeck();
		
		for(int i =0; i<= listStack.size();i++){
			deck.removeCard(listStack.get(i)); 
		}
		
	}
	
	private void initHands(Game game){
		int gameId = dbm.getIdgameWithName(game.getGameName());
		int matchId = dbm.getIdMatchWithGameId(gameId);
		
		Map<String, ArrayList<Card>> map = dbm.getLastHandPlayers(matchId);
		
		for (Player player : game.getPlayers()) {
			player.setCards(map.get(player.getName()));
		}
		
	}
	
	private void initPlayer(Game game){
		String gameName = game.getName();
		
		int gameId = dbm.getIdgameWithName(gameName);
		
		ArrayList<Player> listPlayers = dbm.getIdUserAndPositionWithGameId(gameId);
		
		game.setPlayers(listPlayers);
		
		
	}
	
}
