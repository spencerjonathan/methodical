package uk.co.methodical;

public class RepetitiveChange {
	private int[] row = null;
	private boolean at_hand_in_first_le;
	private int row_number_in_first_le = 0;
	private boolean at_hand_in_second_le;
	private int row_number_in_second_le = 0;
	private int second_lead_number = 0;
	
	public int getSecond_lead_number() {
		return second_lead_number;
	}
	public void setSecond_lead_number(int second_lead_number) {
		this.second_lead_number = second_lead_number;
	}
	public int getFirst_lead_number() {
		return first_lead_number;
	}
	public void setFirst_lead_number(int first_lead_number) {
		this.first_lead_number = first_lead_number;
	}
	private int first_lead_number = 0;
	
	public int[] getRow() {
		return row;
	}
	public void setRow(int[] row) {
		this.row = row;
	}
	public boolean isAt_hand_in_first_le() {
		return at_hand_in_first_le;
	}
	public void setAt_hand_in_first_le(boolean at_hand_in_first_le) {
		this.at_hand_in_first_le = at_hand_in_first_le;
	}
	public int getRow_number_in_first_le() {
		return row_number_in_first_le;
	}
	public void setRow_number_in_first_le(int row_number_in_first_le) {
		this.row_number_in_first_le = row_number_in_first_le;
	}
	public boolean isAt_hand_in_second_le() {
		return at_hand_in_second_le;
	}
	public void setAt_hand_in_second_le(boolean at_hand_in_second_le) {
		this.at_hand_in_second_le = at_hand_in_second_le;
	}
	public int getRow_number_in_second_le() {
		return row_number_in_second_le;
	}
	public void setRow_number_in_second_le(int row_number_in_second_le) {
		this.row_number_in_second_le = row_number_in_second_le;
	}
	
	
}
