package fr.unice.idse.model.regle;
import fr.unice.idse.model.*;
import fr.unice.idse.model.card.*;

public class RuleReverse extends EffectCard {
	
	public RuleReverse(Game game, Value value)
	{
		super(game, value);
	}
	
	@Override
	public void action()
	{
		getGame().changeMeaning();
	}
}
