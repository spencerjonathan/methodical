package uk.co.methodical;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import org.junit.Assert;


import uk.co.methodical.Touch;
import uk.co.methodical.database.MethodNotFoundException;

public class TouchFactoryTest extends BaseTest {

	@Test
	@Category(uk.co.methodical.IntegrationTests.class)
	public void methodsControllerCanCreatePlainCourse() {
		TouchFactory factory = new TouchFactory();
		
		int method_id = 1;
		Touch touch = null;
		try {
			touch = factory.createPlainCourse("Plain Bob Doubles");
		} catch (MethodNotFoundException e) {
			e.printStackTrace();
			Assert.assertTrue("TouchFactory can instanciate method 'Plain Bob Doubles'", false);
		}
		
		ArrayList<LeadEnd> lead_ends = touch.getLead_ends();
		
		Assert.assertTrue("TouchFactory can create a plain course of Plain Bob Doubles with the right number of LeadEnds", lead_ends.size() == 5);
		
		
		int[] actual_lead_end_order = lead_ends.get(1).getRows().get(lead_ends.get(1).getRows().size()-1);
		int[] expected_lead_end_order1 = {1, 3, 5, 2, 4 };
		Assert.assertArrayEquals(actual_lead_end_order, expected_lead_end_order1);
		
		actual_lead_end_order = lead_ends.get(2).getRows().get(lead_ends.get(2).getRows().size()-1);
		int[] expected_lead_end_order2 = {1, 5, 4, 3, 2 };
		Assert.assertArrayEquals(actual_lead_end_order, expected_lead_end_order2);
		
		actual_lead_end_order = lead_ends.get(3).getRows().get(lead_ends.get(3).getRows().size()-1);
		int[] expected_lead_end_order3 = {1, 4, 2, 5, 3 };
		Assert.assertArrayEquals(actual_lead_end_order, expected_lead_end_order3);
		
		actual_lead_end_order = lead_ends.get(4).getRows().get(lead_ends.get(4).getRows().size()-1);
		int[] expected_lead_end_order4 = {1, 2, 3, 4, 5 };
		Assert.assertArrayEquals(actual_lead_end_order, expected_lead_end_order4);		
		
	}
	
}
