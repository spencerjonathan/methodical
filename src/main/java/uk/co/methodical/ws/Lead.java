package uk.co.methodical.ws;

import uk.co.methodical.LeadEnd;

public class Lead {

	String call = "";
	String method = "";
	String bell_order = "";
	String coursing_order = "";
	
	public String getCoursing_order() {
		return coursing_order;
	}

	public void setCoursing_order(String coursing_order) {
		this.coursing_order = coursing_order;
	}

	public Lead(LeadEnd lead_end) {
		if (lead_end.getCall() != null) this.call = lead_end.getCall().getName();
		if (lead_end.getMethod() != null) this.method = lead_end.getMethod().getName();
		this.bell_order = lead_end.getBellOrderString();
		this.coursing_order = lead_end.getCoursingOrderString();
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBell_order() {
		return bell_order;
	}

	public void setBell_order(String bell_order) {
		this.bell_order = bell_order;
	}

}
