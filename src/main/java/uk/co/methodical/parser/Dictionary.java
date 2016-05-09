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

		/*
		 * if (part_item.getClass().getName() != "Part") throw new
		 * DictionaryException("Cannot add " + item_id + " to " + id +
		 * " because it is not a PART");
		 */

		// Part part = (Part)part_item;

		part_item.add(item);

	}
	
	public Item get(String id) {
		return dictionary.get(id);
	}

	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return dictionary.keySet();
	}
}
