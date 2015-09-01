package main.java.com.townscript.todo.dao.user;

import main.java.com.townscript.todo.model.User;
public interface UserDao {

	public int addUser(User user);
	public boolean checkUserExist(String username);
	public void deleteUser(int userid);
	public void updatePassword(int userid,String password);
	boolean isAuthenticUser(String username, String password);
	User readUser(int userId);
	
	

}
