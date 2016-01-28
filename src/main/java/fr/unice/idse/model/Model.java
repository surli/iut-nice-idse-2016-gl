package fr.unice.idse.model;

import java.util.ArrayList;

public class Model {
	private ArrayList<Game> games;
	private static Model model=null;
	
	/**
	 * Constructeur par d�faut
	 */
	private Model()
	{
		this.games= new ArrayList<Game>();
	}

	/**
	 * Récupérer une instance du modèle, si null création du modèle
	 * @return model
	 */
	public static Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}
	
	public ArrayList<Game> getGames() { return games; }

	/**
	 * Ajouter une partie
	 * @param gameName
	 * @return true/false
	 */
	public boolean addGame(Player player, String gameName)
	{
		if(!existsGame(gameName))
		{
			Game game = new Game(player,gameName);
			this.games.add(game);
			return true;
		}
		return false;
	}
	
	/**
	 * Créer un joueur s'il n'existe pas
	 * @param playerName
	 * @return Player
	 */
	public Player createPlayer(String playerName)
	{
		if(!playerExists(playerName))
		{
			return new Player(playerName);
		}
		return null;
	}
	
	/**
	 * Vérifie si le joueur indiqué existe dans les parties
	 * @param playerName
	 * @return true/false
	 */
	public boolean playerExists(String playerName)
	{
		for(Game game : games)
		{
			for(Player player : game.getPlayers())
			{
				if(player.getName().equals(playerName))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Verifie si la partie existe
	 * @param gameName
	 * @return true/false
	 */
	public boolean existsGame(String gameName)
	{
		for(Game game : games)
		{
			if(game.getName().equals(gameName))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Chercher la partie selon son nom
	 * @param gameName
	 * @return Game/null
	 */
	public Game findGameByName(String gameName)
	{
		for(Game game : games)
		{
			if(game.getName().equals(gameName))
			{
				return game;
			}
		}
		return null;
	}
	
	/**
	 * Ajoute un joueur à une partie si le joueur n'est pas déjà existant et que le nom de la partie est correct
	 * @param gameName
	 * @param player
	 * @return true/false
	 */
	public boolean addPlayerToGame(String gameName,Player player)
	{
		Game game = findGameByName(gameName);
		if(game != null)
		{
			return game.addPlayer(player);
		}
		return false;
	}
	
	/**
	 * Supprime joueur d'une partie
	 * @param gameName
	 * @param playerName
	 * @return true/false
	 */
	public boolean removePlayerFromGame(String gameName, String playerName)
	{
		Game game = findGameByName(gameName);
		if(game != null)
		{
			return game.removePlayer(playerName);
		}
		return false;
	}
	
	
	/**
	 * Chercher un joueur selon le nom de la partie et le nom du joueur renseignés
	 * @param gameName
	 * @param playerName
	 * @return Player/null
	 */
	public Player findPlayerByName(String gameName,String playerName)
	{
		Game game = findGameByName(gameName);
		if(game != null)
		{
			return game.findPlayerByName(playerName);
		}
		return null;
	}
	
	/**
	 * Le joueur désigné joue une carte en fonction de la position de la carte
	 * (cardPosition)dans la main du joueur, dans la partie indiquée  
	 * @param game
	 * @param playerName
	 * @param colorNumber
	 * @param cardPosition
	 * @return true/false
	 */
	public boolean play(String gameName,String playerName, int cardPosition)
	{
		Game game=findGameByName(gameName);
		Player player=findPlayerByName(gameName, playerName);
		if(player != null)
			return player.play(cardPosition,game.getBoard());
		return false;
	}
	
	/**
	 * Le joueur désigné joue une carte en fonction de la position de la carte
	 * (cardPosition)dans la main du joueur, dans la partie indiquée et change de
	 * couleur selon la couleur indiquée (0 -> Bleu, 1 -> Jaune, 2 -> Rouge, 3 -> Vert)
	 * @param game
	 * @param playerName
	 * @param colorNumber
	 * @return true/false
	 */
	public boolean play(String gameName,String playerName,int cardPosition, int colorNumber)
	{
		boolean result = false;
		Game game = findGameByName(gameName);
		Board board = game.getBoard();
		Player player = findPlayerByName(gameName, playerName);
		if(player != null)
		{
			result= player.play(cardPosition,game.getBoard());
			Color color=null;
			switch(colorNumber)
			{
				case 0: color=Color.Bleu;
				case 1: color=Color.Jaune;
				case 2: color=Color.Rouge;
				case 3: color=Color.Vert;
			}
			try{
				board.changeColor(color);
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
			}
		}
		return false;
	}
}