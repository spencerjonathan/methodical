package uk.co.methodical.database;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class TowerJDBCTemplate implements TowerDAO {

	private static TowerJDBCTemplate self = null;
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	public static TowerJDBCTemplate instance() {

		if (self == null) {
			ApplicationContext context = new ClassPathXmlApplicationContext(
					"Beans.xml");

			self = (TowerJDBCTemplate) context.getBean("towerJDBCTemplate");

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
	public void addTower(String city, String county, String country, String designation, Integer bells, String notes) {
		String SQL = "call AddTower(?, ?, ?, ?, ?, ?)";
		jdbcTemplateObject.update(SQL, new Object[] { city, county, country, designation, bells, notes });
		
	}
}
