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
		if(getBoard().getEffect())
		{
			getBoard().setCptDrawCard(getBoard().getCptDrawCard() + 2);
		}
		else
		{
			getBoard().setEffect();
			getBoard().setCptDrawCard(2);
		}
	}
	
	public void inEffect()
	{
		getBoard().drawCard();
	}

}
