package main.java.com.townscript.todo.dao.user;

import main.java.com.townscript.todo.model.User;
public interface UserDao {

	public int addUser(User user);
	public void deleteUser(int userid);
	public void updatePassword(int userid,String password);
	boolean isAuthenticUser(String userEmail, String userPassword);
	User readUser(int userId);
	
	

}
