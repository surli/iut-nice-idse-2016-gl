package fr.unice.idse.model.player;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.NumberCardByColor;
import fr.unice.idse.model.card.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class IAHard extends IA {

    private Logger logger = LoggerFactory.getLogger(IAHard.class);

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


// ------------------------------------------------------------------- cardContre

    /** Détermine la meilleur carte pour contré le joueur suivant. */
    protected Card cardContre;

    /**
     * Getter de la meilleur carte pour contré le joueur suivant
     * @return Card la meilleur carte pour contré le joueur suivant
     */
    public Card getCardContre() {
        return this.cardContre;
    }

    /**
     * Setter de la meilleur carte pour contré le joueur suivant
     * @param cardContre Détermine la nouvelle meilleur carte pour contré le joueur suivant.
     */
    public void setCardContre(Card cardContre) {
        this.cardContre = cardContre;
    }

    //CONSTRUCTEUR
    public IAHard(String name, int difficulty) {
        super(name);
        this.difficulty = difficulty;
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
        int i = 0;

        setTurnPlay(checkNextPlayer(game));

        while (!getTurnPlay() && nbCard < cards.get(3).getNumber()) {
            myCard = mainIA.get(i);
            setTurnPlay(testCardPlayable(playableCards, myCard));
            nbCard++;
        }
        while (!getTurnPlay() && nbCard < cards.get(2).getNumber()) {
            myCard = mainIA.get(i);
            setTurnPlay(testCardPlayable(playableCards, myCard));
            nbCard++;
        }
        while (!getTurnPlay() && nbCard < cards.get(1).getNumber()) {
            myCard = mainIA.get(i);
            setTurnPlay(testCardPlayable(playableCards, myCard));
            nbCard++;
        }
        while (!getTurnPlay() && nbCard < cards.get(0).getNumber()) {
            myCard = mainIA.get(i);
            setTurnPlay(testCardPlayable(playableCards, myCard));
            nbCard++;
        }

        return myCard;
    }

    public boolean testCardPlayable(ArrayList<Card> playableCards, Card myCard) {
        logger.info("Appel de la fonction testCardPlayableAndPlay ");
        for (Card aCard : playableCards) {
            if (myCard == aCard) {
                setTurnPlay(true);
                break;
            }
        }
        return getTurnPlay();
    }


    public boolean checkNextPlayer (Game game) {
        int nextPlayer = game.getNextPlayer();

        ArrayList<Card> mainIA = game.getActualPlayer().getCards();

        int nbCardMainNextPlayer = game.getPlayers().get(nextPlayer).getCards().size();

        if(nbCardMainNextPlayer == 1) {

            if(searchColorCard(mainIA, Color.Black)) {   // ESSAYER DE JOUER CHANGE COLOUR OU +4
                setTurnPlay(true);
            }/* Bug - Il faut verifie la valeur + la couleur pour jouer cette carte
            else if (searchValueCard(mainIA, Value.DrawTwo)) {
            	game.poseCard(cardContre);
                turnPlay = true;
            }*/
            else if (searchValueCard(mainIA, game.getStack().topCard().getValue())) {  //MEME NOMBRE QUE LA DERNIERE CARTE POSE
                setTurnPlay(true);
            }
        }

        return getTurnPlay();
    }


    /**
     * Retourne true si le joueur a des cartes de couleur precise dans sa main en paramettre sinon retourne false
     */
    @Override
    public boolean searchColorCard (ArrayList<Card> main, Color colorCard) {
        boolean colorExist = false;

        for (Card aCard : main) {
            if(aCard.getColor() == colorCard) {
                colorExist = true;
                setCardContre(aCard);
            }
        }

        return colorExist;
    }

    /**
     * Retourne true si le joueur a des cartes de valeur precise dans sa main en paramettre sinon retourne false
     */
    @Override
    public boolean searchValueCard (ArrayList<Card> main, Value valueCard) {
        boolean valueExist = false;
        for (Card aCard : main) {
            if(aCard.getValue() == valueCard) {
                valueExist = true;
                setCardContre(aCard);
            }
        }

        return valueExist;
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
