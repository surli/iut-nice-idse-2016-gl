package fr.unice.idse.model.save;

import java.util.ArrayList;

import fr.unice.idse.db.DataBaseManagement;
import fr.unice.idse.model.Game;

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
		initStack(game);
		
		return game;
	}
	
	
	private void initStack(Game game){
		
	}
	
	private void initHands(Game game){
		
	}
	
	
	private void initPlayer(Game game){
		
	}
	
}
