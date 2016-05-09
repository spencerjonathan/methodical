package uk.co.methodical.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import uk.co.methodical.Composition;

public class CompositionMapper implements RowMapper<Composition> {

	public Composition mapRow(ResultSet rs, int rowNum) throws SQLException {
		Composition composition = new Composition();
		composition.setId(rs.getInt("id"));
		composition.setTitle(rs.getString("title"));
		composition.setAuthor(rs.getString("author"));
		//composition.setCreated(rs.getDate("created"));
		composition.setComposition(rs.getString("composition"));

		return composition;
	}

}