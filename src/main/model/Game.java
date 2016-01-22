import java.util.ArrayList;

public class Game {
	
	private String name;
	private ArrayList<Player> players;
	private Board board;
	
	public Game(String name)
	{
		this.name = name;
		this.players = new ArrayList<Player>();
		this.board = new Board(players);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	
	public boolean addPlayer(String name)
	{
		if(!containsPlayer(name))
		{
			Player player=new Player(name);
			this.players.add(player);
			return true;
		}
		return false;
	}

	public boolean containsPlayer(String name)
	{
		for(Player player : players)
		{
			if(player.getName().equals(name))
				return true;
		}
		return false;
	}
}
