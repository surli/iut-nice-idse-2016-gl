package fr.unice.idse.model.regle;
import fr.unice.idse.model.*;
import fr.unice.idse.model.card.*;

public class RuleReverse extends EffectCard {
	
	public RuleReverse(Board board, Value value)
	{
		super(board, value);
	}
	
	@Override
	public void action()
	{
		getBoard().changeMeaning();
	}
}
