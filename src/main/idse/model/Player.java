package idse.model;


import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Card> cards;
	private boolean turn;
	private Card lastCardPlayed;
	private int score;
	private boolean dealer;
	
	public Player(String name)
	{
		this.name = name;
		this.cards = new ArrayList<>();
		this.turn = false;
		this.lastCardPlayed = null;
		this.score = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public Card getLastCardPlayed() {
		return lastCardPlayed;
	}

	public void setLastCardPlayed(Card lastCardPlayed) {
		this.lastCardPlayed = lastCardPlayed;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isDealer() {
		return dealer;
	}

	public void setDealer(boolean dealer) {
		this.dealer = dealer;
	}
	
	
}
