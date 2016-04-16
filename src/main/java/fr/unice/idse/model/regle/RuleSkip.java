package fr.unice.idse.model.regle;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.*;

public class RuleSkip extends EffectCard{

	public RuleSkip(Game board, Value value) 
	{
		this.game = game;
		this.isColorChangingCard = false;
		this.value = value;
	}
	
	@Override
	public void action() {
		getGame().nextPlayer();
	}

}
