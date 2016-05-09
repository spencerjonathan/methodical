package uk.co.methodical.ws;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import uk.co.methodical.Touch;

public class TouchList {
	final private long id;
	final private TouchMapItem[] maps;
	
	private static long next_id = 1;
	
	public TouchList(long id, TouchMapItem[] maps) {
		this.id = next_id++;
		this.maps = maps;
	}
	
	public TouchList(Map<Long, ArrayList<Touch>> touch_set) {
		this.id = next_id++;
		
		Set<Long> lengths = touch_set.keySet();
		maps = new TouchMapItem[lengths.size()];
		int record_no = 0;
		
		for (Iterator<Long> i = lengths.iterator(); i.hasNext();) {
			Long length = i.next();
			ArrayList<Touch> touches = touch_set.get(length);
			maps[record_no++] = new TouchMapItem(length, touches);
		}
		
	}

	public long getId() {
		return id;
	}

	public TouchMapItem[] getMaps() {
		return maps;
	}
}
