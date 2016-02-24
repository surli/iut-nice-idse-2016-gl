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

		/**
		 * Sert à trier par ordre de couleur (Bleu->Jaune->Rouge->Vert->Noir)
		 * Puis par valeur croissante
		 */
		public int compareTo(Card obj) {
			int i;
			int colorSupport=0,colorrObj=0;
			int valueSupport=0,valueObj=0;
			if(this.color.equals(Color.Blue))
				colorSupport=0;
			if(this.color.equals(Color.Yellow))
				colorSupport=1;
			if(this.color.equals(Color.Red))
				colorSupport=2;
			if(this.color.equals(Color.Green))
				colorSupport=3;
			if(this.color.equals(Color.Black))
				colorSupport=4;
			
			if(obj.color.equals(Color.Blue))
				colorrObj=0;
			if(obj.color.equals(Color.Yellow))
				colorrObj=1;
			if(obj.color.equals(Color.Red))
				colorrObj=2;
			if(obj.color.equals(Color.Green))
				colorrObj=3;
			if(obj.color.equals(Color.Black))
				colorrObj=4;
			
			i=Integer.compare(colorSupport, colorrObj);
			
		    if (i != 0) return i;
		    else
		    {
		    	if(this.value.equals(Value.Zero))
		    		valueSupport=0;
		    	if(this.value.equals(Value.One))
		    		valueSupport=1;
		    	if(this.value.equals(Value.Two))
		    		valueSupport=2;
		    	if(this.value.equals(Value.Three))
		    		valueSupport=3;
		    	if(this.value.equals(Value.Four))
		    		valueSupport=4;
		    	if(this.value.equals(Value.Five))
		    		valueSupport=5;
		    	if(this.value.equals(Value.Six))
		    		valueSupport=6;
		    	if(this.value.equals(Value.Seven))
		    		valueSupport=7;
		    	if(this.value.equals(Value.Eight))
		    		valueSupport=8;
		    	if(this.value.equals(Value.Nine))
		    		valueSupport=9;
		    	if(this.value.equals(Value.Skip))
		    		valueSupport=10;
		    	if(this.value.equals(Value.Reverse))
		    		valueSupport=11;
		    	if(this.value.equals(Value.DrawTwo))
		    		valueSupport=12;
		    	if(this.value.equals(Value.Wild))
		    		valueSupport=13;
		    	if(this.value.equals(Value.DrawFour))
		    		valueSupport=14;
				
		    	if(obj.value.equals(Value.Zero))
		    		valueObj=0;
		    	if(obj.value.equals(Value.One))
		    		valueObj=1;
		    	if(obj.value.equals(Value.Two))
		    		valueObj=2;
		    	if(obj.value.equals(Value.Three))
		    		valueObj=3;
		    	if(obj.value.equals(Value.Four))
		    		valueObj=4;
		    	if(obj.value.equals(Value.Five))
		    		valueObj=5;
		    	if(obj.value.equals(Value.Six))
		    		valueObj=6;
		    	if(obj.value.equals(Value.Seven))
		    		valueObj=7;
		    	if(obj.value.equals(Value.Eight))
		    		valueObj=8;
		    	if(obj.value.equals(Value.Nine))
		    		valueObj=9;
		    	if(obj.value.equals(Value.Skip))
		    		valueObj=10;
		    	if(obj.value.equals(Value.Reverse))
		    		valueObj=11;
		    	if(obj.value.equals(Value.DrawTwo))
		    		valueObj=12;
		    	if(obj.value.equals(Value.Wild))
		    		valueObj=13;
		    	if(obj.value.equals(Value.DrawFour))
		    		valueObj=14;
		    }
		    return Integer.compare(valueSupport, valueObj);
		}
		
		
	}

