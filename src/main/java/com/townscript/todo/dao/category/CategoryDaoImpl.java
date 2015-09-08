package com.townscript.todo.dao.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import com.townscript.todo.dao.JdbcTemplateFactory;
import com.townscript.todo.model.Category;
import com.townscript.todo.model.CategoryRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class CategoryDaoImpl implements CategoryDao{

	@Override
	public int addCategory(Category category) {
		final String sql = "insert into CATEGORIES(ID,CATEGORY_NAME,TASKIDS) VALUES (" + category.getId()+ ", '"+category.getCategoryName()+ "', '"+category.getTaskids()+"')";
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
	public void removeCategory(int categoryid) {
		String sql = "delete from CATEGORIES where ID = "+categoryid;
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql);
		
	}

	@Override
	public Category readCategory(int categoryid) {
		String sql = "SELECT * FROM CATEGORIES " + "WHERE ID = "+ categoryid;
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		List<Category> categoryList = jdbcTemplate.query(sql, new CategoryRowMapper());
		if(categoryList == null || categoryList.isEmpty()){
			return null;
		}
		else{
			return categoryList.get(0);
		}
	}

	@Override
	public void updateCategory(Category category) {
		String sql = "UPDATE CATEGORIES SET CATEGORY_NAME = ?, TASKIDS = ? " + "WHERE ID = ?" ;
		Object[] params = { category.getCategoryName(), category.getTaskids(), category.getId() };
		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.INTEGER};
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql, params, types);
	}

	@Override
	public Category getCategoryofTask(int taskid) {
		String taskidString = Integer.toString(taskid);
		String sql = "SELECT * FROM CATEGORIES " + "WHERE TASKIDS LIKE '%"+taskidString+"%' ";
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		List<Category> categoryList = jdbcTemplate.query(sql, new CategoryRowMapper());
		
		if(categoryList == null || categoryList.isEmpty()){
			return null;
		}
		else{
			return categoryList.get(0);
		}
	}

}
