package main.java.com.townscript.todo.service.user;

import main.java.com.townscript.todo.model.User;

public interface UserService {
	public int registerUser(User user);
	public boolean authenticateUser(String username,String password) throws Exception;
	public void changePassword(int userid, String newPassword);
	public User getUserInfo(int userid);
	public void deleteUser(int userid);
}
