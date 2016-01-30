package fr.unice.idse.model;

import java.util.ArrayList;

public class Board 
{
	
	private ArrayList<Player> players;
	private Alternative variante;
	private int actualPlayer;
	private Stack stack;
	private Deck deck;
	private Color actualColor;
	
	public Board()
	{
		this.players = new ArrayList<Player>();
		variante = new Alternative();
		deck = new Deck();
		stack = new Stack();
		actualPlayer = 0;
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
	 * Change la couleur actuel du jeu
	 * @param color
	 */
	//Je suis pas trop daccord sur le fait de changer la couleur de la carte 
	//car celà impliquerait qu'il y ai une carte d'une couleur final en plus 
	//et une carte de couleur final en moins si vous voyez ce que je veu dire
	public void changeColor(Color color)
	{
		actualColor = color;
	}
	
	/**
	 * Initialise le plateau de jeu
	 */
	public void init()
	{
		deck.fillDeck();
		deck.shuffle();
		for(int i = 0; i<players.size(); i++)
		{
			for(int j = 0; j < 7; j++)
			{
				players.get(i).getCards().add(deck.topCard());
				deck.removeTopCard();
			}
		}
		stack.initStack(deck);
		actualColor = stack.topCard().getColor();
	}
}
