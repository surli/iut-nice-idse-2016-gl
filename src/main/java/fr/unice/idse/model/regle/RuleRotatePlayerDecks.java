package fr.unice.idse.model.regle;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.card.Value;

public class RuleRotatePlayerDecks extends EffectCard{

	public RuleRotatePlayerDecks(Board board, Value value) {
		super(board, value);
	}
	
	@Override
	public void action() {
		getBoard().rotatePlayerDecks();
	}
}
