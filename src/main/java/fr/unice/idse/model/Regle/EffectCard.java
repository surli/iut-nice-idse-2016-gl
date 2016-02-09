package fr.unice.idse.model.regle;

import fr.unice.idse.model.*;
import fr.unice.idse.model.card.*;

public class EffectCard 
{
	private Value value;
	private Board board;
	
	public EffectCard(Board board, Value value)
	{
		this.value = value;
		this.board = board;
	}
	
	public Value getValue()
	{
		return value;
	}
	
	public Board getBoard()
	{
		return board;
	}
	
	/**
	 * Pour savoir si la carte jouée correspond à la règle.
	 * @param card
	 * @return
	 */
	public boolean isEffect(Card card)
	{
		return card.getValue() == getValue();	
	}
	
	//Methode à override
	public void action()
	{
		
	}
}
