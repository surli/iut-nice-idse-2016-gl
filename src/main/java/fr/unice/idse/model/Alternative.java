package fr.unice.idse.model;

import java.util.ArrayList;
import java.util.HashMap;

import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Value;
import fr.unice.idse.model.regle.*;

public class Alternative 
{
	private ArrayList<EffectCard> actions;
	private Game game;
	
	public Alternative(Game game, boolean defaut)
	{
		if(defaut)
		{
			this.game = game;
			actions = new ArrayList<EffectCard>();
			actions.add(new RuleChangeColor(game, Value.Wild));
			actions.add(new RuleReverse(game, Value.Reverse));
			actions.add(new RuleSkip(game, Value.Skip));
			actions.add(new RuleDrawTwo(game, Value.DrawTwo));
			actions.add(new RuleDrawFour(game, Value.DrawFour));
		}
		else
		{
			this.game = game;
			actions = new ArrayList<EffectCard>();
		}
	}
	
	public void addActionToCard(EffectCard action)
	{
		actions.add(action);
	}
	
	public EffectCard getEffectCard(Card card)
	{
		for (EffectCard rule : actions)
		{
			if (rule.isEffect(card))
			{
				return rule;
			}
		}
		return null;
	}
}
