package fr.unice.idse.model.regle;

import fr.unice.idse.model.*;
import fr.unice.idse.model.card.*;

public class RuleDrawTwo extends EffectCard
{

	public RuleDrawTwo(Game game, Value value)
	{
		this.game = game;
		this.isColorChangingCard = false;
		this.value = value;
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

	@Override
	public void action(Color color) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void action(String playerName) 
	{
		// TODO Auto-generated method stub
	}
}
