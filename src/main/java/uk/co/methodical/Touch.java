package uk.co.methodical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import uk.co.methodical.parser.Dictionary;

public class Touch {

	/**
	 * 
	 */
	private ArrayList<LeadEnd> lead_ends = null;

	private int number_of_lead_ends = 0;
	private int touch_length = 0;
	private long uid = -1;
	private static long next_uid = 1;
	private int number_of_bells;

	private boolean isTrue = true;
	private boolean comesRound = false;
	private boolean isRepetitious = false;
	private boolean stopAtRounds = true;

	private Method method = null;

	private Call previous_call = null;

	public Touch() {
		this.setUid(next_uid++);
		lead_ends = new ArrayList<LeadEnd>();

	}

	public Touch(int bell_number) {
		lead_ends = new ArrayList<LeadEnd>();
		LeadEnd le = LEFactory.createLE(bell_number);
		addLeadEnd(le);
		number_of_lead_ends = 1;
		this.setUid(next_uid++);
		this.setNumber_of_bells(bell_number);
	}

	public Touch(Touch touch) {
		lead_ends = new ArrayList<LeadEnd>();
		for (Iterator<LeadEnd> i = touch.lead_ends.iterator(); i.hasNext();) {
			lead_ends.add(i.next());
		}
		number_of_lead_ends = touch.number_of_lead_ends;
		this.setUid(next_uid++);
		this.number_of_bells = touch.getNumber_of_bells();
	}

	public Touch(boolean stopAtRounds) {
		this();
		this.stopAtRounds = stopAtRounds; 
	}

	public void addLeadEnd(LeadEnd new_lead_end) {
		lead_ends.add(new_lead_end);
		++number_of_lead_ends;
		
		// Reset the touch length in case it has already been calculated
		touch_length=0;
	}

	public void print() {

		LeadEnd le;

		int touch_length = getLength();

		StreamFactory.getMethodOutputStrem().println("Length " + touch_length);

		for (Iterator<LeadEnd> i = lead_ends.iterator(); i.hasNext();) {
			le = i.next();
			le.printAll();
		}
	}

	public RepetitiveChange[] getRepetitiveChanges() {
		ArrayList<RepetitiveChange> repetitions = new ArrayList<RepetitiveChange>();
		
		for (int i = 1; i < (lead_ends.size() - 1); ++i) {
			for (int j = i+1; j < lead_ends.size(); ++j) {
				LeadEnd first_le = lead_ends.get(i);
				LeadEnd second_le = lead_ends.get(j);
				ArrayList<RepetitiveChange> rc_list = first_le.getRepetitiveChanges(second_le, i, j);
				repetitions.addAll(rc_list);
			}
		}
		
		return (RepetitiveChange[]) repetitions.toArray(new RepetitiveChange[repetitions.size()]);
	}
	
	public boolean isRepetition(LeadEnd new_le) {
		// TODO Auto-generated method stub
		Iterator<LeadEnd> i = lead_ends.iterator();

		// Ignore the first record because this is just rounds
		if (i.hasNext())
			i.next();

		for (; i.hasNext();) {
			LeadEnd le = i.next();

			if (le.equals(new_le))
				return true;
		}

		return false;
	}

	public LeadEnd getLastLeadEnd() {
		return lead_ends.get(number_of_lead_ends - 1);
	}

	public void removeLast() {
		lead_ends.remove(--number_of_lead_ends);
	}

	public int getLength() {

		if (touch_length == 0) { // if the touch_length hasn't been set yet
									// (i.e. this method hasn't been called
									// before)
			for (Iterator<LeadEnd> i = lead_ends.iterator(); i.hasNext();) {
				touch_length += i.next().getLength();
			}

			// Remove one from the touch length because the initial lead-head
			// will have been counted
			--touch_length;
		}

		return touch_length;
	}

	public ArrayList<LeadEnd> getLead_ends() {
		return lead_ends;
	}

	public void setLead_ends(ArrayList<LeadEnd> lead_ends) {
		this.lead_ends = lead_ends;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getNumber_of_bells() {
		return number_of_bells;
	}

	public void setNumber_of_bells(int number_of_bells) {
		this.number_of_bells = number_of_bells;
	}

	public void addCall(Call call) throws TouchException {

		if (method == null)
			throw new TouchException("Must start ringing a method before making a call: " + call.getName());

		LeadEnd previous = lead_ends.get(lead_ends.size() - 1);

		try {
			LeadEnd new_lead_end = LEFactory.createLE(previous, method, previous_call, call, false, stopAtRounds);
			comesRound = new_lead_end.isRounds();
			if (isRepetition(new_lead_end))
				isRepetitious = true;

			this.addLeadEnd(new_lead_end);

		} catch (UnusedCall e) {
			System.out.println("Caught UnusedCall" + e.getMessage());
			;
			e.printStackTrace();
		}
		
		this.previous_call = call;

	}

	public void addMethod(Method method) {
		this.method = method;

		if (lead_ends.size() == 0) {
			LeadEnd le = LEFactory.createLE(method.getNumberOfBells());
			lead_ends.add(le);
			this.number_of_bells = method.getNumber_of_bells();
		}

	}

	public boolean isTrue() {
		return isTrue;
	}

	public boolean comesRound() {
		return comesRound;
	}

	public boolean isRepetitious() {
		return isRepetitious;
	}

	public Map<String, Integer> getMusicalQualities(Map<String, String> music_definitions) {
		// TODO Auto-generated method stub
		Map<String, Integer> musical_qualities = new HashMap<String, Integer>();
		
		// Discard the first LeadEnd
		Iterator<LeadEnd> i = lead_ends.iterator(); 
		if (i.hasNext()) i.next();
		
		for (; i.hasNext();) {
			i.next().addMusicalQualities(musical_qualities, music_definitions);
		}
		
		return musical_qualities;
	}

}
