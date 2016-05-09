package uk.co.methodical.ws;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import uk.co.methodical.BaseTest;
import uk.co.methodical.IntegrationTests;
import uk.co.methodical.TouchException;
import uk.co.methodical.database.MethodNotFoundException;
import uk.co.methodical.parser.ParseException;
import uk.co.methodical.parser.Dictionary.DictionaryException;
import uk.co.methodical.ws.MethodsController;
import uk.co.methodical.ws.Music;
import uk.co.methodical.ws.ParseRequest;
import uk.co.methodical.ws.TouchListItem;

public class MethodsControllerTest extends BaseTest {

	@Test
	@Category(uk.co.methodical.IntegrationTests.class)
	public void methodsControllerCanCreateTouch() {
		MethodsController controller = new MethodsController();

		ParseRequest request = new ParseRequest();

		String composition = "METHOD M1 = \"St Simon's Bob Doubles\" \nCALL B = 14\nPART P1 = { B B B P }\nCOMPOSITION = { M1 P1 P1 P1 }";
		String music = "\"Queens\" = 13524\n\"Titums\" = 14253\n\"Rollup\" = 45$";

		request.setComposition(composition);
		request.setMusic(music);

		TouchListItem response = null;

		response = (TouchListItem) controller.parse(request);

		Assert.assertTrue(
				"MethodsController can create 9 bob touch of St Simon's Bob Doubles without throwing exception",
				response != null);
		Assert.assertTrue("MethodsController can create 9 bob touch of St Simon's Bob Doubles and it comes round",
				response.getComesRound());
		Assert.assertFalse(
				"MethodsController can create 9 bob touch of St Simon's Bob Doubles and it isn't repetitious",
				response.isRepetitious());
		Assert.assertTrue(
				"MethodsController can create 9 bob touch of St Simon's Bob Doubles and it reports the correct length",
				response.getTouch_length() == 120);

		Map<String, Integer> correct_music = new HashMap<String, Integer>();
		correct_music.put("\"Queens\"", 1);
		correct_music.put("\"Titums\"", 1);
		correct_music.put("\"Rollup\"", 6);

		Music[] response_music = response.getMusic();
		for (Music m : response_music) {
			Assert.assertTrue(
					"MethodsController can identify " + m.getName() + " in 9 bob touch of St Simon's Bob Doubles",
					m.getOccurances() == correct_music.get(m.getName()));
		}
	}

	@Test
	@Category(uk.co.methodical.IntegrationTests.class)
	public void methodsControllerCanCreateTouchWithoutStoppingAtRounds() {
		MethodsController controller = new MethodsController();

		ParseRequest request = new ParseRequest();

		String composition = "CALL B = 14\nCALL S = 1234\nMETHOD PBM = \"Plain Bob Minor\"\nCOMPOSITION = { PBM B B B P P P P P }";
		String music = "\"Queens\" = 13524\n\"Titums\" = 14253\n\"Rollup\" = 45$";

		request.setComposition(composition);
		request.setMusic(music);
	
		TouchListItem response = null;

		request.setStopAtRounds(false);
		response = (TouchListItem) controller.parse(request);
		Assert.assertEquals("MethodsController can create a touch that doesn't stop at rounds", response.getTouch_length(), 96);
		
		request.setStopAtRounds(true);
		response = (TouchListItem) controller.parse(request);
		Assert.assertEquals("MethodsController can create a touch that does stop at rounds", response.getTouch_length(), 36);
	}
}
