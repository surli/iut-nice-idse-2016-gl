package fr.unice.idse.model.regle;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.*;

public class RuleSkip extends EffectCard{

	public RuleSkip(Game board, Value value) {
		super(board, value);
	}
	
	@Override
	public void action() {
		getGame().nextPlayer();
	}

}
