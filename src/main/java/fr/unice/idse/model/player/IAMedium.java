package fr.unice.idse.model.player;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;

import java.util.ArrayList;

public class IAMedium extends IA {

    public IAMedium(String name, String token){
        super(name, token);
    }
    
    public Color chooseColor(Board board)
	{
		ArrayList<Card> mainIA = board.getActualPlayer().getCards();
		
		int countB = 0;
		int countG = 0;
		int countR = 0;
		int countY = 0;
		int bestCount = countB;
		Color bestColor = Color.Blue;
		
		for(Card aCard : mainIA)
		{
			if(aCard.getColor()== Color.Blue)
			{
				countB = countB +=1;
			}
			
			if(aCard.getColor()== Color.Green)
			{
				countG = countG +=1;
			}
			
			if(aCard.getColor()== Color.Red)
			{
				countR = countR +=1;
			}
			
			if(aCard.getColor()== Color.Yellow)
			{
				countY = countY +=1;
			}
		}
		
		if(bestCount< countR)
		{
			countR = bestCount; 
			bestColor = Color.Red;
		}
	
		if(bestCount < countG)
		{
			countG = bestCount;
			bestColor = Color.Green;
		}
		
		if(bestCount < countY)
		{
			countY = bestCount;
			bestColor = Color.Yellow;
		}
		
		
		return bestColor;
	}


}
