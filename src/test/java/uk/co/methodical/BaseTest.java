package uk.co.methodical;

import uk.co.methodical.Method;
import uk.co.methodical.ws.MethodListItem;

public class BaseTest {
	
	protected Integer Id = 20;
	protected String name = "Test Name";
	protected Integer number_of_bells = 8;
	protected String place_notation = "Test Notation";
	protected String bob_place_notation = "Test Bob Notation";
	protected String single_place_notation = "Test Single Notation";
	protected boolean favourite = true;
	
	protected Method createPlainBobMinorMethod() {
		Method plain_bob_minor = new Method();
		plain_bob_minor.setName("Plain Bob Minor");
		plain_bob_minor.setNumber_of_bells(6);
		plain_bob_minor.setNotation("X.16.X.16.X.16.X.16.X.16.X.12");
		
		return plain_bob_minor;
	}
	
	protected Method createGrandsireDoublesMethod() {
		Method grandsire_doubles = new Method();
		grandsire_doubles.setName("Grandsire Doubles");
		grandsire_doubles.setNumber_of_bells(5);
		grandsire_doubles.setNotation("3.1.5.1.5.1.5.1.5.1");
		
		return grandsire_doubles;
	}
	
	protected Method createStSimonsDoublesMethod() {
		Method grandsire_doubles = new Method();
		grandsire_doubles.setName("Grandsire Doubles");
		grandsire_doubles.setNumber_of_bells(5);
		grandsire_doubles.setNotation("5.1.5.3.5.3.5.1.5.125");
		
		return grandsire_doubles;
	}
	
	protected MethodListItem createMethodListItem() {
		return new MethodListItem(Id, name, number_of_bells, place_notation, bob_place_notation,
				single_place_notation, favourite);
	}
}
