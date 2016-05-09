package uk.co.methodical.ws;

public class ParseRequest {
	private String composition = null;
	private boolean stopAtRounds = true;
	private String music = null;
	
	public ParseRequest() {
	}
	
	public String getComposition() {
		return composition;
	}
	public void setComposition(String composition) {
		this.composition = composition;
	}
	public String getMusic() {
		return music;
	}
	public void setMusic(String music) {
		this.music = music;
	}

	public boolean isStopAtRounds() {
		return stopAtRounds;
	}

	public void setStopAtRounds(boolean stopAtRounds) {
		this.stopAtRounds = stopAtRounds;
	}
}
