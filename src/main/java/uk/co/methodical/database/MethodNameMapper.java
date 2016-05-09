package uk.co.methodical.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.jdbc.core.RowMapper;

import uk.co.methodical.Call;
import uk.co.methodical.Method;

public class MethodNameMapper implements RowMapper<String> {

	public String mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		return rs.getString("name");

	}
}