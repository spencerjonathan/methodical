package uk.co.methodical.parser;

import java.util.Iterator;

import uk.co.methodical.Touch;
import uk.co.methodical.TouchException;
import uk.co.methodical.parser.Dictionary.DictionaryException;

public abstract class Item {

	private String id;
	
	public Item() {
		id = "";
	}
	
	public Item(String id) {
		this.id = id;
	}
	
	public Iterator iterator() {
		// By default, Items are not iteratable
		return null;
	}
	
	public void add(Item item) throws ParseException {
		throw new ParseException("Cannot add " + item + " to " + id + " because it is not a PART");
	}

	abstract public void applyYourselfTo(Touch touch) throws TouchException;

}
