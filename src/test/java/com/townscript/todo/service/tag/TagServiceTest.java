package test.java.com.townscript.todo.service.tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import main.java.com.townscript.todo.dao.JdbcTemplateFactory;
import main.java.com.townscript.todo.model.Tag;
import main.java.com.townscript.todo.model.TagRowMapper;
import main.java.com.townscript.todo.model.User;
import main.java.com.townscript.todo.service.tag.TagService;
import main.java.com.townscript.todo.service.tag.TagServiceImpl;
import main.java.com.townscript.todo.service.task.TaskService;
import main.java.com.townscript.todo.service.task.TaskServiceImpl;
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

public class TagServiceTest {
	TagService tagService = new TagServiceImpl();
	UserService userService = new UserServiceImpl();
	TaskService taskService = new TaskServiceImpl();
	
	@Before
	public void createEnvironment()
	{
		String sql = "DELETE FROM TAGS";
		
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql);
		
		System.out.println("Set Up Environment");
	}
	
	@After
	public void clearEnvironment()
	{
		String sql = "DELETE FROM TAGS";
		
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql);
		
		System.out.println("Clear Environment");
	}
	
	@Test
	public void testAddTag(){
		Tag tag = new Tag();
		tag.setTagName("skyfall");
		int tagid = tagService.addTag(tag);
		String sql = "SELECT * from TAGS WHERE ID = "+tagid;
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		List<Tag> tagsList = jdbcTemplate.query(sql, new TagRowMapper());
		Assert.assertEquals("skyfall", tagsList.get(0).getTagName());
	}
	
	@Test
	public void testDeleteTag(){
		final String sql = "insert into TAGS(ID,TAG_NAME,TASKIDS) VALUES (0, 'testjames','1')";;
		 
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		  new PreparedStatementCreator() {
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			      return connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
			    }
			  }, keyHolder);

		int tagid  =  keyHolder.getKey().intValue();
		tagService.deleteTag(tagid);
		String sqlTwo = "SELECT * from TAGS WHERE ID = "+tagid;
		List<Tag> tagsList = jdbcTemplate.query(sqlTwo, new TagRowMapper());
		Assert.assertTrue(tagsList.isEmpty());
	}
	
	@Test
	public void testReadTag(){
		final String sql = "insert into TAGS(ID,TAG_NAME,TASKIDS) VALUES (0, 'testjames','1')";;
		 
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		  new PreparedStatementCreator() {
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			      return connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
			    }
			  }, keyHolder);

		int tagid  =  keyHolder.getKey().intValue();
		tagService.readTag(tagid);
		String sqlTwo = "SELECT * from TAGS WHERE ID = "+tagid;
		List<Tag> tagsList = jdbcTemplate.query(sqlTwo, new TagRowMapper());
		Assert.assertEquals("testjames", tagsList.get(0).getTagName());
		Assert.assertEquals("1", tagsList.get(0).getTaskids());
	}
	
	@Test
	public void testChangeTagName(){
		final String sql = "insert into TAGS(ID,TAG_NAME,TASKIDS) VALUES (0, 'testjames','1')";
		 
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		  new PreparedStatementCreator() {
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			      return connection.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
			    }
			  }, keyHolder);

		int tagid  =  keyHolder.getKey().intValue();
		tagService.changeTagName(tagid, "testbond");
		String sqlTwo = "SELECT * from TAGS WHERE ID = "+tagid;
		List<Tag> tagsList = jdbcTemplate.query(sqlTwo, new TagRowMapper());
		Assert.assertEquals("testbond", tagsList.get(0).getTagName());
	}
	
	@Test
	public void testAddExistingTagtoTask(){
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
		final String sqlTag = "insert into TAGS(ID,TAG_NAME,TASKIDS) VALUES (0, 'testjames','1')";
		KeyHolder keyHolderTag = new GeneratedKeyHolder();
		jdbcTemplate.update(
		  new PreparedStatementCreator() {
			    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			      return connection.prepareStatement(sqlTag,  Statement.RETURN_GENERATED_KEYS);
			    }
			  }, keyHolderTag);

		int tagid  =  keyHolderTag.getKey().intValue();
		String sqlTwo = "SELECT * from TAGS WHERE ID = "+tagid;
		List<Tag> tagsList = jdbcTemplate.query(sqlTwo, new TagRowMapper());
		tagService.addExistingTagtoTask(tagsList.get(0), taskid); //add tag to the task
		Assert.assertEquals("1, "+ Integer.toString(taskid),tagsList.get(0).getTaskids());
		String sqlThree = "DELETE FROM TASKS";
		String sqlFour = "DELETE FROM USER";
		jdbcTemplate.update(sqlThree);
		jdbcTemplate.update(sqlFour);
	}
}
