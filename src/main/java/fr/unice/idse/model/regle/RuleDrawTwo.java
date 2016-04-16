package fr.unice.idse.model.regle;

import fr.unice.idse.model.*;
import fr.unice.idse.model.card.*;

public class RuleDrawTwo extends EffectCard
{

	public RuleDrawTwo(Game game, Value value)
	{
		super(game, value);
	}

	@Override
	public void action()
	{
		if(getGame().getCptDrawCard()>1)
		{
			getGame().setCptDrawCard(getGame().getCptDrawCard() + 2);
		}
		else
		{
			getGame().setCptDrawCard(2);
		}
	}
	
	@Override
	public void effect()
	{
		if(!getGame().askPlayerCanPlay(getGame().getActualPlayer()))
		{
			getGame().drawCard();
			getGame().setCptDrawCard(1);
		}
	}
}
