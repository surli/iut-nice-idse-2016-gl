package fr.unice.idse.model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.NumberCardByColor;

import java.util.ArrayList;
import java.util.Collections;

public abstract class IA extends Player {

    //CONSTANTE
    private int difficulty;

    //GETTER
    public int getDifficulty() {
        return difficulty;
    }

    //SETTER
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }


    public IA(String name, String token, int difficulty) {
        super(name, token);
        this.difficulty = difficulty;
    }

    public Card chooseCardToPlay (ArrayList<Card> mainIA, ArrayList<Card> playableCards) {
        Card chooseCard = null;

        return chooseCard;
    }

    public void playCard (Game game, Card cardToPlay, ArrayList<Card> mainIA, Boolean turnPlay) {
        if(turnPlay) {
        	game.poseCard(cardToPlay);
            System.out.println("Carte joué : " + cardToPlay);

            if (game.getAlternative().getEffectCard(cardToPlay).isColorChangingCard()) {
            	game.getAlternative().getEffectCard(cardToPlay).changeColor(chooseColor(mainIA));
            	game.getAlternative().getEffectCard(cardToPlay).action();
            }
        }
        else {
        	game.drawCard();
        }
    }

    public Color chooseColor(ArrayList<Card> mainIA) {
        Color colorChoose = mainIA.get(0).getColor();

        return colorChoose;
    }

    public ArrayList<NumberCardByColor> calculateNumberCardByColor (ArrayList<Card> mainIA) {

        ArrayList<NumberCardByColor> cards = new ArrayList<NumberCardByColor>();

        int countB = 0;
        int countG = 0;
        int countR = 0;
        int countY = 0;

        for (Card aCard : mainIA) {
            if (aCard.getColor() == Color.Blue) {
                countB += 1;
            } else if (aCard.getColor() == Color.Green) {
                countG += 1;
            } else if (aCard.getColor() == Color.Red) {
                countR += 1;
            } else if (aCard.getColor() == Color.Yellow) {
                countY += 1;
            }
        }

        cards.add(new NumberCardByColor(Color.Blue, countB));
        cards.add(new NumberCardByColor(Color.Green, countG));
        cards.add(new NumberCardByColor(Color.Red, countR));
        cards.add(new NumberCardByColor(Color.Yellow, countY));

        Collections.sort(cards);
        System.out.println("cards IA = " + cards);

        return cards;
    }

}
