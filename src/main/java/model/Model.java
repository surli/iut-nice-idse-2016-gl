package java.model;
import java.util.ArrayList;

public class Model {
	private ArrayList<Game> games;
	private static Model model=null;
	
	/**
	 * Constructeur par dï¿½faut
	 */
	private Model()
	{
		this.games= new ArrayList<Game>();
	}

	/**
	 * Rï¿½cupï¿½rer une instance du modï¿½le, si null crï¿½ation du modï¿½le
	 * @return model
	 */
	public static Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}
	
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
	 * Ajoute un joueur ï¿½ une partie si le nom du joueur n'est pas dï¿½jï¿½ existant et que le nom de la partie est correct
	 * @param gameName
	 * @param playerName
	 * @return true/false
	 */
	public boolean addPlayerToGame(String gameName,String playerName)
	{
		Game game = findGameByName(gameName);
		if(game != null)
		{
			return game.addPlayer(playerName);
		}
		return false;
	}
	
	
	/**
	 * Chercher un joueur selon le nom de la partie et le nom du joueur renseignï¿½s
	 * @param gameName
	 * @param playerName
	 * @return Player/null
	 */
	public Player findPlayerByName(String gameName,String playerName)
	{
		Game game = findGameByName(gameName);
		if(game != null)
		{
			for(Player player : game.getPlayers())
			{
				if(player.getName().equals(playerName))
				{
					return player;
				}
			}
		}
		return null;
	}
}