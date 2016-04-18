package fr.unice.idse.model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.NumberCardByColor;
import fr.unice.idse.model.card.Value;

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

    static Color bestColor = null;

    public void thinking (Game game) {
        ArrayList<Card> mainIA = game.getActualPlayer().getCards();
        ArrayList<Card> playableCards = game.playableCards();
        System.out.println("Carte jouable : " + playableCards.toString());

        myCard = chooseCardToPlay(mainIA, playableCards, game);
        playCard(game, myCard, mainIA, turnPlay);
    }

    public Card chooseCardToPlay (ArrayList<Card> mainIA, ArrayList<Card> playableCards, Game game) {

        ArrayList<NumberCardByColor> cards = calculateNumberCardByColor(mainIA);

        int nbCard = 0;
        Card myCard = null;

        while (!turnPlay && nbCard < cards.get(3).getNumber()) {
            bestColor = cards.get(3).getColor();
            myCard = mainIA.get(nbCard);
            turnPlay = testCardPlayable(game, playableCards, myCard, cards.get(0).getColor(), turnPlay);
            nbCard++;
        }
        while (!turnPlay && nbCard < cards.get(2).getNumber()) {
            myCard = mainIA.get(nbCard);
            turnPlay = testCardPlayable(game, playableCards, myCard, cards.get(1).getColor(), turnPlay);
            nbCard++;
        }
        while (!turnPlay && nbCard < cards.get(1).getNumber()) {
            myCard = mainIA.get(nbCard);
            turnPlay = testCardPlayable(game, playableCards, myCard, cards.get(2).getColor(), turnPlay);
            nbCard++;
        }
        while (!turnPlay && nbCard < cards.get(0).getNumber()) {
            myCard = mainIA.get(nbCard);
            turnPlay = testCardPlayable(game, playableCards, myCard, cards.get(3).getColor(), turnPlay);
            nbCard++;
        }

        return myCard;
    }

    public boolean testCardPlayable(Game game, ArrayList<Card> playableCards, Card myCard, Color bestColor, boolean turnPlay) {
        System.out.println("Appel de la fonction testCardPlayableAndPlay ");
        for (Card aCard : playableCards) {
            if (myCard == aCard) {
                turnPlay = true;
                break;
            }
        }
        return turnPlay;
    }

    public Color chooseColor() {
        return bestColor;
    }

    public void changeColor(ArrayList<Card> mainIA, Game game) {
        game.getAlternative().getEffectCard(myCard).action(chooseColor());
    }

    public void playCard (Game game, Card cardToPlay, ArrayList<Card> mainIA, boolean turnPlay) {
        if(turnPlay) {
            game.poseCard(cardToPlay);
            System.out.println("Carte joué : " + cardToPlay);

            if (game.getAlternative().getEffectCard(cardToPlay).isColorChangingCard()) {
                changeColor(mainIA, game);
            }
        }
        else {
            game.drawCard();
        }
    }
}