package uk.co.methodical.ws;

public class Music {
	private String name;
	private Integer occurances;
	
	public Music() {
		
	}
	
	public Music(String name, Integer occurances) {
		this.name = name;
		this.occurances = occurances;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOccurances() {
		return occurances;
	}
	public void setOccurances(Integer occurances) {
		this.occurances = occurances;
	}
	
}
