package fr.unice.idse.model.regle;

import fr.unice.idse.model.Board;

public class RuleSkip extends EffectCard{

	public RuleSkip(Board board, int value) {
		super(board, value);
	}
	
	@Override
	public void action() {
		getBoard().nextPlayer();
	}

}
