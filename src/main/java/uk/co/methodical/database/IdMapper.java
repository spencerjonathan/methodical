package uk.co.methodical.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IdMapper implements RowMapper<Integer> {
	public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Integer id = rs.getInt("id");
		return id;
	}
}