package fr.unice.idse.model.save;

import java.util.ArrayList;

import fr.unice.idse.db.DataBaseManagement;
import fr.unice.idse.model.Deck;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;

public class Load {
	protected DataBaseManagement dbm;
	protected ArrayList listPlayers;
	protected ArrayList listHandplayers;
	protected ArrayList listStack;
	
	public Load() {
		dbm = new DataBaseManagement();
		listPlayers= new ArrayList<>();
		listHandplayers = new ArrayList<>();
		listStack = new ArrayList<>();

	}
	
	
	public Game Load(String gameName) throws Exception{
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
	
	
	@SuppressWarnings("unused")
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
		
	}
	
	
	private void initPlayer(Game game){
		
	}
	
}
