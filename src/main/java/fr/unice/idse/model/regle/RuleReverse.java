package fr.unice.idse.model.regle;
import fr.unice.idse.model.*;
import fr.unice.idse.model.card.*;

public class RuleReverse extends EffectCard {
	
	public RuleReverse(Game game, Value value)
	{
		this.game = game;
		this.isColorChangingCard = false;
		this.value = value;
	}

	@Override
	public void action()
	{
		getGame().changeMeaning();
	}

	@Override
	public void action(Color color) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void action(String playerName) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void effect()
	{
		// TODO Auto-generated method stub
	}
}
