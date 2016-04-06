package fr.unice.idse.model.regle;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public class RuleChangeColor extends EffectCard 
{

	public RuleChangeColor(Game game, Value value) 
	{
		super(game, value);
		setColorChangingCard(true);
	}
	
	@Override
	public void changeColor(Color color)
	{
		getGame().changeColor(color);
	}

}
