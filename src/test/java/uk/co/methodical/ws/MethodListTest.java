package uk.co.methodical.ws;

import org.junit.Assert;
import org.junit.Test;
import uk.co.methodical.BaseTest;

public class MethodListTest extends BaseTest {

	@Test
	public void canConstructMethodList() {
		MethodListItem mli = createMethodListItem();
		MethodListItem[] items = { mli };
		
		long id = 100;
		
		MethodList ml = new MethodList(id, number_of_bells, items);
		
		Assert.assertTrue("MethodList can return the Id that it was constructed with", id == ml.getId());
		Assert.assertTrue("MethodList can return the Number Of Bells that it was constructed with", number_of_bells == ml.getNumber_of_bells());
		Assert.assertTrue("MethodList can return the MethodListItems that it was constructed with (test 1)", ml.getItems().length == 1);
		Assert.assertTrue("MethodList can return the MethodListItems that it was constructed with (test 2)", ml.getItems()[0].equals(mli));
	}
}

