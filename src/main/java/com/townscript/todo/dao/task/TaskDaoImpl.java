package main.java.com.townscript.todo.dao.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import main.java.com.townscript.todo.dao.JdbcTemplateFactory;
import main.java.com.townscript.todo.model.Task;
import main.java.com.townscript.todo.model.TaskRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
public class TaskDaoImpl implements TaskDao {

	@Override
	public int addTask(Task task) {
		final String sql = "INSERT INTO TASKS(ID,TAG_NAME,USER_ID,TAGIDS,CATEGORY_ID,MARK,SUBTASK,PARENT_ID,SEQUENCE_NUMBER) VALUES (" + task.getId()+ "', '"+task.getTaskName()+ "', '"+task.getUserid()+ "', '"+task.getagids()+ "', '"+task.getCategoryid()+ "', '"+task.isMark()+ "', '"+task.isSubtask()+ "', '"+task.getParentid()+ "', '"+task.getSequenceNumber()+"')";
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
	public void markTaskDone(int taskid) {
		String sql = "UPDATE TASKS SET MARK = ? where ID = ?";
		Object[] params = {1,taskid};
		int[] types = {Types.BOOLEAN,Types.INTEGER};
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql,params,types);
	}

	@Override
	public Task readTask(int taskid) {
		String sql = "SELECT * FROM TASKS " +
				"WHERE ID = "+ taskid;
	 
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
	 
		List<Task> taskList = jdbcTemplate.query(sql, new TaskRowMapper());
		
		if(taskList == null || taskList.isEmpty()){
			return null;
		}
		else{
			return taskList.get(0);
		}
	}

	@Override
	public void addDefaultTasks() {
		Task defaultTask1 = new Task();
		Task defaultTask2 = new Task();
		defaultTask1.setTaskName("Buy groceries today");
		defaultTask2.setTaskName("Write code tomorrow");
		
	}

	@Override
	public String getTagsList(int taskid) {
		String sql = "SELECT TAGIDS FROM TASKS " + "WHERE ID = "+ taskid;
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
	 
		String tagList = jdbcTemplate.queryForObject(sql, String.class);
		if(tagList == null || tagList.isEmpty()){
			return null;
		}
		else{
			return tagList;
		}
	}

	@Override
	public int getCategory(int taskid) {
		String sql = "SELECT CATEGORY_ID FROM TASKS " + "WHERE ID = "+ taskid;
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		int categoryid = jdbcTemplate.queryForObject(sql,Integer.class);
		return categoryid;
	}

	@Override
	public void makeSubtaskTask(int taskid) {
		String sql = "UPDATE TASKS SET SUBTASK = ?, PARENT_ID = ?  where ID = ?";
		Object[] params = {1,-1,taskid};
		int[] types = {Types.BOOLEAN,Types.INTEGER,Types.INTEGER};
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql,params,types);
		
	}

	@Override
	public void makeTaskSubtask(int taskid, int parentid) {
		String sql = "UPDATE TASKS SET SUBTASK = ?, PARENT_ID = ?  where ID = ?";
		Object[] params = {0,parentid,taskid};
		int[] types = {Types.BOOLEAN,Types.INTEGER,Types.INTEGER};
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql,params,types);
	}

}
