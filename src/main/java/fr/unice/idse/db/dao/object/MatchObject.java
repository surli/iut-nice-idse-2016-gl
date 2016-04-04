package fr.unice.idse.db.dao.object;


public class MatchObject {
	private int id;
	private int idGame;

	public MatchObject() {
	}

	public MatchObject(int id) {
		this.id = id;
	}

	public MatchObject(int id, int idGame) {
		this.id = id;
		this.idGame = idGame;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}
}
