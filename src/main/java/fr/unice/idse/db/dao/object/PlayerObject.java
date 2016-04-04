package fr.unice.idse.db.dao.object;

public class PlayerObject {
	private int idGame;
	private int position;
	private int idUser;

	public PlayerObject() {
	}

	public PlayerObject(int idGame, int idUser, int position) {
		this.idGame = idGame;
		this.idUser = idUser;
		this.position = position;
	}

	public int getIdGame() {
		return idGame;
	}

	public void setIdGame(int idGame) {
		this.idGame = idGame;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

}