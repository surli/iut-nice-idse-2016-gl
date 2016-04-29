package fr.unice.idse.model;

import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.model.regle.EffectCard;
import fr.unice.idse.model.save.SaveListEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Observer;


/**
 * Classe qui définit tous les comportements d'une partie de UNO(exemple : piocher, passer au joueurs suivants...).
 * Elle permet également de s'occuper de la gestion des joueurs (exemple : ajouter, supprimer rechercher des joueurs dans la partie...). 
 * C'est la classe centrale du projet, elle connaît tous les éléments d'une partie.
 */
public class Game extends Observable implements Observer {
	
	private Player host;
	private String gameName;
	private int numberPlayers;
	private ArrayList<Player> players;
	private Alternative alternative;
	private int actualPlayer;
	private Stack stack;
	private Deck deck;
	private Color actualColor;
	private boolean orientation;
	private boolean gameBegin;
	private boolean gameEnd;
	private int cptDrawCard;
	private int nextPlayer;
	
	public Game(Player host,String gameName, int numberOfPlayers)
	{
			this.host = host;
			this.gameName = gameName;
			this.numberPlayers = numberOfPlayers;
			this.players = new ArrayList<Player>();
			this.alternative = new Alternative(this);
			this.deck = new Deck();
			this.stack = new Stack();
			this.actualPlayer = 0;
			this.orientation = true;
			this.gameBegin = false;
			this.gameEnd = false;
			this.cptDrawCard = 1;
	}
	
	public Game(Player host,String gameName, int numberOfPlayers, ArrayList<EffectCard> regles)
	{
			this.host = host;
			this.gameName = gameName;
			this.numberPlayers = numberOfPlayers;
			this.players = new ArrayList<Player>();
			this.alternative = new Alternative(this, regles);
			this.deck = new Deck();
			this.stack = new Stack();
			this.actualPlayer = 0;
			this.orientation = true;
			this.gameBegin = false;
			this.gameEnd = false;
			this.cptDrawCard = 1;
	}

	public Player getHost() { return host; }
	public void setHost(Player host) { this.host = host; }
	
	public String getGameName() { return gameName; }
	public void setGameName(String gameName) { this.gameName = gameName; }
	
	public String getName() { return gameName; }
	public void setName(String gameName) { this.gameName = gameName; }
	
	public ArrayList<Player> getPlayers() {	return this.players;}
	public void setPlayers(ArrayList<Player> players) {	this.players=players; }
	
	public Alternative getAlternative() {return this.alternative;};
	public void setAlternative(Alternative alternative) { this.alternative=alternative;}
	
	public int getNumberPlayers() { return numberPlayers; }
	public void setNumberPlayers(int numberPlayers) { this.numberPlayers = numberPlayers; }
	
	public void setActualColor(Color actualColor){ this.actualColor = actualColor; }
	public Color getActualColor(){return this.actualColor; }

	public void setGameBegin(Boolean value){ this.gameBegin = value; }
	
	/**
	 * Ajoute joueur à la partie
	 * @param player
	 * @return true/false
	 */
	public boolean addPlayer(Player player)
	{
		if(getPlayers().size() == numberPlayers)
		{
			return false;
		}
		if(!containsPlayerByToken(player.getToken()))
		{
			getPlayers().add(player);
			return true;
		}
		return false;
	}
	
	/**
	 * Verifie si le joueur indiqué est présent dans la partie
	 * @param playerName
	 * @return true/false
	 */
	public boolean containsPlayerByName(String playerName)
	{
		for(Player player : getPlayers())
		{
			if(player.getName().equals(playerName))
				return true;
		}
		return false;
	}
	
	/**
	 * Cherche joueur en fonction de son nom
	 * @param playerName
	 * @return Player
	 */
	public Player findPlayerByName(String playerName)
	{
		for(Player player : getPlayers())
		{
			if(player.getName().equals(playerName))
				return player;
		}
		return null;
	}
	
	/**
	 * Supprime joueur de la partie selon son nom
	 * @param playerName
	 * @return true/false
	 */
	public boolean removePlayerByName(String playerName)
	{
		if(containsPlayerByName(playerName))
		{
			getPlayers().remove(findPlayerByName(playerName));
			return true;
		}
		return false;
	}
	
	/**
	 * Verifie si le joueur indiqué est présent dans la partie
	 * @param playerToken
	 * @return true/false
	 */
	public boolean containsPlayerByToken(String playerToken)
	{
		for(Player player : getPlayers())
		{
			if(player.getToken().equals(playerToken))
				return true;
		}
		return false;
	}
	
	/**
	 * Cherche joueur en fonction de son token
	 * @param playerToken
	 * @return Player
	 */
	public Player findPlayerByToken(String playerToken)
	{
		for(Player player : getPlayers())
		{
			if(player.getToken().equals(playerToken))
				return player;
		}
		return null;
	}
	
	/**
	 * Supprime joueur de la partie selon son token
	 * @param playerToken
	 * @return true/false
	 */
	public boolean removePlayerByToken(String playerToken)
	{
		if(containsPlayerByToken(playerToken))
		{
			getPlayers().remove(findPlayerByName(playerToken));
			return true;
		}
		return false;
	}
	
	/**
	 * Retourne le nombre de places restantes
	 * @return int
	 */
	public int remainingSlot()
	{
		return numberPlayers-getPlayers().size();
	}
	
	/**
	 * Retourne le nombre de joueurs présents dans la partie
	 * @return int
	 */
	public int numberOfPlayers()
	{
		return getPlayers().size();
	}
	
	/**
	 * Méthode qui initialise une partie afin de qu'elle devienne jouable
	 * @return
	 */
	public boolean start()
	{
		if(numberOfPlayers()==numberPlayers)
		{
			init();
			
			setChanged();
			notifyObservers(SaveListEnum.NewGameSave);
			
			return true;
		}
		return false;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		setChanged();
		notifyObservers(SaveListEnum.SaveTurn);
	}
	

	/**
	 * Retourne le joueur suivant
	 */
	public int getNextPlayer () {
		if(orientation)
		{
			nextPlayer = (actualPlayer+1)%players.size();
		}
		else
		{
			nextPlayer = (actualPlayer-1)%players.size();
			if(nextPlayer < 0)
			{
				nextPlayer += players.size();
			}
		}
		return nextPlayer;
	}
	
	/**
	 * Change le sens de la partie.
	 */
	public void changeOrientation()
	{
		orientation = !orientation;
	}
	
	
	public boolean getOrientation() {
		return orientation;
	}

	public void setMeaning(boolean orientation) {
		this.orientation = orientation;
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
	 * Change le joueur actuel par raport au sens de la partie.
	 */
	public void nextPlayer()
	{
		if(orientation)
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
		setChanged();
		notifyObservers();
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
		for(Player player : players)
			player.setCards(new ArrayList<Card>());
		deck.initDeck();
		for(int j = 0; j < 7; j++)
		{
			for(Player player : players)
			{
				player.getCards().add(deck.topCard());
				deck.removeTopCard();
			}
		}
		for(Player player : players)
		{
			player.sortCards();
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
	 * @param card
	 */
	public boolean askPlayableCard(Card card)
	{
		if(cptDrawCard > 1)
		{
			return card.getValue() == stack.topCard().getValue();
		}
		else
		{
			return card.getValue() == stack.topCard().getValue() || card.getColor().equals(actualColor) || card.getColor().equals(Color.Black);
		}
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
			getActualPlayer().sortCards();
			deck.removeTopCard();
			cpt--;
		}
		cptDrawCard = 1;
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
	
	/**
	 * Tourne les jeux dans le sens du jeu
	 * Exemple :
	 * Il y a 3 joueurs dans la partie; le jeu est dans le sens horaire.
	 * Lors de l'appel de cette méthode le joueur 1 donne son jeu au joueur 2,
	 * le joueur 2 donne son jeu au joueur 3 et enfin le joueur 3 donne son jeu au joueur 1.
	 */
	public void rotatePlayersDecks()
	{
		int numberOfPlayers = players.size();
		if(numberOfPlayers>1)
		{
			if(orientation)
			{
				ArrayList<Card> temp=players.get(numberOfPlayers-1).getCards();
				for(int i=numberOfPlayers-1;i>0;i--)
				{
					players.get(i).setCards(players.get(i-1).getCards());
				}
				players.get(0).setCards(temp);
			}
			else
			{
				ArrayList<Card> temp = players.get(0).getCards();
				for(int i=0;i<numberOfPlayers-1;i++)
				{
					players.get(i).setCards(players.get(i+1).getCards());
				}
				players.get(numberOfPlayers-1).setCards(temp);
			}
		}	
	}
	
	/**
	 * Echange les jeux de cartes de 2 joueurs
	 * @param player1
	 * @param player2
	 */
	public void tradePlayersDecks(Player player1, Player player2)
	{
		ArrayList<Card> temp=player1.getCards();
		player1.setCards(player2.getCards());
		player2.setCards(temp);
	}
	
	/**
	 * Calcul des points des joueurs
	 */
	public void calculatePoints()
	{
		if(gameEnd)
		{
			for(Player player:players)
			{
				player.calculatePoints();
			}
		}
	}
	
	/**
	 * Renvoie le classement de la partie
	 * @return result
	 */
	public LinkedHashMap<String,Integer> ranking()
	{
		if(gameEnd)
		{
			LinkedHashMap<String,Integer> result=new LinkedHashMap<String, Integer>();
			ArrayList<Player> players=(ArrayList<Player>) getPlayers().clone();
			Collections.sort(players);
			for(Player player:players)
			{
				result.put(player.getName(), player.getScore());
			}
			return result;
		}
		return null;
	}
	
	/**
	 * Renvoie un hashcode
	 * return result 
 	*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualColor == null) ? 0 : actualColor.hashCode());
		result = prime * result + actualPlayer;
		result = prime * result + ((alternative == null) ? 0 : alternative.hashCode());
		result = prime * result + cptDrawCard;
		result = prime * result + ((deck == null) ? 0 : deck.hashCode());
		result = prime * result + (gameBegin ? 1231 : 1237);
		result = prime * result + (gameEnd ? 1231 : 1237);
		result = prime * result + ((gameName == null) ? 0 : gameName.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + (orientation ? 1231 : 1237);
		result = prime * result + nextPlayer;
		result = prime * result + numberPlayers;
		result = prime * result + ((players == null) ? 0 : players.hashCode());
		result = prime * result + ((stack == null) ? 0 : stack.hashCode());
		return result;
	}

	/**
	 * Contrôle l'égalité de 2 Game
	 * @return true/false
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (actualColor != other.actualColor)
			return false;
		if (actualPlayer != other.actualPlayer)
			return false;
		if (alternative == null) {
			if (other.alternative != null)
				return false;
		} else if (!alternative.equals(other.alternative))
			return false;
		if (cptDrawCard != other.cptDrawCard)
			return false;
		if (deck == null) {
			if (other.deck != null)
				return false;
		} else if (!deck.equals(other.deck))
			return false;
		if (gameBegin != other.gameBegin)
			return false;
		if (gameEnd != other.gameEnd)
			return false;
		if (gameName == null) {
			if (other.gameName != null)
				return false;
		} else if (!gameName.equals(other.gameName))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (orientation != other.orientation)
			return false;
		if (nextPlayer != other.nextPlayer)
			return false;
		if (numberPlayers != other.numberPlayers)
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
			return false;
		if (stack == null) {
			if (other.stack != null)
				return false;
		} else if (!stack.equals(other.stack))
			return false;
		return true;
	}
	
	

}
