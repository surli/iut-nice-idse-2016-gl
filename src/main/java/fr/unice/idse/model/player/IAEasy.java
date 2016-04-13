package fr.unice.idse.model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;

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


    public void reflexion(Game game) {

        ArrayList<Card> mainIA = game.getActualPlayer().getCards();
        ArrayList<Card> playableCards = game.playableCards();
        System.out.println("Carte jouable : " + playableCards.toString());

        myCard = chooseCardToPlay(mainIA, playableCards);
        playCard(game, myCard, mainIA, turnPlay);
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
