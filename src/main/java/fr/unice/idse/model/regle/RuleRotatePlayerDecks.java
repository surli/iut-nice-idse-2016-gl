package fr.unice.idse.model.regle;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public class RuleRotatePlayerDecks extends EffectCard
{

	public RuleRotatePlayerDecks(Game game, Value value) 
	{
		this.game = game;
		this.isColorChangingCard = false;
		this.value = value;
	}
	
	public RuleRotatePlayerDecks( Value value) 
	{

		this.isColorChangingCard = false;
		this.value = value;
	}

	@Override
	public void action() {
		if(!getGame().getActualPlayer().getCards().isEmpty())
		{
			getGame().rotatePlayersDecks();
		}
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
