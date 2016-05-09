package uk.co.methodical;


import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import uk.co.methodical.Call;
import uk.co.methodical.Method;
import uk.co.methodical.Touch;
import uk.co.methodical.TouchException;

public class TouchTest extends BaseTest {

	@Test
	public void canCreateShortTouch() {
		Touch touch = new Touch();
		
		Method pbm = super.createPlainBobMinorMethod();
		Call bob = new Call("B", "14");
		Call plain = new Call("P", "12");
		
		// Call standard 120 (WHWH)
		try {
			pbm.applyYourselfTo(touch);
			bob.applyYourselfTo(touch);
			plain.applyYourselfTo(touch);
			plain.applyYourselfTo(touch);
			plain.applyYourselfTo(touch);
			bob.applyYourselfTo(touch);
			bob.applyYourselfTo(touch);
			plain.applyYourselfTo(touch);
			plain.applyYourselfTo(touch);
			plain.applyYourselfTo(touch);
			bob.applyYourselfTo(touch);
		} catch (TouchException e) {
			e.printStackTrace();
			Assert.assertTrue("Can create standard 120 of Plain Bob Minor without exception", false);
		}
		
		//touch.print();
		
		Assert.assertTrue("Calling standard 120 of Plain Bob Minor comes round", touch.comesRound());
		Assert.assertFalse("Calling standard 120 of Plain Bob Minor does not contain repetition", touch.isRepetitious());
		Assert.assertTrue("Touch can identify its own length", touch.getLength() == 120);
		
		HashMap<String, String> music_definitions = new HashMap<String, String>();
		music_definitions.put("546$", "546$");
		music_definitions.put("546", "546");
		music_definitions.put("135246", "135246");
		music_definitions.put("123456", "123456");
		music_definitions.put("123564", "123564");
		
		Map<String, Integer> music = touch.getMusicalQualities(music_definitions);
		
		Assert.assertTrue("getMusicalQualities correctly identifies '546$' once", music.get("546$") == 1);
		Assert.assertTrue("getMusicalQualities correctly identifies '546' four times", music.get("546") == 4);
		Assert.assertTrue("getMusicalQualities correctly doesn't include a record for '135246'", music.get("135246") == null);
		//Assert.assertTrue("getMusicalQualities correctly ignores rounds from music", music.get("123456") == null);
		Assert.assertTrue("getMusicalQualities correctly identifies '123564' once", music.get("123564") == 1);
		
		
		
		
	}
}
