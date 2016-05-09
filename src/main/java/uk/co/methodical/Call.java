package uk.co.methodical;

import java.util.ArrayList;

import uk.co.methodical.parser.Item;

public class Call extends Item {

	/**
	 * 
	 */
	private String name;
	private ArrayList<int[]> notation;
	private ArrayList<int[]> notation_next_lead;
	private String notation_string;

	public Call() {
		super("P");
		name = "P";
		notation = new ArrayList<int[]>();
		notation_next_lead = new ArrayList<int[]>();
		notation_string = null;
	}

	/*
	 * public Call(String name, ArrayList<int[]> arrayList) {
	 * this.setName(name); this.setNotation(arrayList); }
	 */

	public Call(String name, String place_notation) {
		// TODO Auto-generated constructor stub
		this.setName(name);
		// this.notation_string = place_notation;
		this.setNotation(place_notation);
		notation = new ArrayList<int[]>();
		notation_next_lead = new ArrayList<int[]>();
		MethodLibrary.processNotationString(place_notation, this.notation, this.notation_next_lead);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void print() {
		StreamFactory.getMethodOutputStrem().print(" " + name);
	}

	public ArrayList<int[]> getNotation() {
		return notation;
	}

	public String getNotation_string() {
		return notation_string;
	}

	public void setNotation(String place_notation) {
		this.notation_string = place_notation;
		notation = new ArrayList<int[]>();
		notation_next_lead = new ArrayList<int[]>();
		MethodLibrary.processNotationString(place_notation, this.notation, this.notation_next_lead);
	}

	@Override
	public void applyYourselfTo(Touch touch) throws TouchException {
		touch.addCall(this);
	}

	public ArrayList<int[]> getNotationNextLead() {
		
		return this.notation_next_lead;
	}

}
