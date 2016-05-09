package uk.co.methodical.database;

import javax.sql.DataSource;

public interface TowerDAO {
	/**
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);
	
	void addTower(String city, String county, String country, String designation, Integer bells, String notes);
	
}
