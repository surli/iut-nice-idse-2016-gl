package java.model;
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
	 * R�cup�rer une instance du mod�le, si null cr�ation du mod�le
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
	 * @param name
	 * @return true/false
	 */
	public boolean addGame(String name)
	{
		if(!existsGame(name))
		{
			Game game = new Game(name);
			this.games.add(game);
			return true;
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
	 * Ajoute un joueur � une partie si le nom du joueur n'est pas d�j� existant et que le nom de la partie est correct
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
	 * Chercher un joueur selon le nom de la partie et le nom du joueur renseign�s
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