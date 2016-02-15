package fr.unice.idse.model;

import java.util.ArrayList;

import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;

public class Board 
{
	
	private ArrayList<Player> players;
	private Alternative variante;
	private int actualPlayer;
	private Stack stack;
	private Deck deck;
	private Color actualColor;
	private boolean meaning;
	private boolean gameBegin;
	private boolean gameEnd;
	private int cptDrawCard;
	
	public Board()
	{
		this.players = new ArrayList<Player>();
		variante = new Alternative();
		deck = new Deck();
		stack = new Stack();
		actualPlayer = 0;
		meaning = true;
		gameBegin = false;
		gameEnd = false;
		cptDrawCard = 1;
	}	
	
	/**
	 * Change le sens de la partie.
	 */
	public void changeMeaning()
	{
		meaning = !meaning;
	}
	
	/**
	 * Change le nombre de carte à piocher.
	 * @param i
	 */
	public void setCptDrawCard(int i)
	{
		cptDrawCard = i;
	}
	
	/**
	 * Retourne le nombre de carte à piocher.
	 * @return
	 */
	public int getCptDrawCard()
	{
		return cptDrawCard;
	}
	
	/**
	 * Change le joueur actuel par raport au sens de la partie.
	 */
	public void nextPlayer()
	{
		if(meaning)
		{
			actualPlayer = (actualPlayer+1)%players.size();
		}
		else
		{
			actualPlayer = (actualPlayer-1)%players.size();
			if(actualPlayer < 0)
			{
				actualPlayer += players.size();
			}
		}
	}
	
	/**
	 * Retourne la liste de joueur de la partie.
	 * @return
	 */
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	
	public void setPlayers(ArrayList<Player> players)
	{
		this.players = players;
	}
	
	/**
	 * Retourne le joueur actuel.
	 * @return
	 */
	public Player getActualPlayer()
	{
		return players.get(actualPlayer);
	}
	
	/**
	 * Retourne la fosse de la partie.
	 * @return
	 */
	public Stack getStack() {
		return stack;
	}

	/**
	 * Retourne la pioche de la partie.
	 * @return
	 */
	public Deck getDeck() {
		return deck;
	}
	
	/**
	 * Change la couleur actuel de la partie.
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
	 * Initialise le plateau du jeu.
	 */
	public void init()
	{
		deck.initDeck();
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
		return card.getValue() == stack.topCard().getValue() || card.getColor().equals(actualColor) || card.getColor().equals(Color.Black);
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
	 * Le joueur actuel pose une carte dans la fosse.
	 * Met actuellement fin à la partie si le joueur a plus de carte.
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
			if(getActualPlayer().getCards().isEmpty())
			{
				gameEnd = true;
			}
		}
	}
	
	/**
	 * Le joueur actuel pioche une carte, ne fait pas passer le tour pour le cas
	 * où il subit une carte à effet spécial.
	 */
	public void drawCard()
	{
		int cpt = cptDrawCard;
		while(cpt > 0)
		{
			if(getDeck().isEmpty())
			{
				getDeck().reassembleDeck(getStack());
			}
			getActualPlayer().getCards().add(deck.topCard());
			deck.removeTopCard();
			cpt--;
		}
		
	}
	
	/**
	 * Retourne si le jeu a commencé
	 * @return
	 */
	public boolean gameBegin()
	{
		return gameBegin;
	}
	
	/**
	 * Retourne si le jeu est fini
	 * @return
	 */
	public boolean gameEnd()
	{
		return gameEnd;
	}
}
