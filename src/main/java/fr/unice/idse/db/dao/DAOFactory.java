package fr.unice.idse.db.dao;

import java.sql.Connection;

import fr.unice.idse.db.dao.object.UserObject;

public class DAOFactory {
	protected static final Connection conn = Connexion.getConnection();
	
	 /**
	  * Retourne un objet User interagissant avec la BDD
	  * @return DAO
	  */
	  public static DAO<UserObject> getUserDAO(){
	    return new UserDAO(conn);
	  }
}
