package fr.unice.idse.model.save;

import java.util.Observable;
import java.util.Observer;

import fr.unice.idse.db.DataBaseManagement;

public class Save implements Observer {
	protected DataBaseManagement dbm;

	public Save() {
		dbm = new DataBaseManagement();
	}

	public DataBaseManagement getDbm() {
		return dbm;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO : Implement the notification
	}

}
