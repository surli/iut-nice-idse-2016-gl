package fr.unice.idse.model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.NumberCardByColor;

import java.util.ArrayList;

public class IAMedium extends IA {

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
    public IAMedium(String name, String token, int difficulty) {
        super(name, token, difficulty);
    }

    public void thinking (Game game) {
        ArrayList<Card> mainIA = game.getActualPlayer().getCards();
        ArrayList<Card> playableCards = game.playableCards();

        setMyCard(chooseCardToPlay(mainIA, playableCards, game));
        playCard(game, getMyCard(), mainIA, getTurnPlay());
    }

    public Card chooseCardToPlay (ArrayList<Card> mainIA, ArrayList<Card> playableCards, Game game) {

        ArrayList<NumberCardByColor> cards = calculateNumberCardByColor(mainIA);

        int nbCard = 0;
        Card myCard = null;

        while (!getTurnPlay() && nbCard < cards.get(3).getNumber()) {
            setBestColor(cards.get(3).getColor());
            myCard = mainIA.get(nbCard);
            setTurnPlay(testCardPlayable(game, playableCards, myCard, cards.get(0).getColor(), getTurnPlay()));
            nbCard++;
        }
        while (!getTurnPlay() && nbCard < cards.get(2).getNumber()) {
            myCard = mainIA.get(nbCard);
            setTurnPlay(testCardPlayable(game, playableCards, myCard, cards.get(1).getColor(), getTurnPlay()));
            nbCard++;
        }
        while (!getTurnPlay() && nbCard < cards.get(1).getNumber()) {
            myCard = mainIA.get(nbCard);
            setTurnPlay(testCardPlayable(game, playableCards, myCard, cards.get(2).getColor(), getTurnPlay()));
            nbCard++;
        }
        while (!getTurnPlay() && nbCard < cards.get(0).getNumber()) {
            myCard = mainIA.get(nbCard);
            setTurnPlay(testCardPlayable(game, playableCards, myCard, cards.get(3).getColor(), getTurnPlay()));
            nbCard++;
        }

        return myCard;
    }

    public boolean testCardPlayable(Game game, ArrayList<Card> playableCards, Card myCard, Color bestColor, boolean turnPlay) {
        System.out.println("Appel de la fonction testCardPlayableAndPlay ");
        for (Card aCard : playableCards) {
            if (myCard == aCard) {
                setTurnPlay(true);
                break;
            }
        }
        return turnPlay;
    }

    @Override
    public Color chooseColor(ArrayList<Card> mainIA) {
        return getBestColor();
    }

    @Override
    public void changeColor(Card cardToPlay, ArrayList<Card> mainIA, Game game) {
        game.getAlternative().getEffectCard(getMyCard()).action(chooseColor(mainIA));
    }
}