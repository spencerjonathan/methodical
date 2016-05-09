package uk.co.methodical.ws;

import uk.co.methodical.LeadEnd;

public class Lead {

	String call = "";
	String method = "";
	String bell_order = "";
	
	public Lead(LeadEnd lead_end) {
		if (lead_end.getCall() != null) this.call = lead_end.getCall().getName();
		if (lead_end.getMethod() != null) this.method = lead_end.getMethod().getName();
		this.bell_order = lead_end.getBellOrderString();
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
