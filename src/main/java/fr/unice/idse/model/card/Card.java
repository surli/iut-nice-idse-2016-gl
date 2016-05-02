package fr.unice.idse.model.card;

/**
 * Classe qui définit une carte de jeu du UNO, elle possède des comportements tels que
 * la comparaison des valeurs, des couleurs ...
 */
public class Card implements Comparable<Card>{
	private Value value;
	private Color color;
	
	// --- Constructeur
		public Card(Value value) {
			this.value=value;
			this.color=null;
		}
		
		public Card(Value value,Color color) {
			this.value=value;
			this.color=color;
		}
		
		// --- Getters
		public Value getValue() { return value; }
		public Color getColor() { return color; }

		// --- Setters
		public void setValue(Value value) { this.value = value; }
		public void setColor(Color color) { this.color = color; }

		// --- Methode sameColor
		public boolean sameColor(Card c) {
			if((this.color).equals(c.color))
				return true;
			return false;
		}
		
		// --- Methode sameValue
		public boolean sameValue(Card c) {
			if(this.value==c.value)
				return true;
			return false;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((color == null) ? 0 : color.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
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
			Card other = (Card) obj;
			if (color != other.color)
				return false;
			if (value != other.value)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Carte(" + value + " " + color + ")";
		}

		/**
		 * Sert à trier par ordre de couleur (Bleu->Jaune->Rouge->Vert->Noir)
		 * Puis par valeur croissante
		 */
		public int compareTo(Card obj) {
			int i;
			
			int colorSupport=this.getColor().getNumber();
		    int colorObj=obj.getColor().getNumber();
			int valueSupport=this.getValue().getNumber();
			int valueObj=obj.getValue().getNumber();
			
			
			i=Integer.compare(colorSupport, colorObj);
			
		    if (i != 0) return i;
		    else
		    {		  
		    	return Integer.compare(valueSupport, valueObj);
		    }
		}
		
		
	}

