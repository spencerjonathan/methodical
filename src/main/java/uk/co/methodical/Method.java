package uk.co.methodical;

import java.util.ArrayList;

import uk.co.methodical.parser.Item;

public class Method extends Item {

	private Integer id;
	private String name;
	// private int[] bell_reorder;
	private Call[] calls;
	private String notation_string;
	private ArrayList<int[]> notation;
	private int number_of_bells;
	private int number_of_hunts;
	private boolean favourite;

	public int getNumber_of_bells() {
		return number_of_bells;
	}

	public void setNumber_of_bells(int number_of_bells) {
		this.number_of_bells = number_of_bells;
	}

	public boolean isFavourite() {
		return favourite;
	}

	public void setNotation_string(String notation_string) {
		this.notation_string = notation_string;
	}

	public Method() {
		name = null;
		// bell_reorder = null;
		setCalls(null);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * public int[] getBell_reorder() { return bell_reorder; }
	 * 
	 * public void setBell_reorder(int[] bell_reorder) { this.bell_reorder =
	 * bell_reorder; }
	 */

	public Call[] getCalls() {
		return calls;
	}

	public void setCalls(Call[] calls) {
		this.calls = calls;
	}

	public void setNotation(ArrayList<int[]> arrayList) {
		this.notation = arrayList;

	}

	public ArrayList<int[]> getNotation() {
		// TODO Auto-generated method stub
		return notation;
	}

	public int getNumberOfBells() {
		return number_of_bells;
	}

	public void setNumberOfBells(int number_of_bells) {
		this.number_of_bells = number_of_bells;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNotation(String place_notation) {
		// TODO Auto-generated method stub
		this.notation_string = place_notation;
		this.notation = new ArrayList<int[]>();
		MethodLibrary.processNotationString(place_notation, this.notation);
		
	}

	public String getNotation_string() {
		return notation_string;
	}

	public int getNumber_of_hunts() {
		return number_of_hunts;
	}

	public void setNumber_of_hunts(int number_of_hunts) {
		this.number_of_hunts = number_of_hunts;
	}

	public void setFavourite(boolean favourite) {
		// TODO Auto-generated method stub
		this.favourite = favourite;
		
	}

	@Override
	public void applyYourselfTo(Touch touch) throws TouchException {
		touch.addMethod(this);
	}
	
	@Override
	public Integer affectMaxStage(Integer max_stage) {
		if (number_of_bells > max_stage) return number_of_bells;
		else return max_stage;
	}

}
