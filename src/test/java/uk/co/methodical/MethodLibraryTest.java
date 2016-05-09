package uk.co.methodical;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import uk.co.methodical.Method;
import uk.co.methodical.MethodLibrary;
import uk.co.methodical.database.MethodNotFoundException;

public class MethodLibraryTest {

	@Test
	public void canParseNotation() {
		String notation = "X.180ET.X";
		ArrayList<int[]> static_positions = new ArrayList<int[]>();
		MethodLibrary.processNotationString(notation, static_positions);
		int[] i = { 1, 8, 10, 11, 12 };

		Assert.assertTrue("Can Parse E and T in method place notation", Arrays.equals(static_positions.get(1), i));
		Assert.assertTrue("Can Parse X in method place notation",
				static_positions.get(0).length + static_positions.get(2).length == 0);
	}

	@Test
	public void canParseCambridgeMaxNotation() {
		Method cambridge = null;

		try {
			cambridge = MethodLibrary.instance().method("Cambridge Surprise Maximus");
		} catch (MethodNotFoundException e) {
			e.printStackTrace();
			Assert.assertTrue("Can instanciate Cambridge Surprise Maximus", false);
		}

		ArrayList<int[]> notation = cambridge.getNotation();

		Assert.assertTrue("Can correctly establish correct lead length for Cambridge Surprise Maximus",
				notation.size() == 48);

		int[] change2 = { 3, 12 };
		int[] change48 = { 1, 2 };
		int[] change42 = { 1, 2, 5, 12 };

		Assert.assertTrue("Can correctly parse Cambridge Surprise Maximus String: Check change 1", notation.get(0).length == 0);
		Assert.assertTrue("Can correctly parse Cambridge Surprise Maximus String: Check change 2", Arrays.equals(notation.get(1), change2));
		Assert.assertTrue("Can correctly parse Cambridge Surprise Maximus String: Check change 48", Arrays.equals(notation.get(47), change48));
		Assert.assertTrue("Can correctly parse Cambridge Surprise Maximus String: Check change 42", Arrays.equals(notation.get(41), change42));
		
	}

}
