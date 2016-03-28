package fr.unice.idse.model.regle;

import fr.unice.idse.model.Board;
import fr.unice.idse.model.card.*;
import fr.unice.idse.model.player.Player;

public class RuleTradePlayerDecks extends EffectCard{

		public RuleTradePlayerDecks(Board board, Value value) {
			super(board, value);
		}
		
		@Override
		public boolean tradeDecks(String playerName)
		{
			Player actualPlayer = getBoard().getActualPlayer();
			Player otherPlayer = getBoard().findPlayerByName(playerName);
			if(otherPlayer != null){
				getBoard().tradePlayersDecks(actualPlayer, otherPlayer);
				return true;
			}
			return false;
		}

}