package fr.unice.idse.model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IAEasy extends IA {
	
    private Logger logger = LoggerFactory.getLogger(IAEasy.class);

    // ------------------------------------------------------------------- myCard
    /** La carte qui sera joué. */
    protected Card myCard;

    /**
     * Getter de la carte qui sera joué.
     * @return Card La carte qui sera joué.
     */
    public Card getMyCard() {
        return this.myCard;
    }

    /**
     * Setter de la carte qui sera joué.
     * @param myCard La nouvelle carte qui sera joué.
     */
    public void setMyCard(Card myCard) {
        this.myCard = myCard;
    }

// ------------------------------------------------------------------- turnPlay
    /** Détermine si l'IA est prête a joué son tour. */
    protected boolean turnPlay;

    /**
     * Getter l'IA est prête a joué.
     * @return boolean l'IA est prête a joué.
     */
    public boolean getTurnPlay() {
        return this.turnPlay;
    }

    /**
     * Setter l'IA est prête a joué.
     * @param turnPlay Nouveau statut l'IA est prête a joué
     */
    public void setTurnPlay(boolean turnPlay) {
        this.turnPlay = turnPlay;
    }


    //CONSTRUCTEUR
    public IAEasy(String name, int difficulty) {
        super(name);
        this.difficulty = difficulty;
    }


    public void thinking (Game game) {
        ArrayList<Card> mainIA = game.getActualPlayer().getCards();
        ArrayList<Card> playableCards = game.playableCards();
        setTurnPlay(false);
        setMyCard(chooseCardToPlay(playableCards));
        playCard(game, getMyCard(), mainIA, getTurnPlay());
    }

    public Card chooseCardToPlay (ArrayList<Card> playableCards) {
        Card cardToPlay = null;
        if(playableCards.size() > 0) {
            setTurnPlay(true);
            cardToPlay = playableCards.get(0);
        }
        return cardToPlay;
    }


    @Override
    public Color chooseColor(ArrayList<Card> mainIA) {
        int i = 0;
        while (mainIA.get(i).getColor() == Color.Black) {
            i++;
        }
        return mainIA.get(i).getColor();
    }

    @Override
    public void changeColor(Card cardToPlay, ArrayList<Card> mainIA, Game game) {
        Color color = chooseColor(mainIA);
        game.getAlternative().getEffectCard(cardToPlay).action(color);
        game.setActualColor(color);
    }
}
