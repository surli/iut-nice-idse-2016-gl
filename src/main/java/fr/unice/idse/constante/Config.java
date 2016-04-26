package fr.unice.idse.constante;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.unice.idse.model.regle.*;
import fr.unice.idse.model.card.Value;;

/**
 * Classe config
 */
public final class Config {
	public static String _salt = "projetIDSEUNO";
	private static String port = "3306";
	public static String url = "jdbc:mysql://localhost:" + port + "/uno";
	public static String user = "uno";
	public static String pass = "uno";
	public static Map<String, ArrayList<EffectCard>> variantes = new HashMap<String, ArrayList<EffectCard>>()
			{{
				put("variante1", new ArrayList<EffectCard>()
				{{
					new RuleChangeColor(Value.Wild);
					new RuleDrawFour(Value.DrawFour);
					new RuleDrawTwo(Value.DrawTwo);
					new RuleReverse(Value.Reverse);
					new RuleRotatePlayerDecks(Value.Zero);
					new RuleSkip(Value.Skip);
					new RuleTradePlayerDecks(Value.Seven);
				}});
				//Todo : Ecrire les prochaines variantes ici
			}};
}
