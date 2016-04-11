package fr.unice.idse.model.player;

import fr.unice.idse.model.Board;
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


    public IAHard(String name, String token, int difficulty) {
        super(name, token, difficulty);
    }

    public Card cardContre;

    public void reflexion(Board board) {

        ArrayList<Card> mainIA = board.getActualPlayer().getCards();
        ArrayList<Card> playableCards = board.playableCards();
        System.out.println("Carte jouable : " + playableCards.toString());

        ArrayList<NumberCardByColor> cards = new ArrayList<NumberCardByColor>();

        int countB = 0;
        int countG = 0;
        int countR = 0;
        int countY = 0;
        int bestCount = countB;
        Color bestColor = Color.Blue;


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

        if (bestCount < countR) {
            bestCount = countR;
            bestColor = Color.Red;
        }

        if (bestCount < countG) {
            bestCount = countG;
            bestColor = Color.Green;
        }

        if (bestCount < countY) {
            bestCount = countY;
            bestColor = Color.Yellow;
        }

        cards.add(new NumberCardByColor(Color.Blue, countB));
        cards.add(new NumberCardByColor(Color.Green, countG));
        cards.add(new NumberCardByColor(Color.Red, countR));
        cards.add(new NumberCardByColor(Color.Yellow, countY));

        Collections.sort(cards);

        System.out.println("cards IA = " + cards);

        int nbCard = 0;
        Card myCard;
        int i = 0;

        turnPlay = checkNextPlayer (board, turnPlay);

        while (!turnPlay && nbCard < cards.get(0).getNumber()) {
            myCard = mainIA.get(i);
            turnPlay = testCardPlayableAndPlay(board, playableCards, myCard, bestColor, turnPlay);
            nbCard++;
        }
        while (!turnPlay && nbCard < cards.get(1).getNumber()) {
            myCard = mainIA.get(i);
            turnPlay = testCardPlayableAndPlay(board, playableCards, myCard, bestColor, turnPlay);
            nbCard++;
        }
        while (!turnPlay && nbCard < cards.get(2).getNumber()) {
            myCard = mainIA.get(i);
            turnPlay = testCardPlayableAndPlay(board, playableCards, myCard, bestColor, turnPlay);
            nbCard++;
        }
        while (!turnPlay && nbCard < cards.get(3).getNumber()) {
            myCard = mainIA.get(i);
            turnPlay = testCardPlayableAndPlay(board, playableCards, myCard, bestColor, turnPlay);
            nbCard++;
        }

        if (!turnPlay) {
            board.drawCard();
        }
    }

    public boolean testCardPlayableAndPlay(Board board, ArrayList<Card> playableCards, Card myCard, Color bestColor, boolean turnPlay) {
        System.out.println("Appel de la fonction testCardPlayableAndPlay ");
        for (Card aCard : playableCards) {
            if (myCard == aCard) {
                board.poseCard(myCard);
                System.out.println("Carte joué : " + myCard);
                if (board.getAlternative().getEffectCard(myCard).isColorChangingCard()) {
                    board.getAlternative().getEffectCard(myCard).changeColor(chooseColor(board.getActualPlayer().getCards()));
                    board.getAlternative().getEffectCard(myCard).action();
                }
                turnPlay = true;
                break;
            }
        }

        return turnPlay;
    }


    public boolean checkNextPlayer (Board board, boolean turnPlay) {
        int nextPlayer = board.getNextPlayer();
        ArrayList<Card> mainIA = board.getActualPlayer().getCards();

        ArrayList<Card> mainNextPlayer = board.getPlayers().get(nextPlayer).getCards();

        if(mainNextPlayer.size() == 1) {

            if(searchColorCard(mainIA, Color.Black)) {   // ESSAYER DE JOUER CHANGE COLOR OU +4
                board.poseCard(cardContre);
                System.out.println("Carte joué : " + cardContre);

                if (board.getAlternative().getEffectCard(cardContre).isColorChangingCard()) {
                    board.getAlternative().getEffectCard(cardContre).changeColor(chooseColor(mainIA));
                    board.getAlternative().getEffectCard(cardContre).action();
                }
                turnPlay = true;
            }
            else if (searchValueCard(mainIA, Value.DrawTwo)) {
                board.poseCard(cardContre);
                System.out.println("Carte joué : " + cardContre);

                if (board.getAlternative().getEffectCard(cardContre).isColorChangingCard()) {
                    board.getAlternative().getEffectCard(cardContre).changeColor(chooseColor(mainIA));
                    board.getAlternative().getEffectCard(cardContre).action();
                }
                turnPlay = true;
            }
            else if (searchValueCard(mainIA, board.getStack().topCard().getValue())) {  //MEME NOMBRE QUE LA DERNIERE CARTE POSE
                board.poseCard(cardContre);
                System.out.println("Carte joué : " + cardContre);

                if (board.getAlternative().getEffectCard(cardContre).isColorChangingCard()) {
                    board.getAlternative().getEffectCard(cardContre).changeColor(chooseColor(mainIA));
                    board.getAlternative().getEffectCard(cardContre).action();
                }
                turnPlay = true;
            }
            else {
                board.poseCard(cardContre);
                System.out.println("Carte joué : " + cardContre);

                if (board.getAlternative().getEffectCard(cardContre).isColorChangingCard()) {
                    board.getAlternative().getEffectCard(cardContre).changeColor(chooseColor(mainIA));
                    board.getAlternative().getEffectCard(cardContre).action();
                }
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

}
