package fr.unice.idse.model.Regle;
import fr.unice.idse.model.*;

public class RuleReverse extends EffectCard {
	
	public RuleReverse(Board board, int value, Color color)
	{
		super(board, value, color);
	}
	
	@Override
	public void action()
	{
		getBoard().changeMeaning();
	}
}
