package fr.unice.idse.db;

import java.sql.SQLException;

import org.apache.commons.lang3.EnumUtils;

import fr.unice.idse.model.card.Color;
import fr.unice.idse.model.card.Value;

public class DataBaseCard extends DataBaseOrigin {

	public int getIdCard(String valueTopCard, String colorTopCard) {
		String query = "SELECT c_id FROM cards WHERE c_value = ? AND c_color = ?";
		if (executeSQL(query, valueTopCard, colorTopCard))
			try {
				return rs.getInt(1);
			} catch (SQLException e) {
			}
		return 0;
	}

	public int countCardsWithThisValueAndThisColor(String value, String color) {
		String query = "SELECT COUNT(*) FROM cards WHERE c_value = ? AND c_color = ?";
		if (executeSQL(query, value, color))
			try {
				return rs.getInt(1);
			} catch (SQLException e) {
			}
		return 0;
	}
	
	public boolean addCard(String value, String color) {
		if (!EnumUtils.isValidEnum(Value.class, value) || !EnumUtils.isValidEnum(Color.class, color))
			return false;
		int nbCards = countCardsWithThisValueAndThisColor(value, color);
		String query = "INSERT INTO cards (c_value, c_color) VALUES (?, ?)";
		if (executeSQL(query, value, color))
			if (countCardsWithThisValueAndThisColor(value, color) == nbCards + 1)
				return true;
		return false;
	}

	public boolean deleteCard(String value, String color) {
		int nbCards = countCardsWithThisValueAndThisColor(value, color);
		if (nbCards == 0)
			return false;
		int id = getIdCard(value, color);
		if (id == 0)
			return false;
		String query = "DELETE FROM cards WHERE c_id = ?";
		if (executeSQL(query, Integer.toString(id)))
			if (countCardsWithThisValueAndThisColor(value, color) == nbCards - 1)
				return true;
		return false;
	}
}
