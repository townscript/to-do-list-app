package main.java.com.townscript.todo.dao.user;

import main.java.com.townscript.todo.model.User;
public interface UserDao {

	public int addUser(User user);
	public void deleteUser(int userid);
	public void updateUser(User user);
	boolean isAuthenticUser(String username, String password);
	User readUser(int userId);
	
	

}
