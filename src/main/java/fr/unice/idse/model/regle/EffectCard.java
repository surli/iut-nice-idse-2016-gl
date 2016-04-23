package fr.unice.idse.model.regle;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public abstract class EffectCard
{
	protected Value value;
	protected Game game;
	protected boolean isColorChangingCard;

	public Value getValue()
	{
		return value;
	}

	public Game getGame()
	{
		return game;
	}

	public boolean isColorChangingCard() {
		return isColorChangingCard;
	}

	/**
	 * Pour savoir si la carte jouée correspond à la règle.
	 * @param card
	 * @return
	 */
	public boolean isEffect(Card card)
	{
		return card.getValue() == value;
	}
	
	/**
	 * Pour savoir si on a affaire à une regle contrable
	 * @return
	 */
	public boolean getEffect()
	{
		return game.getCptDrawCard() > 1;
	}
	
	
	/***
	 * Methode à override pour l'échange des jeux de cartes du joueur actuel avec un autre
	 * @param : joueur souhaité pour réaliser l'échange.
	 */
	public boolean tradeDecks(String playerName)
	{
		return false;
	}
	
	/***
	 * Enclenche l'effet d'une carte au moment de la pose
	 */
	public abstract void action();
	
	/***
	 * Enclenche l'effet d'une carte au moment de la pose
	 * @param color
	 */
	public abstract void action(Color color);
	
	/***
	 * Enclenche l'effet d'une carte au moment de la pose
	 * @param playerName
	 */
	public abstract void action(String playerName);
	
	/***
	 * Enclenche l'effet d'une carte après la pose
	 */
	public abstract void effect();
}
