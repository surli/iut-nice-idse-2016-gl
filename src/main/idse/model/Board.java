package idse.model;

import java.util.ArrayList;

public class Board {
	private ArrayList<Player> players;
	public Board()
	{
		this.players = new ArrayList<Player>();
	}
	public Board(ArrayList<Player> players)
	{
		this.players = players;
	}
}
