package fr.unice.idse.model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.NumberCardByColor;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IAMedium extends IA {

    private Logger logger = LoggerFactory.getLogger(IAMedium.class);
    
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

// ------------------------------------------------------------------- bestColor
    /** Détermine la meilleur couleur en main. */
    protected Color bestColor;

    /**
     * Getter de la meilleur couleur en main.
     * @return Color la meilleur couleur en main.
     */
    public Color getBestColor() {
        return this.bestColor;
    }

    /**
     * Setter la meilleur couleur en main.
     * @param bestColor Détermine la nouvelle la meilleur couleur en main.
     */
    public void setBestColor(Color bestColor) {
        this.bestColor = bestColor;
    }

// ------------------------------------------------------------------- CONSTRUCTEUR
    public IAMedium(String name, int difficulty) {
        super(name);
        this.difficulty = difficulty;
    }

    public void thinking (Game game) {
        ArrayList<Card> mainIA = game.getActualPlayer().getCards();
        ArrayList<Card> playableCards = game.playableCards();
        setTurnPlay(false);

        setMyCard(chooseCardToPlay(mainIA, playableCards, game));
        playCard(game, getMyCard(), mainIA, getTurnPlay());
    }

    public Card chooseCardToPlay (ArrayList<Card> mainIA, ArrayList<Card> playableCards, Game game) {
        ArrayList<NumberCardByColor> cards = calculateNumberCardByColor(mainIA);
        int nbCard = 0;
        Card myCard = null;
        if(cards.get(4) != null) {
        	while (!getTurnPlay() && nbCard < cards.get(4).getNumber()) {
                setBestColor(cards.get(4).getColor());
                myCard = mainIA.get(nbCard);
                setTurnPlay(testCardPlayable(game, playableCards, myCard, cards.get(0).getColor()));
                nbCard++;
            }
        }
        if(cards.get(3) != null) {
        	while (!getTurnPlay() && nbCard < cards.get(3).getNumber()) {
                setBestColor(cards.get(3).getColor());
                myCard = mainIA.get(nbCard);
                setTurnPlay(testCardPlayable(game, playableCards, myCard, cards.get(0).getColor()));
                nbCard++;
            }
        }
        if(cards.get(2) != null) {
            while (!getTurnPlay() && nbCard < cards.get(2).getNumber()) {
                setBestColor(cards.get(2).getColor());
                myCard = mainIA.get(nbCard);
                setTurnPlay(testCardPlayable(game, playableCards, myCard, cards.get(1).getColor()));
                nbCard++;
            }
        }
        if(cards.get(1) != null) {
            while (!getTurnPlay() && nbCard < cards.get(1).getNumber()) {
                setBestColor(cards.get(1).getColor());
                myCard = mainIA.get(nbCard);
                setTurnPlay(testCardPlayable(game, playableCards, myCard, cards.get(2).getColor()));
                nbCard++;
            }
        }
        if(cards.get(0) != null) {
            while (!getTurnPlay() && nbCard < cards.get(0).getNumber()) {
                setBestColor(cards.get(0).getColor());
                myCard = mainIA.get(nbCard);
                setTurnPlay(testCardPlayable(game, playableCards, myCard, cards.get(3).getColor()));
                nbCard++;
            }
        }

        return myCard;
    }
    public boolean testCardPlayable(Game game, ArrayList<Card> playableCards, Card myCard, Color bestColor) {
       
        for (Card aCard : playableCards) {
            if (myCard == aCard) {
                setTurnPlay(true);
                break;
            }
        }
        return getTurnPlay();
    }

    @Override
    public Color chooseColor(ArrayList<Card> mainIA) {
        return getBestColor();
    }

    @Override
    public void changeColor(Card cardToPlay, ArrayList<Card> mainIA, Game game) {
        Color color = chooseColor(mainIA);
        game.getAlternative().getEffectCard(cardToPlay).action(color);
        game.setActualColor(color);
    }
}