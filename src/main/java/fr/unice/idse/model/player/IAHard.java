package fr.unice.idse.model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.NumberCardByColor;
import fr.unice.idse.model.card.Value;

import java.util.ArrayList;
import java.util.Collections;

public class IAHard extends IA {

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
    public IAHard(String name, String token, int difficulty) {
        super(name, token, difficulty);
    }

    static Color bestColor = null;
    static Card cardContre;

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
        int i = 0;

        turnPlay = checkNextPlayer(game);

        while (!turnPlay && nbCard < cards.get(3).getNumber()) {
            myCard = mainIA.get(i);
            turnPlay = testCardPlayable(playableCards, myCard);
            nbCard++;
        }
        while (!turnPlay && nbCard < cards.get(2).getNumber()) {
            myCard = mainIA.get(i);
            turnPlay = testCardPlayable(playableCards, myCard);
            nbCard++;
        }
        while (!turnPlay && nbCard < cards.get(1).getNumber()) {
            myCard = mainIA.get(i);
            turnPlay = testCardPlayable(playableCards, myCard);
            nbCard++;
        }
        while (!turnPlay && nbCard < cards.get(0).getNumber()) {
            myCard = mainIA.get(i);
            turnPlay = testCardPlayable(playableCards, myCard);
            nbCard++;
        }

        return myCard;
    }

    public boolean testCardPlayable(ArrayList<Card> playableCards, Card myCard) {
        System.out.println("Appel de la fonction testCardPlayableAndPlay ");
        for (Card aCard : playableCards) {
            if (myCard == aCard) {
                turnPlay = true;
                break;
            }
        }
        return turnPlay;
    }


    public boolean checkNextPlayer (Game game) {
        int nextPlayer = game.getNextPlayer();
        ArrayList<Card> mainIA = game.getActualPlayer().getCards();

        int nbCardMainNextPlayer = game.getPlayers().get(nextPlayer).getCards().size();

        if(nbCardMainNextPlayer == 1) {

            if(searchColorCard(mainIA, Color.Black)) {   // ESSAYER DE JOUER CHANGE COLOUR OU +4
                turnPlay = true;
            }/* Bug - Il faut verifie la valeur + la couleur pour jouer cette carte
            else if (searchValueCard(mainIA, Value.DrawTwo)) {
            	game.poseCard(cardContre);
                turnPlay = true;
            }*/
            else if (searchValueCard(mainIA, game.getStack().topCard().getValue())) {  //MEME NOMBRE QUE LA DERNIERE CARTE POSE
                turnPlay = true;
            }
        }

        return turnPlay;
    }


    /**
     * Retourne true si le joueur a des cartes de couleur precise dans sa main en paramettre sinon retourne false
     */
    public boolean searchColorCard (ArrayList<Card> main, Color colorCard) {
        boolean colorExist = false;

        for (Card aCard : main) {
            if(aCard.getColor() == colorCard) {
                colorExist = true;
                cardContre = aCard;
            }
        }

        return colorExist;
    }

    /**
     * Retourne true si le joueur a des cartes de valeur precise dans sa main en paramettre sinon retourne false
     */
    public boolean searchValueCard (ArrayList<Card> main, Value valueCard) {
        boolean valueExist = false;
        for (Card aCard : main) {
            if(aCard.getValue() == valueCard) {
                valueExist = true;
                cardContre = aCard;
            }
        }

        return valueExist;
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
