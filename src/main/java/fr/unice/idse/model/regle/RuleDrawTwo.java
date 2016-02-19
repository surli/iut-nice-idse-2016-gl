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
