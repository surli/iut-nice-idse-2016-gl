package fr.unice.idse.db.dao.object;

public class StackObject {
	private MatchObject match;
	private TurnObject turn;
	private CardObject card;

	public StackObject() {
	}

	public StackObject(MatchObject match, TurnObject turn, CardObject card) {
		this.match = match;
		this.turn = turn;
		this.card = card;
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

}
