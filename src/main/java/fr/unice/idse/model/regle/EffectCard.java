package fr.unice.idse.model.regle;

import fr.unice.idse.model.*;
import fr.unice.idse.model.card.*;

public class EffectCard
{
	private Value value;
	private Board board;

	private boolean effect;
	
	public EffectCard(Board board, Value value)
	{
		this.value = value;
		this.board = board;
		effect = false;
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
	
	/**
	 * Pour savoir si on a affaire à une regle contrable
	 * @return
	 */
	public boolean getEffect()
	{
		return effect;
	}
	
	public void setEffect()
	{
		effect = !effect;
	}
	
	/**
	 * A apellé pour change couleur et pioche 4 cartes
	 * @param color
	 */
	public void changeColor(Color color)
	{
		if(board.getStack().topCard().getColor().equals(Color.Black))
		{
			board.changeColor(color);
		}
	}
	
	//Methode à override pour les effets immediat
	public void action()
	{

	}
	
	//Methode à override pour les effets contrable
	public void effect()
	{
		
	}
}
