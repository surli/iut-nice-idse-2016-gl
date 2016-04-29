package fr.unice.idse.model;

import java.util.ArrayList;

import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;

public class Stack {
	private ArrayList<Card> stack;
	
	/**
	 * Constructeur du talon
	 */
	public Stack(){
		stack = new ArrayList<Card>();
	}
	
	/**
	 * Getter du talon
	 * @return
	 */
	public ArrayList<Card> getStack(){
		return stack;
	}
	
	/**
	 * Setter du talon
	 * @param stack
	 */
	public void setStack(ArrayList<Card> stack){
		this.stack = stack;
	}
	
	/**
	 * Permet de récupérer la carte supérieure du talon
	 * @return
	 */
	public Card topCard(){
		int size = stack.size();
		if(size != 0){
			return stack.get(size-1);
		}
		return null;
	}
	
	/**
	 * Permet de vider le talon
	 */
	public void clearStack(){
		stack.clear();
	}
	
	/**
	 * Permet d'ajouter une carte dans le talon
	 * @param c
	 */
	public void addCard(Card c){
		stack.add(c);
	}
	
	/**
	 * Initialise le talon en retirant la carte supérieure de la pioche
	 * et en la posant dans le talon
	 * @param deck
	 */
	public void initStack(Deck deck){
		Card deckTopCard = deck.topCard();
		addCard(deckTopCard);
		deck.removeCard(deckTopCard);
	}
	
	/**
	 * Permet de changer la couleur de la carte supérieure du talon
	 * @param color
	 */
	public void changeColor(Color color){
		topCard().setColor(color);
	}
	
	/**
	 * Retourne le nombre de cartes du talon
	 * @return
	 */
	public int countCards(){
		return stack.size();
	}
	
	/**
	 * Permet de vérifier si le talon est vide
	 * @return
	 */
	public boolean isEmpty(){
		if(countCards()==0){
			return true;
		}
		return false;
	}
	
	/**
	 * toString du talon
	 */
	public String toString() {
		String result="\nStack :\n\n";
		for(Card card : stack)
		{
			result+="- "+card+"\n";
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stack == null) ? 0 : stack.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stack other = (Stack) obj;
		if (stack == null) {
			if (other.stack != null)
				return false;
		} else if (!stack.equals(other.stack))
			return false;
		return true;
	}
	
	
}
