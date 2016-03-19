package fr.unice.idse.model.save;

import java.util.Observable;
import java.util.Observer;

import fr.unice.idse.model.Game;

public class Save implements Observer {
	protected BusinessQuery bq;

	public Save() {
		bq = new BusinessQuery();
	}

	public BusinessQuery getDbm() {
		return bq;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Game) {
			this.saveNewGame((Game)o);
		}
	}
	
	/*
	 * Sauvegarde d'une nouvelle partie
	 * @param Game game
	 */
	private void saveNewGame(Game game) {
		// gl & hf
		//	BusinessQuery.newGame(new Game(new Player("Roger", "Espadon"), "Test", 3));
	}

}
