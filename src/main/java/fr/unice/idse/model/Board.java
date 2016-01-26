package fr.unice.idse.model;

import java.util.ArrayList;

public class Board 
{
	
	private ArrayList<Player> players;
	private Alternative variante;
	private int actualPlayer =0;
	
	public Board()
	{
		this.players = new ArrayList<Player>();
		variante = new Alternative();
	}
	
	/*public Board(ArrayList<Player> players)
	{
		this.players = players;
		this.variante = new Alternative();
	}*/	
	
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	
	public void setPlayers(ArrayList<Player> players)
	{
		this.players = players;
	}
	
	public Player getActualPlayer()
	{
		return players.get(actualPlayer);
	}
}
