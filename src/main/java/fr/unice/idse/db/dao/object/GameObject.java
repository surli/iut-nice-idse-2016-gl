package fr.unice.idse.db.dao.object;

public class GameObject {
	private int id;
	private String nom;
	private int nbMaxJoueurs;
	private int nbMaxIa;
	private int status;

	public GameObject() {
	}

	public GameObject(int id, String nom, int nbMaxJoueurs, int nbMaxIa,
			int status) {
		super();
		this.id = id;
		this.nom = nom;
		this.nbMaxJoueurs = nbMaxJoueurs;
		this.nbMaxIa = nbMaxIa;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getNbMaxJoueurs() {
		return nbMaxJoueurs;
	}

	public void setNbMaxJoueurs(int nbMaxJoueurs) {
		this.nbMaxJoueurs = nbMaxJoueurs;
	}

	public int getNbMaxIa() {
		return nbMaxIa;
	}

	public void setNbMaxIa(int nbMaxIa) {
		this.nbMaxIa = nbMaxIa;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
