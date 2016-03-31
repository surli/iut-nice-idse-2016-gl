package fr.unice.idse.db.dao.object;

public class MatchObject {
	private int id;
	private GameObject gameObject;

	public MatchObject() {
	}

	public MatchObject(int id) {
		this.id = id;
	}

	public MatchObject(int id, GameObject gameObject) {
		this.id = id;
		this.gameObject = gameObject;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GameObject getGameObject() {
		return gameObject;
	}

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
}
