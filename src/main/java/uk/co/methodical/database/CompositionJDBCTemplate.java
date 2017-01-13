package uk.co.methodical.database;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import uk.co.methodical.Composition;

public class CompositionJDBCTemplate implements CompositionDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private static CompositionJDBCTemplate self = null;

	public static CompositionJDBCTemplate instance() {

		if (self == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext("DataSource.xml");

			self = new CompositionJDBCTemplate();
			// self.setDataSource((DataSource) context.getBean("dataSource"));

			Map<String, String> env = System.getenv();

			DriverManagerDataSource dataSource = (DriverManagerDataSource) context.getBean("dataSource");
			if (env.get("OPENSHIFT_MYSQL_DB_HOST") != null && env.get("OPENSHIFT_MYSQL_DB_PORT") != null) {

				String url = "jdbc:mysql://" + env.get("OPENSHIFT_MYSQL_DB_HOST") + ":"
						+ env.get("OPENSHIFT_MYSQL_DB_PORT") + "/methodical";
				dataSource.setUrl(url);
			}

			self.setDataSource(dataSource);

			((AbstractApplicationContext) context).registerShutdownHook();
		}
		return self;
	}

	@Override
	public void setDataSource(DataSource dataSource) {

		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(this.dataSource);
	}

	@Override
	public List<Composition> listCompositions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Composition> listCompositions(String search_string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Composition composition) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer addComposition(Composition composition) {
		String SQL = "call AddComposition(?, ?, ?, ?, ?, ? )";

		Integer id = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { composition.getTitle(), composition.getAuthor(),
						new java.sql.Timestamp(composition.getCreated().getTime()), composition.getLength(), 
						composition.isTrue(), composition.getComposition() },
				Integer.class);

		return id;
	}

	@Override
	public Composition getComposition(Integer id) throws CompositionNotFoundException {

		System.out.println("Searching for composition with the id: " + id);
		String SQL = "call GetCompositionById(?)";
		List<Composition> compositions = jdbcTemplateObject.query(SQL, new Object[] { id }, new CompositionMapper());
		if (compositions.size() != 1)
			throw new CompositionNotFoundException("Could not find composition with id '" + id + "'");
		return compositions.get(0);

	}

	public List<Composition> getCompositionsByTitle(String title) {
		title = title.replaceAll("^\"|\"$", "");
		System.out.println("Searching for compositions with the title: " + title);
		String SQL = "call GetCompositionByTitle(?)";
		List<Composition> compositions = jdbcTemplateObject.query(SQL, new Object[] { title }, new CompositionMapper());
		return compositions;
	}

	public List<Composition> getCompositions(String title, String author, Integer lengthMin, Integer lengthMax,
			String createdMin, String createdMax) {
		// title = title.replaceAll("^\"|\"$", "");
		System.out.println("Searching for compositions with the title: " + title + ", author: " + author
				+ ", lengthMin: " + lengthMin + ", lengthMax: " + lengthMax + ", createdMin: " + createdMin
				+ ", createdMax: " + createdMax);

		if (createdMin != null && createdMin.equals(""))
			createdMin = null;
		if (createdMax != null && createdMax.equals(""))
			createdMax = null;

		String SQL = "call GetCompositionByAttributes(?, ?, ?, ?, ?, ?)";
		List<Composition> compositions = jdbcTemplateObject.query(SQL,
				new Object[] { title, author, lengthMin, lengthMax, createdMin, createdMax }, new CompositionMapper());
		return compositions;
	}

}
