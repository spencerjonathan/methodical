package uk.co.methodical.database;

import org.junit.Assert;
import org.junit.Test;

import uk.co.methodical.Composition;
import uk.co.methodical.database.CompositionJDBCTemplate;
import uk.co.methodical.database.CompositionNotFoundException;

public class CompositionJDBCTemplateTest {

	@Test
	public void canSaveAComposition() { 
		Composition orig_comp = new Composition();
		orig_comp.setAuthor(1);
		orig_comp.setTitle("Simple Composition");
		orig_comp.setComposition("CALL B = 14\nCALL S = 1234\nMETHOD PBM = \"Plain Bob Minor\"\nCOMPOSITION = { PBM P P P P P }");
		
		orig_comp.setLength(60);
		orig_comp.setTrue(true);
		orig_comp.setCreated(new java.util.Date());
		
		CompositionJDBCTemplate jdbc = CompositionJDBCTemplate.instance();
		Integer id = jdbc.addComposition(orig_comp);
		Assert.assertTrue("AddComposition returns a positive id", id > 0);
		
		try {
			Composition ret_comp = jdbc.getComposition(id);
			Assert.assertTrue("getComposition returns the same Composition that was passed to addComposition", ret_comp.equals(orig_comp));
		} catch (CompositionNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
}
