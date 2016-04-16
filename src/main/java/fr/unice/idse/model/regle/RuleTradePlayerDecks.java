package fr.unice.idse.model.regle;

import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.*;
import fr.unice.idse.model.player.Player;

public class RuleTradePlayerDecks extends EffectCard{

		public RuleTradePlayerDecks(Game board, Value value) 
		{
			this.game = board;
			this.isColorChangingCard = false;
			this.value = value;
		}

		@Override
		public void action() 
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void action(Color color) 
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void action(String playerName) 
		{
			Player actualPlayer = getGame().getActualPlayer();
			Player otherPlayer = getGame().findPlayerByName(playerName);
			if(otherPlayer != null){
				getGame().tradePlayersDecks(actualPlayer, otherPlayer);
			}
		}

		@Override
		public void effect() 
		{
			// TODO Auto-generated method stub
		}
}