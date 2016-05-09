package uk.co.methodical.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.jdbc.core.RowMapper;

import uk.co.methodical.Call;
import uk.co.methodical.Method;

public class MethodMapper implements RowMapper<Method> {

	public Method mapRow(ResultSet rs, int rowNum) throws SQLException {
		Method method = new Method();
		method.setId(rs.getInt("id"));
		method.setName(rs.getString("name"));

		String notation = rs.getString("place_notation");
		method.setNotation(translate(notation));

		// method.setNotation(rs.getString("place_notation"));
		method.setNumberOfBells(rs.getInt("number_of_bells"));
		
		method.setNumber_of_hunts(rs.getInt("numberOfHunts"));
		
		ArrayList<Call> calls = new ArrayList<Call>();

		String bob_place_notation = rs.getString("bob_place_notation");
		if (bob_place_notation != null && !bob_place_notation.equals("")) {
			calls.add(new Call("Bob", rs.getString("bob_place_notation")));
		}

		String single_place_notation = rs.getString("single_place_notation");
		if (single_place_notation != null && !single_place_notation.equals("")) {
			calls.add(new Call("Single", rs.getString("single_place_notation")));
		}

		method.setCalls(calls.toArray(new Call[calls.size()]));
		
		method.setFavourite(rs.getBoolean("favourite"));

		return method;
	}

	private String translate(String notation) {

		String[] subParts = notation.split(",");

		/*
		 * String translatedNotation = "";
		 * 
		 * for (int i = 0; i < subParts.length; ++i) {
		 * 
		 * translatedNotation += parse(subParts[i]); }
		 */

		if (subParts.length == 1) {
			return parse(subParts[0], false);
		} else {
			return parse(subParts[0], true) + "." + parse(subParts[1], true);
		}

		// return translatedNotation;
	}

	private boolean isValidPositionChar(char character) {
		switch (character) {
		case 'E': return true;
		case 'T': return true;
		case 'A': return true;
		case 'B': return true;
		case 'C': return true;
		case 'D': return true;
		default:
			if (character >= '0' && character <= '9') return true;
			else return false;
		}
	}
	
	private String parse(String subPart, boolean mirror) {

		ArrayList<String> elements = new ArrayList<String>();

		for (int i = 0; i < subPart.length();) {
			if (subPart.charAt(i) == '-') {
				elements.add("X");
				// sb.append(".X.");
				++i;
			} else if (subPart.charAt(i) == '.') {
				// sb.append('.');
				++i;
			} else if (isValidPositionChar(subPart.charAt(i))) {
				StringBuilder sb = new StringBuilder();
				while (i < subPart.length() && isValidPositionChar(subPart.charAt(i))) {
					sb.append(subPart.charAt(i++));
				}
				elements.add(sb.toString());
			} else
				++i;
		}

		if (mirror) {
			for (int i = elements.size() - 2; i >= 0; --i) {
				elements.add(elements.get(i));
			}
		}

		StringBuilder sb = new StringBuilder();

		Iterator<String> i = elements.iterator();

		sb.append(i.next());
		while (i.hasNext()) {
			sb.append('.');
			sb.append(i.next());
		}

		return sb.toString();
	}
}