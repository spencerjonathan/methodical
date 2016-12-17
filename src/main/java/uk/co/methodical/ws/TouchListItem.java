package uk.co.methodical.ws;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import uk.co.methodical.LeadEnd;
import uk.co.methodical.RepetitiveChange;
import uk.co.methodical.Touch;

public class TouchListItem extends ReturnItem {

	private final int touch_length;
	private Lead[] leads;
	private boolean is_repetitious;
	private boolean comes_round;
	private long uid;
	private RepetitiveChange[] repetitive_changes;
	private Music[] music;

	public TouchListItem(Touch touch, Map<String, Integer> musical_qualities) {
		
		this.touch_length = touch.getLength();
		this.setUid(touch.getUid());
		
		ArrayList<LeadEnd> lead_ends = touch.getLead_ends();
		this.leads = new Lead[lead_ends.size()];
		int lead_no = 0;
		for (Iterator<LeadEnd> i = lead_ends.iterator(); i.hasNext();) {
			this.leads[lead_no++] = new Lead(i.next()); 
		}
		
		this.music = new Music[musical_qualities.size()];
		int music_no = 0;
		for (Iterator<String> i = musical_qualities.keySet().iterator(); i.hasNext();) {
			String key = i.next();
			this.music[music_no++] = new Music(key, musical_qualities.get(key)); 
		}
		
		setComes_round(touch.comesRound());
		
		setRepetitive_changes(touch.getRepetitiveChanges());
		setIs_repetitious(repetitive_changes.length > 0);
		setException(false);
	}

	public Music[] getMusic() {
		return music;
	}

	public void setMusic(Music[] music) {
		this.music = music;
	}

	public TouchListItem(Touch touch) {
		this(touch, null);
	}

	public int getTouch_length() {
		return touch_length;
	}

	public Lead[] getLeads() {
		return leads;
	}

	public void setLeads(Lead[] leads) {
		this.leads = leads;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public boolean isRepetitious() {
		return is_repetitious;
	}

	public void setIs_repetitious(boolean is_repetitious) {
		this.is_repetitious = is_repetitious;
	}

	public boolean getComesRound() {
		return comes_round;
	}

	public void setComes_round(boolean comes_round) {
		this.comes_round = comes_round;
	}

	public RepetitiveChange[] getRepetitive_changes() {
		return repetitive_changes;
	}

	public void setRepetitive_changes(RepetitiveChange[] repetitive_changes) {
		this.repetitive_changes = repetitive_changes;
	}
	
}
