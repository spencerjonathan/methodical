package uk.co.methodical;

import java.util.ArrayList;
import java.util.List;

import uk.co.methodical.database.MethodJDBCTemplate;
import uk.co.methodical.database.MethodNotFoundException;


public class MethodLibrary {

	private static MethodLibrary library = null;
	
	MethodJDBCTemplate methodJDBCTemplate;

	public static MethodLibrary instance() {
		if (library == null) {
			library = new MethodLibrary();
		}
		return library;
	}

	private MethodLibrary() {

		methodJDBCTemplate = MethodJDBCTemplate.instance();

	}

	public Method method(String name, int number_of_bells) {
		return methodJDBCTemplate.getMethod(name, number_of_bells);
	}

	private static Integer notationCharToInteger(char character) {
		switch (character) {
		case '0': return 10;
		case 'E': return 11;
		case 'T': return 12;
		case 'A': return 13;
		case 'B': return 14;
		case 'C': return 15;
		case 'D': return 16;
		default:
			return Integer.parseInt(String.valueOf(character));
		}
	}
	
	static void processNotationString(String notation, ArrayList<int[]> static_positions_array) {

		String[] static_positions_strings = notation.split("\\.");

		if (notation == null || notation.equals("")) {
			return;
		}

		for (int i = 0; i < static_positions_strings.length; ++i) {
			int[] static_bell_positions = null;
			if (static_positions_strings[i].equalsIgnoreCase("X")) {
				static_bell_positions = new int[0];
			} else {
				static_bell_positions = new int[static_positions_strings[i]
						.length()];
				for (int j = 0; j < static_positions_strings[i].length(); ++j) {
					//static_bell_positions[j] = Integer.parseInt(String
					//		.valueOf((static_positions_strings[i].charAt(index)(j))));
					static_bell_positions[j] = notationCharToInteger(static_positions_strings[i].charAt(j));
				}
			}

			static_positions_array.add(static_bell_positions);

		}

	}

	public List<Method> findMethodNames(String search_string,
			int number_of_bells, Boolean favouritesOnly, Integer userId) {

		if (favouritesOnly) {
			List<Method> methods = methodJDBCTemplate.listFavouriteMethods(
					search_string, number_of_bells, userId);
			return methods;
		} else {
			List<Method> methods = methodJDBCTemplate.listMethods(
					search_string, number_of_bells, userId);
			return methods;
		}

	}

	public boolean addMethod(String methodName, Integer numberOfBells,
			String methodPlaceNotation, String bobPlaceNotation,
			String singlePlaceNotation) {

		methodJDBCTemplate.create(methodName, numberOfBells,
				methodPlaceNotation, bobPlaceNotation, singlePlaceNotation);

		return true;
	}

	public Method method(int method_id) {
		return methodJDBCTemplate.getMethod(method_id);
	}
	
	public Method method(String method_name) throws MethodNotFoundException {
		return methodJDBCTemplate.getMethod(method_name);
	}

	public void updateMethod(Integer id, String methodName,
			Integer numberOfBells, String methodPlaceNotation,
			String bobPlaceNotation, String singlePlaceNotation) {
		//
		methodJDBCTemplate.update(id, methodName, numberOfBells,
				methodPlaceNotation, bobPlaceNotation, singlePlaceNotation);
	}

	public void setFavourite(Integer userId, Integer methodId, Boolean favourite) {
		methodJDBCTemplate.setFavourite(userId, methodId, favourite);

	}

	public static void processNotationString(String place_notation, ArrayList<int[]> notation,
			ArrayList<int[]> notation_next_lead) {
		
		String[] static_positions_strings = place_notation.split(",");
		
		if (static_positions_strings.length >= 1)
			processNotationString(static_positions_strings[0], notation);

		if (static_positions_strings.length >= 2)
			processNotationString(static_positions_strings[1], notation_next_lead);
		
	}

}
