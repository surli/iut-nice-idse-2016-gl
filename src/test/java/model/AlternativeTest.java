package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import fr.unice.idse.model.Alternative;
import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Card;
import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.model.regle.EffectCard;
import fr.unice.idse.model.regle.RuleChangeColor;
import fr.unice.idse.model.regle.RuleDrawFour;
import fr.unice.idse.model.regle.RuleDrawTwo;
import fr.unice.idse.model.regle.RuleReverse;
import fr.unice.idse.model.regle.RuleSkip;

public class AlternativeTest {

	private Game game;
	private Player host;
	private Alternative alternative;
	
	@Before
	public void initialize(){
		host=new Player("host","host");
		game = new Game(host,"game",4); 
		alternative = new Alternative(game);
	}
	
	@Test
	public void getterActions(){
		ArrayList<EffectCard> actions = new ArrayList<EffectCard>();
		actions.add(new RuleChangeColor(game, Value.Wild));
		actions.add(new RuleReverse(game, Value.Reverse));
		actions.add(new RuleSkip(game, Value.Skip));
		actions.add(new RuleDrawTwo(game, Value.DrawTwo));
		actions.add(new RuleDrawFour(game, Value.DrawFour));
		assertEquals(actions,alternative.getActions());
	}
	
	@Test
	public void parametrerLaPartiePourLesEffetsDesCartes() {
		alternative.setGameToEffectCards(game);
		assertEquals(game, alternative.getActions().get(0).getGame());
	}
	
	@Test
	public void retourneEffetDeLaCarteSelonLaCarte() {
		Card card=new Card(Value.DrawFour, Color.Black);
		assertEquals(RuleDrawFour.class.getName(), alternative.getEffectCard(card).getClass().getName());
	}

}
