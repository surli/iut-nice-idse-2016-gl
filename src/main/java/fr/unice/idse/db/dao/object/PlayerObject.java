package fr.unice.idse.db.dao.object;

public class PlayerObject {
	private GameObject game;
	private UserObject user;
	private int position;

	public PlayerObject() {
	}

	public PlayerObject(GameObject game, UserObject user, int position) {
		this.game = game;
		this.user = user;
		this.position = position;
	}

	public GameObject getGame() {
		return game;
	}

	public void setGame(GameObject game) {
		this.game = game;
	}

	public UserObject getUser() {
		return user;
	}

	public void setUser(UserObject user) {
		this.user = user;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
