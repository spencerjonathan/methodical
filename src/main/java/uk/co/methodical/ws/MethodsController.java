package uk.co.methodical.ws;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.imageio.ImageIO;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uk.co.methodical.Call;
import uk.co.methodical.Composition;
import uk.co.methodical.ImageCreator;
import uk.co.methodical.Method;
import uk.co.methodical.MethodLibrary;
import uk.co.methodical.Touch;
import uk.co.methodical.TouchException;
import uk.co.methodical.TouchFactory;
import uk.co.methodical.database.CompositionJDBCTemplate;
import uk.co.methodical.database.CompositionNotFoundException;
import uk.co.methodical.database.MethodJDBCTemplate;
import uk.co.methodical.database.MethodNotFoundException;
import uk.co.methodical.file.TowerImporter;
import uk.co.methodical.parser.CompositionParser;
import uk.co.methodical.parser.Dictionary;
import uk.co.methodical.parser.Item;
import uk.co.methodical.parser.MusicParser;
import uk.co.methodical.parser.ParseException;
import uk.co.methodical.parser.Dictionary.DictionaryException;
import uk.co.methodical.xml.CCFileImporter;

@RestController
/* @RequestMapping("/ws") */
public class MethodsController {

	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/getMethodByTitle")
	public String[] getMethodByTitle(@RequestParam(value = "searchString", required = true) String searchString) {

		List<String> methods = MethodJDBCTemplate.instance().getMethodTitles("%" + searchString + "%");
		return (String[]) methods.toArray(new String[methods.size()]);
	}

	@RequestMapping("/getMethodList")
	public MethodList getMethodList(@RequestParam(value = "searchString", required = false) String searchString,
			@RequestParam(value = "numberOfBells", required = true) Integer numberOfBells,
			@RequestParam(value = "favouritesOnly", required = false, defaultValue = "0") Boolean favouritesOnly) {

		if (searchString == null) {
			searchString = "";
		}

		List<Method> methods = MethodLibrary.instance().findMethodNames(searchString, numberOfBells, favouritesOnly, 1);

		MethodListItem[] items = new MethodListItem[methods.size()];
		int iteration = 0;

		for (Iterator<Method> i = methods.iterator(); i.hasNext();) {
			Method m = i.next();

			Call bob = null;
			if (m.getCalls().length >= 1)
				bob = m.getCalls()[0];
			String bob_place_notation = null;
			if (bob != null)
				bob_place_notation = bob.getNotation_string();

			Call single = null;
			if (m.getCalls().length >= 2)
				single = m.getCalls()[1];
			String single_place_notation = null;
			if (single != null)
				single_place_notation = single.getNotation_string();

			m.getCalls()[0].getNotation_string();
			items[iteration++] = new MethodListItem(m.getId(), m.getName(), m.getNumberOfBells(),
					m.getNotation_string(), bob_place_notation, single_place_notation, false);
		}

		return new MethodList(counter.incrementAndGet(), numberOfBells, items);
	}

	@RequestMapping(value = "/createTouches", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public TouchList createTouches(@RequestBody Integer[] methods) {

		TouchFactory factory = new TouchFactory();
		Map<Long, ArrayList<Touch>> l = factory.createTouches(methods);

		return new TouchList(l);

	}

	@RequestMapping(value = "/createImage", method = RequestMethod.GET, produces = "image/png")
	public byte[] createImage(@RequestParam(value = "methodName") String methodName) throws MethodNotFoundException {

		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();

		TouchFactory factory = new TouchFactory();
		Touch plain_course = factory.createPlainCourse(methodName);

		BufferedImage img = ImageCreator.CreateImage(plain_course);

		try {
			ImageIO.write(img, "png", outputstream);
		} catch (IOException e) {

			e.printStackTrace();
		}

		return outputstream.toByteArray();
	}

	@RequestMapping(value = "/createMethod")
	public String createMethod(@RequestParam(value = "methodName") String methodName,
			@RequestParam(value = "numberOfBells") Integer numberOfBells,
			@RequestParam(value = "methodPlaceNotation") String methodPlaceNotation,
			@RequestParam(value = "bobPlaceNotation") String bobPlaceNotation,
			@RequestParam(value = "singlePlaceNotation") String singlePlaceNotation,
			@RequestParam(value = "id", required = false) Integer id)

	{
		if (id != null) {
			MethodLibrary.instance().updateMethod(id, methodName, numberOfBells, methodPlaceNotation, bobPlaceNotation,
					singlePlaceNotation);
		} else
			MethodLibrary.instance().addMethod(methodName, numberOfBells, methodPlaceNotation, bobPlaceNotation,
					singlePlaceNotation);

		return methodName + " was successfully added";

	}

	@RequestMapping(value = "/importMethods")
	public void createMethod(@RequestParam(value = "fileName") String fileName) {

		CCFileImporter.importFile(fileName);

	}

	@RequestMapping(value = "/setFavourite", method = RequestMethod.GET)
	public String setFavourite(@RequestParam(value = "methodId") Integer methodId,
			@RequestParam(value = "favourite") Boolean favourite) {

		MethodLibrary.instance().setFavourite(1, methodId, favourite);

		if (favourite)
			return "Method id: " + methodId + " added to favourites";
		else
			return "Method id: " + methodId + " removed from favourites";

	}

	private Touch createTouch(String composition, boolean stopAtRounds) throws ExceptionItem {
		Dictionary composition_dictionary;
		try {
			composition_dictionary = CompositionParser.parse(composition);
		} catch (ParseException e) {

			throw new ExceptionItem("Could not parse the Touch definition.  " + e.getMessage());
		} catch (DictionaryException e) {

			throw new ExceptionItem(e.getMessage());
		} catch (MethodNotFoundException e) {

			throw new ExceptionItem(e.getMessage());
		}
		Item comp = composition_dictionary.get("COMPOSITION");

		for (Iterator<Item> i = comp.iterator(); i.hasNext();) {
			System.out.println(i.next().toString());
		}

		TouchFactory touchFactory = new TouchFactory();
		Touch touch;
		try {
			touch = touchFactory.createTouch(comp, composition_dictionary.getMax_stage(), stopAtRounds);
		} catch (TouchException e) {

			e.printStackTrace();
			throw new ExceptionItem(e.getMessage());
		}
		
		return touch;
	}
	
	@RequestMapping(value = "/parse", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ReturnItem parse(@RequestBody ParseRequest request) {

		Touch touch;
		try {
			touch = createTouch(request.getComposition(), request.isStopAtRounds());
		} catch (ExceptionItem e1) {
			return e1;
		}

		Map<String, String> music_definitions;
		try {
			music_definitions = MusicParser.parse(request.getMusic());
		} catch (ParseException e) {

			return new ExceptionItem("Could not parse the Music definition.  " + e.getMessage());
		}
		
		Map<String, Integer> musical_qualities = touch.getMusicalQualities(music_definitions);

		return new TouchListItem(touch, musical_qualities);
	}

	@RequestMapping(value = "/saveComposition", method = RequestMethod.POST, consumes = "text/html", produces = "application/json")
	public int saveComposition(@RequestParam String title, @RequestParam boolean stopAtRounds, @RequestBody String composition) {
		Composition comp = new Composition();
		comp.setAuthor(1);
		comp.setComposition(composition);
		comp.setTitle(title);
		
		Touch touch;
		try {
			touch = createTouch(composition, stopAtRounds);
		} catch (ExceptionItem e1) {
			return -1;
		}

		comp.setLength(touch.getLength());
		comp.setTrue(touch.getRepetitiveChanges().length == 0);
		comp.setCreated(new java.util.Date());
		
		return CompositionJDBCTemplate.instance().addComposition(comp);
	}

	@RequestMapping(value = "/getComposition", method = RequestMethod.GET, produces = "application/json")
	public Composition saveComposition(@RequestParam Integer id) throws CompositionNotFoundException {
		return CompositionJDBCTemplate.instance().getComposition(id);

	}

	@RequestMapping("/getCompositionByTitle")
	public Composition[] getCompositionByTitle(
			@RequestParam(value = "searchString", required = true) String searchString) {

		List<Composition> methods = CompositionJDBCTemplate.instance().getCompositionsByTitle("%" + searchString + "%");
		return (Composition[]) methods.toArray(new Composition[methods.size()]);
	}

	@RequestMapping("/getCompositions")
		public Composition[] getCompositions(@RequestParam(value = "searchString", required = false) String searchString,
				@RequestParam(value = "author", required = false) String author,
				@RequestParam(value = "lengthMin", required = false) Integer lengthMin,
				@RequestParam(value = "lengthMax", required = false) Integer lengthMax,
				@RequestParam(value = "createdMin", required = false) String createdMin,
				@RequestParam(value = "createdMax", required = false) String createdMax
				) {

			List<Composition> methods = CompositionJDBCTemplate.instance().getCompositions("%" + searchString + "%", "%" + author + "%", lengthMin, lengthMax, createdMin, createdMax);
			return (Composition[]) methods.toArray(new Composition[methods.size()]);
		}

	@RequestMapping(value = "/importTowers")
	public void importTowers(@RequestParam(value = "fileName") String fileName) throws IOException {

		TowerImporter.importFile(fileName);

	}

}
