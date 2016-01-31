package fr.unice.idse.model;

import java.util.ArrayList;
import java.util.Collections;

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
		for(int i=0;i<=14;i++)
		{
			if(i==0){
				// Création d'une carte 0 par couleur
				deck.add(new Card(i,Color.Blue));
				deck.add(new Card(i,Color.Red));
				deck.add(new Card(i,Color.Yellow));
				deck.add(new Card(i,Color.Green));
			}
			else if (i==13 || i==14){
				// Création de 4 cartes joker et +4
				deck.add(new Card(i,Color.Black));
				deck.add(new Card(i,Color.Black));
				deck.add(new Card(i,Color.Black));
				deck.add(new Card(i,Color.Black));
			}
			else{
				// Création de 2 cartes de chaque couleur
				deck.add(new Card(i,Color.Blue));
				deck.add(new Card(i,Color.Blue));
				deck.add(new Card(i,Color.Red));
				deck.add(new Card(i,Color.Red));
				deck.add(new Card(i,Color.Yellow));
				deck.add(new Card(i,Color.Yellow));
				deck.add(new Card(i,Color.Green));
				deck.add(new Card(i,Color.Green));
			}
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
		Card topCard = topCard();
		deck.remove(topCard);
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
}
