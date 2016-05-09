package uk.co.methodical;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import uk.co.methodical.Call;
import uk.co.methodical.Method;
import uk.co.methodical.Part;
import uk.co.methodical.parser.Item;

public class PartTest {

	@Test
	public void canIterateOverASimplePart() {
		Part p = new Part("P1");
		
		
		ArrayList<Item> staged_al = new ArrayList();
		setupSimplePart(staged_al, p);
		
		ArrayList<Item> result_al = new ArrayList();
		
		for (Iterator<Item> i = p.iterator(); i.hasNext();) {
			result_al.add(i.next());
		}
		
		Assert.assertTrue("Part Iterator can correctly iterate over simple Part", result_al.equals(staged_al));
		
	}
	
	/*@Test
	public void canIterateOverAPartWithEmptySubPart() {
		Part p = new Part("P1");
		Part p2 = new Part("P2");
			
		ArrayList<Item> staged_al = new ArrayList();
		setupSimplePart(staged_al, p);
		
		p.add(p2);
		
		ArrayList<Item> result_al = new ArrayList();
		
		for (Iterator<Item> i = p.iterator(); i.hasNext();) {
			result_al.add(i.next());
		}
		
		Assert.assertTrue("Part Iterator can correctly iterate over Part with empty subpart", result_al.equals(staged_al));
		
	}*/
	
	@Test
	public void canIterateOverAPartWithNestedSubPart() {
		Part p = new Part("P1");
		Part p2 = new Part("P2");
			
		ArrayList<Item> staged_al = new ArrayList();
		setupSimplePart(staged_al, p);
		setupSimplePart(staged_al, p2);
		
		p.add(p2);
		
		ArrayList<Item> result_al = new ArrayList();
		
		for (Iterator<Item> i = p.iterator(); i.hasNext();) {
			result_al.add(i.next());
		}
		
		Assert.assertTrue("Part Iterator can correctly iterate over Part nested subpart", result_al.equals(staged_al));
		
	}
	
	private void setupSimplePart(ArrayList<Item> al, Part p) {
		
		Call c1 = new Call("B", "14");
		Call c2 = new Call("S", "1234");
		Method m1 = new Method();
		m1.setName("Plain Bob Major");

		al.add(m1);
		p.add(m1);
		
		al.add(c1);
		p.add(c1);
		
		al.add(c2);
		p.add(c2);
		
		al.add(c1);
		p.add(c1);
		
		al.add(c2);
		p.add(c2);
		
		return;
	}
}
