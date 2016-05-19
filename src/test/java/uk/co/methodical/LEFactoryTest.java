package uk.co.methodical;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import uk.co.methodical.Call;
import uk.co.methodical.LEFactory;
import uk.co.methodical.LeadEnd;
import uk.co.methodical.Method;
import uk.co.methodical.UnusedCall;



public class LEFactoryTest extends BaseTest {
	
	@Test
	public void canThrowUnusedCallExceptionWhenNeeded() {
		int [] rounds_next = {1, 3, 2, 5, 4, 6};
		Method plain_bob_minor = createPlainBobMinorMethod();

		Call bob = new Call("B", "14");
		
		LeadEnd start = new LeadEnd(rounds_next, null, null);
		LeadEnd created_le = null;
		
		try {
			created_le = LEFactory.createLE(start, plain_bob_minor, null, bob, true, true);
		} catch (UnusedCall e) {
			// Do Nothing
		}
		
		Assert.assertNull("Throws an UnusedCall exception wen needed", created_le);
	}
	
	@Test
	public void canGenerateALeadWithAnUnusedCall() {
		int [] rounds_next = {1, 3, 2, 5, 4, 6};
		Method plain_bob_minor = createPlainBobMinorMethod();

		Call bob = new Call("B", "14");
		
		LeadEnd start = new LeadEnd(rounds_next, null, null);
		LeadEnd created_le = null;
		
		try {
			created_le = LEFactory.createLE(start, plain_bob_minor, null, bob, false, true);
		} catch (UnusedCall e) {
			// Do Nothing
		}
		
		Assert.assertNotNull("Can create a LeadEnd without throwing UnusedCall exception", created_le);
		Assert.assertTrue("Can create LeadEnd that comes round without a call", LeadEnd.isRounds(created_le.getBell_order()));
	}
	
	@Test
	public void canGenerateAnEvenBellLeadWithACall() {
		int [] rounds_next = {1, 3, 2, 6, 4, 5};
		Method plain_bob_minor = createPlainBobMinorMethod();

		Call single = new Call("S", "1234");
		
		LeadEnd start = new LeadEnd(rounds_next, null, null);
		LeadEnd created_le = null;
		try {
			created_le = LEFactory.createLE(start, plain_bob_minor, null, single, false, true);
		} catch (UnusedCall e) {
			// Do Nothing
		}

		Assert.assertNotNull("Can create LeadEnd that comes round with a single and not throwing UnusedCall exception", created_le);
		Assert.assertTrue("Can create LeadEnd that comes round with a single", LeadEnd.isRounds(created_le.getBell_order()));

	}

	public void canGenerateAnOddBellLeadWithACall() {
		int [] start_position = {1, 4, 5, 3, 2};
		Method st_simons_doubles = createStSimonsDoublesMethod();

		Call bob = new Call("B", "14");
		
		LeadEnd start = new LeadEnd(start_position, null, null);
		LeadEnd created_le = null;
		try {
			created_le = LEFactory.createLE(start, st_simons_doubles, null, bob, false, true);
		} catch (UnusedCall e) {
			// Do Nothing
		}

		Assert.assertNotNull("Can create LeadEnd that comes round with a single and not throwing UnusedCall exception", created_le);
		Assert.assertTrue("Can create LeadEnd in a doubles method with a bob", LeadEnd.isRounds(created_le.getBell_order()));

	}

	@Test
	public void canGenerateALeadWithLowerStageWithoutCall() {
		int [] start_position = {1, 3, 5, 2, 4, 6};
		Method st_simons_doubles = createStSimonsDoublesMethod();

		//Call bob = new Call("B", "14");
		
		LeadEnd start = new LeadEnd(start_position, null, null);
		LeadEnd created_le = null;
		try {
			created_le = LEFactory.createLE(start, st_simons_doubles, null, null, false, true);
		} catch (UnusedCall e) {
			// Do Nothing
		}

		Assert.assertNotNull("Can create LeadEnd from a previous one with more bells that comes round", created_le);
		Assert.assertTrue("Can create LeadEnd from a previous one with more bells that comes round", LeadEnd.isRounds(created_le.getBell_order()));

	}
	
	@Test
	public void canGenerateALeadWithLowerStageWithCall() {
		int [] start_position = {1, 4, 5, 3, 2, 6};
		Method st_simons_doubles = createStSimonsDoublesMethod();

		Call bob = new Call("B", "14");
		
		LeadEnd start = new LeadEnd(start_position, null, null);
		LeadEnd created_le = null;
		try {
			created_le = LEFactory.createLE(start, st_simons_doubles, null, bob, false, true);
		} catch (UnusedCall e) {
			// Do Nothing
		}

		Assert.assertNotNull("Can create LeadEnd from a previous one with more bells that comes round with a bob", created_le);
		Assert.assertTrue("Can create LeadEnd from a previous one with more bells that comes round with a bob", LeadEnd.isRounds(created_le.getBell_order()));

	}
	
	@Test
	public void canGenerateALeadWithoutACall() {
		int [] rounds_next = {1, 4, 2, 6, 3, 5};
		Method plain_bob_minor = createPlainBobMinorMethod();
		
		LeadEnd start = new LeadEnd(rounds_next, null, null);
		LeadEnd created_le = null;
		try {
			created_le = LEFactory.createLE(start, plain_bob_minor, null, null, false, true);
		} catch (UnusedCall e) {
			// Do Nothing
		}

		Assert.assertNotNull("Can create LeadEnd that comes round with no call and not throwing UnusedCall exception", created_le);
		Assert.assertTrue("Can create LeadEnd that comes round with no call", LeadEnd.isRounds(created_le.getBell_order()));

	}
	
	@Test
	public void canGenerateALeadWithALongCall() {
		int [] start_order = {1, 2, 3, 4, 5};
		Method grandsire_doubles = createGrandsireDoublesMethod();
		
		LeadEnd start = new LeadEnd(start_order, null, null);
		
		LeadEnd created_le1 = null;
		LeadEnd created_le2 = null;
		int[] le1_order = { 1, 4, 5, 2, 3};
		LeadEnd staged_le1 = new LeadEnd(le1_order, null, null);
		
		// With a 3 change single
		try {
			Call single_3 = new Call("B", "3.1,5");
			created_le1 = LEFactory.createLE(start, grandsire_doubles, null, single_3, false, true);
			created_le2 = LEFactory.createLE(start, grandsire_doubles, single_3, null, false, true);
		} catch (UnusedCall e) {
			// Do Nothing
		}
		
		Assert.assertTrue("Can create LeadEnd with a 3-change single", created_le1.equals(staged_le1));
		Assert.assertTrue("Can create LeadEnd with a 3-change single previous", created_le2.isRounds());
		
		// Now with a 2 change single
		try {
			Call single_2 = new Call("B", "3.1");
			created_le1 = LEFactory.createLE(start, grandsire_doubles, null, single_2, false, true);
			created_le2 = LEFactory.createLE(start, grandsire_doubles, single_2, null, false, true);
		} catch (UnusedCall e) {
			// Do Nothing
		}
		int[] le3_order = { 1, 2, 5, 3, 4};
		LeadEnd staged_le3 = new LeadEnd(le3_order, null, null);
		
		Assert.assertTrue("Can create LeadEnd with a 2-change single", created_le1.equals(staged_le1));
		Assert.assertTrue("Can create LeadEnd with a 2-change single previous", created_le2.equals(staged_le3));
		
	}
	
}
