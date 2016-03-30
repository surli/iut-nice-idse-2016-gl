package fr.unice.idse.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.unice.idse.constante.Config;

public class Connexion {
	private static Connection connect = null;
	
	public static Connection getInstance(){
		if(connect == null){
			try {
				connect = DriverManager.getConnection(Config.url, Config.user, Config.pass);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return connect;	
	}

}
