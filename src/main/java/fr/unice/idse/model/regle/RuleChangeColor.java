package fr.unice.idse.model.regle;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public class RuleChangeColor extends EffectCard 
{

	public RuleChangeColor(Board board, Value value) 
	{
		super(board, value);

	}
	
	@Override
	public void changeColor(Color color)
	{
		getBoard().changeColor(color);
	}

}
