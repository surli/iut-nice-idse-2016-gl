package fr.unice.idse.model;

import java.util.ArrayList;

public class Game {
	
	private Player host;
	private String gameName;
	//private ArrayList<Player> players;//à discuter la nécessitée de la mettre
	private Board board;
	private int minPlayer=2,maxPlayer=4;
	
	public Game(Player host,String gameName)
	{
		this.host = host;
		this.gameName = gameName;
		//this.players = new ArrayList<Player>();
		this.board = new Board();
		this.board.getPlayers().add(host);
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
	
	public int getMaxPlayer() { return maxPlayer; }
	public void setMaxPlayer(int maxPlayer) { this.maxPlayer = maxPlayer; }
	
	/**
	 * Ajoute joueur à la partie
	 * @param player
	 * @return true/false
	 */
	public boolean addPlayer(Player player)
	{
		if(board.getPlayers().size() == maxPlayer)
		{
			return false;
		}
		if(!containsPlayer(player.getName()))
		{
			this.board.getPlayers().add(player);
			return true;
		}
		return false;
	}

	/**
	 * Verifie si le joueur indiqué est présent dans la partie
	 * @param playerName
	 * @return true/false
	 */
	public boolean containsPlayer(String playerName)
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
	 * Supprime joueur de la partie
	 * @param playerName
	 * @return true/false
	 */
	public boolean removePlayer(String playerName)
	{
		if(containsPlayer(playerName))
		{
			board.getPlayers().remove(findPlayerByName(playerName));
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
		return maxPlayer-board.getPlayers().size();
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
		if(numberOfPlayers()>minPlayer && numberOfPlayers()<maxPlayer)
		{
			board.init();
			return true;
		}
		return false;
	}

}
