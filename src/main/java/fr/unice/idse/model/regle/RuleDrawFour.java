package fr.unice.idse.model.regle;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public class RuleDrawFour extends EffectCard 
{
	public RuleDrawFour(Board board, Value value) 
	{
		super(board, value);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void changeColor(Color color)
	{
		getBoard().changeColor(color);
	}
	
	@Override
	public void action()
	{
		getBoard().setCptDrawCard(4);
	}
	
	public void effect()
	{
		getBoard().drawCard();
		getBoard().setCptDrawCard(1);
	}
}
