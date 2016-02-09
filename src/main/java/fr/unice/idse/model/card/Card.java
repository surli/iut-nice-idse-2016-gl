package fr.unice.idse.model.card;

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
					result += "Zero "+color;
					break;
				case 1:
					result += "One "+color;
					break;
				case 2:
					result += "Two "+color;
					break;
				case 3:
					result += "Three "+color;
					break;
				case 4:
					result += "Four "+color;
					break;
				case 5:
					result += "Five "+color;
					break;
				case 6:
					result += "Six "+color;
					break;
				case 7:
					result += "Seven "+color;
					break;
				case 8:
					result += "Eight "+color;
					break;
				case 9:
					result += "Nine "+color;
					break;
				case 10:
					result += "Skip "+color;
					break;
				case 11:
					result += "Reverse "+color;
					break;
				case 12:
					result += "DrawTwo "+color;
					break;
				case 13:
					result += "Wild "+color;
					break;
				case 14:
					result += "DrawFour "+color;
					break;
				default:
					result = "test";
					break;
			}
			result += ")";
			return result;
		}
		
		@Override
		public int compareTo(Card obj) {
			int i;
			int couleurSupport=0,couleurObj=0;
			
			if(this.color.equals(Color.Blue))
				couleurSupport=1;
			if(this.color.equals(Color.Green))
				couleurSupport=2;
			if(this.color.equals(Color.Red))
				couleurSupport=3;
			if(this.color.equals(Color.Yellow))
				couleurSupport=4;
			
			if(((Card)obj).color.equals(Color.Blue))
				couleurObj=1;
			if(((Card)obj).color.equals(Color.Green))
				couleurObj=2;
			if(((Card)obj).color.equals(Color.Red))
				couleurObj=3;
			if(((Card)obj).color.equals(Color.Yellow))
				couleurObj=4;
			
			i=Integer.compare(couleurSupport, couleurObj);
			
		    if (i != 0) return i;

		    return Integer.compare(value, ((Card)obj).value);
		}
	}

