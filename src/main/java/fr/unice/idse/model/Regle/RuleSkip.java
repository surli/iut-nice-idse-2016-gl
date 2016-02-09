package fr.unice.idse.model.regle;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.card.*;

public class RuleSkip extends EffectCard{

	public RuleSkip(Board board, Value value) {
		super(board, value);
	}
	
	@Override
	public void action() {
		getBoard().nextPlayer();
	}

}
