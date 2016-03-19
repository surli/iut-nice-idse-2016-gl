package fr.unice.idse.model;

import fr.unice.idse.model.player.Player;

import java.util.ArrayList;
import java.util.Observable;

public class Game extends Observable {
	
	private Player host;
	private String gameName;
	private Board board;
	private int numberPlayers;
	
	public Game(Player host,String gameName, int numberOfPlayers)
	{
			this.host = host;
			this.gameName = gameName;
			this.board = new Board();
			this.numberPlayers = numberOfPlayers;
	}

	public Player getHost() { return host; }
	public void setHost(Player host) { this.host = host; }
	
	public String getGameName() { return gameName; }
	public void setGameName(String gameName) { this.gameName = gameName; }
	
	public String getName() { return gameName; }
	public void setName(String gameName) { this.gameName = gameName; }
	
	public ArrayList<Player> getPlayers() {	return board.getPlayers();	}
	public void setPlayers(ArrayList<Player> players) {	this.board.setPlayers(players); }
	
	public Board getBoard() { return board; }
	public void setBoard(Board board) {	this.board = board;	}
	
	public Alternative getAlternative() {return board.getAlternative();};
	public void setAlternative(Alternative alternative) { board.setAlternative(alternative);}
	
	public int getNumberPlayers() { return numberPlayers; }
	public void setNumberPlayers(int numberPlayers) { this.numberPlayers = numberPlayers; }
	
	/**
	 * Ajoute joueur à la partie
	 * @param player
	 * @return true/false
	 */
	public boolean addPlayer(Player player)
	{
		if(board.getPlayers().size() == numberPlayers)
		{
			return false;
		}
		if(!containsPlayerByToken(player.getToken()))
		{
			this.board.getPlayers().add(player);
			return true;
		}
		return false;
	}
	
	public boolean gameBegin()
	{
		return board.gameBegin();
	}

	public boolean gameEnd()
	{
		return board.gameEnd();
	}
	
	/**
	 * Verifie si le joueur indiqué est présent dans la partie
	 * @param playerName
	 * @return true/false
	 */
	public boolean containsPlayerByName(String playerName)
	{
		for(Player player : board.getPlayers())
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
		for(Player player : board.getPlayers())
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
			board.getPlayers().remove(findPlayerByName(playerName));
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
		for(Player player : board.getPlayers())
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
		for(Player player : board.getPlayers())
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
			board.getPlayers().remove(findPlayerByName(playerToken));
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
		return numberPlayers-board.getPlayers().size();
	}
	
	/**
	 * Retourne le nombre de joueurs présents dans la partie
	 * @return int
	 */
	public int numberOfPlayers()
	{
		return board.getPlayers().size();
	}
	
	/**
	 * Méthode qui initialise une partie afin de qu'elle devienne jouable
	 * @return
	 */
	public boolean start()
	{
		if(numberOfPlayers()==numberPlayers)
		{
			board.init();
			
			setChanged();
			notifyObservers();
			
			return true;
		}
		return false;
	}

}
