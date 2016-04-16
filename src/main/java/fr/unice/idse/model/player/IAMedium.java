package fr.unice.idse.model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.NumberCardByColor;

import java.util.ArrayList;
import java.util.Collections;

public class IAMedium extends IA {

    //CONSTANTE
    private static Card myCard;
    private static boolean turnPlay = false;


    //GETTER
    public Card getMyCard() {
        return myCard;
    }

    public boolean getTurnPlay () {
        return turnPlay;
    }


    //SETTER
    public void setMyCard(Card myCard) {
        this.myCard = myCard;
    }


    //CONSTRUCTEUR
    public IAMedium(String name, String token, int difficulty) {
        super(name, token, difficulty);
    }

    public void reflexion(Game game) {

        ArrayList<Card> mainIA = game.getActualPlayer().getCards();
        ArrayList<Card> playableCards = game.playableCards();
        System.out.println("Carte jouable : " + playableCards.toString());

        ArrayList<NumberCardByColor> cards = calculateNumberCardByColor(mainIA);

        int nbCard = 0;

        while (!turnPlay && nbCard < cards.get(0).getNumber()) {
            myCard = mainIA.get(nbCard);
            turnPlay = testCardPlayableAndPlay(game, playableCards, myCard, cards.get(0).getColor(), turnPlay);
            nbCard++;
        }
        while (!turnPlay && nbCard < cards.get(1).getNumber()) {
            myCard = mainIA.get(nbCard);
            turnPlay = testCardPlayableAndPlay(game, playableCards, myCard, cards.get(1).getColor(), turnPlay);
            nbCard++;
        }
        while (!turnPlay && nbCard < cards.get(2).getNumber()) {
            myCard = mainIA.get(nbCard);
            turnPlay = testCardPlayableAndPlay(game, playableCards, myCard, cards.get(2).getColor(), turnPlay);
            nbCard++;
        }
        while (!turnPlay && nbCard < cards.get(3).getNumber()) {
            myCard = mainIA.get(nbCard);
            turnPlay = testCardPlayableAndPlay(game, playableCards, myCard, cards.get(3).getColor(), turnPlay);
            nbCard++;
        }

        if (!turnPlay) {
        	game.drawCard();
        }
    }

    public Color chooseColor(Color bestColor) {
        Color color = bestColor;

        return color;
    }

    // TODO CONTINUER A REFACTORER LA SUITE
    public boolean testCardPlayableAndPlay(Game game, ArrayList<Card> playableCards, Card myCard, Color bestColor, boolean turnPlay) {
        System.out.println("Appel de la fonction testCardPlayableAndPlay ");
        for (Card aCard : playableCards) {
            if (myCard == aCard) {
            	game.poseCard(myCard);
                System.out.println("Carte joué : " + myCard);
                if (game.getAlternative().getEffectCard(myCard).isColorChangingCard()) {
                	game.getAlternative().getEffectCard(myCard).changeColor(chooseColor(bestColor));
                	game.getAlternative().getEffectCard(myCard).action();
                }
                turnPlay = true;
                break;
            }
        }
        return turnPlay;
    }

    @Override
    public Card chooseCardToPlay (ArrayList<Card> mainIA, ArrayList<Card> playableCards) {
        System.out.println("Carte jouable : " + playableCards.toString());
        int i = 0;

        while (i < mainIA.size() && !turnPlay) {
            myCard = mainIA.get(i);

            if (playableCards.contains(myCard)) {
                turnPlay = true;
            }
            i++;
        }

        return myCard;
    }
}