package main.java.com.townscript.todo.dao.tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import main.java.com.townscript.todo.dao.JdbcTemplateFactory;
import main.java.com.townscript.todo.model.Tag;
import main.java.com.townscript.todo.model.TagRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class TagDaoImpl implements TagDao{

	@Override
	public int addTag(Tag tag) {
		final String sql = "insert into TAGS(ID,TAG_NAME) VALUES (" + tag.getId()+ "', '"+tag.getTagName()+"')";
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		  new PreparedStatementCreator() {
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			      return connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			    }
			  }, keyHolder);

		return keyHolder.getKey().intValue();
		
	}

	@Override
	public void removeTag(int tagid) {
		String sql = "delete TAGS where ID = ?";
		Object[] params = {tagid};
		int[] types = {Types.INTEGER};
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql,params,types);
		
	}

	@Override
	public Tag readTag(int tagid) {
		String sql = "SELECT * FROM TAGS " + "WHERE ID = "+ tagid;
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
	 
		List<Tag> tagList = jdbcTemplate.query(sql, new TagRowMapper());
		
		if(tagList == null || tagList.isEmpty()){
			return null;
		}
		else{
			return tagList.get(0);
		}
		
	}

	@Override
	public void updateTagName(int tagid, String newTagName) {
		String sql = "UPDATE TAGS SET TAG_NAME = ? where ID = ?";
		Object[] params = {newTagName,tagid};
		int[] types = {Types.VARCHAR,Types.INTEGER};
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql,params,types);
		
	}

}
