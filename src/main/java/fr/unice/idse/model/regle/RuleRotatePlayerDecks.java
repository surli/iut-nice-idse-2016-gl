package fr.unice.idse.model.regle;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Value;

public class RuleRotatePlayerDecks extends EffectCard{

	public RuleRotatePlayerDecks(Game game, Value value) 
	{
		this.game = game;
		this.isColorChangingCard = false;
		this.value = value;
	}
	
	@Override
	public void action() {
		getGame().rotatePlayersDecks();
	}
}
