package uk.co.methodical.database;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import uk.co.methodical.Composition;

public class CompositionJDBCTemplate implements CompositionDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	private static CompositionJDBCTemplate self = null;

	public static CompositionJDBCTemplate instance() {

		if (self == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			self = (CompositionJDBCTemplate) context.getBean("compositionJDBCTemplate");

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
		String SQL = "call AddComposition(?, ?, ?)";

		Integer id = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { composition.getTitle(), composition.getAuthor(), composition.getComposition() },
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

}
