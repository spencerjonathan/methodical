package uk.co.methodical;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.methodical.ws.Music;

public class LeadEnd {

	private Call call;
	private Method method;
	private int[] bell_order;
	private ArrayList<int[]> rows;
	private int[] coursing_order;

	public LeadEnd(int[] bo, Method m, Call c) {
		setBell_order(bo);

		setMethod(m);
		setCall(c);

		ArrayList<int[]> r = new ArrayList<int[]>();
		r.add(bo);
		setRows(r);
	}

	public LeadEnd(ArrayList<int[]> rows, Method m, Call c) {
		setRows(rows);
		setMethod(m);
		setCall(c);

		coursing_order = null;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Call getCall() {
		return call;
	}

	public void setCall(Call call) {
		this.call = call;
	}

	public int[] getBell_order() {
		if (rows != null) {
			return rows.get(rows.size() - 1);
		} else
			return bell_order;
	}

	public void setBell_order(int[] bell_order) {
		this.bell_order = bell_order;
	}

	public void print() {

		if (method != null)
			StreamFactory.getMethodOutputStrem().print(method.getName() + " ");

		for (int i = 0; i < Array.getLength(getBell_order()); ++i) {
			StreamFactory.getMethodOutputStrem().print(getBell_order()[i]);
		}

		if (call != null) {
			call.print();
		}

		StreamFactory.getMethodOutputStrem().println();
	}

	public void printAll() {

		if (method != null)
			StreamFactory.getMethodOutputStrem().println(method.getName() + " ");

		for (Iterator<int[]> iter = rows.iterator(); iter.hasNext();) {
			int[] row = iter.next();
			for (int i = 0; i < row.length; ++i) {
				StreamFactory.getMethodOutputStrem().print(row[i]);
			}

			StreamFactory.getMethodOutputStrem().println();
		}

		if (call != null) {
			call.print();
		}

		StreamFactory.getMethodOutputStrem().println();
	}

	public boolean isRounds() {

		return isRounds(getBell_order());

	}

	public static boolean isRounds(int[] bell_order) {
		for (int i = 1; i < bell_order.length; ++i) {
			if (bell_order[i] < bell_order[i - 1])
				return false;
		}

		return true;
	}

	public boolean equals(LeadEnd le) {

		return rowsAreTheSame(getBell_order(), le.getBell_order());
	}

	private boolean rowsAreTheSame(int[] row1, int[] row2) {
		for (int i = 0; i < row1.length; ++i) {
			if (row1[i] != row2[i])
				return false;
		}

		return true;
	}

	public ArrayList<RepetitiveChange> getRepetitiveChanges(LeadEnd other_le, int first_lead_number,
			int second_lead_number) {
		ArrayList<RepetitiveChange> repetitions = new ArrayList<RepetitiveChange>();

		for (int i = 0; i < rows.size(); ++i) {
			for (int other_le_i = 0; other_le_i < other_le.rows.size(); ++other_le_i) {
				if (rowsAreTheSame(rows.get(i), other_le.rows.get(other_le_i))) {
					RepetitiveChange repetition = new RepetitiveChange();
					repetition.setRow(rows.get(i));
					repetition.setAt_hand_in_first_le((i % 2) == 1);
					repetition.setAt_hand_in_second_le((other_le_i % 2) == 1);
					repetition.setRow_number_in_first_le(i + 1);
					repetition.setRow_number_in_second_le(other_le_i + 1);
					repetition.setFirst_lead_number(first_lead_number);
					repetition.setSecond_lead_number(second_lead_number);
					repetitions.add(repetition);
				}
			}

		}

		return repetitions;
	}

	public ArrayList<int[]> getRows() {
		return rows;
	}

	public void setRows(ArrayList<int[]> rows) {
		this.rows = rows;
	}

	public int getLength() {

		if (rows != null)
			return rows.size();
		else
			return 0;
	}

	public String toString() {

		StringBuilder ret = new StringBuilder();

		if (method != null)
			ret.append(method.getName() + " ");

		ret.append(arrayToString(getBell_order()));

		if (call != null) {
			ret.append(" " + call.getName());
		}

		return ret.toString();
	}

	private String arrayToString(int[] array) {
		StringBuilder ret = new StringBuilder();

		for (int i = 0; i < array.length; ++i) {
			ret.append(bellNumberToChar(array[i]));
		}

		return ret.toString();

	}

	public String getCoursingOrderString() {
		if (coursing_order != null) {
			return arrayToString(coursing_order);
		} else
			return "";
	}

	public String getBellOrderString() {

		return arrayToString(getBell_order());
		/*
		 * StringBuilder ret = new StringBuilder();
		 * 
		 * for (int i = 0; i < Array.getLength(getBell_order()); ++i) {
		 * ret.append(bellNumberToChar(getBell_order()[i])); }
		 * 
		 * return ret.toString();
		 */

	}

	private String bellNumberToChar(int bellNumber) {
		switch (bellNumber) {
		case 10:
			return "0";
		case 11:
			return "E";
		case 12:
			return "T";
		case 13:
			return "A";
		case 14:
			return "B";
		case 15:
			return "C";
		case 16:
			return "D";

		default:
			return Integer.toString(bellNumber);
		}
	}

	public void addMusicalQualities(List<Music> musical_qualities, Map<String, String> music_definitions, int lead_number) {

		int row_number = 0;
		for (Iterator<int[]> row_iterator = rows.iterator(); row_iterator.hasNext();) {
			++row_number;
			int[] row = row_iterator.next();
			Set<String> keys = music_definitions.keySet();

			for (Iterator<String> music_iterator = keys.iterator(); music_iterator.hasNext();) {
				String music = music_iterator.next();
				Pattern p = Pattern.compile(music);
				Matcher m = p.matcher(arrayToString(row));

				if (m.find()) {
					String music_name = music_definitions.get(music);
					musical_qualities.add(new Music(music_name, row_number, lead_number));
				}

			}
			
			
		}
	}

	private int indexOf(int[] array, int value) {
		for (int i = 0; i < array.length; ++i) {
			if (array[i] == value)
				return i;
		}

		return -1;
	}

	public void stampCoursingOrder() {
		int stage = this.method.getNumber_of_bells();
		int start_position = indexOf(this.getBell_order(), stage);
		System.out.print("Method = " + method.getName() + "; Number of Hunts = " + method.getNumber_of_hunts() + "; Start Position = " + start_position + "; and stage is " + stage + "; and Bell Order is ");
		for (int num : this.getBell_order()) {
			System.out.print(num);
		}
		System.out.println();

		int position = start_position;
		int i = 0;

		
		coursing_order = new int[stage - method.getNumber_of_hunts()];

		do {
			if ((position & 1) == 0) {
				if (position > method.getNumber_of_hunts() + 1) {
					position = position - 2;
				} else
					--position;
				if (position < method.getNumber_of_hunts()) position = method.getNumber_of_hunts()+1;
			} else {
				if (position < stage - 1) {
					position = position + 2;
				} else
					--position;
				if (position > stage - 1) {
					position = stage - 1;
				}
			}

			System.out.println("i = " + i + "; position = " + position);
			coursing_order[i++] = getBell_order()[position];

		} while (position != start_position);
	}

	public int[] getCoursingOrder() {
		return coursing_order;
	}

}
