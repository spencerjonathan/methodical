package uk.co.methodical;

import java.util.ArrayList;

public class LEFactory {

	public static LeadEnd createLE(LeadEnd previous, Method method, Call previous_call, Call call,
			boolean exception_on_unused_call, boolean stop_at_rounds) throws UnusedCall {

		LeadEnd le = new LeadEnd(
				generateLEOrder(previous.getBell_order(), method, previous_call, call, exception_on_unused_call, stop_at_rounds),
				method, call);

		return le;
	}

	private static int[] generateNextStrokeOrder(int[] previous_bell_order, int[] static_positions) {
		int[] new_order = new int[previous_bell_order.length];

		int s = 0; // Static positions iterator
		boolean swap_with_previous = false;

		for (int i = 0; i < new_order.length; ++i) {
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
		if (call != null) {

			strokes_affected = call.getNotation().size();
			// strokes_affected = Math.round((new
			// Float(call.getNotation().size()) + 1) / 2);
			// if (call.getNotation().size() == 0) strokes_affected = 0;
		}

		if (previous_call != null) {
			first_strokes_affected = previous_call.getNotationNextLead().size();
		}

		// Generate the rows at the start of the lead which are impacted by a
		// call at the end of the previous lead
		if (previous_call != null) {
			static_positions = previous_call.getNotationNextLead();
			for (int i = 0; i < static_positions.size(); ++i) {
				temp_bell_order = generateNextStrokeOrder(temp_bell_order, static_positions.get(i));
				rows.add(temp_bell_order);
				if (LeadEnd.isRounds(temp_bell_order) && stop_at_rounds)
					return rows;
			}

		}

		// Generate the rows in the lead excluding those impacted by a call
		static_positions = method.getNotation();
		for (int i = first_strokes_affected; i < static_positions.size() - strokes_affected; ++i) {
			temp_bell_order = generateNextStrokeOrder(temp_bell_order, static_positions.get(i));
			rows.add(temp_bell_order);
			if (LeadEnd.isRounds(temp_bell_order) && stop_at_rounds) {
				if (call != null && exception_on_unused_call)
					throw new UnusedCall();
				else
					return rows;
			}
		}

		// Generate the rows at the end of the lead which are impacted by a call
		if (call != null) {
			static_positions = call.getNotation();
			for (int i = 0; i < static_positions.size(); ++i) {
				temp_bell_order = generateNextStrokeOrder(temp_bell_order, static_positions.get(i));
				rows.add(temp_bell_order);
				if (LeadEnd.isRounds(temp_bell_order) && stop_at_rounds)
					return rows;
			}

		}

		return rows;
	}

	public static LeadEnd createLE(int bell_number) {

		int[] rounds = new int[bell_number];
		for (int i = 0; i < bell_number; ++i) {
			rounds[i] = i + 1;
		}

		return new LeadEnd(rounds, null, null);
	}

}
