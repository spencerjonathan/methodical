package uk.co.methodical;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import uk.co.methodical.database.MethodNotFoundException;
import uk.co.methodical.parser.Item;

public class TouchFactory {

	private static int max_depth = 5;
	private int current_depth = 0;
	private TouchLibrary touch_library = null;

	public TouchFactory() {
		touch_library = new TouchLibrary();
	}

	public Touch createPlainCourse(int method_id) {

		Method method = MethodLibrary.instance().method(method_id);
		return createPlainCourse(method);
	}

	public Touch createPlainCourse(String method_title) throws MethodNotFoundException {

		Method method = MethodLibrary.instance().method(method_title);
		return createPlainCourse(method);

	}
	
	private Touch createPlainCourse(Method method) {
		
		Method[] method_list = new Method[1];
		method_list[0] = method;

		Touch touch = new Touch(method.getNumberOfBells());
		recursiveCreateTouch(touch, method_list, true, null);
		
		return touch_library.getPlainCourse();

	}

	public Touch createTouch(Item composition, Integer stage, boolean stopAtRounds) throws TouchException {

		Touch touch = new Touch(stage, stopAtRounds);

		for (Iterator<Item> i = composition.iterator(); i.hasNext();) {
			Item item = i.next();
			item.applyYourselfTo(touch);

			if (touch.comesRound() && stopAtRounds)
				break;
		}

		return touch;
	}

	private void recursiveCreateTouch(Touch touch, Method[] method_list, boolean plain_course_only,
			Call previous_call) {

		for (int m = 0; m < method_list.length; ++m) {
			Call[] call_list = method_list[m].getCalls();
			for (int c = -1; c < call_list.length; ++c) {

				LeadEnd new_le;
				Call call = null;
				if (c == -1) {
					call = null;
				} else {
					call = call_list[c];
				}

				try {
					new_le = LEFactory.createLE(touch.getLastLeadEnd(), method_list[m], previous_call, call, true,
							true);

					if (new_le.isRounds()) {
						touch.addLeadEnd(new_le);
						touch_library.addCopy(touch);
						if (plain_course_only)
							return;
						touch.removeLast();

					} else if (!touch.isRepetition(new_le) && current_depth < max_depth) {
						touch.addLeadEnd(new_le);
						++current_depth;
						recursiveCreateTouch(touch, method_list, plain_course_only, call);
						if (plain_course_only)
							return;

						--current_depth;
						touch.removeLast();

					}

				} catch (UnusedCall e) {
					// Do Nothing
					// e.printStackTrace();

				}

			}
		}
	}

	public Map<Long, ArrayList<Touch>> createTouches(Integer[] methods) {
		Method[] method_list = new Method[methods.length];

		for (int i = 0; i < methods.length; ++i) {
			method_list[i] = MethodLibrary.instance().method(methods[i]);
		}

		Touch touch = new Touch(method_list[0].getNumberOfBells());

		recursiveCreateTouch(touch, method_list, false, null);

		return touch_library.getTouch_set();
	}

}