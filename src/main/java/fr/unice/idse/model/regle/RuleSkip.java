package fr.unice.idse.model.regle;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.*;

public class RuleSkip extends EffectCard{

	public RuleSkip(Game board, Value value) 
	{
		this.game = board;
		this.isColorChangingCard = false;
		this.value = value;
	}
	
	public RuleSkip( Value value) 
	{
		this.isColorChangingCard = false;
		this.value = value;
	}

	@Override
	public void action() 
	{
		getGame().nextPlayer();
	}

	@Override
	public void action(Color color)
	{
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
