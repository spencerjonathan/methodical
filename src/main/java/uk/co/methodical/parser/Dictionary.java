package uk.co.methodical.parser;

import java.util.HashMap;
import java.util.Set;

import uk.co.methodical.Call;
import uk.co.methodical.Method;
import uk.co.methodical.MethodLibrary;
import uk.co.methodical.Part;
import uk.co.methodical.database.MethodNotFoundException;

public class Dictionary {

	private HashMap<String, Item> dictionary;
	private Integer max_stage = 0;
	
	public Integer getMax_stage() {
		return max_stage;
	}

	public class DictionaryException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2198382640947146263L;

		public DictionaryException(String message) {
			super(message);
		}
	}

	public Dictionary() {
		dictionary = new HashMap<String, Item>();
		
		// Add the definition of a plain lead to the dictionary
		Call plain = new Call();
		dictionary.put(plain.getName(), plain);
	};

	public void addMethod(String id, String method_name) throws MethodNotFoundException {
		Method method = MethodLibrary.instance().method(method_name);
		dictionary.put(id, method);
	}

	public void addCall(String id, String notation) {
		Call call = new Call(id, notation);
		dictionary.put(id, call);
	}

	public void addPart(String id) {
		Part part = new Part(id);
		dictionary.put(id, part);
	}

	public void addItemToPart(String id, String item_id) throws DictionaryException, ParseException {
		Item part_item = dictionary.get(id);
		Item item = dictionary.get(item_id);

		if (part_item == null)
			throw new DictionaryException("Could not find definition for " + id);

		if (item == null)
			throw new DictionaryException("Could not find definition for " + item_id);

		max_stage = item.affectMaxStage(max_stage);
		part_item.add(item);

	}
	
	public Item get(String id) {
		return dictionary.get(id);
	}

	public Set<String> keySet() {
		return dictionary.keySet();
	}
}
