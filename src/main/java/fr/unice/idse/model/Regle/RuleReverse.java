package fr.unice.idse.model.regle;
import fr.unice.idse.model.*;

public class RuleReverse extends EffectCard {
	
	public RuleReverse(Board board, int value)
	{
		super(board, value);
	}
	
	@Override
	public void action()
	{
		getBoard().changeMeaning();
	}
}
