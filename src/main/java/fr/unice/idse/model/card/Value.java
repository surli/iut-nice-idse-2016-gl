package fr.unice.idse.model.card;

public enum Value {
	Zero(0,0),
	One(1,1),
	Two(2,2),
	Three(3,3),
	Four(4,4),
	Five(5,5),
	Six(6,6),
	Seven(7,7),
	Eight(8,8),
	Nine(9,9),
	Skip(10,20),
	Reverse(11,20),
	DrawTwo(12,20),
	Wild(13,50),
	DrawFour(14,50);
	
	private int number;
	private int points;
	Value(int number,int points)
	{
		this.number=number;
		this.points=points;
	}
	
	public int getNumber()
	{
		return points;
	}
	
	public int getPoints()
	{
		return points;
	}
	
}
