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
		composition.setAuthor(rs.getInt("author"));
		composition.setCreated(new java.util.Date(rs.getTimestamp("created").getTime()));
		composition.setLength(rs.getInt("length"));
		composition.setTrue(rs.getBoolean("isTrue"));
		composition.setComposition(rs.getString("composition"));

		return composition;
	}

}