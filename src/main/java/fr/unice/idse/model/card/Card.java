package fr.unice.idse.model.card;

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

		@Override
		public int compareTo(Card arg0) {
			return 0;
		}
		
		
	}

