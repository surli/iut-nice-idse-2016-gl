package fr.unice.idse.model;

import java.util.ArrayList;
import java.util.Collections;

import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public class Deck {
	private ArrayList<Card> deck;
	
	
	/**
	 * Constructeur de la pioche
	 */
	public Deck(){
		deck = new ArrayList<Card>();
	}
	
	/**
	 * 	Getter de la pioche
	 * @return
	 */
	public ArrayList<Card> getDeck(){
		return deck;
	}
	
	/**
	 * Setter de la pioche
	 * @param deck
	 */
	public void setDeck(ArrayList<Card> deck){
		this.deck = deck;
	}
	
	/**
	 * Remplit la pioche avec toutes les cartes :
	 *  - 19 cartes de chaque couleur (2 pour chaque chiffre sauf pour le 0)
	 *  - 8 cartes "+2" (2 de chaque couleur)
	 *  - 8 cartes "Changement de sens" (2 de chaque couleur)
	 *  - 8 cartes "Passe ton tour" (2 de chaque couleur)
	 *  - 4 cartes "Joker"
	 *  - 4 cartes "+4"
	 */
	public void fillDeck() {
		
		deck.add(new Card(Value.Zero,Color.Blue));
		deck.add(new Card(Value.Zero,Color.Red));
		deck.add(new Card(Value.Zero,Color.Green));
		deck.add(new Card(Value.Zero,Color.Yellow));
		for(int i=0; i<2;i++){
			deck.add(new Card(Value.One,Color.Blue));
			deck.add(new Card(Value.One,Color.Red));
			deck.add(new Card(Value.One,Color.Green));
			deck.add(new Card(Value.One,Color.Yellow));
		}
		for(int i=0; i<2;i++){
			deck.add(new Card(Value.Two,Color.Blue));
			deck.add(new Card(Value.Two,Color.Red));
			deck.add(new Card(Value.Two,Color.Green));
			deck.add(new Card(Value.Two,Color.Yellow));
		}
		for(int i=0; i<2;i++){
			deck.add(new Card(Value.Three,Color.Blue));
			deck.add(new Card(Value.Three,Color.Red));
			deck.add(new Card(Value.Three,Color.Green));
			deck.add(new Card(Value.Three,Color.Yellow));
		}
		for(int i=0; i<2;i++){
			deck.add(new Card(Value.Four,Color.Blue));
			deck.add(new Card(Value.Four,Color.Red));
			deck.add(new Card(Value.Four,Color.Green));
			deck.add(new Card(Value.Four,Color.Yellow));
		}
		for(int i=0; i<2;i++){
			deck.add(new Card(Value.Five,Color.Blue));
			deck.add(new Card(Value.Five,Color.Red));
			deck.add(new Card(Value.Five,Color.Green));
			deck.add(new Card(Value.Five,Color.Yellow));
		}
		for(int i=0; i<2;i++){
			deck.add(new Card(Value.Six,Color.Blue));
			deck.add(new Card(Value.Six,Color.Red));
			deck.add(new Card(Value.Six,Color.Green));
			deck.add(new Card(Value.Six,Color.Yellow));
		}
		for(int i=0; i<2;i++){
			deck.add(new Card(Value.Seven,Color.Blue));
			deck.add(new Card(Value.Seven,Color.Red));
			deck.add(new Card(Value.Seven,Color.Green));
			deck.add(new Card(Value.Seven,Color.Yellow));
		}
		for(int i=0; i<2;i++){
			deck.add(new Card(Value.Eight,Color.Blue));
			deck.add(new Card(Value.Eight,Color.Red));
			deck.add(new Card(Value.Eight,Color.Green));
			deck.add(new Card(Value.Eight,Color.Yellow));
		}
		for(int i=0; i<2;i++){
			deck.add(new Card(Value.Nine,Color.Blue));
			deck.add(new Card(Value.Nine,Color.Red));
			deck.add(new Card(Value.Nine,Color.Green));
			deck.add(new Card(Value.Nine,Color.Yellow));
		}
		for(int i=0; i<2;i++){
			deck.add(new Card(Value.Skip,Color.Blue));
			deck.add(new Card(Value.Skip,Color.Red));
			deck.add(new Card(Value.Skip,Color.Green));
			deck.add(new Card(Value.Skip,Color.Yellow));
		}
		for(int i=0; i<2;i++){
			deck.add(new Card(Value.Reverse,Color.Blue));
			deck.add(new Card(Value.Reverse,Color.Red));
			deck.add(new Card(Value.Reverse,Color.Green));
			deck.add(new Card(Value.Reverse,Color.Yellow));
		}
		for(int i=0; i<2;i++){
			deck.add(new Card(Value.DrawTwo,Color.Blue));
			deck.add(new Card(Value.DrawTwo,Color.Red));
			deck.add(new Card(Value.DrawTwo,Color.Green));
			deck.add(new Card(Value.DrawTwo,Color.Yellow));
		}
		for(int i=0; i<4;i++){
			deck.add(new Card(Value.Wild,Color.Black));
		}
		for(int i=0; i<4;i++){
			deck.add(new Card(Value.DrawFour,Color.Black));
		}
	}
	
	/**
	 * Permet de vider la pioche
	 */
	public void clearDeck(){
		deck.clear();
	}
	
	/**
	 * Permet de mélanger la pioche
	 */
	public void shuffle(){
		Collections.shuffle(deck);
	}
	
	/**
	 * Permet de retirer une carte de la pioche
	 * @param c
	 */
	public void removeCard(Card c){
		deck.remove(c);
	}
	
	/**
	 * Retourne le nombre de cartes de la pioche
	 * @return
	 */
	public int countCards(){
		return deck.size();
	}
	
	/**
	 * Permet de vérifier si la pioche est vide
	 * @return
	 */
	public boolean isEmpty(){
		if(countCards()==0){
			return true;
		}
		return false;
	}
	
	/**
	 * Reforme la pioche avec les cartes du talon, en prenant la carte supérieure
	 * du talon pour créer le nouveau talon
	 * @param stack
	 */
	public void reassembleDeck(Stack stack){
		Card topCard = stack.topCard();
		this.deck = stack.getStack();
		stack.clearStack();
		stack.addCard(topCard);
		shuffle();
	}
	
	/**
	 * Retourne la carte supérieure de la pioche
	 * @return
	 */
	public Card topCard(){
		int size = deck.size();
		if(size != 0){
			return deck.get(size-1);
		}
		return null;
	}
	
	/**
	 * Permet de retirer la carte supérieure de la pioche
	 */
	public void removeTopCard(){
		deck.remove(deck.size()-1);
	}
	
	/**
	 * Initialise la pioche
	 */
	public void initDeck(){
		fillDeck();
		shuffle();
	}
	
	/**
	 * Permet de cloner la pioche
	 */
	public Object clone() {  
		Deck deck = new Deck(); 
	    deck.deck = (ArrayList<Card>) this.deck.clone();
	    return deck;
  	}

	@Override
	public String toString() {
		return "Deck "+ deck;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deck == null) ? 0 : deck.hashCode());
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
		Deck other = (Deck) obj;
		if (deck == null) {
			if (other.deck != null)
				return false;
		} else if (!deck.equals(other.deck))
			return false;
		return true;
	}
	
	
}
