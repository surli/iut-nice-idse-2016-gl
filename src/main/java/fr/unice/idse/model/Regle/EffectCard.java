package fr.unice.idse.model.Regle;

import fr.unice.idse.model.*;

public class EffectCard 
{
	private int value;
	private Color color;
	
	public EffectCard(int value, Color color)
	{
		this.value = value;
		this.color = color;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Pour savoir si la carte jouée correspond à la règle.
	 * @param card
	 * @return
	 */
	public boolean isEffect(Card card)
	{
		return card.getColor().equals(getColor()) && card.getValue() == getValue();
	}
	
	public void action()
	{
		
	}
}
