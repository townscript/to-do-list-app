package test.java.com.townscript.todo.service.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import main.java.com.townscript.todo.dao.JdbcTemplateFactory;
import main.java.com.townscript.todo.model.Category;
import main.java.com.townscript.todo.model.CategoryRowMapper;
import main.java.com.townscript.todo.model.User;
import main.java.com.townscript.todo.service.category.CategoryService;
import main.java.com.townscript.todo.service.category.CategoryServiceImpl;
import main.java.com.townscript.todo.service.user.UserService;
import main.java.com.townscript.todo.service.user.UserServiceImpl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class CategoryServiceTest {

	UserService userService = new UserServiceImpl();
	CategoryService categoryService = new CategoryServiceImpl();
	
	@Before
	public void createEnvironment()
	{
		String sql = "DELETE FROM CATEGORIES";
		
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql);
		
		System.out.println("Set Up Environment");
	}
	
	@After
	public void clearEnvironment()
	{
		String sql = "DELETE FROM CATEGORIES";
		
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql);
		
		System.out.println("Clear Environment");
	}
	
	@Test
	public void testAddCategory(){
		Category category = new Category();
		category.setCategoryName("007");
		int categoryid = categoryService.addCategory(category);
		String sql = "SELECT * from CATEGORIES WHERE ID = "+ categoryid;
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		List<Category> categoriesList = jdbcTemplate.query(sql, new CategoryRowMapper());
		Assert.assertEquals("007", categoriesList.get(0).getCategoryName());
	}
	
	@Test
	public void testDeleteCategory(){
		final String sql = "insert into CATEGORIES(ID,CATEGORY_NAME,TASKIDS) VALUES (0,'007','1')";
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		  new PreparedStatementCreator() {
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			      return connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			    }
			  }, keyHolder);

		int categoryid =  keyHolder.getKey().intValue();
		categoryService.deleteCategory(categoryid);
		String sqlTwo= "SELECT * from CATEGORIES WHERE ID = "+ categoryid;
		List<Category> categoriesList = jdbcTemplate.query(sqlTwo, new CategoryRowMapper());
		Assert.assertTrue(categoriesList.isEmpty());
	}
	
	@Test
	public void testReadCategory(){
		final String sql = "insert into CATEGORIES(ID,CATEGORY_NAME,TASKIDS) VALUES (0,'007','1')";
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		  new PreparedStatementCreator() {
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			      return connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			    }
			  }, keyHolder);

		int categoryid =  keyHolder.getKey().intValue();
		Category category = categoryService.readCategory(categoryid);
		Assert.assertEquals(categoryid, category.getId());
		Assert.assertEquals("007",category.getCategoryName());
		Assert.assertEquals("1",category.getTaskids());
	}
	
	@Test
	public void testChangeCategoryName(){
		final String sql = "insert into CATEGORIES(ID,CATEGORY_NAME,TASKIDS) VALUES (0,'007','1')";
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		  new PreparedStatementCreator() {
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			      return connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			    }
			  }, keyHolder);

		int categoryid =  keyHolder.getKey().intValue();
		String newCategoryName = "008";
		categoryService.changeCategoryName(categoryid, newCategoryName);
		String sqlTwo= "SELECT * from CATEGORIES WHERE ID = "+ categoryid;
		List<Category> categoriesList = jdbcTemplate.query(sqlTwo, new CategoryRowMapper());
		Assert.assertEquals("008", categoriesList.get(0).getCategoryName());
	}
	
	@Test
	public void testAddExistingCategoryToTask(){
		User user = new User();
		user.setUsername("jamesbond");
		user.setPassword("ionicbond");
		user.setFirstname("James");
		user.setLastname("Bond");
		int userId = userService.registerUser(user);
		final String sql = "INSERT INTO TASKS(ID,TASK_NAME,USER_ID,MARK,SUBTASK,PARENT_ID,SEQUENCE_NUMBER) VALUES (0,'test-task task',"+userId+",false,false,-1,0)";
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		  new PreparedStatementCreator() {
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			      return connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			    }
			  }, keyHolder);
		int taskid = keyHolder.getKey().intValue();
		final String sqlTwo = "insert into CATEGORIES(ID,CATEGORY_NAME,TASKIDS) VALUES (0,'007','1')";
		KeyHolder keyHolderTwo = new GeneratedKeyHolder();
		jdbcTemplate.update(
		  new PreparedStatementCreator() {
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			      return connection.prepareStatement(sqlTwo,Statement.RETURN_GENERATED_KEYS);
			    }
			  }, keyHolderTwo);

		int categoryid =  keyHolderTwo.getKey().intValue();
		String sqlThree = "SELECT * FROM CATEGORIES WHERE ID = "+categoryid;
		List<Category> categoriesList = jdbcTemplate.query(sqlThree, new CategoryRowMapper());
		categoryService.addExistingCategorytoTask(categoriesList.get(0), taskid);
		Assert.assertEquals("1, "+Integer.toString(taskid), categoriesList.get(0).getTaskids());
		String sqlFour = "DELETE FROM TASKS";
		String sqlFive = "DELETE FROM USER";
		jdbcTemplate.update(sqlFour);
		jdbcTemplate.update(sqlFive);
	}
}
