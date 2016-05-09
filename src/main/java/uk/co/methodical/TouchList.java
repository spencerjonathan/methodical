package uk.co.methodical;

import java.util.List;

public class TouchList {

	private Touch[] touches = null;
	
	public TouchList(List<Touch> touches) {
		touches.toArray(new Touch[touches.size()]);
	}
	
	public Touch[] getTouches() {
		return touches;
	}
}
