package fr.unice.idse.constante;


import fr.unice.idse.model.Game;
import fr.unice.idse.model.card.Value;
import fr.unice.idse.model.player.Player;
import fr.unice.idse.model.regle.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe config
 */
public final class Config {
	public static String _salt = "projetIDSEUNO";
	private static String port = "3306";
	public static String url = "jdbc:mysql://localhost:" + port + "/uno";
	public static String user = "uno";
	public static String pass = "uno";

	public static HashMap<String, ArrayList<EffectCard>> alternatives;
	static {
		alternatives = new HashMap<>();
		ArrayList<EffectCard> alternative_default = new ArrayList<>();
		alternative_default.add(new RuleChangeColor(Value.Wild));
		alternative_default.add(new RuleReverse(Value.Reverse));
		alternative_default.add(new RuleSkip(Value.Skip));
		alternative_default.add(new RuleDrawTwo(Value.DrawTwo));
		alternative_default.add(new RuleDrawFour(Value.DrawFour));
		alternatives.put("default", alternative_default);

		ArrayList<EffectCard> alternative_regle8 = new ArrayList<>();
		alternative_regle8.add(new RuleChangeColor(Value.Wild));
		alternative_regle8.add(new RuleReverse(Value.Reverse));
		alternative_regle8.add(new RuleSkip(Value.Skip));
		alternative_regle8.add(new RuleDrawTwo(Value.DrawTwo));
		alternative_regle8.add(new RuleDrawFour(Value.DrawFour));
		alternative_regle8.add(new RuleRotatePlayerDecks(Value.Eight));
		alternatives.put("regle8", alternative_regle8);
	}
}
