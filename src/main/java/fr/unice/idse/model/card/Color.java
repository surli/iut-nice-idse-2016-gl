package fr.unice.idse.model.card;

/**
 * Enumération qui définit la couleur d'une carte de UNO
 */
public enum Color {
	Blue(0),
	Green(1),
	Red(2),
	Yellow(3),
	Black(4);
	
	private int number;
	
	Color(int number)
	{
		this.number=number;
	}
	
	public int getNumber()
	{
		return this.number;
	}
}