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
		ArrayList listPlayers= new ArrayList<>();
		ArrayList listHandplayers = new ArrayList<>();
		ArrayList listStack = new ArrayList<>();

	}
	
	
	public Game Load(String gameName){
		
		return null;
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
