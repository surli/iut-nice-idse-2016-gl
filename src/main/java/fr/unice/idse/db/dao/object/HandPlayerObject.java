package fr.unice.idse.db.dao.object;

public class HandPlayerObject {
	private MatchObject match;
	private TurnObject turn;
	private CardObject card;
	private UserObject user;

	public HandPlayerObject() {
	}

	public HandPlayerObject(MatchObject match, TurnObject turn,
			CardObject card, UserObject user) {
		this.match = match;
		this.turn = turn;
		this.card = card;
		this.user = user;
	}

	public MatchObject getMatch() {
		return match;
	}

	public void setMatch(MatchObject match) {
		this.match = match;
	}

	public TurnObject getTurn() {
		return turn;
	}

	public void setTurn(TurnObject turn) {
		this.turn = turn;
	}

	public CardObject getCard() {
		return card;
	}

	public void setCard(CardObject card) {
		this.card = card;
	}

	public UserObject getUser() {
		return user;
	}

	public void setUser(UserObject user) {
		this.user = user;
	}
}
