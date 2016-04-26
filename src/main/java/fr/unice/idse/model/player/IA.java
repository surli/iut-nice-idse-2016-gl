package fr.unice.idse.model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.NumberCardByColor;
import fr.unice.idse.model.card.Value;

import java.util.ArrayList;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class IA extends Player {

    private Logger logger = LoggerFactory.getLogger(IA.class);

    // ------------------------------------------------------------------- difficulty
    /** La difficulté de l'IA. */
    protected int difficulty;

    /**
     * Getter de la difficulté de l'IA.
     * @return Card La difficulté de l'IA.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Setter de la difficulté de l'IA.
     * @param difficulty La nouvelle difficulté de l'IA.
     */
    public void setDifficulty(int difficulty) {
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
        return cards;
    }

    public abstract Color chooseColor(ArrayList<Card> mainIA);

    public abstract void changeColor(Card cardToPlay, ArrayList<Card> mainIA, Game game);

    public void playCard (Game game, Card cardToPlay, ArrayList<Card> mainIA, boolean turnPlay) {
        if(turnPlay){
            game.poseCard(cardToPlay);
            logger.info("Carte joué : " + cardToPlay);

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
