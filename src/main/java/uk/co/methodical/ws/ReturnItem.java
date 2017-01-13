package uk.co.methodical.ws;

public class ReturnItem extends Throwable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean exception;

	public boolean isException() {
		return exception;
	}

	public void setException(boolean exception) {
		this.exception = exception;
	}

}
