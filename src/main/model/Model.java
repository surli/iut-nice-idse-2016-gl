import java.util.ArrayList;

public class Model {
	private ArrayList<Game> games;
	private static Model model=null;
	
	private Model()
	{
		this.games= new ArrayList<Game>();
	}

	public static Model getInstance() {
		if (model == null) {
			model = new Model();
		}
		return model;
	}
	
	public boolean addGame(String name)
	{
		if(!containsGame(name))
		{
			Game game = new Game(name);
			this.games.add(game);
			return true;
		}
		return false;
	}
	
	public boolean containsGame(String name)
	{
		for(Game game : games)
		{
			if(game.getName().equals(name))
			{
				return true;
			}
		}
		return false;
	}

	public boolean addPlayerToGame(String gameName,String playerName)
	{
		Game game = findGameByName(gameName);
		if(game != null)
		{
			game.addPlayer(playerName);
			return true;
		}
		return false;
	}
	
	public Game findGameByName(String name)
	{
		for(Game game : games)
		{
			if(game.getName().equals(name))
			{
				return game;
			}
		}
		return null;
	}
	
	
}