package fr.unice.idse.db.dao.object;

public class UserObject {
	private int id, status;
	private String pseudo, email, password;
	private boolean banned;

	public UserObject(int id, String pseudo, String email,
			String password, int status, boolean banned) {
		this.id = id;
		this.status = status;
		this.pseudo = pseudo;
		this.email = email;
		this.password = password;
		this.banned = banned;
	}
	
	public UserObject(String pseudo, String email, String password,
			int status, boolean banned) {
		this(0, pseudo, email, password, status, banned);
	}
	
	public UserObject(String pseudo, String email, String password) {
		this(0, pseudo, email, password, 0, false);
	}

	public UserObject(String pseudo) {
		this.pseudo = pseudo;
	}

	public UserObject() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}
}
