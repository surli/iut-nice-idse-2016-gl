package fr.unice.idse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.regle.EffectCard;
import fr.unice.idse.model.player.*;


public class Main
{

    public static void main(String[] args)
    {
        Player playerHostTest = new Player("PlayerHostTest","tok1");
        Player playerTest2 = new Player("Test","tok2");
        Player playerTest3 = new Player("Toto","tok3");
        Game gameTest = new Game(playerHostTest, "GameTest", 3);

        gameTest.addPlayer(playerTest2);
        gameTest.addPlayer(playerTest3);
        if (gameTest.start())
        {
            Alternative variante = gameTest.getAlternative();
            System.out.println("Debut de la partie");
            Player winner = null;
            while(!gameTest.gameEnd())
            {
                Player actualPlayer = gameTest.getActualPlayer();
                Card actualCardInStack = gameTest.getStack().topCard();
                System.out.println("Main du joueur " + actualPlayer.getName());
                for (int i = 0 ; i < actualPlayer.getCards().size() ; i++)
                {
                    Card card = actualPlayer.getCards().get(i);
                    System.out.println("[" + (i) + "] : " + card);
                }
                System.out.println("Carte dans la fosse " + actualCardInStack.toString());
                if(gameTest.askPlayerCanPlay(actualPlayer))
                {
                    ArrayList<Card> playableCards = gameTest.playableCards();
                    System.out.println("Carte jouable : " + playableCards.toString());
                    boolean played = false;
                    while(!played)
                    {
                        int numberCardsPlayer = actualPlayer.getCards().size();
                        System.out.println("Entrez une valeure qui est entre crochet pour jouer une carte.");
                        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                        String input = "";
                        try
                        {
                            input = bufferRead.readLine();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        int numberCard = Integer.parseInt(input);
                        if(!input.equals("") && numberCard < actualPlayer.getCards().size())
                        {

                            Card card = actualPlayer.getCards().get(numberCard);
                            gameTest.poseCard(card);
                            if(gameTest.gameEnd())
                            {
                                winner = actualPlayer;
                                played = true;
                                System.out.println("Le joueur joue : " + card);
                            }
                            else
                            {
                                if(numberCardsPlayer > actualPlayer.getCards().size())
                                {
                                    played = true;
                                    System.out.println("Le joueur joue : " + card);
                                    if(actualPlayer.getCards().size() == 1)
                                    {
                                        System.out.println("Uno");
                                    }
                                    EffectCard effectCard = variante.getEffectCard(card);
                                    if(effectCard != null)
                                    {
                                        if(card.getColor().equals(Color.Black))
                                        {
                                            boolean chooseColor = false;
                                            while (!chooseColor)
                                            {
                                                System.out.println("Choisie une couleur :  1 pour Red, 2 pour Blue, 3 pour Yellow, 4 pour Green");
                                                input = "";
                                                try
                                                {
                                                    input = bufferRead.readLine();
                                                }
                                                catch (IOException e)
                                                {
                                                    e.printStackTrace();
                                                }
                                                int choose = Integer.parseInt(input);
                                                switch(choose)
                                                {
                                                    case 1 :
                                                        effectCard.action(Color.Red);
                                                        chooseColor = true;
                                                        System.out.println("Le joueur a choisie : Red");
                                                        break;
                                                    case 2 :
                                                        effectCard.action(Color.Blue);
                                                        System.out.println("Le joueur a choisie : Blue");
                                                        chooseColor = true;
                                                        break;
                                                    case 3 :
                                                        effectCard.action(Color.Yellow);
                                                        System.out.println("Le joueur a choisie : Yellow");
                                                        chooseColor = true;
                                                        break;
                                                    case 4 :
                                                        effectCard.action(Color.Green);
                                                        System.out.println("Le joueur a choisie : Green");
                                                        chooseColor = true;
                                                        break;
                                                    default :
                                                        break;
                                                }
                                            }
                                        }
                                        effectCard.action();
                                    }
                                    gameTest.nextPlayer();
                                    if(effectCard != null && effectCard.getEffect())
                                    {
                                        effectCard.effect();
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                	gameTest.drawCard();
                    System.out.println("Le joueur a pioche : " + actualPlayer.getCards().get(actualPlayer.getCards().size()-1));
                    if(!gameTest.askPlayerCanPlay(actualPlayer))
                    {
                    	gameTest.nextPlayer();
                    }
                }

            }
            System.out.println("Fin de la partie. Vainqueur : " + winner.getName());
        }
        else
        {
            System.out.println("Problème de chargement de la partie");
        }
    }
}
