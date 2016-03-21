package fr.unice.idse.model.player;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.NumberCardByColor;
import fr.unice.idse.model.card.Value;

import java.util.ArrayList;
import java.util.Collections;

public class IAMedium extends IA {

    public IAMedium(String name, String token, int difficulty) {
        super(name, token, difficulty);
    }

    public void reflexion(Board board) {

        ArrayList<Card> mainIA = board.getActualPlayer().getCards();
        ArrayList<Card> playableCards = board.playableCards();
        System.out.println("Carte jouable : " + playableCards.toString());


        ArrayList<NumberCardByColor> cards = new ArrayList<NumberCardByColor>();

        // REALISER LES COMPTEURS

        int countB = 0;
        int countG = 0;
        int countR = 0;
        int countY = 0;
        int bestCount = countB;
        Color bestColor = Color.Blue;
        int i = 0;

        for (Card aCard : mainIA) {
            if (aCard.getColor() == Color.Blue) {
                countB += 1;
            }
            else if (aCard.getColor() == Color.Green) {
                countG += 1;
            }
            else if (aCard.getColor() == Color.Red) {
                countR += 1;
            }
            else if (aCard.getColor() == Color.Yellow) {
                countY += 1;
            }
        }

        if (bestCount < countR) {
            bestCount = countR;
            bestColor = Color.Red;
        }

        if (bestCount < countG) {
            bestCount = countG;
            bestColor = Color.Green;
        }

        if (bestCount < countY) {
            bestCount = countY;
            bestColor = Color.Yellow;
        }

        // FIN COMPTAGE

        cards.add(new NumberCardByColor(Color.Blue, countB));
        cards.add(new NumberCardByColor(Color.Green, countG));
        cards.add(new NumberCardByColor(Color.Red, countR));
        cards.add(new NumberCardByColor(Color.Yellow, countY));

        Collections.sort(cards);

        System.out.println("cards IA = "+cards);

        int nbCard = 0;
        Card myCard;
        boolean turnPlay = false;

        while(!turnPlay && nbCard < cards.get(0).getNumber()) {
            myCard = mainIA.get(i);

            for (Card aCard : playableCards) {
                if (myCard == aCard) {
                    board.poseCard(myCard);
                    System.out.println("Carte joué : " + myCard);

                    if (board.getAlternative().getEffectCard(myCard).isColorChangingCard()) {
                        board.getAlternative().getEffectCard(myCard).changeColor(chooseColor(bestColor));
                        board.getAlternative().getEffectCard(myCard).action();
                    }
                    turnPlay = true;
                    break;
                }
            }
            nbCard++;
        }
        while(!turnPlay && nbCard < cards.get(1).getNumber()) {
            myCard = mainIA.get(i);

            for (Card aCard : playableCards) {
                if (myCard == aCard) {
                    board.poseCard(myCard);
                    System.out.println("Carte joué : " + myCard);

                    if (board.getAlternative().getEffectCard(myCard).isColorChangingCard()) {
                        board.getAlternative().getEffectCard(myCard).changeColor(chooseColor(bestColor));
                        board.getAlternative().getEffectCard(myCard).action();
                    }
                    turnPlay = true;
                    break;
                }
            }
            nbCard++;
        }
        while(!turnPlay && nbCard < cards.get(2).getNumber()) {
            myCard = mainIA.get(i);

            for (Card aCard : playableCards) {
                if (myCard == aCard) {
                    board.poseCard(myCard);
                    System.out.println("Carte joué : " + myCard);

                    if (board.getAlternative().getEffectCard(myCard).isColorChangingCard()) {
                        board.getAlternative().getEffectCard(myCard).changeColor(chooseColor(bestColor));
                        board.getAlternative().getEffectCard(myCard).action();
                    }
                    turnPlay = true;
                    break;
                }
            }
            nbCard++;
        }
        while(!turnPlay && nbCard < cards.get(3).getNumber()) {
            myCard = mainIA.get(i);

            for (Card aCard : playableCards) {
                if (myCard == aCard) {
                    board.poseCard(myCard);
                    System.out.println("Carte joué : " + myCard);

                    if (board.getAlternative().getEffectCard(myCard).isColorChangingCard()) {
                        board.getAlternative().getEffectCard(myCard).changeColor(chooseColor(bestColor));
                        board.getAlternative().getEffectCard(myCard).action();
                    }
                    turnPlay = true;
                    break;
                }
            }
            nbCard++;
        }

        if (!turnPlay) {
            board.drawCard();
        }
    }

    public Color chooseColor(Color bestColor) {
        Color color = bestColor;

        return color;
    }
}