package fr.unice.idse.db.dao.object;

public class TurnObject {
	private int id;
	private int idMatch;
	private boolean inverded;
	private int idUser;

	public TurnObject() {
	}

	public TurnObject(int id, int idMatch, boolean inverded, int idUser) {
		this.id = id;
		this.idMatch = idMatch;
		this.inverded = inverded;
		this.idUser = idUser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdMatch() {
		return idMatch;
	}

	public void setIdMatch(int idMatch) {
		this.idMatch = idMatch;
	}

	public boolean isInverded() {
		return inverded;
	}

	public void setInverded(boolean inverded) {
		this.inverded = inverded;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
}
