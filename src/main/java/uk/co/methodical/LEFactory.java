package uk.co.methodical;

import java.util.ArrayList;
import java.util.Arrays;

public class LEFactory {

	public static LeadEnd createLE(LeadEnd previous, Method method, Call previous_call, Call call,
			boolean exception_on_unused_call, boolean stop_at_rounds) throws UnusedCall {

		LeadEnd le = new LeadEnd(generateLEOrder(previous.getBell_order(), method, previous_call, call,
				exception_on_unused_call, stop_at_rounds), method, call);

		return le;
	}

	private static int[] generateNextStrokeOrder(int[] previous_bell_order, int[] static_positions, int stage) {
		int[] new_order;

		// Copy previous as a starting position in case different stage from
		// previous lead
		new_order = Arrays.copyOf(previous_bell_order, previous_bell_order.length);

		int s = 0; // Static positions iterator
		boolean swap_with_previous = false;

		for (int i = 0; i < stage; ++i) {
			if (s < static_positions.length && (static_positions[s] == i + 1)) {

				// This is a static position, so carry forward the bell in this
				// position from the previous row
				new_order[i] = previous_bell_order[i];

				// Take note that next iteration we should not swap with this
				// position
				swap_with_previous = false;

				// Increment the static positions iterator
				++s;

			} else if (!swap_with_previous) {

				// Take note that on the next cycle we must swap with this
				// position number
				swap_with_previous = true;

				// However, to handle situations that would result in nothing
				// being set in this field, carry the previous bell position
				// forward for now (probably to be overwritten in the next cycle
				new_order[i] = previous_bell_order[i];

			} else {
				new_order[i - 1] = previous_bell_order[i];
				new_order[i] = previous_bell_order[i - 1];
				swap_with_previous = false;
			}
		}

		return new_order;
	}

	private static ArrayList<int[]> generateLEOrder(int[] bell_order, Method method, Call previous_call, Call call,
			boolean exception_on_unused_call, boolean stop_at_rounds) throws UnusedCall {

		ArrayList<int[]> static_positions = null;

		int[] temp_bell_order = bell_order;
		ArrayList<int[]> rows = new ArrayList<int[]>();

		// if we've got a call then get the number of strokes that it affects in
		// this lead (assume that call affects one more stroke in this lead than
		// it does in the next)
		int strokes_affected = 0;
		int first_strokes_affected = 0;

		// Generate the rows at the start of the lead which are impacted by a
		// call at the end of the previous lead
		if (previous_call != null) {

			first_strokes_affected = previous_call.getNotationNextLead().size();
			static_positions = previous_call.getNotationNextLead();

			if (createRowsForCall(temp_bell_order, rows, method.getNumber_of_bells(), static_positions,
					stop_at_rounds)) {

				return rows;
			}

			if (rows.size() > 0) {
				temp_bell_order = rows.get(rows.size() - 1);
			}

		}

		if (call != null) {
			strokes_affected = call.getNotation().size();
		}

		// Generate the rows in the lead excluding those impacted by a call
		if (createRowsForBody(temp_bell_order, rows, first_strokes_affected, strokes_affected, method, call,
				stop_at_rounds, exception_on_unused_call))
			return rows;

		// Generate the rows at the end of the lead which are impacted by a call
		if (call != null) {
			if (createRowsForCall(rows.get(rows.size() - 1), rows, method.getNumber_of_bells(), call.getNotation(),
					stop_at_rounds))
				return rows;
		}

		return rows;
	}

	private static boolean createRowsForBody(int[] current_bell_order, ArrayList<int[]> rows,
			Integer first_strokes_affected, Integer strokes_affected, Method method, Call call, boolean stop_at_rounds,
			boolean exception_on_unused_call) throws UnusedCall {

		// Generate the rows in the lead excluding those impacted by a call
		ArrayList<int[]> static_positions = method.getNotation();
		for (int i = first_strokes_affected; i < static_positions.size() - strokes_affected; ++i) {

			current_bell_order = generateNextStrokeOrder(current_bell_order, static_positions.get(i),
					method.getNumber_of_bells());

			rows.add(current_bell_order);

			if (LeadEnd.isRounds(current_bell_order) && stop_at_rounds) {
				if (call != null && exception_on_unused_call)
					throw new UnusedCall();
				else
					return true;
			}
		}

		return false;
	}

	private static boolean createRowsForCall(int[] current_bell_order, ArrayList<int[]> rows, Integer stage,
			ArrayList<int[]> static_positions, boolean stop_at_rounds) {

		for (int i = 0; i < static_positions.size(); ++i) {

			current_bell_order = generateNextStrokeOrder(current_bell_order, static_positions.get(i), stage);
			rows.add(current_bell_order);

			if (LeadEnd.isRounds(current_bell_order) && stop_at_rounds)
				return true;
		}

		return false;
	}

	public static LeadEnd createLE(int bell_number) {

		int[] rounds = new int[bell_number];
		for (int i = 0; i < bell_number; ++i) {
			rounds[i] = i + 1;
		}

		return new LeadEnd(rounds, null, null);
	}

}
