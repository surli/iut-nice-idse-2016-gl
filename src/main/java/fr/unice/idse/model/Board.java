package fr.unice.idse.model;

import java.util.ArrayList;

public class Board 
{
	
	private ArrayList<Player> players;
	private Alternative variante;
	private int actualPlayer = 0;
	private Stack stack;
	private Deck deck;
	
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

	public Stack getStack() {
		return stack;
	}

	public Deck getDeck() {
		return deck;
	}
	
	/**
	 * Change la couleur de la carte supérieur du talon
	 * @param color
	 */
	public void changeColor(Color color)
	{
		getStack().changeColor(color);
	}
	
	/**
	 * Initialise le plateau
	 */
	public void init()
	{
		
	}
}
