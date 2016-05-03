package fr.unice.idse.db.dao.object;

public class StackObject {
	private int idMatch;
	private int idTurn;
	private int idCard; 
	private CardObject card;

	public StackObject() {
	}

	public StackObject(int idMatch, int idTurn, int idCard) {
		this.idMatch = idMatch;
		this.idTurn = idTurn;
		this.idCard = idCard;
	}

	
	public StackObject(int idMatch, int idTurn, CardObject card) {
		this.idMatch = idMatch;
		this.idTurn = idTurn;
		this.card = card;
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
	
	public int getIdCard() {
		return idCard;
	}

	public void setIdCard(int idCard) {
		this.idCard = idCard;
	}

	public CardObject getCard() {
		return card;
	}

	public void setCard(CardObject card) {
		this.card = card;
	}

}
