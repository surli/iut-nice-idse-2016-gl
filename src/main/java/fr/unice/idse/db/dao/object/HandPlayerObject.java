package fr.unice.idse.db.dao.object;

import java.util.List;

public class HandPlayerObject {
	private int idMatch;
	private int idTurn;
	private int idUser;
	private List<CardObject> card;

	public HandPlayerObject() {
	}

	public HandPlayerObject(int idMatch, int idTurn, int idUser, List<CardObject> card) {
		super();
		this.idMatch = idMatch;
		this.idTurn = idTurn;
		this.card = card;
		this.idUser = idUser;
	}
	
	public HandPlayerObject(int idMatch, int idTurn, int idUser) {
		super();
		this.idMatch = idMatch;
		this.idTurn = idTurn;
		this.idUser = idUser;
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

	public List<CardObject> getCard() {
		return card;
	}

	public void setCard(List<CardObject> card) {
		this.card = card;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

}
