package fr.unice.idse.model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.NumberCardByColor;
import fr.unice.idse.model.card.Value;

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

    public IA(String name, String token) {
        super(name, token);
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

    public Color chooseColor(ArrayList<Card> mainIA) {
        Color color = mainIA.get(0).getColor();

        return color;
    }

    public void changeColor(Card cardToPlay, ArrayList<Card> mainIA, Game game) {
        game.getAlternative().getEffectCard(cardToPlay).action(chooseColor(mainIA));
    }

    public void playCard (Game game, Card cardToPlay, ArrayList<Card> mainIA, boolean turnPlay) {
        if(turnPlay){
            game.poseCard(cardToPlay);
            System.out.println("Carte joué : " + cardToPlay);

            if (game.getAlternative().getEffectCard(cardToPlay) != null && game.getAlternative().getEffectCard(cardToPlay).isColorChangingCard()) {
                changeColor(cardToPlay, mainIA, game);
            }
        }
        else {
            game.drawCard();
        }
    }


    /**
     * Retourne true si le joueur a des cartes de couleur precise dans sa main en paramettre sinon retourne false
     */
    public boolean searchColorCard (ArrayList<Card> main, Color colorCard) {
        boolean colorExist = false;

        for (Card aCard : main) {
            if(aCard.getColor() == colorCard) {
                colorExist = true;
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
            }
        }

        return valueExist;
    }
}
