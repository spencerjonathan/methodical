package uk.co.methodical.database;

import java.util.List;

import javax.sql.DataSource;

import uk.co.methodical.Composition;
import uk.co.methodical.Method;

public interface CompositionDAO {
	/**
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);

	public Composition getComposition(Integer id) throws CompositionNotFoundException;

	/**
	 * This is the method to be used to list down all the records from the
	 * Student table.
	 */
	public List<Composition> listCompositions();

	public List<Composition> listCompositions(String search_string);

	/**
	 * This is the method to be used to delete a record from the Student table
	 * corresponding to a passed student id.
	 */
	public void delete(Integer id);

	/**
	 * This is the method to be used to update a record into the Student table.
	 */
	public void update(Composition composition);

	/**
	 * This is the method to be used to create a record in the composition table
	 * and return its id.
	 */

	public Integer addComposition(Composition composition);


}
