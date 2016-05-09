package uk.co.methodical.ws;

public class MethodListItem {

    private Integer id;
	private String name;
	private Integer number_of_bells;
    private boolean favourite;
    private String place_notation;
    private String bob_notation;
    private String single_notation;
    
    public MethodListItem() {
    	name = "";
    	favourite=false;
    }
    
    public MethodListItem(Integer id, String name, Integer number_of_bells, String place_notation, String bob_place_notation, String single_place_notation, boolean favourite) {
    	this.setId(id);
    	this.setName(name);
    	this.setNumber_of_bells(number_of_bells);
    	this.setFavourite(favourite);
    	this.setPlace_notation(place_notation);
    	this.setBob_notation(bob_place_notation);
    	this.setSingle_notation(single_place_notation);
    	
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlace_notation() {
		return place_notation;
	}

	public void setPlace_notation(String place_notation) {
		this.place_notation = place_notation;
	}

	public String getBob_notation() {
		return bob_notation;
	}

	public void setBob_notation(String bob_notation) {
		this.bob_notation = bob_notation;
	}

	public String getSingle_notation() {
		return single_notation;
	}

	public void setSingle_notation(String single_notation) {
		this.single_notation = single_notation;
	}

	public Integer getNumber_of_bells() {
		return number_of_bells;
	}

	public void setNumber_of_bells(Integer number_of_bells) {
		this.number_of_bells = number_of_bells;
	}

    
}
