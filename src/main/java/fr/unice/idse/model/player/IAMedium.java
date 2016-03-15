package fr.unice.idse.model.player;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;

import java.util.ArrayList;

public class IAMedium extends IA {

    public IAMedium(String name, String token , int difficulty){
        super(name, token , difficulty);
    }
	
    public Color chooseColor(ArrayList<Card> mainIA)
	{
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
				countB +=1;
			}

			if(aCard.getColor()== Color.Green)
			{
				countG +=1;
			}

			if(aCard.getColor()== Color.Red)
			{
				countR +=1;
			}

			if(aCard.getColor()== Color.Yellow)
			{
				countY +=1;
			}
		}

		if(bestCount< countR)
		{
            bestCount = countR;
			bestColor = Color.Red;
		}

		if(bestCount < countG)
		{
            bestCount = countG;
			bestColor = Color.Green;
		}

		if(bestCount < countY)
		{
            bestCount = countY;
			bestColor = Color.Yellow;
		}


		return bestColor;
	}

}
