package uk.co.methodical.database;

import java.util.List;

import javax.sql.DataSource;

import uk.co.methodical.Method;

public interface MethodDAO {
	/**
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);

	/**
	 * This is the method to be used to create a record in the Method table.
	 */
	public Method create(String name, Integer number_of_bells,
			String place_notation, String bob_place_notation,
			String single_place_notation);

	/**
	 * This is the method to be used to list down a record from the Method table
	 * corresponding to a passed method id.
	 */
	public Method getMethod(Integer id);

	/**
	 * This is the method to be used to list down a record from the Method table
	 * corresponding to a passed method name and number of bells.
	 */
	public Method getMethod(String name, Integer number_of_bells);
	
	public Method getMethod(String name) throws MethodNotFoundException;

	/**
	 * This is the method to be used to list down all the records from the
	 * Student table.
	 */
	public List<Method> listMethods();

	public List<Method> listMethods(String search_string, int number_of_bells, int userId);

	/**
	 * This is the method to be used to delete a record from the Student table
	 * corresponding to a passed student id.
	 */
	public void delete(Integer id);

	/**
	 * This is the method to be used to update a record into the Student table.
	 */
	public void update(Integer id, String name, Integer number_of_bells,
			String place_notation, String bob_place_notation,
			String single_place_notation);

	/**
	 * This is the method to be used to create a record in the CCMethodSet table
	 * and return its id.
	 */
	public Integer addCCMethodSet(Integer stage, Integer lengthOfLead,
			Integer numberOfHunts, String huntBellPath, String symmetry);

	public Integer addCCMethod(String ccId, String name, String classification,
			boolean plain, boolean little, String title, String notation, String leadHead,
			Integer methodSetId);

	public void setFavourite(Integer userId, Integer methodId, Boolean favourite);

	public List<Method> listFavouriteMethods(String search_string,
			int number_of_bells, Integer userId);

}
