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
	
	public void ChangeMeaning()
	{
		meaning = !meaning;
	}
	
	public void NextPlayer()
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
	public ArrayList<Card> PlayableCards()
	{
		ArrayList<Card> playableCards = new ArrayList<Card>();
		for(Card card : getActualPlayer().getCards())
		{
			if(AskPlayableCard(card))
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
	public boolean AskPlayableCard(Card card)
	{
		return card.getValue() == stack.topCard().getValue() || card.getColor().equals(actualColor);
	}
	
	/**
	 * Retourne si le joueur peut jouer
	 * @param player
	 * @return
	 */
	public boolean AskPlayerCanPlay(Player player)
	{
		return player.equals(getActualPlayer()) && PlayableCards().size() > 0;
	}
	
	/**
	 * Joue le tour du joueur actuel en posant une carte dans la fosse.
	 * @param card
	 */
	public void PoseCard(Card card)
	{
		//Control si la carte est posable
		if(AskPlayableCard(card))
		{
			stack.addCard(card);
			actualColor = card.getColor();
			getActualPlayer().getCards().remove(card);
			NextPlayer();
		}
	}
	
	/**
	 * Le joueur actuel pioche une carte, ne fait pas passer le tour pour le cas
	 * où il subit une carte à effet spécial.
	 */
	public void Pioche()
	{
		getActualPlayer().getCards().add(deck.topCard());
		deck.removeTopCard();
	}
	
	/**
	 * Retourne si le jeu à commencé
	 * @return
	 */
	public boolean GameBegin()
	{
		return gameBegin;
	}
}
