package fr.unice.idse.db.dao;

import java.sql.Connection;

import fr.unice.idse.db.dao.object.CardObject;
import fr.unice.idse.db.dao.object.GameObject;
import fr.unice.idse.db.dao.object.MatchObject;
import fr.unice.idse.db.dao.object.PlayerObject;
import fr.unice.idse.db.dao.object.UserObject;

public class DAOFactory {
	protected static final Connection conn = Connexion.getConnection();

	/**
	 * Retourne un objet User interagissant avec la BDD
	 * 
	 * @return DAO
	 */
	public static DAO<UserObject> getUserDAO() {
		return new UserDAO(conn);
	}

	/**
	 * Retourne un objet Game interagissant avec la BDD
	 * 
	 * @return DAO
	 */
	public static DAO<GameObject> getGameDAO() {
		return new GameDAO(conn);
	}

	/**
	 * Retourne un objet Match interagissant avec la BDD
	 * 
	 * @return DAO
	 */
	public static DAO<MatchObject> getMatchDAO() {
		return new MatchDAO(conn);
	}

	/**
	 * Retourne un objet Player interagissant avec la BDD
	 * 
	 * @return DAO
	 */
	public static DAO<PlayerObject> getPlayerDAO() {
		return null;
	}
	
	/**
	 * Retourne un objet Card interagissant avec la BDD
	 * 
	 * @return DAO
	 */
	
	public static DAO<CardObject> getCardDAO(){
		return new CardDAO(conn);
	}
	
}
