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
	private boolean meaning;
	private int maxPlayer;
	private boolean gameBegin;
	
	public Board()
	{
		this.players = new ArrayList<Player>();
		variante = new Alternative();
		deck = new Deck();
		stack = new Stack();
		actualPlayer = 0;
		meaning = true;
		maxPlayer = 4;
		gameBegin = false;
	}
	
	/*public Board(ArrayList<Player> players)
	{
		this.players = players;
		this.variante = new Alternative();
	}*/	
	
	public void changeMeaning()
	{
		meaning = !meaning;
	}
	
	public void nextPlayer()
	{
		if(meaning)
		{
			actualPlayer = (actualPlayer+1)%maxPlayer;
		}
		else
		{
			actualPlayer = (actualPlayer-1)%maxPlayer;
		}
	}
	
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
	//et une carte de couleur depart en moins si vous voyez ce que je veu dire
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
		for(Player player : players)
		{
			for(int j = 0; j < 7; j++)
			{
				player.getCards().add(deck.topCard());
				deck.removeTopCard();
			}
		}
		stack.initStack(deck);
		actualColor = stack.topCard().getColor();
		gameBegin = true;
	}
	
	/**
	 * Retourne la liste des cartes jouables
	 */
	public ArrayList<Card> playableCards()
	{
		ArrayList<Card> playableCards = new ArrayList<Card>();
		for(Card card : getActualPlayer().getCards())
		{
			if(askPlayableCard(card))
			{
				playableCards.add(card);
			}
		}
		return playableCards;
	}
	
	/**
	 * Retourne si une carte est jouable
	 * @param Card
	 */
	public boolean askPlayableCard(Card card)
	{
		return card.getValue() == stack.topCard().getValue() || card.getColor().equals(actualColor);
	}
	
	/**
	 * Retourne si le joueur peut jouer
	 * @param player
	 * @return
	 */
	public boolean askPlayerCanPlay(Player player)
	{
		return player.equals(getActualPlayer()) && playableCards().size() > 0;
	}
	
	/**
	 * Joue le tour du joueur actuel en posant une carte dans la fosse.
	 * @param card
	 */
	public void poseCard(Card card)
	{
		//Control si la carte est posable
		if(askPlayableCard(card))
		{
			stack.addCard(card);
			actualColor = card.getColor();
			getActualPlayer().getCards().remove(card);
			nextPlayer();
		}
	}
	
	/**
	 * Le joueur actuel pioche une carte, ne fait pas passer le tour pour le cas
	 * où il subit une carte à effet spécial.
	 */
	public void pioche()
	{
		getActualPlayer().getCards().add(deck.topCard());
		deck.removeTopCard();
	}
	
	/**
	 * Retourne si le jeu à commencé
	 * @return
	 */
	public boolean gameBegin()
	{
		return gameBegin;
	}
}
