package fr.unice.idse.model;

public class Card implements Comparable<Card>{
	private int value;
	private Color color;
	
	// --- Constructeur
		public Card(int value) {
			this.value=value;
		}
		
		public Card(int value,Color color) {
			this.value=value;
			this.color=color;
		}
		
		// --- Getters
		public int getValue() { return value; }
		public Color getColor() { return color; }

		// --- Setters
		public void setValue(int value) { this.value = value; }
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
		
		// --- Methode higherValue
		public boolean higherValue(Card c) {
			if(this.value>c.value)
				return true;
			return false;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((color == null) ? 0 : color.hashCode());
			result = prime * result + value;
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
			if (color == null) {
				if (other.color != null)
					return false;
			} else if (!color.equals(other.color))
				return false;
			if (value != other.value)
				return false;
			return true;
		}

		@Override
		public String toString() {
			String result="Carte(";
			switch(value) {
				case 0:
					result += "Zero"+color;
				case 1:
					result += "One"+color;
				case 2:
					result += "Two"+color;
				case 3:
					result += "Three"+color;
				case 4:
					result += "Four"+color;
				case 5:
					result += "Five"+color;
				case 6:
					result += "Six"+color;
				case 7:
					result += "Seven"+color;
				case 8:
					result += "Eight"+color;
				case 9:
					result += "Nine"+color;
				case 10:
					result += "Skip"+color;
				case 11:
					result += "Reverse"+color;
				case 12:
					result += "Wild"+color;
				case 13:
					result += "DrawFour"+color;
				
				default:
					result = "";
			}
			return result;
		}
		
		@Override
		public int compareTo(Card obj) {
			int i;
			int couleurSupport=0,couleurObj=0;
			
			if(this.color.equals("carreau"))
				couleurSupport=1;
			if(this.color.equals("trefle"))
				couleurSupport=2;
			if(this.color.equals("coeur"))
				couleurSupport=3;
			if(this.color.equals("pique"))
				couleurSupport=4;
			
			if(((Card)obj).color.equals("carreau"))
				couleurObj=1;
			if(((Card)obj).color.equals("trefle"))
				couleurObj=2;
			if(((Card)obj).color.equals("coeur"))
				couleurObj=3;
			if(((Card)obj).color.equals("pique"))
				couleurObj=4;
			
			i=Integer.compare(couleurSupport, couleurObj);
			
		    if (i != 0) return i;

		    return Integer.compare(value, ((Card)obj).value);
		}
	}

