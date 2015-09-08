package com.townscript.todo.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import com.townscript.todo.dao.JdbcTemplateFactory;
import com.townscript.todo.model.User;
import com.townscript.todo.model.UserRowMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


public class UserDaoImpl implements UserDao {

	@Override
	public int addUser(final User user) {
		
		// Comment-1: For easily readibility, below line should be broken into multiple lines
		// Comment-2: Table names and Column names are parameters used multiple times in this class, 
		// they should be defined at instance level as - Private Static Final String
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
		String sql = "delete from USER where ID = "+userId;
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql);
	}

	@Override
	public void updateUser(User user) {
		String sql = "update USER set USERNAME = ?,PASSWORD = ?,FIRSTNAME = ?,LASTNAME = ? where ID = ?";
		Object[] params = {user.getUsername(),user.getPassword(),user.getFirstname(),user.getLastname(),user.getId()};
		int[] types = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
		jdbcTemplate.update(sql,params,types);
		
	}
	@Override
	public boolean isAuthenticUser(String username, String password) {
		String sql = "SELECT * FROM USER " + "WHERE USERNAME = '"+ username+"' AND PASSWORD = '"+ password + "'";
		 
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
                
                // If our aim is to just check number of user, why should we load whole object in memory? 
                // a count query is enough. More memory & performance optimised solution :)
		List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
		
		// Do we really need Else here? :)
		if(users == null || users.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
	@Override
	public User readUser(int userId) {
		String sql = "SELECT * FROM USER " + "WHERE ID = "+ userId;
	 
		JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();
	 
		List<User> usersList = jdbcTemplate.query(sql, new UserRowMapper());
		
		if(usersList == null || usersList.isEmpty()){
			return null;
		}
		else{
			return usersList.get(0);
		}
	}


}
