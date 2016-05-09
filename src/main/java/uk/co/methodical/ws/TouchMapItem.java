package uk.co.methodical.ws;

import java.util.ArrayList;
import java.util.Iterator;

import uk.co.methodical.Touch;

public class TouchMapItem {
	
	private Long length;
	private TouchListItem[] items;

	public TouchMapItem(Long length, ArrayList<Touch> touches) {
		this.setLength(length);
		items = new TouchListItem[touches.size()];
		int record_no = 0;
		
		for (Iterator<Touch> i = touches.iterator(); i.hasNext();) {
			items[record_no++] = new TouchListItem(i.next());
		}
		
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public TouchListItem[] getItems() {
		return items;
	}

	public void setItems(TouchListItem[] items) {
		this.items = items;
	}
	
	
}
