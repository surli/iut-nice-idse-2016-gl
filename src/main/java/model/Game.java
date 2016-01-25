import java.util.ArrayList;

public class Game {
	
	private Player host;
	private String gameName;
	private ArrayList<Player> players;
	private Board board;
	
	public Game(Player host,String gameName)
	{
		this.host = host;
		this.gameName = gameName;
		this.players = new ArrayList<Player>();
		this.board = new Board(players);
		this.players.add(host);
	}
	
	public Player getHost() { return host; }
	public void setHost(Player host) { this.host = host; }
	
	public String getGameName() { return gameName; }
	public void setGameName(String gameName) { this.gameName = gameName; }
	
	public String getName() { return gameName; }
	public void setName(String gameName) { this.gameName = gameName; }
	
	public ArrayList<Player> getPlayers() {	return players;	}
	public void setPlayers(ArrayList<Player> players) {	this.players = players; }
	
	public Board getBoard() { return board; }
	public void setBoard(Board board) {	this.board = board;	}
	
	public boolean addPlayer(String playerName)
	{
		if(!containsPlayer(playerName))
		{
			Player player=new Player(playerName);
			this.players.add(player);
			return true;
		}
		return false;
	}

	public boolean containsPlayer(String playerName)
	{
		for(Player player : players)
		{
			if(player.getName().equals(playerName))
				return true;
		}
		return false;
	}
}
