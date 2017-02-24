package uk.co.methodical.ws;

public class Music {
	private String music_name;
	private int row_number;
	private int lead_number;
	private String row;

	public String getMusic_name() {
		return music_name;
	}

	public void setMusic_name(String music_name) {
		this.music_name = music_name;
	}

	public int getRow_number() {
		return row_number;
	}

	public void setRow_number(int row_number) {
		this.row_number = row_number;
	}

	public int getLead_number() {
		return lead_number;
	}

	public void setLead_number(int lead_number) {
		this.lead_number = lead_number;
	}

	public Music(String music_name, int row_number, int lead_number, String row) {
		this.music_name = music_name;
		this.row_number = row_number;
		this.lead_number = lead_number;
		this.row = row;
	}

	public boolean equals(Object other) {
		
		if (other == null) return false;
		if (!(other instanceof Music)) return false;
		
		Music otherMusic = (Music)other;
		return this.music_name.equals(otherMusic.music_name) && this.row_number == otherMusic.row_number
				&& this.lead_number == otherMusic.lead_number && this.row.equals(otherMusic.row);
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}
}
