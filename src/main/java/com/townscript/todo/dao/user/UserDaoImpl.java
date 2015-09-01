package main.java.com.townscript.todo.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import main.java.com.townscript.todo.dao.JdbcTemplateFactory;
import main.java.com.townscript.todo.model.User;
import main.java.com.townscript.todo.model.UserRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class UserDaoImpl implements UserDao {

	@Override
	public int addUser(final User user) {
		final String sql = "insert into USER(ID,USERNAME,PASSWORD,FIRSTNAME,LASTNAME) VALUES (" + user.getId()+ ", '" + user.getUsername() +"', '"+ user.getPassword()+"', '"+ user.getFirstname()+"', '"+user.getLastname()+"')";
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
	public void deleteUser(int userId) {
		String sql = "delete USER where ID = ?";
		Object[] params = {userId};
		int[] types = {Types.INTEGER};
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql,params,types);
	}

	@Override
	public void updatePassword(int userid, String password) {
		String sql = "update USER set PASSWORD = ? where ID = ?";
		Object[] params = {password,userid};
		int[] types = {Types.INTEGER};
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql,params,types);
		
	}
	@Override
	public boolean isAuthenticUser(String username, String password) {
		String sql = "SELECT * FROM USER " +
				"WHERE USERNAME = '"+ username+"' AND PASSWORD = '"+ password + "'";
		 
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();

		List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
		if(users == null || users.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
	@Override
	public User readUser(int userId) {
		String sql = "SELECT * FROM USER " +
				"WHERE ID = "+ userId;
	 
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
	 
		List<User> usersList = jdbcTemplate.query(sql, new UserRowMapper());
		
		if(usersList == null || usersList.isEmpty()){
			return null;
		}
		else{
			return usersList.get(0);
		}
	}

	@Override
	public boolean checkUserExist(String username) {
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		
		String sql = "SELECT * FROM USER WHERE USERNAME = "+username;
		List<User> usersList;
		try {
			usersList = jdbcTemplate.query(sql, new UserRowMapper());
		} 
		catch (Exception e) {
			return false;
		}
		if(usersList == null || usersList.isEmpty()) {
			return false;
		}
		return true;
	}


}
