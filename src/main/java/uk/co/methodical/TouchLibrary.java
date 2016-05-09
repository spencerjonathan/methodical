package uk.co.methodical;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
import java.util.Map;

public class TouchLibrary {
	private Map<Long, ArrayList<Touch>> touch_set;
	private Touch plain_course = null;
	
	public Map<Long, ArrayList<Touch>> getTouch_set() {
		return touch_set;
	}

	public void setTouch_set(Map<Long, ArrayList<Touch>> touch_set) {
		this.touch_set = touch_set;
	}

	public TouchLibrary() {
		touch_set = new HashMap<Long, ArrayList<Touch>>();
	}
	
	public static TouchLibrary getInstance() {
		return new TouchLibrary();
		
		/*if (library == null) {
			library = new TouchLibrary();
		}
		return library;*/
	}
	
	public void addCopy(Touch touch) {
		Touch new_touch = new Touch(touch);
		//touch_list.add(new_touch);
		Long length = new Long(new_touch.getLength());
		
		if (!touch_set.containsKey(length)) {
			touch_set.put(length, new ArrayList<Touch>());
		}
		
		touch_set.get(length).add(new_touch);
		
		// First touch to be added should be the plain course
		if (plain_course == null) {
			plain_course = new_touch;
		}
				
	}

	public Touch getPlainCourse() {
		return plain_course;
	}

	/*public void print() {
		for (Iterator<Touch> i = touch_list.iterator(); i.hasNext();) {
			i.next().print();
			StreamFactory.getMethodOutputStrem().println();
		}
	}*/
}
