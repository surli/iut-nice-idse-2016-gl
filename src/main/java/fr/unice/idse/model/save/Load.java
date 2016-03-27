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
		ArrayList listPlayers= new ArrayList<>();
		ArrayList listHandplayers = new ArrayList<>();
		ArrayList listStack = new ArrayList<>();

	}
	
	
	public Game Load(String gameName){
		
		return null;
	}
	
	
	private void initStack(Game game){
		
	}
	
	private void initHands(Game game){
		
	}
	
	
	private void initPlayer(Game game){
		
	}
	
}
