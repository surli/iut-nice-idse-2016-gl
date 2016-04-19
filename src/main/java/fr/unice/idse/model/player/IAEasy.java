package fr.unice.idse.model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;

import java.util.ArrayList;

public class IAEasy extends IA {

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
    public IAEasy(String name, String token, int difficulty) {
        super(name, token, difficulty);
    }


    public void thinking (Game game) {
        ArrayList<Card> mainIA = game.getActualPlayer().getCards();
        ArrayList<Card> playableCards = game.playableCards();
        System.out.println("Carte jouable : " + playableCards.toString());

        myCard = chooseCardToPlay(mainIA, playableCards, game);
        playCard(game, myCard, mainIA, turnPlay);
    }

    public Card chooseCardToPlay (ArrayList<Card> mainIA, ArrayList<Card> playableCards, Game game) {
         boolean turnPlay = false;

        int i = 0;
        Card myCard = null;

        while (i < mainIA.size() && !turnPlay) {
            myCard = mainIA.get(i);

            for (Card aCard : playableCards) {
                if (myCard == aCard) {
                    turnPlay = true;
                    break;
                }
            }
            i++;
        }
        return myCard;
    }

    @Override
    public Color chooseColor(ArrayList<Card> mainIA) {
        Color color = mainIA.get(0).getColor();

        return color;
    }

    public void changeColor(ArrayList<Card> mainIA, Game game) {
        game.getAlternative().getEffectCard(myCard).action(chooseColor(mainIA));
    }

    public void playCard (Game game, Card cardToPlay, ArrayList<Card> mainIA, boolean turnPlay) {
        if(turnPlay){
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
