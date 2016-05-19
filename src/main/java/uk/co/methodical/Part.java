package uk.co.methodical;

import java.util.ArrayList;
import java.util.Iterator;

import uk.co.methodical.parser.Item;

public class Part extends Item {

	ArrayList<Item> items;
	//private int max_stage = 0;

	public class PartIterator implements Iterator<Item> {

		private Part part;
		private Iterator<Item> list_iterator;
		private Iterator<Item> part_iterator;
		private Item current_item;

		public PartIterator(Part part) {
			this.part = part;
			list_iterator = part.items.iterator();
			part_iterator = null;
			current_item = null;
		}

		@Override
		public boolean hasNext() {

			if (current_item == null) {
				return list_iterator.hasNext();
			}

			if (part_iterator == null) {
				part_iterator = current_item.iterator();
			}

			if (part_iterator != null) {
				if (part_iterator.hasNext()) return true;
			}

			return list_iterator.hasNext();

		}

		@Override
		public Item next() {

			if (part_iterator != null) {
				if (part_iterator.hasNext())
					return part_iterator.next();
				else {
					current_item = list_iterator.next();
					part_iterator = current_item.iterator();

					if (part_iterator == null)
						return current_item;
					else
						return this.next();

					/*
					 * // TO DO: Need to protect against empty parts as hasNext
					 * will give a false reading. if (part_iterator != null) {
					 * if (part_iterator.hasNext()) return part_iterator.next();
					 * 
					 * // Recursive call to this method else return this.next();
					 * } else return current_item;
					 */
				}
			} else {
				current_item = list_iterator.next();
				part_iterator = current_item.iterator();

				if (part_iterator != null && part_iterator.hasNext()) {
					return part_iterator.next();
				} else
					return current_item;
			}
		}

		@Override
		public void remove() {
			// Do nothing - no need for this function

		}

	}

	public Part(String id) {
		super(id);
		items = new ArrayList<Item>();
	}

	public Iterator<Item> iterator() {
		return new PartIterator(this);
	}

	public void add(Item item) {
		items.add(item);

	}
	
/*	public void add(Item item, int stage) {
		items.add(item);
		if (stage > max_stage) max_stage = stage;
	}
*/
	@Override
	public void applyYourselfTo(Touch touch) throws TouchException {
		// Should never be executed
		throw new RuntimeException("Part.applyYourselfTo() called");
		
	}

}
