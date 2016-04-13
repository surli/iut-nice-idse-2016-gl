package fr.unice.idse.model.regle;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public class RuleDrawFour extends EffectCard 
{
	public RuleDrawFour(Game game, Value value) 
	{
		super(game, value);
		setColorChangingCard(true);
	}

	@Override
	public void changeColor(Color color)
	{
		getGame().changeColor(color);
	}
	
	@Override
	public void action()
	{
		getGame().setCptDrawCard(4);
	}
	
	@Override
	public void effect()
	{
		getGame().drawCard();
		getGame().setCptDrawCard(1);
	}
}
