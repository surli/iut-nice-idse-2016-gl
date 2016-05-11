package fr.unice.idse.model.regle;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public class RuleDrawFour extends EffectCard 
{
	public RuleDrawFour(Game game, Value value) 
	{
		this.game = game;
		this.isColorChangingCard = true;
		this.value = value;
	}
	
	public RuleDrawFour( Value value) 
	{
		this.isColorChangingCard = true;
		this.value = value;
	}

	@Override
	public void action(Color color)
	{
		getGame().changeColor(color);
	}

	public void action()
	{
		if(getGame().getCptDrawCard()>3)
		{
			getGame().setCptDrawCard(getGame().getCptDrawCard() + 4);
		}
		else
		{
			getGame().setCptDrawCard(4);
		}
	}
	
	@Override
	public void effect()
	{
		getGame().drawCard();
		getGame().setCptDrawCard(1);
	}

	@Override
	public void action(String playerName) 
	{
		// TODO Auto-generated method stub
	}
}
