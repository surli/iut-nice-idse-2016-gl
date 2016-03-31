package fr.unice.idse.db.dao.object;

public class TurnObject {
	private int id;
	private MatchObject match;
	private boolean inverded;
	private UserObject user;

	public TurnObject() {
	}

	public TurnObject(int id, MatchObject match, boolean inverded,
			UserObject user) {
		this.id = id;
		this.match = match;
		this.inverded = inverded;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MatchObject getMatch() {
		return match;
	}

	public void setMatch(MatchObject match) {
		this.match = match;
	}

	public boolean isInverded() {
		return inverded;
	}

	public void setInverded(boolean inverded) {
		this.inverded = inverded;
	}

	public UserObject getUser() {
		return user;
	}

	public void setUser(UserObject user) {
		this.user = user;
	}

}
