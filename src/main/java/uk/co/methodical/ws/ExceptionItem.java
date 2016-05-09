package uk.co.methodical.ws;

public class ExceptionItem extends ReturnItem {
	
	private String message;

	public ExceptionItem(String message) {
		setMessage(message);
		setException(true);
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	


}
