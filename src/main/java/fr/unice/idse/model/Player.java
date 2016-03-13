package fr.unice.idse.model;


import java.util.ArrayList;
import java.util.Collections;
import fr.unice.idse.model.card.*;

public class Player implements Comparable<Player>{
	private String name;
	private ArrayList<Card> cards;
	private boolean turn;
	private Card lastCardPlayed;
	private int score;
	private boolean dealer;
	private String token;
	
	public Player(String name, String token)
	{
		this.name = name;
		this.cards = new ArrayList<Card>();
		this.turn = false;
		this.lastCardPlayed = null;
		this.score = 0;
		this.token = token;
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
	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean play(int cardPosition,Board board)
	{
		return false;	
	}
	
	public void sortCards()
	{
		Collections.sort(this.cards);
	}
	
	public void calculatePoints()
	{
		for(Card card:cards)
		{
			switch(card.getValue())
			{
				case Zero:
					break;
				case One:
					this.score+=1;
					break;
				case Two:
					this.score+=2;
					break;
				case Three:
					this.score+=3;
					break;
				case Four:
					this.score+=4;
					break;
				case Five:
					this.score+=5;
					break;
				case Six:
					this.score+=6;
					break;
				case Seven:
					this.score+=7;
					break;
				case Eight:
					this.score+=8;
					break;
				case Nine:
					this.score+=9;
					break;
				case Skip: case Reverse: case DrawTwo:
					this.score+=20;
					break;
				case Wild: case DrawFour:
					this.score+=50;
					break;
			}
		}
	}
	
	@Override
	public int compareTo(Player player) {
		
		return ((Integer)score).compareTo(player.getScore());
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
		result = prime * result + (dealer ? 1231 : 1237);
		result = prime * result + ((lastCardPlayed == null) ? 0 : lastCardPlayed.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + score;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + (turn ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!cards.equals(other.cards))
			return false;
		if (dealer != other.dealer)
			return false;
		if (lastCardPlayed == null) {
			if (other.lastCardPlayed != null)
				return false;
		} else if (!lastCardPlayed.equals(other.lastCardPlayed))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (score != other.score)
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (turn != other.turn)
			return false;
		return true;
	}
	
	
	
}
