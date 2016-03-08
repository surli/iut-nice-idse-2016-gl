package fr.unice.idse.model;

import java.util.ArrayList;
import java.util.HashMap;

import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Value;
import fr.unice.idse.model.regle.*;

public class Alternative 
{
	private ArrayList<EffectCard> actions;
	private Board board;
	
	public Alternative(Board board, boolean defaut)
	{
		if(defaut)
		{
			this.board = board;
			actions = new ArrayList<EffectCard>();
			actions.add(new RuleChangeColor(board, Value.Wild));
			actions.add(new RuleReverse(board, Value.Reverse));
			actions.add(new RuleSkip(board, Value.Skip));
			actions.add(new RuleDrawTwo(board, Value.DrawTwo));
			actions.add(new RuleDrawFour(board, Value.DrawFour));
		}
		else
		{
			this.board = board;
			actions = new ArrayList<EffectCard>();
		}
	}
	
	public void addActionToCard(EffectCard action)
	{
		actions.add(action);
	}
	
	public EffectCard isEffectCardAfterPose(Card card)
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
	
	public EffectCard isEffectCardBeforePlay(Card card)
	{
		for (EffectCard rule : actions)
		{
			if (rule.isEffect(card) && rule.getEffect()) 
			{
				return rule;
			}
		}
		return null;
	}
}
