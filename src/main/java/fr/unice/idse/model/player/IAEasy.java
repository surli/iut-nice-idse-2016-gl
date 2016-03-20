package fr.unice.idse.model.player;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;

import java.util.ArrayList;

public class IAEasy extends IA {

    public IAEasy(String name, String token, int difficulty) {
        super(name, token, difficulty);
    }

    public static void reflexion(Board board) {

        ArrayList<Card> mainIA = board.getActualPlayer().getCards();
        ArrayList<Card> playableCards = board.playableCards();
        System.out.println("Carte jouable : " + playableCards.toString());
        boolean turnPlay = false;
        int i = 0;
        Card myCard;

        while (i < mainIA.size() && !turnPlay) {
            myCard = mainIA.get(i);

            for (Card aCard : playableCards) {
                if (myCard == aCard) {
                    board.poseCard(myCard);
                    System.out.println("Carte joué : " + myCard);

                    if (myCard.getColor() == Color.Black) {
                        board.getAlternative().getEffectCard(myCard).changeColor(chooseColor(mainIA));
                        board.getAlternative().getEffectCard(myCard).action();
                    }
                    turnPlay = true;
                }
            }
            i++;
        }

        if (!turnPlay) {
            board.drawCard();
        }
    }

    public static Color chooseColor(ArrayList<Card> mainIA) {
        Color color = mainIA.get(0).getColor();

        return color;
    }


}
