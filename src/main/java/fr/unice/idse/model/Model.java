package fr.unice.idse.model;

import java.util.ArrayList;

import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.model.regle.EffectCard;
import fr.unice.idse.model.save.Save;

public class Model {
	private ArrayList<Game> games;
	private ArrayList<Player> players;
	private static Model model=null;
	
	/**
	 * Constructeur par défaut
	 */
	private Model()
	{
		this.games= new ArrayList<>();
		this.players= new ArrayList<>();
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
	 * @param player Player
	 * @param gameName String
	 * @param numberPlayers Int
	 * @return Boolean
	 */
	public boolean addGame(Player player, String gameName, int numberPlayers)
	{
		if(!existsGame(gameName))
		{
			Game game = new Game(player,gameName,numberPlayers);
			this.games.add(game);
			
			boolean saveEnable = false;
			if(saveEnable){
				game.addObserver(Save.getInstance());
				game.addObserver(game);
			}
			
			addPlayerToGame(gameName, player);
			return true;
		}
		return false;
	}
	
	/**
	 * Ajouter une partie avec des règles définies
	 * @param player Player
	 * @param gameName String
	 * @param numberPlayers Int
	 * @param regles ArrayList<EffectCard>
	 * @return Boolean
	 */
	public boolean addGame(Player player, String gameName, int numberPlayers, ArrayList<EffectCard> regles)
	{
		if(!existsGame(gameName))
		{
			Game game = new Game(player,gameName,numberPlayers, regles);
			game.getAlternative().setGameToEffectCards(game);
			this.games.add(game);
			
			boolean saveEnable = false;
			if(saveEnable){
				game.addObserver(Save.getInstance());
				game.addObserver(game);
			}
			
			addPlayerToGame(gameName, player);
			return true;
		}
		return false;
	}

	/**
	 * Créer un joueur s'il n'existe pas et l'ajoute dans la liste des joueurs
	 * @param playerName, playerToken
	 * @return Boolean
	 */
	public boolean createPlayer(String playerName, String playerToken)
	{
		if(!playerExists(playerName))
		{
			if(!playerExistsInList(playerName))
			{
				players.add(new Player(playerName,playerToken));
				return true;
			}
		}
		return false;
	}

	/**
	 * Supprimer un joueur de la liste des joueurs
	 * @param playerToken String
	 * @return Boolean
	 */
	public boolean removePlayer(String playerToken)
	{
		if(playerExistsInListByToken(playerToken))
		{
			Player player = getPlayerFromList(playerToken);
			players.remove(player);
			return true;
		}
		return false;
	}
	
	/**
	 * Retourne le player indiqué avec son token
	 * @param playerToken String
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
	 * Vérifie si le player existe dans la liste des joueurs par son playername
	 * @param playerName String
	 * @return Boolean
	 */
	public boolean playerExistsInList(String playerName)
	{
		for(Player player : players)
		{
			if(player.getName().equals(playerName))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Vérifie si le player existe dans la liste des joueurs par son token
	 * @param playerToken String
	 * @return Boolean
	 */
	public boolean playerExistsInListByToken(String playerToken)
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
	 * Vérifie si le joueur indiqué existe dans les parties par le token
	 * @param playerToken String
	 * @return Boolean
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
	 * @param gameName String
	 * @return Boolean
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
	 * @param gameName String
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
	 * @param gameName String
	 * @param player Player
	 * @return Boolean
	 */
	public boolean addPlayerToGame(String gameName,Player player)
	{
		Game game = findGameByName(gameName);
		if(game != null)
		{
			players.remove(player);
			return game.addPlayer(player);
		}
		return false;
	}
	
	/**
	 * Supprime joueur d'une partie par son playername
	 * @param gameName String
	 * @param playerName String
	 * @return Boolean
	 */
	public boolean removePlayerFromGameByName(String gameName, String playerName)
	{
		Game game = findGameByName(gameName);
		if(game != null)
		{
			Player player = findPlayerByName(gameName, playerName);
			if(game.removePlayerByName(playerName))
			{
				players.add(player);
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Chercher un joueur selon le nom de la partie et le nom du joueur renseignés
	 * @param gameName String
	 * @param playerName String
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
	 * Supprime joueur d'une partie par son token
	 * @param gameName String
	 * @param playerToken String
	 * @return Boolean
	 */
	public boolean removePlayerFromGameByToken(String gameName, String playerToken)
	{
		Game game = findGameByName(gameName);
		if(game != null)
		{
			Player player = findPlayerByToken(gameName, playerToken);
			if(game.removePlayerByToken(playerToken))
			{
				players.add(player);
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Chercher un joueur selon le nom de la partie et le nom du joueur renseignés
	 * @param gameName String
	 * @param playerToken String
	 * @return Player/null
	 */
	public Player findPlayerByToken(String gameName,String playerToken)
	{
		Game game = findGameByName(gameName);
		if(game != null)
		{
			return game.findPlayerByToken(playerToken);
		}
		return null;
	}
	
	/**
	 * Le joueur désigné joue une carte en fonction de la position de la carte
	 * (cardPosition)dans la main du joueur, dans la partie indiquée  
	 * @param gameName String
	 * @param playerName String
	 * @param cardPosition Int
	 * @return Boolean
	 */
	public boolean play(String gameName,String playerName, int cardPosition)
	{
		Game game=findGameByName(gameName);
		Player player=findPlayerByName(gameName, playerName);
		if(player != null)
			return player.play(cardPosition,game);
		return false;
	}
	
	/**
	 * Le joueur désigné joue une carte en fonction de la position de la carte
	 * (cardPosition)dans la main du joueur, dans la partie indiquée et change de
	 * couleur selon la couleur indiquée (0 -> Bleu, 1 -> Jaune, 2 -> Rouge, 3 -> Vert)
	 * @param playerName String
	 * @param colorNumber String
	 * @return Boolean
	 */
	public boolean play(String gameName,String playerName,int cardPosition, int colorNumber)
	{
		boolean result = false;
		Game game = findGameByName(gameName);
		Player player = findPlayerByName(gameName, playerName);
		if(player != null)
		{
			result= player.play(cardPosition,game);
			Color color=null;
			switch(colorNumber)
			{
				case 0:
					color=Color.Blue;
					break;
				case 1:
					color=Color.Yellow;
					break;
				case 2:
					color=Color.Red;
					break;
				case 3:
					color=Color.Green;
					break;
			}
			try{
				game.changeColor(color);
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
	 * @param gameName String
	 * @param playerName String
	 * @return Boolean
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
	 
	/**
	 * Méthode qui permet la suppression d'une partie
	 */
	public boolean removeGame(String gameName)
	{
		Game game=findGameByName(gameName);
		if(game!=null)
		{
			game.deleteObserver(Save.getInstance());
			
			games.remove(game);
			return true;
		}
		return false;
	}
}