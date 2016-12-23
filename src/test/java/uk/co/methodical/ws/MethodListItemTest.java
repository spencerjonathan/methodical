package uk.co.methodical.ws;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.co.methodical.BaseTest;

public class MethodListItemTest extends BaseTest {

	@Test
	public void testThatAMethodListItemCanBeConstructed() {
		MethodListItem mli = createMethodListItem();
		
		assertEquals("MethodListItem can return the Id that it was constructed with", Id, mli.getId());
		assertEquals("MethodListItem can return the name that it was constructed with", name, mli.getName());
		assertEquals("MethodListItem can return the number of beels that it was constructed with", number_of_bells, mli.getNumber_of_bells());
		assertEquals("MethodListItem can return the place notation that it was constructed with", place_notation, mli.getPlace_notation());
		assertEquals("MethodListItem can return the bob place notation that it was constructed with", bob_place_notation, mli.getBob_notation());
		assertEquals("MethodListItem can return the single place notation that it was constructed with", single_place_notation, mli.getSingle_notation());
		assertEquals("MethodListItem can return the favourite flag that it was constructed with", favourite, mli.isFavourite());
	}

}
