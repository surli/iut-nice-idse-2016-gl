package fr.unice.idse.model.regle;

import fr.unice.idse.model.*;
import fr.unice.idse.model.card.*;

public class RuleDrawTwo extends EffectCard
{

	public RuleDrawTwo(Board board, Value value)
	{
		super(board, value);
	}

	@Override
	public void action()
	{
		if(getBoard().getCptDrawCard()>1)
		{
			getBoard().setCptDrawCard(getBoard().getCptDrawCard() + 2);
		}
		else
		{
			getBoard().setCptDrawCard(2);
		}
	}
	
	@Override
	public void effect()
	{
		if(!getBoard().askPlayerCanPlay(getBoard().getActualPlayer()))
		{
			getBoard().drawCard();
			getBoard().setCptDrawCard(1);
		}
	}
}
