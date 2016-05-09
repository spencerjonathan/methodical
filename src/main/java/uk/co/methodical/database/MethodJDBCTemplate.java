package uk.co.methodical.database;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import uk.co.methodical.Method;

public class MethodJDBCTemplate implements MethodDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private static MethodJDBCTemplate self = null;

	public static MethodJDBCTemplate instance() {

		if (self == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			self = (MethodJDBCTemplate) context.getBean("methodJDBCTemplate");

			((AbstractApplicationContext) context).registerShutdownHook();
		}
		return self;
	}

	@Override
	public void setDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}

	@Override
	public Method create(String name, Integer number_of_bells, String place_notation, String bob_place_notation,
			String single_place_notation) {
		String SQL = "insert into method (name, number_of_bells, place_notation, bob_place_notation, single_place_notation) values (?, ?, ?, ?, ?)";

		return getMethod(name, number_of_bells);
	}

	@Override
	public Method getMethod(Integer id) {
		// String SQL = "select * from Method where id = ?";
		String SQL = "call GetMethod(?)";
		Method method = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new MethodMapper());
		return method;
	}

	@Override
	public List<Method> listMethods() {
		String SQL = "select * from Method";
		List<Method> methods = jdbcTemplateObject.query(SQL, new MethodMapper());
		return methods;
	}

	@Override
	public void delete(Integer id) {

		String SQL = "delete from Method where id = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted Record with ID = " + id);
		return;

	}

	@Override
	public void update(Integer id, String name, Integer number_of_bells, String place_notation,
			String bob_place_notation, String single_place_notation) {

		String SQL = "update Method set name = ?, number_of_bells=?, place_notation = ?, bob_place_notation = ?, single_place_notation = ? where id = ?";
		jdbcTemplateObject.update(SQL, name, number_of_bells, place_notation, bob_place_notation, single_place_notation,
				id);
		System.out.println("Updated Record with ID = " + id);
		return;

	}

	@Override
	public Method getMethod(String name, Integer number_of_bells) {
		// String SQL = "select * from Method where name = ? and number_of_bells
		// = ?";
		String SQL = "call GetMethods(?,?)";
		Method method = jdbcTemplateObject.queryForObject(SQL, new Object[] { name, number_of_bells },
				new MethodMapper());
		return method;
	}

	@Override
	public List<Method> listMethods(String search_string, int number_of_bells, int userId) {

		// String SQL = "select * from Method where number_of_bells = ? and name
		// like ?";
		String SQL = "call GetMethods(?,?, ?)";
		List<Method> methods = jdbcTemplateObject.query(SQL,
				new Object[] { "%" + search_string + "%", number_of_bells, userId }, new MethodMapper());

		return methods;
	}

	@Override
	public List<Method> listFavouriteMethods(String search_string, int number_of_bells, Integer userId) {

		String SQL = "call GetFavouriteMethods(?,?,?)";
		List<Method> methods = jdbcTemplateObject.query(SQL,
				new Object[] { "%" + search_string + "%", number_of_bells, userId }, new MethodMapper());

		return methods;

	}

	@Override
	public Integer addCCMethodSet(Integer stage, Integer lengthOfLead, Integer numberOfHunts, String huntBellPath,
			String symmetry) {

		String SQL = "call AddMethodSet(?,?,?,?,?)";
		try {
			List<Integer> ids = jdbcTemplateObject.query(SQL,
					new Object[] { stage, lengthOfLead, numberOfHunts, "", symmetry }, new IdMapper());

			if (ids.size() != 1) {
				System.err.println(
						"Error: number of records expected from procedure AddMethodSet is 1.  Received: " + ids.size());
			}

			return ids.get(0);

		} catch (Exception e) {
			System.err.println("Problem occurred inserting CCMethodSet with values stage = " + stage
					+ ", lengthOfLead = " + lengthOfLead + ", numberOfHunts = " + numberOfHunts + ", huntBellPath = '"
					+ huntBellPath + "', symmetry = '" + symmetry + "'");
		}
		
		return -1;

	}

	@Override
	public Integer addCCMethod(String ccId, String name, String classification, boolean plain, boolean little,
			String title, String notation, String leadHead, Integer methodSetId) {
		

		String SQL = "call AddMethod(?,?,?,?,?,?,?,?,?)";
		try {
		List<Integer> ids = jdbcTemplateObject.query(SQL,
				new Object[] { ccId, name, classification, plain, title, notation, leadHead, methodSetId, little },
				new IdMapper());

		if (ids.size() != 1) {
			System.err.println(
					"Error: number of records expected from procedure AddMethod is 1.  Received: " + ids.size());
		}

		return ids.get(0);
		} catch (Exception e) {
			System.err.println("Problem occurred inserting CCMethod with values ccId = '" + ccId + "', name = '" + name + "', classification = '" + classification + 
					"', plain = " + plain + ", little = " + little + ", title = '" + title + "', notation = '" + notation + "', leadHead = '"
					+ leadHead + "', methodSetId = " + methodSetId);
		}
		
		return -1;
	}

	@Override
	public void setFavourite(Integer userId, Integer methodId, Boolean favourite) {
		// TODO Auto-generated method stub
		String SQL = "call SetFavouriteMethod(?,?,?)";

		jdbcTemplateObject.update(SQL, new Object[] { userId, methodId, favourite });
	}

	@Override
	public Method getMethod(String title) throws MethodNotFoundException {
		title = title.replaceAll("^\"|\"$", "");
		System.out.println("Searching for method with the title: " + title);
		String SQL = "call GetMethodByTitle(?, ?)";
		List<Method> methods = jdbcTemplateObject.query(SQL, new Object[] { title, new Integer(1) },
				new MethodMapper());
		if (methods.size() != 1)
			throw new MethodNotFoundException("Could not find method with title '" + title + "'");
		return methods.get(0);
	}

	public List<String> getMethodTitles(String title) {
		title = title.replaceAll("^\"|\"$", "");
		System.out.println("Searching for methods with the title: " + title);
		String SQL = "call GetMethodByTitle(?, ?)";
		List<String> methodNames = jdbcTemplateObject.query(SQL, new Object[] { title, new Integer(1) },
				new MethodNameMapper());
		return methodNames;
	}

}
