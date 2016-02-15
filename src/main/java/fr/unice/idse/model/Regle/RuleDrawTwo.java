package fr.unice.idse.model.Regle;

import fr.unice.idse.model.*;

public class RuleDrawTwo extends EffectCard 
{

	public RuleDrawTwo(Board board, int value) 
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
	
	public void inEffect()
	{
		getBoard().drawCard();
		getBoard().setCptDrawCard(1);
	}

}
