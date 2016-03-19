package fr.unice.idse.model.save;

import fr.unice.idse.db.DataBaseManagement;

public class Save {
	protected DataBaseManagement dbm;
	 
	
	public Save()
	{
		dbm = new DataBaseManagement();
		
	}

	public DataBaseManagement getDbm() {
		return dbm;
	}
	
}
