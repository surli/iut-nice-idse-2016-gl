package fr.unice.idse.db.dao.object;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.unice.idse.db.dao.CardDAO;
import fr.unice.idse.db.dao.DAOFactory;
import fr.unice.idse.model.card.Card;

public class HandPlayerObject {
	private int idMatch;
	private int idTurn;
	private int idUser;
	private List<CardObject> cards;

	public HandPlayerObject() {
		this.cards = new ArrayList<>();
	}

	public HandPlayerObject(int idMatch, int idTurn, int idUser, List<CardObject> cards) {
		this.idMatch = idMatch;
		this.idTurn = idTurn;
		this.cards = cards;
		this.idUser = idUser;
	}
	
	public HandPlayerObject(int idMatch, int idTurn, int idUser) {
		this.idMatch = idMatch;
		this.idTurn = idTurn;
		this.idUser = idUser;
		this.cards = new ArrayList<>();
	}

	public int getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}

	public int getIdTurn() {
		return idTurn;
	}

	public void setIdTurn(int idTurn) {
		this.idTurn = idTurn;
	}

	public List<CardObject> getCards() {
		return cards;
	}

	public void setCards(List<CardObject> cards) {
		this.cards = cards;
	}
	
	public void addCard(CardObject card) {
		this.cards.add(card);
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public void setCard(List<Card> cards) {
		try {
			for(Card card : cards) {
				CardObject cardObj = ((CardDAO)DAOFactory.getCardDAO()).find(card.getColor().getNumber(), card.getValue().getNumber()); 
				this.cards.add(cardObj);
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
	}

}
