package fr.unice.idse.model;

import java.util.ArrayList;

public class Model {
	private ArrayList<Game> games;
	private ArrayList<Player> players;
	private static Model model=null;
	
	/**
	 * Constructeur par défaut
	 */
	private Model()
	{
		this.games= new ArrayList<Game>();
		this.players= new ArrayList<Player>();
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
	public void setGames(ArrayList<Game> games) { this.games = games; }
	public ArrayList<Player> getPlayers() {return players;}
	public void setPlayers(ArrayList<Player> players) {this.players = players;}

	/**
	 * Ajouter une partie
	 * @param gameName
	 * @return true/false
	 */
	public boolean addGame(Player player, String gameName, int numberPlayers)
	{
		if(!existsGame(gameName))
		{
			Game game = new Game(player,gameName,numberPlayers);
			this.games.add(game);
			return true;
		}
		return false;
	}
	
	/**
	 * Créer un joueur s'il n'existe pas
	 * @param playerName, playerToken
	 * @return Player
	 */
	public Player createPlayer(String playerName,String playerToken)
	{
		if(!playerExists(playerName))
		{
			return new Player(playerName,playerToken);
		}
		return null;
	}
	
	/**
	 * Créer un joueur s'il n'existe pas et l'ajoute dans la liste des joueurs
	 * @param playerName, playerToken
	 * @return true/false
	 */
	public boolean createPlayerBis(String playerName,String playerToken)
	{
		if(!playerExists(playerName))
		{
			if(!playerExistsInList(playerToken))
			{
				players.add(new Player(playerName,playerToken));
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Retourne le player indiqué avec son token
	 * @param playerToken
	 * @return Player
	 */
	public Player getPlayerFromList(String playerToken)
	{
		for(Player player : players)
		{
			if(player.getToken().equals(playerToken))
			{
				return player;
			}
		}
		return null;
	}
	
	/**
	 * Vérifie si le player existe dans la liste des joueurs
	 * @param playerToken
	 * @return true/false
	 */
	public boolean playerExistsInList(String playerToken)
	{
		for(Player player : players)
		{
			if(player.getToken().equals(playerToken))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Vérifie si le joueur indiqué existe dans les parties
	 * @param playerToken
	 * @return true/false
	 */
	public boolean playerExists(String playerToken)
	{
		for(Game game : games)
		{
			for(Player player : game.getPlayers())
			{
				if(player.getToken().equals(playerToken))
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
	public boolean removePlayerFromGameByName(String gameName, String playerName)
	{
		Game game = findGameByName(gameName);
		if(game != null)
		{
			return game.removePlayerByName(playerName);
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
	 * Supprime joueur d'une partie
	 * @param gameName
	 * @param playerToken
	 * @return true/false
	 */
	public boolean removePlayerFromGameByToken(String gameName, String playerToken)
	{
		Game game = findGameByName(gameName);
		if(game != null)
		{
			return game.removePlayerByToken(playerToken);
		}
		return false;
	}
	
	
	/**
	 * Chercher un joueur selon le nom de la partie et le nom du joueur renseignés
	 * @param gameName
	 * @param playerToken
	 * @return Player/null
	 */
	public Player findPlayerByToken(String gameName,String playerToken)
	{
		Game game = findGameByName(gameName);
		if(game != null)
		{
			return game.findPlayerByName(playerToken);
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
				case 0: color=Color.Blue;
				case 1: color=Color.Yellow;
				case 2: color=Color.Red;
				case 3: color=Color.Green;
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
	
	
	/**
	 * Méthode qui permet de lancer une partie en vérifiant que l'hote de la partie est bien celui indiqué en paramètre
	 * @param gameName
	 * @param playerName
	 * @return true/false
	 */
	public boolean startGame(String gameName,String playerName)
	{
		Game game = findGameByName(gameName);
		
		if(game != null)
		{
			Player player = findPlayerByName(gameName, playerName);
			if(player != null)
			{
				if(game.getHost().getName().equals(player.getName()))
				{
					return game.start();
				}
			}
		}
		return false;
	}
	 
}