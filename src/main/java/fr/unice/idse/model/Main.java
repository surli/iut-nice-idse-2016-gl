package fr.unice.idse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class Main 
{

	public static void main(String[] args) 
	{
		Player playerHostTest = new Player("PlayerHostTest");
		Player playerTest2 = new Player("Test");
		Player playerTest3 = new Player("Toto");
		Game gameTest = new Game(playerHostTest, "GameTest", 3);
		
		gameTest.addPlayer(playerTest2);
		gameTest.addPlayer(playerTest3);
		if (gameTest.start())
		{
			Board board = gameTest.getBoard();
			System.out.println("Debut de la partie");
			Player winner = null;
			while(!gameTest.gameEnd())
			{
				Player actualPlayer = board.getActualPlayer();
				System.out.println("Main du joueur " + actualPlayer.getName());
				for (int i = 0 ; i < actualPlayer.getCards().size() ; i++) 
				{
					Card card = actualPlayer.getCards().get(i);
					System.out.println("[" + (i) + "] : " + card);
				}
				Card actualCardInStack = board.getStack().topCard();
				System.out.println("Carte dans la fosse " + actualCardInStack.toString());
				if(board.askPlayerCanPlay(actualPlayer))
				{
					ArrayList<Card> playableCards = board.playableCards();
					System.out.println("Carte jouable : " + playableCards.toString());
					boolean played = false;
					while(!played)
					{
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
		                if(input != "" && numberCard < actualPlayer.getCards().size())
		                {
		                	
		                	Card card = actualPlayer.getCards().get(numberCard);
		                	board.poseCard(card);
		                	if(gameTest.gameEnd())
		                	{
		                		winner = actualPlayer;
		                	}
		                	else
		                	{
		                		if(!actualCardInStack.equals(board.getStack().topCard()))
			                	{
			                		played = true;
			                		board.nextPlayer();
			                	}
		                	}
		                }
					}
				}
				else
				{
					board.pioche();
					System.out.println("Le joueur a pioche : " + actualPlayer.getCards().get(actualPlayer.getCards().size()-1));
					board.nextPlayer();
				}
				
			}
			System.out.println("Fin de la partie. Vainqueur : " + winner.getName());
		}
	}
}
