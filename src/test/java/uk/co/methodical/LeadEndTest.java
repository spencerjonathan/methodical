package uk.co.methodical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import uk.co.methodical.LeadEnd;
import uk.co.methodical.RepetitiveChange;
import uk.co.methodical.database.MethodNotFoundException;
import uk.co.methodical.ws.Music;

public class LeadEndTest {

	@Test
	public void canLeadEndDetectRounds() {
		int[] rounds_on_6 = { 1, 2, 3, 4, 5, 6 };
		Assert.assertTrue("Can identify rounds on 6", LeadEnd.isRounds(rounds_on_6));

		int[] not_rounds_on_6 = { 1, 2, 3, 4, 6, 5 };
		Assert.assertFalse("Can identify when not rounds on 6", LeadEnd.isRounds(not_rounds_on_6));
	}

	@Test
	public void canConvertLeadEndToString() {

		ArrayList<int[]> al1 = new ArrayList<int[]>();
		int[] al1_bell_order = { 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 };
		al1.add(al1_bell_order);
		LeadEnd le1 = new LeadEnd(al1, null, null);

		Assert.assertTrue("Can convert LeadEnd to String", le1.getBellOrderString().equals("DCBATE0987654321"));
	}

	@Test
	public void canLeadEndDetectEquality() {

		ArrayList<int[]> al1 = new ArrayList<int[]>();
		int[] al1_bell_order = { 1, 3, 5, 2, 4, 6 };
		al1.add(al1_bell_order);
		LeadEnd le1 = new LeadEnd(al1, null, null);

		ArrayList<int[]> al2 = new ArrayList<int[]>();
		int[] al2_bell_order = { 1, 3, 5, 2, 4, 6 };
		al2.add(al2_bell_order);
		LeadEnd le2 = new LeadEnd(al2, null, null);

		ArrayList<int[]> al3 = new ArrayList<int[]>();
		int[] al3_bell_order = { 1, 4, 2, 5, 3, 6 };
		al3.add(al3_bell_order);
		LeadEnd le3 = new LeadEnd(al3, null, null);

		Assert.assertTrue("Can identify when two LeadEnds are the same", le1.equals(le2));
		Assert.assertFalse("Can identify when two LeadEnds are the same", le1.equals(le3));

		// Mokito.verify();
	}

	@Test
	public void canLeadEndDetectRepetition() {
		int[] row1 = { 1, 2, 3, 4, 5, 6 };
		int[] row2 = { 1, 3, 5, 2, 4, 6 };
		int[] row3 = { 6, 5, 4, 3, 2, 1 };
		// int[] row4 = { 1, 2, 3, 4, 5, 6 };
		int[] row4 = { 1, 4, 2, 5, 3, 6 };
		// int[] row6 = { 6, 5, 4, 3, 2, 1 };
		int[] row5 = { 1, 2, 3, 4, 5, 6 };
		int[] row6 = { 1, 3, 5, 2, 4, 6 };

		ArrayList<int[]> le1_rows = new ArrayList<int[]>();
		le1_rows.add(row1);
		le1_rows.add(row2);
		le1_rows.add(row3);

		ArrayList<int[]> le2_rows = new ArrayList<int[]>();
		le2_rows.add(row4);
		le2_rows.add(row5);
		le2_rows.add(row6);

		LeadEnd le1 = new LeadEnd(le1_rows, null, null);
		LeadEnd le2 = new LeadEnd(le2_rows, null, null);

		ArrayList<RepetitiveChange> repetition = le1.getRepetitiveChanges(le2, 0, 0);

		Assert.assertTrue("LeadEnd can identify the correct number of repetitive changes", repetition.size() == 2);

		for (int i = 0; i < repetition.size(); ++i) {
			Assert.assertTrue("LeadEnd can identify the rows that are repetition",
					repetition.get(i).getRow() == row1 || repetition.get(i).getRow() == row2);
		}
	}

	@Test
	public void canLeadEndDetectMusic() {
		int[] row1 = { 1, 2, 3, 4, 5, 6 };
		int[] row2 = { 1, 3, 5, 2, 4, 6 };
		int[] row3 = { 6, 5, 4, 3, 2, 1 };

		ArrayList<int[]> le_rows = new ArrayList<int[]>();
		le_rows.add(row1);
		le_rows.add(row2);
		le_rows.add(row2);
		le_rows.add(row3);

		LeadEnd le = new LeadEnd(le_rows, null, null);

		List<Music> musical_qualities = new ArrayList<Music>();
		Map<String, String> music_definitions = new HashMap<String, String>();
		music_definitions.put("135246", "Queens");
		music_definitions.put("142536", "Titums");

		le.addMusicalQualities(musical_qualities, music_definitions, 1);

		int queens_found_count = 0;
		for (Music m : musical_qualities) {
			if (m.getMusic_name().equals("Titums")) {
				Assert.assertFalse("LeadEnd doesn't incorrectly claim to find Titums", true);
			}
			if (m.getMusic_name().equals("Queens")) {
				++queens_found_count;
			}
		}

		Assert.assertTrue("LeadEnd correctly identifies 2 occurances of Queens", queens_found_count == 2);

	}

	private Method createPlainBobMajor() {

		try {
			return MethodLibrary.instance().method("Plain Bob Major");
		} catch (MethodNotFoundException e) {
			e.printStackTrace();
			Assert.assertTrue("MethodLibrary can find 'Plain Bob Major'", false);
		}

		return null;
	}

	private Method createPlainBobTriples() {

		try {
			return MethodLibrary.instance().method("Plain Bob Triples");
		} catch (MethodNotFoundException e) {
			e.printStackTrace();
			Assert.assertTrue("MethodLibrary can find 'Plain Bob Triples'", false);
		}

		return null;
	}

	private Method createGrandsireTriples() {

		try {
			return MethodLibrary.instance().method("Grandsire Triples");
		} catch (MethodNotFoundException e) {
			e.printStackTrace();
			Assert.assertTrue("MethodLibrary can find 'Grandsire Triples'", false);
		}

		return null;
	}

	@Test
	public void canStampCoursingOrderInPlainBobMajorHomePosition() {
		int[] bell_order = { 1, 2, 3, 4, 5, 6, 7, 8 };
		int[] expected_coursing_order = { 7, 5, 3, 2, 4, 6, 8 };
		ArrayList<int[]> le_rows = new ArrayList<int[]>();
		le_rows.add(bell_order);

		Method method = createPlainBobMajor();

		LeadEnd le = new LeadEnd(le_rows, method, null);
		le.stampCoursingOrder();

		Assert.assertArrayEquals(expected_coursing_order, le.getCoursingOrder());
	}

	@Test
	public void canStampCoursingOrderInPlainBobMajorMiddlePosition() {
		int[] bell_order = { 1, 4, 2, 6, 3, 8, 5, 7 };
		int[] expected_coursing_order = { 7, 5, 3, 2, 4, 6, 8 };
		ArrayList<int[]> le_rows = new ArrayList<int[]>();
		le_rows.add(bell_order);

		Method method = createPlainBobMajor();

		LeadEnd le = new LeadEnd(le_rows, method, null);
		le.stampCoursingOrder();

		Assert.assertArrayEquals(expected_coursing_order, le.getCoursingOrder());
	}

	@Test
	public void canStampCoursingOrderInPlainBobTriplesHomePosition() {
		int[] bell_order = { 1, 2, 3, 4, 5, 6, 7 };
		int[] expected_coursing_order = { 5, 3, 2, 4, 6, 7 };
		ArrayList<int[]> le_rows = new ArrayList<int[]>();
		le_rows.add(bell_order);

		Method method = createPlainBobTriples();

		LeadEnd le = new LeadEnd(le_rows, method, null);
		le.stampCoursingOrder();

		Assert.assertArrayEquals(expected_coursing_order, le.getCoursingOrder());
	}

	@Test
	public void canStampCoursingOrderInPlainBobTriplesMiddlePosition() {
		int[] bell_order = { 1, 3, 5, 2, 7, 4, 6 };
		int[] expected_coursing_order = { 5, 3, 2, 4, 6, 7 };
		ArrayList<int[]> le_rows = new ArrayList<int[]>();
		le_rows.add(bell_order);

		Method method = createPlainBobTriples();

		LeadEnd le = new LeadEnd(le_rows, method, null);
		le.stampCoursingOrder();

		Assert.assertArrayEquals(expected_coursing_order, le.getCoursingOrder());
	}

	@Test
	public void canStampCoursingOrderInTwinHuntMethodHomePosition() {
		int[] bell_order = { 1, 2, 3, 4, 5, 6, 7 };
		int[] expected_coursing_order = { 5, 3, 4, 6, 7 };
		ArrayList<int[]> le_rows = new ArrayList<int[]>();
		le_rows.add(bell_order);

		Method method = createGrandsireTriples();

		LeadEnd le = new LeadEnd(le_rows, method, null);
		le.stampCoursingOrder();

		Assert.assertArrayEquals(expected_coursing_order, le.getCoursingOrder());
	}

	@Test
	public void canStampCoursingOrderInTwinHuntMethodMiddlePosition() {
		int[] bell_order = { 1, 2, 5, 3, 7, 4, 6 };
		int[] expected_coursing_order = { 5, 3, 4, 6, 7 };
		ArrayList<int[]> le_rows = new ArrayList<int[]>();
		le_rows.add(bell_order);

		Method method = createGrandsireTriples();

		LeadEnd le = new LeadEnd(le_rows, method, null);
		le.stampCoursingOrder();

		Assert.assertArrayEquals(expected_coursing_order, le.getCoursingOrder());
	}

}
