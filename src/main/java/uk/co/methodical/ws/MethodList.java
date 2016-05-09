package uk.co.methodical.ws;

public class MethodList {

	private long id;
	private long number_of_bells;
	private MethodListItem[] items;
	
	public MethodList() {
		id=-1;
		number_of_bells = -1;
	}
	
	public MethodList(final long id, final long number_of_bells, MethodListItem[] items) {
		this.setId(id);
		this.items = items;
		this.setNumber_of_bells(number_of_bells);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNumber_of_bells() {
		return number_of_bells;
	}

	public void setNumber_of_bells(long number_of_bells) {
		this.number_of_bells = number_of_bells;
	}

	public MethodListItem[] getItems() {
		return items;
	}

	public void setItems(MethodListItem[] items) {
		this.items = items;
	}

}
